import { RegisterMinisteryFormData } from '@/schemas/registerMinisterySchema';
import { Paths, File } from 'expo-file-system';
import { getStoredTokens } from '../loginUser';

const UPLOAD_URL = 'https://harppia-endpoints.onrender.com/v1/ministry/create';
const REQUEST_TIMEOUT = 120000;

const logFormData = (form: FormData, label: string) => {
  console.log(`\n=== ${label} ===`);
  try {
    const parts = (form as any)._parts;
    if (parts && Array.isArray(parts)) {
      parts.forEach(([key, value]: [string, any], index: number) => {
        if (typeof value === 'object' && value !== null) {
          if (value.uri) {
            console.log(`  [${index}] ${key}:`, { uri: value.uri?.substring(0, 50) + '...', name: value.name, type: value.type });
          } else {
            console.log(`  [${index}] ${key}:`, typeof value === 'string' ? value.substring(0, 100) : value);
          }
        } else {
          console.log(`  [${index}] ${key}:`, typeof value === 'string' ? value.substring(0, 100) : value);
        }
      });
    }
  } catch (err) {
    console.log('  (Erro ao inspecionar FormData)');
  }
  console.log('===\n');
};

const fetchWithTimeout = async (url: string, options: RequestInit, timeout: number): Promise<Response> => {
  const controller = new AbortController();
  const timeoutId = setTimeout(() => {
    console.warn('⏱️ Timeout após', timeout, 'ms');
    controller.abort();
  }, timeout);

  try {
    const response = await fetch(url, {
      ...options,
      signal: controller.signal,
    });
    clearTimeout(timeoutId);
    return response;
  } catch (error) {
    clearTimeout(timeoutId);
    throw error;
  }
};

const createTempJsonFile = async (data: any): Promise<string> => {
  const jsonContent = JSON.stringify(data);
  const filename = `ministry_data_${Date.now()}.json`;
  
  console.log('📝 Criando arquivo JSON temporário:', filename);
  
  const tempFile = new File(Paths.cache, filename);
  await tempFile.write(jsonContent);
  
  console.log('✅ Arquivo JSON criado:', jsonContent.length, 'bytes');
  console.log('   URI:', tempFile.uri);
  return tempFile.uri;
};

const deleteTempFile = async (fileUri: string): Promise<void> => {
  try {
    const filename = fileUri.split('/').pop();
    if (!filename) return;
    
    const tempFile = new File(Paths.cache, filename);
    
    if (tempFile.exists) {
      await tempFile.delete();
      console.log('🗑️ Arquivo temporário deletado:', filename);
    }
  } catch (err) {
    console.warn('⚠️ Erro ao deletar arquivo temporário:', err);
  }
};

const executeWithTokenRefresh = async (apiCall: () => Promise<Response>): Promise<Response> => {
  try {
    return await apiCall();
  } catch (error: any) {
    // Se for erro 401/403, tenta renovar o token e repetir a chamada
    if (error.response?.status === 401 || error.response?.status === 403) {
      console.log('🔄 Token expirado, tentando renovar...');
      const { refreshAccessToken } = await import('../loginUser');
      const newTokens = await refreshAccessToken();
      
      if (newTokens) {
        console.log('✅ Token renovado, tentando novamente...');
        return await apiCall();
      } else {
        throw new Error('Não foi possível renovar o token. Faça login novamente.');
      }
    }
    throw error;
  }
};

export const registerMinistery = async (
  formData: RegisterMinisteryFormData,
  userId: string,
  churchId: string
): Promise<any> => {
  console.log('⛪ Iniciando cadastro de ministério...');
  console.log('📋 Dados do formulário:', formData);
  console.log('👤 ID do criador:', userId);
  console.log('🏛️ ID da igreja:', churchId);

  let jsonFileUri: string | null = null;

  try {
    // Monta o payload no formato esperado pela API
    const ministryPayload = {
      idCriador: userId,
      idIgreja: churchId,
      nome: formData.nome,
      descricao: formData.descricao,
    };

    console.log('📤 Payload do ministério:', JSON.stringify(ministryPayload, null, 2));

    // Cria arquivo JSON temporário
    jsonFileUri = await createTempJsonFile(ministryPayload);

    // Monta FormData
    const fd = new FormData();

    // Adiciona arquivo JSON com nome específico 'ministry_data'
    fd.append('ministry_data', {
      uri: jsonFileUri,
      name: 'ministry_data.json',
      type: 'application/json',
    } as any);

    // Adiciona imagem se existir com nome específico 'ministry_photo'
    if (formData.arquivo?.caminho) {
      const imageUri = formData.arquivo.caminho;
      const imageName = formData.arquivo.bucketArquivo?.nome || `ministry_${Date.now()}.jpg`;
      const imageMime = formData.arquivo.mimeType || 'image/jpeg';

      console.log('🖼️ Adicionando imagem do ministério:', { imageUri, imageName, imageMime });

      fd.append('ministry_photo', {
        uri: imageUri,
        name: imageName,
        type: imageMime,
      } as any);
    }

    logFormData(fd, 'FormData final para envio');

    // Recupera token de acesso
    console.log('🔐 Recuperando token de acesso do SecureStore...');
    const tokens = await getStoredTokens();
    console.log('📦 Tokens recuperados:', {
      hasAccessToken: !!tokens.accessToken,
      hasRefreshToken: !!tokens.refreshToken,
      hasUserId: !!tokens.userId,
      accessTokenLength: tokens.accessToken?.length || 0
    });
    
    if (!tokens.accessToken || tokens.accessToken === '') {
      throw new Error('Token de acesso não encontrado. Faça login novamente.');
    }

    console.log('🔑 Token de acesso recuperado:', tokens.accessToken.substring(0, 20) + '...');
    console.log('🌐 Enviando para:', UPLOAD_URL);
    
    const makeRequest = async () => {
      const currentTokens = await getStoredTokens();
      return await fetchWithTimeout(
        UPLOAD_URL,
        {
          method: 'POST',
          body: fd,
          headers: {
            'Accept': 'application/json',
            'Authorization': `Bearer ${currentTokens.accessToken}`,
          },
        },
        REQUEST_TIMEOUT
      );
    };

    const response = await executeWithTokenRefresh(makeRequest);

    console.log('📥 Status da resposta:', response.status, response.statusText);

    if (!response.ok) {
      const errorText = await response.text();
      console.error('❌ Erro na resposta:', errorText);
      throw new Error(`Erro ${response.status}: ${errorText || 'Falha ao cadastrar ministério'}`);
    }

    const responseData = await response.json();
    console.log('✅ Ministério cadastrado com sucesso!');
    console.log('📄 Resposta da API:', responseData);

    return responseData;

  } catch (error: any) {
    console.error('❌ Erro no cadastro do ministério:', error);
    throw error;
  } finally {
    if (jsonFileUri) {
      await deleteTempFile(jsonFileUri);
    }
  }
};
