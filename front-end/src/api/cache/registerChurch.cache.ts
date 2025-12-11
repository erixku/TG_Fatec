// Versão com CACHE LOCAL - Substitui registerChurch.ts
import { addChurch, generateId, getUser } from '../../services/localCache';
import { RegisterChurchFormData } from '@/schemas/registerChurchSchema';

export interface RegisterChurchResponse {
  idIgreja: string;
  message: string;
}

// Registra igreja no cache local
export const registerChurch = async (
  churchData: RegisterChurchFormData
): Promise<RegisterChurchResponse> => {
  try {
    console.log('📤 [CACHE MODE] Registrando igreja no cache local...');

    const churchId = generateId();
    const user = await getUser();
    
    if (!user) {
      throw new Error('Usuário não encontrado. Faça login primeiro!');
    }

    // Salva igreja no cache local
    await addChurch({
      id: churchId,
      idCriador: user.id,
      idDono: user.id,
      cnpj: churchData.cnpj,
      nome: churchData.nome,
      denominacao: churchData.denominacao.toLowerCase(),
      outraDenominacao: churchData.outra_denominacao,
      foto: churchData.arquivo?.caminho || undefined,
      endereco: {
        cep: churchData.endereco.cep,
        uf: churchData.endereco.uf,
        cidade: churchData.endereco.cidade,
        bairro: churchData.endereco.bairro,
        logradouro: churchData.endereco.rua,
        numero: churchData.endereco.numero || 'S/N',
        complemento: churchData.endereco.complemento,
      },
      createdAt: new Date().toISOString(),
    });

    console.log('✅ Igreja registrada com sucesso no cache!');
    console.log('🆔 ID gerado:', churchId);
    console.log('⛪ Nome:', churchData.nome);
    console.log('📍 Cidade:', churchData.endereco.cidade);

    return {
      idIgreja: churchId,
      message: 'Igreja registrada com sucesso (cache local)',
    };
  } catch (error) {
    console.error('❌ Erro ao registrar igreja (cache):', error);
    throw error;
  }
};
