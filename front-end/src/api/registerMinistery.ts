// Versão com CACHE LOCAL - Substitui registerMinistery.ts
import { 
  addMinistry, 
  generateId, 
  generateMinistryCode,
  getUser, 
  getChurchById,
  addMinistryMember
} from '../services/localCache';
import { RegisterMinisteryFormData } from '@/schemas/registerMinisterySchema';

export interface RegisterMinisteryResponse {
  id: string;
  message: string;
}

// Estende o tipo para incluir idIgreja
type MinistryDataWithChurch = RegisterMinisteryFormData & { idIgreja: string };

// Registra ministério no cache local
export const registerMinistry = async (
  ministryData: MinistryDataWithChurch
): Promise<RegisterMinisteryResponse> => {
  try {
    console.log('📤 [CACHE MODE] Registrando ministério no cache local...');

    const ministryId = generateId();
    const user = await getUser();
    
    if (!user) {
      throw new Error('Usuário não encontrado. Faça login primeiro!');
    }

    // Valida se a igreja existe
    const church = await getChurchById(ministryData.idIgreja);
    if (!church) {
      throw new Error('Igreja não encontrada');
    }

    // Salva ministério no cache local
    await addMinistry({
      id: ministryId,
      idCriador: user.id,
      idIgreja: ministryData.idIgreja,
      nome: ministryData.nome,
      descricao: ministryData.descricao,
      codigo: generateMinistryCode(),
      foto: ministryData.arquivo?.caminho || undefined,
      createdAt: new Date().toISOString(),
    });

    // Adiciona o criador automaticamente como líder do ministério
    await addMinistryMember({
      id: generateId(),
      idUsuario: user.id,
      idMinisterio: ministryId,
      papel: 'lider',
      dataEntrada: new Date().toISOString(),
    });

    console.log('✅ Ministério registrado com sucesso no cache!');
    console.log('🆔 ID gerado:', ministryId);
    console.log('🙏 Nome:', ministryData.nome);
    console.log('⛪ Igreja:', church.nome);
    console.log('👑 Criador definido como líder');

    return {
      id: ministryId,
      message: 'Ministério registrado com sucesso (cache local)',
    };
  } catch (error) {
    console.error('❌ Erro ao registrar ministério (cache):', error);
    throw error;
  }
};
