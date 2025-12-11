import { getMinistries, loadCache } from '../services/localCache';

/**
 * Script para exibir todos os ministérios cadastrados no cache
 */
export const showMinistries = async (): Promise<void> => {
  try {
    const cache = await loadCache();
    const ministries = await getMinistries();

    console.log('📋 ===== MINISTÉRIOS CADASTRADOS =====');
    console.log(`Total: ${ministries.length}`);
    console.log('');

    if (ministries.length === 0) {
      console.log('⚠️ Nenhum ministério encontrado no cache');
      return;
    }

    ministries.forEach((ministry, index) => {
      console.log(`${index + 1}. ${ministry.nome}`);
      console.log(`   ID: ${ministry.id}`);
      console.log(`   Igreja ID: ${ministry.idIgreja}`);
      console.log(`   Criador ID: ${ministry.idCriador}`);
      console.log(`   Criado em: ${new Date(ministry.createdAt).toLocaleString('pt-BR')}`);
      console.log('');
    });

    // Exibe também os membros
    console.log('👥 ===== MEMBROS DOS MINISTÉRIOS =====');
    console.log(`Total de membros: ${cache.ministryMembers.length}`);
    console.log('');

    cache.ministryMembers.forEach((member, index) => {
      const ministry = ministries.find(m => m.id === member.idMinisterio);
      console.log(`${index + 1}. Usuário: ${member.idUsuario}`);
      console.log(`   Ministério: ${ministry?.nome || 'Não encontrado'}`);
      console.log(`   Papel: ${member.papel.toUpperCase()}`);
      console.log(`   Data entrada: ${new Date(member.dataEntrada).toLocaleString('pt-BR')}`);
      console.log('');
    });

  } catch (error) {
    console.error('❌ Erro ao buscar ministérios:', error);
    throw error;
  }
};
