/**
 * EXEMPLO COMPLETO DE USO DO CACHE LOCAL
 * 
 * Este arquivo demonstra como usar o sistema de cache local
 * para gerenciar todos os dados da aplicação sem API.
 */

import { 
  // User Management
  saveUser, 
  getUser, 
  updateUser,
  
  // Auth Management
  saveAuth, 
  getAuth, 
  clearAuth,
  
  // Church Management
  addChurch,
  getChurches,
  getChurchById,
  updateChurch,
  deleteChurch,
  
  // Ministry Management
  addMinistry,
  getMinistries,
  getMinistryById,
  updateMinistry,
  deleteMinistry,
  
  // Settings
  saveSettings,
  getSettings,
  
  // Utilities
  loadCache,
  clearCache,
  getCacheSize,
  exportCache,
  importCache,
  generateId,
} from '@/services/localCache';

// APIs com cache
import { loginUser } from '@/api/loginUser';
import { registerUser } from '@/api/registerUser';
import { registerChurch } from '@/api/registerChurch';
import { registerMinistry } from '@/api/registerMinistery';

// ========================================
// EXEMPLO 1: Registro e Login Completo
// ========================================
export async function exemploRegistroCompleto() {
  console.log('\n=== EXEMPLO 1: Registro e Login ===\n');
  
  // 1. Registrar novo usuário
  const userResponse = await registerUser({
    nome: 'Maria',
    sobrenome: 'Santos',
    nomeSocial: null,
    sobrenomeSocial: null,
    nomeCompleto: 'Maria Santos',
    email: 'maria@exemplo.com',
    confirm_email: 'maria@exemplo.com',
    cpf: '12345678900',
    telefone: '11987654321',
    dataNascimento: '1995-05-15',
    senha: 'Senha@123',
    confirm_senha: 'Senha@123',
    sexo: 'F',
    arquivo: undefined, // Foto opcional
    endereco: {
      cep: '01310100',
      uf: 'SP',
      cidade: 'São Paulo',
      bairro: 'Centro',
      rua: 'Av Paulista',
      numero: '1000',
      complemento: null,
    }
  } as any);
  
  console.log('✅ Usuário registrado:', userResponse.id);
  
  // 2. Fazer login
  const loginResponse = await loginUser({
    email: 'maria@exemplo.com',
    senha: 'senha123',
  });
  
  console.log('✅ Login realizado:', loginResponse.email);
  console.log('🔐 Token:', loginResponse.accessToken);
  
  // 3. Verificar usuário logado
  const currentUser = await getUser();
  console.log('👤 Usuário atual:', currentUser?.nome);
}

// ========================================
// EXEMPLO 2: Gerenciamento de Igrejas
// ========================================
export async function exemploGerenciamentoIgrejas() {
  console.log('\n=== EXEMPLO 2: Gerenciamento de Igrejas ===\n');
  
  // 1. Registrar nova igreja
  const churchResponse = await registerChurch({
    cnpj: '12.345.678/0001-90',
    nome: 'Igreja Batista Central',
    denominacao: 'batista',
    outra_denominacao: undefined,
    endereco: {
      cep: '01310100',
      uf: 'SP',
      cidade: 'São Paulo',
      bairro: 'Centro',
      rua: 'Avenida Paulista',
      numero: '1000',
      complemento: 'Sala 101',
      endereco_principal: true,
    },
    arquivo: undefined, // Foto opcional
  });
  
  console.log('✅ Igreja registrada:', churchResponse.idIgreja);
  
  // 2. Listar todas as igrejas
  const allChurches = await getChurches();
  console.log(`📋 Total de igrejas: ${allChurches.length}`);
  
  // 3. Buscar igreja específica
  const church = await getChurchById(churchResponse.idIgreja);
  console.log('⛪ Igreja encontrada:', church?.nome);
  
  // 4. Atualizar igreja
  await updateChurch(churchResponse.idIgreja, {
    nome: 'Igreja Batista Central - Atualizada',
  });
  console.log('✅ Igreja atualizada');
  
  // 5. Verificar atualização
  const updatedChurch = await getChurchById(churchResponse.idIgreja);
  console.log('⛪ Nome atualizado:', updatedChurch?.nome);
}

