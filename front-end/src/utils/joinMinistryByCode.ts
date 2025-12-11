import { 
  getUser, 
  getMinistries, 
  addMinistryMember,
  generateId,
  loadCache
} from '../services/localCache';

/**
 * Permite que um usuário entre em um ministério usando o ID do ministério
 * O usuário entra como LEVITA
 */
export const joinMinistryByCode = async (ministryId: string): Promise<{ success: boolean; message: string; ministryId?: string }> => {
  try {
    // Busca dados do cache
    const user = await getUser();
    const ministries = await getMinistries();
    const cache = await loadCache();

    // Validações
    if (!user) {
      return {
        success: false,
        message: 'Você precisa estar logado para entrar em um ministério'
      };
    }

    if (!ministryId || ministryId.trim() === '') {
      return {
        success: false,
        message: 'Código do ministério não pode estar vazio'
      };
    }

    // Busca o ministério pelo ID
    const ministry = ministries.find(m => m.id === ministryId);

    if (!ministry) {
      return {
        success: false,
        message: 'Ministério não encontrado. Verifique o código e tente novamente.'
      };
    }

    // Verifica se o usuário já é membro
    const existingMember = cache.ministryMembers.find(
      m => m.idUsuario === user.id && m.idMinisterio === ministryId
    );

    if (existingMember) {
      return {
        success: false,
        message: `Você já é ${existingMember.papel} deste ministério`
      };
    }

    // Adiciona o usuário como LEVITA do ministério
    await addMinistryMember({
      id: generateId(),
      idUsuario: user.id,
      idMinisterio: ministryId,
      papel: 'levita',
      dataEntrada: new Date().toISOString(),
    });

    console.log('✅ Usuário adicionado ao ministério como LEVITA');
    console.log(`👤 Usuário: ${user.nome}`);
    console.log(`🎵 Ministério: ${ministry.nome}`);

    return {
      success: true,
      message: `Você entrou no ministério "${ministry.nome}" como LEVITA!`,
      ministryId: ministryId
    };

  } catch (error) {
    console.error('❌ Erro ao entrar no ministério:', error);
    return {
      success: false,
      message: error instanceof Error ? error.message : 'Erro desconhecido ao entrar no ministério'
    };
  }
};
