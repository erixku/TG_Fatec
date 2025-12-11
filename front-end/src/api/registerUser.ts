// Versão com CACHE LOCAL - Substitui registerUser.ts
import { saveUser, generateId } from '../services/localCache';
import { RegisterUserFormData } from '@/schemas/registerUserSchema';

export interface RegisterUserResponse {
  id: string;
  message: string;
}

// Registra usuário no cache local
export const registerUser = async (userData: RegisterUserFormData): Promise<RegisterUserResponse> => {
  try {
    console.log('📤 [CACHE MODE] Registrando usuário no cache local...');

    const userId = generateId();
    
    // Normaliza data se for Date
    const normalizeDate = (val: any): string => {
      if (!val) return '';
      if (typeof val === 'object' && 'toISOString' in val) {
        return val.toISOString().split('T')[0];
      }
      if (typeof val === 'string') {
        return val.split('T')[0];
      }
      return '';
    };
    const dataNascimento = normalizeDate(userData.dataNascimento);
    
    // Salva usuário no cache local
    await saveUser({
      id: userId,
      email: userData.email,
      nome: `${userData.nome} ${userData.sobrenome}`,
      nomeSocial: userData.nomeSocial ? `${userData.nomeSocial} ${userData.sobrenomeSocial}` : undefined,
      telefone: userData.telefone,
      cpf: userData.cpf,
      dataNascimento: dataNascimento as string,
      sexo: userData.sexo,
      senha: userData.senha, // Armazena senha (em produção, usar hash)
      foto: userData.arquivo?.caminho || undefined,
      endereco: {
        cep: userData.endereco.cep,
        uf: userData.endereco.uf,
        cidade: userData.endereco.cidade,
        bairro: userData.endereco.bairro,
        rua: userData.endereco.rua,
        numero: userData.endereco.numero || 'S/N',
        complemento: userData.endereco.complemento || undefined,
      },
      createdAt: new Date().toISOString(),
    });

    console.log('✅ Usuário registrado com sucesso no cache!');
    console.log('🆔 ID gerado:', userId);
    console.log('📧 Email:', userData.email);
    console.log('👤 Nome:', `${userData.nome} ${userData.sobrenome}`);

    return {
      id: userId,
      message: 'Usuário registrado com sucesso (cache local)',
    };
  } catch (error) {
    console.error('❌ Erro ao registrar usuário (cache):', error);
    throw error;
  }
};
