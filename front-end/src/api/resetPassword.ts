import { loadCache, saveCache } from '@/services/localCache';

interface ResetPasswordData {
  email: string;
  newPassword: string;
}

/**
 * Atualiza a senha do usuário no cache local
 */
export async function resetPassword(data: ResetPasswordData): Promise<{ success: boolean }> {
  console.log('🔐 [resetPassword] Iniciando reset de senha para:', data.email);

  try {
    const cache = await loadCache();

    // Encontra o usuário pelo email
    const userIndex = cache.users.findIndex(u => u.email === data.email);

    if (userIndex === -1) {
      console.log('❌ [resetPassword] Usuário não encontrado');
      throw new Error('Usuário não encontrado');
    }

    // Atualiza a senha
    cache.users[userIndex].senha = data.newPassword;

    // Salva no cache
    await saveCache(cache);

    console.log('✅ [resetPassword] Senha atualizada com sucesso!');

    return { success: true };

  } catch (error: any) {
    console.error('❌ [resetPassword] Erro ao resetar senha:', error);
    throw error;
  }
}
