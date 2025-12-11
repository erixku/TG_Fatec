// Versão com CACHE LOCAL - Substitui loginUser.ts
import { saveUser, saveAuth, getAuth, getUser, loadCache } from '../../services/localCache';

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

// Login usando cache local
export const loginUser = async (credentials: LoginCredentials): Promise<LoginResponse> => {
  try {
    console.log('🔐 [CACHE MODE] Fazendo login com cache local...');
    
    // Busca usuário existente no cache
    const cachedUser = await getUser();
    
    if (!cachedUser) {
      throw new Error('Usuário não encontrado. Registre-se primeiro!');
    }
    
    // Valida credenciais (simplificado - em produção, usar hash de senha)
    if (credentials.email && cachedUser.email !== credentials.email) {
      throw new Error('Email ou senha incorretos');
    }
    
    // Simula tokens JWT
    const accessToken = `fake-access-token-${Date.now()}`;
    const refreshToken = `fake-refresh-token-${Date.now()}`;
    
    // Salva auth no cache
    await saveAuth({
      accessToken,
      refreshToken,
      lastLogin: new Date().toISOString(),
    });

    console.log('✅ Login bem-sucedido (cache local)');
    console.log('👤 Usuário:', cachedUser.email);

    // Busca igrejas do usuário
    const cache = await loadCache();
    const userChurches = cache.churches.filter(c => 
      c.idCriador === cachedUser.id || c.idDono === cachedUser.id
    );

    return {
      id: cachedUser.id,
      email: cachedUser.email,
      accessToken,
      refreshToken,
      systemRoles: [],
      churchRoles: null,
      idIgrejas: userChurches.map(c => c.id)
    };
  } catch (error) {
    console.error('❌ Erro no login (cache):', error);
    throw error;
  }
};

// Retorna tokens salvos
export const getStoredTokens = async () => {
  const auth = await getAuth();
  const user = await getUser();
  
  return {
    accessToken: auth?.accessToken || null,
    refreshToken: auth?.refreshToken || null,
    userId: user?.id || null,
    userEmail: user?.email || null,
  };
};

// Refresh token (simulado)
export const refreshAccessToken = async (): Promise<string> => {
  console.log('🔄 [CACHE MODE] Simulando refresh de token...');
  
  const newAccessToken = `fake-access-token-${Date.now()}`;
  const newRefreshToken = `fake-refresh-token-${Date.now()}`;
  
  await saveAuth({
    accessToken: newAccessToken,
    refreshToken: newRefreshToken,
    lastLogin: new Date().toISOString(),
  });
  
  console.log('✅ Token atualizado (cache local)');
  return newAccessToken;
};

// Limpa auth
export const clearTokens = async () => {
  await saveAuth(null);
  console.log('🗑️ Tokens limpos');
};

// Logout (limpa auth e redireciona)
export const logoutUser = async (): Promise<void> => {
  try {
    console.log('👋 [CACHE MODE] Fazendo logout...');
    await saveAuth(null);
    console.log('✅ Logout realizado com sucesso');
  } catch (error) {
    console.error('❌ Erro ao fazer logout:', error);
    throw error;
  }
};

// Verifica autenticação
export const checkAuthentication = async (): Promise<boolean> => {
  try {
    const auth = await getAuth();
    const user = await getUser();
    
    const isAuthenticated = !!(auth?.accessToken && user?.id);
    console.log('🔍 [CACHE MODE] Verificando autenticação:', isAuthenticated);
    
    return isAuthenticated;
  } catch (error) {
    console.error('❌ Erro ao verificar autenticação:', error);
    return false;
  }
};
