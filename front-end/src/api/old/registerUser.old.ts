// ...existing code...
import { RegisterUserFormData, buildUserPayload } from '@/schemas/registerUserSchema';
import { Paths, File } from 'expo-file-system';

const UPLOAD_URL = 'https://harppia-endpoints.onrender.com/v1/users/register';
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

// Helper: cria arquivo JSON temporário e retorna URI usando nova API
const createTempJsonFile = async (data: any): Promise<string> => {
  const jsonContent = JSON.stringify(data);
  const filename = `user_data_${Date.now()}.json`;
  
  console.log('📝 Criando arquivo JSON temporário:', filename);
  
  // Usa nova API File com Paths.cache
  const tempFile = new File(Paths.cache, filename);
  await tempFile.write(jsonContent);
  
  console.log('✅ Arquivo JSON criado:', jsonContent.length, 'bytes');
  console.log('   URI:', tempFile.uri);
  return tempFile.uri;
};

// Helper: deleta arquivo temporário usando nova API
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

// ESTRATÉGIA: enviar dois arquivos - imagem + JSON como arquivo
const uploadWithTwoFiles = async (
  imageUri: string,
  imageName: string,
  imageMime: string,
  userPayload: any
): Promise<any> => {
  console.log('🚀 Estratégia: dois arquivos (imagem + JSON)');
  
  let jsonFileUri: string | null = null;
  
  try {
    // 1. Cria arquivo JSON temporário
    jsonFileUri = await createTempJsonFile(userPayload);
    
    // 2. Monta FormData com dois descritores de arquivo
    const fd = new FormData();
    
    console.log('📷 Anexando imagem:', { uri: imageUri, name: imageName, type: imageMime });
    fd.append('profile_photo', {
      uri: imageUri,
      name: imageName,
      type: imageMime,
    } as any);
    
    console.log('📄 Anexando JSON:', { uri: jsonFileUri, name: 'user_data.json', type: 'application/json' });
    fd.append('user_data', {
      uri: jsonFileUri,
      name: 'user_data.json',
      type: 'application/json',
    } as any);
    
    logFormData(fd, 'FormData (dois arquivos)');
    
    console.log('📤 Enviando via fetch...');
    const res = await fetchWithTimeout(UPLOAD_URL, {
      method: 'POST',
      body: fd,
    }, REQUEST_TIMEOUT);
    
    console.log('✅ Resposta:', res.status);
    
    if (!res.ok) {
      const text = await res.text().catch(() => '');
      throw new Error(`HTTP ${res.status}: ${text}`);
    }
    
    return await res.json();
    
  } finally {
    // 3. Limpa arquivo temporário
    if (jsonFileUri) {
      await deleteTempFile(jsonFileUri);
    }
  }
};

// FALLBACK: FormData sem foto
const uploadWithoutPhoto = async (userPayload: any): Promise<any> => {
  console.log('📷 Sem foto - enviando apenas JSON como arquivo');
  
  let jsonFileUri: string | null = null;
  
  try {
    jsonFileUri = await createTempJsonFile(userPayload);
    
    const fd = new FormData();
    fd.append('profile_photo', '');
    
    console.log('📄 Anexando JSON:', { uri: jsonFileUri, name: 'user_data.json', type: 'application/json' });
    fd.append('user_data', {
      uri: jsonFileUri,
      name: 'user_data.json',
      type: 'application/json',
    } as any);
    
    logFormData(fd, 'FormData (sem foto)');
    
    console.log('📤 Enviando via fetch...');
    const res = await fetchWithTimeout(UPLOAD_URL, {
      method: 'POST',
      body: fd,
    }, REQUEST_TIMEOUT);
    
    console.log('✅ Resposta:', res.status);
    
    if (!res.ok) {
      const text = await res.text().catch(() => '');
      throw new Error(`HTTP ${res.status}: ${text}`);
    }
    
    return await res.json();
    
  } finally {
    if (jsonFileUri) {
      await deleteTempFile(jsonFileUri);
    }
  }
};

// Utility: send only user_data as JSON (no file, no Blob conversion)
export const registerUserDry = async (data: RegisterUserFormData): Promise<any> => {
  const userPayload = buildUserPayload(data);

  console.log('📤 Enviando JSON puro (application/json)');
  const res = await fetchWithTimeout(
    UPLOAD_URL,
    {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ user_data: userPayload }),
    },
    REQUEST_TIMEOUT
  );

  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(`HTTP ${res.status}: ${text}`);
  }
  return res.json();
};

// Função principal
export const registerUser = async (data: RegisterUserFormData): Promise<any> => {
  const { arquivo } = data;
  const userPayload = buildUserPayload(data);

  console.log('\n>>> registerUser - iniciando <<<');
  console.log('📋 Dados do usuário (normalizado):');
  console.log(JSON.stringify(userPayload, null, 2));

  // SEM FOTO: usa FormData com Blob
  if (!arquivo?.caminho) {
    return await uploadWithoutPhoto(userPayload);
  }

  // COM FOTO: usa uploadAsync (nativo, mais confiável)
  const fileUri = arquivo.caminho;
  const mime = arquivo.mimeType || 'image/jpeg';
  const filename = arquivo.bucketArquivo?.nome || fileUri.split('/').pop() || 'photo.jpg';

  console.log('📷 Arquivo detectado:', filename, mime);
  console.log('📄 user_data size:', JSON.stringify(userPayload).length, 'bytes');

  return await uploadWithTwoFiles(fileUri, filename, mime, userPayload);
};

// Utility: multipart sem foto (debug)
export const registerUserDryAsBlob = async (data: RegisterUserFormData): Promise<any> => {
  const userPayload = buildUserPayload(data);
  return await uploadWithoutPhoto(userPayload);
};
// ...existing code...