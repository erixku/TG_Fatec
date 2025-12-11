import axios from 'axios';
import * as SecureStore from 'expo-secure-store';

const LOGIN_URL = 'https://harppia-endpoints.onrender.com/v1/users/auth/login';
const AUTHENTICATE_URL = 'https://harppia-endpoints.onrender.com/v1/users/auth/authenticate';
const REFRESH_URL = 'https://harppia-endpoints.onrender.com/v1/users/auth/refresh';

const ACCESS_TOKEN_KEY = 'harppia_access_token';
const REFRESH_TOKEN_KEY = 'harppia_refresh_token';
const USER_ID_KEY = 'harppia_user_id';
const USER_EMAIL_KEY = 'harppia_user_email';

export interface LoginCredentials {
  email?: string;
  telefone?: string;
  cpf?: string;
  senha: string;
}

export interface LoginResponse {
  id: string;
  email: string;
  accessToken: string;
  refreshToken: string;
  systemRoles?: Array<{ authority: string }>;
  churchRoles?: any;
  idIgrejas?: string[];
}

export interface AuthenticateResponse {
  id: string;
  email: string;
  accessToken: string;
  refreshToken: string;
}

// Salva tokens no armazenamento seguro
export const saveTokens = async (data: LoginResponse): Promise<void> => {
  try {
    console.log('💾 Salvando tokens no SecureStore...');
    console.log('  - accessToken:', data.accessToken ? data.accessToken.substring(0, 20) + '...' : 'VAZIO');
    console.log('  - refreshToken:', data.refreshToken ? data.refreshToken.substring(0, 20) + '...' : 'VAZIO');
    console.log('  - id:', data.id || 'VAZIO');
    console.log('  - email:', data.email || 'VAZIO');
    
    await SecureStore.setItemAsync(ACCESS_TOKEN_KEY, String(data.accessToken || ''));
    await SecureStore.setItemAsync(REFRESH_TOKEN_KEY, String(data.refreshToken || ''));
    await SecureStore.setItemAsync(USER_ID_KEY, String(data.id || ''));
    await SecureStore.setItemAsync(USER_EMAIL_KEY, String(data.email || ''));
    console.log('✅ Tokens salvos com sucesso no SecureStore');
  } catch (error) {
    console.error('❌ Erro ao salvar tokens:', error);
    throw new Error('Não foi possível salvar as credenciais');
  }
};

// Recupera tokens do armazenamento
export const getStoredTokens = async (): Promise<{
  accessToken: string | null;
  refreshToken: string | null;
  userId: string | null;
  userEmail: string | null;
}> => {
  try {
    console.log('🔍 Recuperando tokens do SecureStore...');
    const accessToken = await SecureStore.getItemAsync(ACCESS_TOKEN_KEY);
    const refreshToken = await SecureStore.getItemAsync(REFRESH_TOKEN_KEY);
    const userId = await SecureStore.getItemAsync(USER_ID_KEY);
    const userEmail = await SecureStore.getItemAsync(USER_EMAIL_KEY);
    
    console.log('  - accessToken:', accessToken ? accessToken.substring(0, 20) + '... (length: ' + accessToken.length + ')' : 'NULL');
    console.log('  - refreshToken:', refreshToken ? refreshToken.substring(0, 20) + '... (length: ' + refreshToken.length + ')' : 'NULL');
    console.log('  - userId:', userId || 'NULL');
    console.log('  - userEmail:', userEmail || 'NULL');
    
    return { accessToken, refreshToken, userId, userEmail };
  } catch (error) {
    console.error('❌ Erro ao recuperar tokens:', error);
    return { accessToken: null, refreshToken: null, userId: null, userEmail: null };
  }
};

// Remove tokens (logout)
export const clearTokens = async (): Promise<void> => {
  try {
    await SecureStore.deleteItemAsync(ACCESS_TOKEN_KEY);
    await SecureStore.deleteItemAsync(REFRESH_TOKEN_KEY);
    await SecureStore.deleteItemAsync(USER_ID_KEY);
    await SecureStore.deleteItemAsync(USER_EMAIL_KEY);
    console.log('✅ Tokens removidos com sucesso');
  } catch (error) {
    console.error('❌ Erro ao remover tokens:', error);
  }
};