// ========================================
// EXEMPLO 3: Gerenciamento de Ministérios
// ========================================
export async function exemploGerenciamentoMinisterios() {
  console.log('\n=== EXEMPLO 3: Gerenciamento de Ministérios ===\n');
  
  // 1. Obter ID de uma igreja existente
  const churches = await getChurches();
  if (churches.length === 0) {
    console.log('⚠️ Nenhuma igreja cadastrada. Registre uma primeiro!');
    return;
  }
  
  const churchId = churches[0].id;
  console.log('⛪ Usando igreja:', churches[0].nome);
  
  // 2. Registrar novo ministério
  const ministryResponse = await registerMinistry({
    idIgreja: churchId,
    nome: 'Ministério de Louvor',
    descricao: 'Responsável pelo louvor e adoração durante os cultos',
    arquivo: undefined, // Foto opcional
  });
  
  console.log('✅ Ministério registrado:', ministryResponse.id);
  
  // 3. Registrar mais ministérios
  await registerMinistry({
    idIgreja: churchId,
    nome: 'Ministério Infantil',
    descricao: 'Cuidado e ensino das crianças',
  });
  
  await registerMinistry({
    idIgreja: churchId,
    nome: 'Ministério de Tecnologia',
    descricao: 'Som, imagem e transmissões online',
  });
  
  // 4. Listar todos os ministérios da igreja
  const churchMinistries = await getMinistries(churchId);
  console.log(`📋 Ministérios da igreja: ${churchMinistries.length}`);
  
  churchMinistries.forEach((m, index) => {
    console.log(`  ${index + 1}. ${m.nome}`);
  });
  
  // 5. Atualizar ministério
  await updateMinistry(ministryResponse.id, {
    descricao: 'Ministério de louvor e adoração - Atualizado',
  });
  console.log('✅ Ministério atualizado');
}

// ========================================
// EXEMPLO 4: Configurações do App
// ========================================
export async function exemploConfiguracoes() {
  console.log('\n=== EXEMPLO 4: Configurações ===\n');
  
  // 1. Obter configurações atuais
  const currentSettings = await getSettings();
  console.log('⚙️ Configurações atuais:', currentSettings);
  
  // 2. Atualizar configurações
  await saveSettings({
    theme: 'dark',
    notifications: false,
    language: 'pt-BR',
  });
  console.log('✅ Configurações salvas');
  
  // 3. Verificar mudanças
  const newSettings = await getSettings();
  console.log('⚙️ Novas configurações:', newSettings);
}

// ========================================
// EXEMPLO 5: Cache Utilities
// ========================================
export async function exemploUtilidades() {
  console.log('\n=== EXEMPLO 5: Utilidades do Cache ===\n');
  
  // 1. Tamanho do cache
  const size = await getCacheSize();
  const sizeKB = (size / 1024).toFixed(2);
  console.log(`📊 Tamanho do cache: ${sizeKB} KB`);
  
  // 2. Carregar cache completo
  const fullCache = await loadCache();
  console.log('📦 Cache completo carregado');
  console.log(`  - Usuário: ${fullCache.user?.nome || 'Nenhum'}`);
  console.log(`  - Igrejas: ${fullCache.churches.length}`);
  console.log(`  - Ministérios: ${fullCache.ministries.length}`);
  
  // 3. Exportar cache (para backup)
  const jsonBackup = await exportCache();
  console.log('📤 Cache exportado (primeiros 100 caracteres):');
  console.log(jsonBackup.substring(0, 100) + '...');
  
  // 4. Gerar ID único
  const newId = generateId();
  console.log('🆔 Novo ID gerado:', newId);
}

