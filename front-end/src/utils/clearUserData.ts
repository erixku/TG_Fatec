import { clearUser } from '../services/localCache';

/**
 * Limpa apenas os dados do usuário do cache,
 * mantendo igrejas, ministérios e membros intactos
 */
export const clearUserData = async (): Promise<void> => {
  try {
    await clearUser();
    console.log('✅ Dados do usuário removidos com sucesso!');
    console.log('ℹ️ Igrejas, ministérios e membros foram mantidos.');
  } catch (error) {
    console.error('❌ Erro ao remover dados do usuário:', error);
    throw error;
  }
};