// Verifica se o usuário está autenticado (chama /authenticate)
export const checkAuthentication = async (): Promise<AuthenticateResponse | null> => {
  try {
    console.log('🔍 [checkAuthentication] Iniciando verificação...');
    
    const { accessToken, refreshToken } = await getStoredTokens();
    
    console.log('🔑 [checkAuthentication] Tokens recuperados:', {
      hasAccessToken: !!accessToken,
      hasRefreshToken: !!refreshToken,
      accessTokenLength: accessToken?.length,
      refreshTokenLength: refreshToken?.length
    });
    
    if (!accessToken || !refreshToken) {
      console.log('⚠️ [checkAuthentication] Tokens não encontrados');
      return null;
    }

    console.log('📤 [checkAuthentication] Enviando requisição para:', AUTHENTICATE_URL);
    
    const response = await axios.post(
      AUTHENTICATE_URL,
      {},
      {
        timeout: 15000,
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': `Bearer ${accessToken}`,
          'X-Refresh-Token': refreshToken,
        },
        maxRedirects: 0,
        validateStatus: (status) => status < 400,
      }
    );
    
    console.log('✅ [checkAuthentication] Resposta recebida:', {
      status: response.status,
      hasData: !!response.data,
      dataKeys: response.data ? Object.keys(response.data) : []
    });
    
    // Atualiza tokens se novos foram retornados
    if (response.data.accessToken && response.data.refreshToken) {
      await saveTokens(response.data);
      console.log('🔄 [checkAuthentication] Tokens atualizados');
    }
    
    return response.data;
  } catch (error: any) {
    console.error('❌ [checkAuthentication] Erro na verificação');
    
    if (error.response) {
      console.error('📋 [checkAuthentication] Resposta de erro:', {
        status: error.response.status,
        statusText: error.response.statusText,
        data: error.response.data
      });
      
      // Token expirado ou inválido
      if (error.response.status === 401 || error.response.status === 403) {
        console.log('🔓 [checkAuthentication] Sessão expirada, limpando tokens...');
        await clearTokens();
        return null;
      }
    } else if (error.request) {
      console.error('📡 [checkAuthentication] Sem resposta do servidor');
      console.error('Request:', error.request);
    } else {
      console.error('⚙️ [checkAuthentication] Erro ao configurar requisição:', error.message);
    }
    
    // Em caso de erro de rede, mantém tokens e retorna null
    return null;
  }
};

/**
 * Realiza login na API usando credenciais.
 * Apenas UM dos campos (email, telefone ou cpf) deve ser preenchido.
 * A senha é sempre obrigatória.
 */
