// Versão com CACHE LOCAL - Substitui findUser.ts
import { getUser } from '../../services/localCache';

/**
 * Encontra um usuário no cache local usando uma chave (email, telefone ou CPF).
 * @param key A chave de busca (email, telefone ou CPF).
 * @returns Os dados do usuário em caso de sucesso, ou null se não encontrado.
 */
export const findUserByKey = async (key: string) => {
  console.log('🔍 [CACHE MODE] Buscando usuário com chave:', key);
  
  const user = await getUser();
  
  if (!user) {
    console.log('❌ Nenhum usuário encontrado no cache');
    return null;
  }
  
  // Verifica se a chave corresponde a algum campo do usuário
  const keyLowerCase = key.toLowerCase();
  const matches = 
    user.email.toLowerCase() === keyLowerCase ||
    user.telefone === key ||
    user.cpf === key;
  
  if (matches) {
    console.log('✅ Usuário encontrado:', user.email);
    return user;
  }
  
  console.log('❌ Chave não corresponde ao usuário no cache');
  return null;
};
