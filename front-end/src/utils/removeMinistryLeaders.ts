import { 
  loadCache,
  saveCache,
  getMinistries
} from '../services/localCache';

/**
 * Remove todos os líderes de um ministério específico
 */
export const removeMinistryLeaders = async (ministryId: string): Promise<{ success: boolean; message: string; removedCount: number }> => {
  try {
    if (!ministryId || ministryId.trim() === '') {
      return {
        success: false,
        message: 'ID do ministério não pode estar vazio',
        removedCount: 0
      };
    }

    const cache = await loadCache();
    const ministries = await getMinistries();

    // Verifica se o ministério existe
    const ministry = ministries.find(m => m.id === ministryId);
    if (!ministry) {
      return {
        success: false,
        message: 'Ministério não encontrado',
        removedCount: 0
      };
    }

    // Filtra para remover todos os líderes deste ministério
    const originalLength = cache.ministryMembers.length;
    const leadersToRemove = cache.ministryMembers.filter(
      m => m.idMinisterio === ministryId && m.papel === 'lider'
    );

    cache.ministryMembers = cache.ministryMembers.filter(
      m => !(m.idMinisterio === ministryId && m.papel === 'lider')
    );

    const removedCount = originalLength - cache.ministryMembers.length;

    // Salva o cache atualizado
    await saveCache(cache);

    console.log(`🗑️ ${removedCount} líder(es) removido(s) do ministério "${ministry.nome}"`);
    leadersToRemove.forEach(leader => {
      console.log(`   - Usuário ID: ${leader.idUsuario}`);
    });

    return {
      success: true,
      message: `${removedCount} líder(es) removido(s) com sucesso do ministério "${ministry.nome}"`,
      removedCount
    };

  } catch (error) {
    console.error('❌ Erro ao remover líderes:', error);
    return {
      success: false,
      message: error instanceof Error ? error.message : 'Erro desconhecido ao remover líderes',
      removedCount: 0
    };
  }
};