export const loginUser = async (credentials: LoginCredentials): Promise<LoginResponse> => {
  try {
    // Validação: garantir que apenas um método de login está preenchido
    const filledFields = [credentials.email, credentials.telefone, credentials.cpf].filter(Boolean);
    
    if (filledFields.length === 0) {
      throw new Error('Informe seu e-mail, telefone ou CPF para fazer login.');
    }
    
    if (filledFields.length > 1) {
      throw new Error('Informe apenas um método de login (e-mail, telefone ou CPF).');
    }

    // Normalizar campos vazios para null
    const normalizedCredentials = {
      email: credentials.email || null,
      telefone: credentials.telefone || null,
      cpf: credentials.cpf || null,
      senha: credentials.senha
    };

    console.log('📤 Tentando login em:', LOGIN_URL);
    console.log('📋 Payload:', JSON.stringify(normalizedCredentials, null, 2));

    const response = await axios.post(LOGIN_URL, normalizedCredentials, {
      timeout: 30000,
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
      maxRedirects: 0,
      validateStatus: (status) => status < 400,
    });
    
    console.log('✅ Login bem-sucedido');
    console.log('📦 Resposta completa da API:', JSON.stringify(response.data, null, 2));
    
    // A API retorna dados do usuário em infUsrLogRVO, igrejas em igrUsrFazPrtRVO e tokens/roles no nível raiz
    const userInfo = response.data.infUsrLogRVO || {};
    const churchInfo = response.data.igrUsrFazPrtRVO || {};
    
    const userData = {
      id: userInfo.id,
      email: userInfo.login, // Campo 'login' contém o email
      accessToken: response.data.accessToken,
      refreshToken: response.data.refreshToken,
      systemRoles: response.data.systemRoles || [],
      churchRoles: response.data.churchRoles || null,
      idIgrejas: churchInfo.idIgrejas || []
    };
    
    console.log('📄 Dados montados para salvar:', {
      id: userData.id,
      email: userData.email,
      hasAccessToken: !!userData.accessToken,
      hasRefreshToken: !!userData.refreshToken,
      accessTokenLength: userData.accessToken?.length || 0,
      refreshTokenLength: userData.refreshToken?.length || 0,
      systemRoles: userData.systemRoles,
      churchRoles: userData.churchRoles,
      totalIgrejas: userData.idIgrejas.length
    });
    
    // Salva tokens automaticamente
    await saveTokens(userData);
    
    return userData;
  } catch (error: any) {
    console.error('❌ Erro na requisição de login');
    
    if (error.response) {
      console.error('Status:', error.response.status);
      console.error('Data:', error.response.data);
      
      if (error.response.status === 302 || error.response.status === 301) {
        console.error('🔄 Redirecionamento detectado para:', error.response.headers.location);
        throw new Error('A API está redirecionando a requisição. Verifique a URL ou autenticação necessária.');
      }
      
      const message = error.response.data?.message || 'Credenciais inválidas';
      throw new Error(message);
    } else if (error.request) {
      console.error('📡 Requisição enviada mas sem resposta');
      throw new Error('Não foi possível conectar ao servidor. Verifique sua conexão.');
    } else {
      console.error('⚙️ Erro ao configurar requisição:', error.message);
      throw new Error(error.message || 'Erro desconhecido ao fazer login');
    }
  }
};

// Renova o access token usando o refresh token
export const refreshAccessToken = async (): Promise<LoginResponse | null> => {
  try {
    console.log('🔄 Renovando access token...');
    
    const { userId, refreshToken } = await getStoredTokens();
    
    if (!userId || !refreshToken) {
      console.error('❌ userId ou refreshToken não encontrados');
      return null;
    }

    console.log('📤 Enviando requisição para:', REFRESH_URL);
    console.log('📋 Payload:', { userId: userId, rawRefreshToken: refreshToken.substring(0, 20) + '...' });

    const response = await axios.post(REFRESH_URL, {
      userId: userId,
      rawRefreshToken: refreshToken
    }, {
      timeout: 15000,
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
      validateStatus: (status) => status < 400,
    });

    console.log('✅ Token renovado com sucesso');
    console.log('📦 Resposta completa da API:', JSON.stringify(response.data, null, 2));
    
    // Extrai dados da resposta (mesmo formato do login)
    const userInfo = response.data.infUsrLogRVO || {};
    const churchInfo = response.data.igrUsrFazPrtRVO || {};
    
    const userData = {
      id: userInfo.id || userId, // Usa o userId atual se não vier na resposta
      email: userInfo.login || (await getStoredTokens()).userEmail || '', // Usa o email atual se não vier
      accessToken: response.data.accessToken,
      refreshToken: response.data.refreshToken,
      systemRoles: response.data.systemRoles || [],
      churchRoles: response.data.churchRoles || null,
      idIgrejas: churchInfo.idIgrejas || []
    };

    console.log('📋 Dados para salvar:', {
      id: userData.id,
      email: userData.email,
      hasAccessToken: !!userData.accessToken,
      hasRefreshToken: !!userData.refreshToken
    });

    // Salva os novos tokens
    await saveTokens(userData);
    console.log('💾 Novos tokens salvos no SecureStore');
    
    return userData;
  } catch (error: any) {
    console.error('❌ Erro ao renovar token');
    
    if (error.response) {
      console.error('Status:', error.response.status);
      console.error('Data:', error.response.data);
      
      // Se o refresh token expirou, fazer logout
      if (error.response.status === 401 || error.response.status === 403) {
        console.log('🔓 Refresh token expirado, limpando credenciais...');
        await clearTokens();
      }
    }
    
    return null;
  }
};

// Faz logout do usuário
export const logoutUser = async (): Promise<void> => {
  await clearTokens();
  console.log('👋 Logout realizado');
};
