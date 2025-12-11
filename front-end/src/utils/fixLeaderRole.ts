/**
 * Script para adicionar Erick Gomes Barbosa como líder do ministério de testes
 * Execute este arquivo para corrigir os dados do cache
 */

import AsyncStorage from '@react-native-async-storage/async-storage';
import { 
  getUser, 
  getMinistries, 
  addMinistryMember, 
  generateId,
  getMinistryMembers,
  loadCache,
  saveCache
} from '../services/localCache';

// Função para verificar e corrigir estrutura do cache
export async function checkAndFixCacheStructure() {
  try {
    console.log('🔍 Verificando estrutura do cache...');
    const cache = await loadCache();
    
    let needsSave = false;
    
    if (!cache.ministries) {
      console.warn('⚠️ Array de ministérios não existe, criando...');
      cache.ministries = [];
      needsSave = true;
    }
    
    if (!cache.ministryMembers) {
      console.warn('⚠️ Array de membros não existe, criando...');
      cache.ministryMembers = [];
      needsSave = true;
    }
    
    if (!cache.churches) {
      console.warn('⚠️ Array de igrejas não existe, criando...');
      cache.churches = [];
      needsSave = true;
    }
    
    if (needsSave) {
      await saveCache(cache);
      console.log('✅ Estrutura do cache corrigida!');
    } else {
      console.log('✅ Estrutura do cache OK');
    }
    
    return cache;
  } catch (error) {
    console.error('❌ Erro ao verificar cache:', error);
    throw error;
  }
}

export async function addErickAsLeader() {
  try {
    console.log('🔧 [Fix] Iniciando correção - Adicionando Erick como líder...');

    // 0. Verificar e corrigir estrutura do cache
    await checkAndFixCacheStructure();

    // 1. Buscar usuário atual
    const user = await getUser();
    if (!user) {
      throw new Error('❌ Usuário não encontrado no cache. Faça login primeiro!');
    }

    console.log('👤 Usuário encontrado:', user.nome);
    console.log('🆔 ID do usuário:', user.id);

    // 2. Buscar todos os ministérios
    const ministries = await getMinistries();
    console.log('🔍 Buscando ministérios...');
    console.log('📊 Resultado:', ministries);
    
    if (!ministries || ministries.length === 0) {
      throw new Error('❌ Nenhum ministério encontrado no cache. Crie um ministério primeiro!');
    }

    console.log('🙏 Ministérios encontrados:', ministries.length);

    // 3. Pegar o primeiro ministério (ministério de testes)
    const ministry = ministries[0];
    console.log('🎯 Ministério selecionado:', ministry.nome);
    console.log('🆔 ID do ministério:', ministry.id);

    // 4. Verificar se já existe como membro
    const existingMembers = await getMinistryMembers(ministry.id);
    const isAlreadyMember = existingMembers.some(m => m.idUsuario === user.id);

    if (isAlreadyMember) {
      console.log('⚠️ Usuário já é membro deste ministério');
      const currentMember = existingMembers.find(m => m.idUsuario === user.id);
      console.log('📋 Papel atual:', currentMember?.papel);
      return;
    }

    // 5. Adicionar como líder
    await addMinistryMember({
      id: generateId(),
      idUsuario: user.id,
      idMinisterio: ministry.id,
      papel: 'lider',
      dataEntrada: new Date().toISOString(),
    });

    console.log('✅ Erick adicionado como líder do ministério!');
    console.log('👑 Papel: Líder');
    console.log('🙏 Ministério:', ministry.nome);
    
  } catch (error) {
    console.error('❌ Erro ao adicionar Erick como líder:', error);
    throw error;
  }
}

// Para debug: Verificar estado atual
export async function checkMembershipStatus() {
  try {
    const user = await getUser();
    const ministries = await getMinistries();
    
    if (!user || ministries.length === 0) {
      console.log('❌ Sem dados para verificar');
      return;
    }

    console.log('\n📊 Status de Membros:');
    console.log('='.repeat(50));
    
    for (const ministry of ministries) {
      const members = await getMinistryMembers(ministry.id);
      console.log(`\n🙏 Ministério: ${ministry.nome}`);
      console.log(`👥 Total de membros: ${members.length}`);
      
      members.forEach(member => {
        console.log(`   - ${member.idUsuario === user.id ? '(VOCÊ) ' : ''}ID: ${member.idUsuario}, Papel: ${member.papel}`);
      });
    }
    
    console.log('='.repeat(50));
  } catch (error) {
    console.error('❌ Erro ao verificar status:', error);
  }
}