// ========================================
// EXEMPLO 6: Fluxo Completo de Uso
// ========================================
export async function exemploFluxoCompleto() {
  console.log('\n=== EXEMPLO 6: Fluxo Completo ===\n');
  
  try {
    // 1. Registro
    console.log('1️⃣ Registrando usuário...');
    await registerUser({
      nome: 'João',
      sobrenome: 'Silva',
      nomeSocial: null,
      sobrenomeSocial: null,
      nomeCompleto: 'João Silva',
      email: 'joao@exemplo.com',
      confirm_email: 'joao@exemplo.com',
      cpf: '98765432100',
      telefone: '11999887766',
      dataNascimento: '1990-01-01',
      senha: 'Senha@123',
      confirm_senha: 'Senha@123',
      sexo: 'M',
      endereco: {
        cep: '01310100',
        uf: 'SP',
        cidade: 'São Paulo',
        bairro: 'Centro',
        rua: 'Rua Exemplo',
        numero: '123',
        complemento: null,
      }
    } as any);
    
    // 2. Login
    console.log('2️⃣ Fazendo login...');
    await loginUser({
      email: 'joao@exemplo.com',
      senha: 'senha123',
    });
    
    // 3. Criar igreja
    console.log('3️⃣ Criando igreja...');
    const church = await registerChurch({
      cnpj: '11.222.333/0001-44',
      nome: 'Igreja Exemplo',
      denominacao: 'evangelica',
      endereco: {
        cep: '01310100',
        uf: 'SP',
        cidade: 'São Paulo',
        bairro: 'Centro',
        rua: 'Rua Exemplo',
        numero: '123',
        endereco_principal: true,
      },
    });
    
    // 4. Criar ministérios
    console.log('4️⃣ Criando ministérios...');
    await registerMinistry({
      idIgreja: church.idIgreja,
      nome: 'Louvor',
      descricao: 'Ministério de louvor',
    });
    
    await registerMinistry({
      idIgreja: church.idIgreja,
      nome: 'Dança',
      descricao: 'Ministério de dança',
    });
    
    // 5. Listar tudo
    console.log('5️⃣ Listando dados...');
    const user = await getUser();
    const churches = await getChurches();
    const ministries = await getMinistries();
    
    console.log('\n📊 RESUMO FINAL:');
    console.log(`👤 Usuário: ${user?.nome}`);
    console.log(`⛪ Igrejas: ${churches.length}`);
    console.log(`🙏 Ministérios: ${ministries.length}`);
    
    // 6. Tamanho do cache
    const size = await getCacheSize();
    console.log(`💾 Tamanho: ${(size / 1024).toFixed(2)} KB`);
    
    console.log('\n✅ Fluxo completo executado com sucesso!');
    
  } catch (error) {
    console.error('❌ Erro no fluxo:', error);
  }
}

// ========================================
// EXEMPLO 7: Limpar e Resetar
// ========================================
export async function exemploLimparCache() {
  console.log('\n=== EXEMPLO 7: Limpar Cache ===\n');
  
  // Ver estado antes
  const beforeCache = await loadCache();
  console.log('📊 Antes de limpar:');
  console.log(`  - Igrejas: ${beforeCache.churches.length}`);
  console.log(`  - Ministérios: ${beforeCache.ministries.length}`);
  
  // Limpar tudo
  await clearCache();
  console.log('🗑️ Cache limpo!');
  
  // Ver estado depois
  const afterCache = await loadCache();
  console.log('📊 Depois de limpar:');
  console.log(`  - Igrejas: ${afterCache.churches.length}`);
  console.log(`  - Ministérios: ${afterCache.ministries.length}`);
}

// ========================================
// FUNÇÃO PARA EXECUTAR TODOS OS EXEMPLOS
// ========================================
export async function executarTodosExemplos() {
  console.log('\n🚀 EXECUTANDO TODOS OS EXEMPLOS DO CACHE LOCAL\n');
  
  try {
    await exemploRegistroCompleto();
    await exemploGerenciamentoIgrejas();
    await exemploGerenciamentoMinisterios();
    await exemploConfiguracoes();
    await exemploUtilidades();
    await exemploFluxoCompleto();
    // await exemploLimparCache(); // Descomente se quiser limpar ao final
    
    console.log('\n🎉 TODOS OS EXEMPLOS EXECUTADOS COM SUCESSO!\n');
    
  } catch (error) {
    console.error('\n❌ ERRO AO EXECUTAR EXEMPLOS:', error);
  }
}

// Para usar em uma tela React:
/*
import { executarTodosExemplos } from '@/examples/cacheExamples';

export default function TesteScreen() {
  return (
    <View>
      <Button 
        title="Executar Exemplos" 
        onPress={executarTodosExemplos} 
      />
    </View>
  );
}
*/
