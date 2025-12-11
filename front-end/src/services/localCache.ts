// Import direto do AsyncStorage
import AsyncStorage from '@react-native-async-storage/async-storage';

const CACHE_KEY = '@harppia_local_cache';

// Estrutura completa do cache local
export interface LocalCacheData {
  user: {
    id: string;
    email: string;
    nome: string;
    nomeSocial?: string;
    telefone: string;
    cpf: string;
    dataNascimento: string;
    sexo: string;
    senha: string; // Senha armazenada (em produção, usar hash)
    foto?: string;
    endereco: {
      cep: string;
      uf: string;
      cidade: string;
      bairro: string;
      rua: string;
      numero: string;
      complemento?: string;
    };
    createdAt: string;
  } | null;
  
  auth: {
    accessToken: string;
    refreshToken: string;
    lastLogin: string;
  } | null;
  
  churches: Array<{
    id: string;
    idCriador: string;
    idDono: string;
    cnpj: string;
    nome: string;
    denominacao: string;
    outraDenominacao?: string;
    foto?: string;
    endereco: {
      cep: string;
      uf: string;
      cidade: string;
      bairro: string;
      logradouro: string;
      numero: string;
      complemento?: string;
    };
    createdAt: string;
  }>;
  
  ministries: Array<{
    id: string;
    idCriador: string;
    idIgreja: string;
    nome: string;
    descricao: string;
    codigo: string;
    foto?: string;
    createdAt: string;
  }>;
  
  ministryMembers: Array<{
    id: string;
    idUsuario: string;
    idMinisterio: string;
    papel: 'lider' | 'ministro' | 'levita';
    dataEntrada: string;
  }>;

  warnings: Array<{
    id: string;
    idMinisterio: string;
    title: string;
    description: string;
    date: string;
    createdBy: string;
    createdAt: string;
  }>;

  schedules: Array<{
    id: string;
    idMinisterio: string;
    title: string;
    description: string;
    date: string;
    time: string;
    createdBy: string;
    createdAt: string;
  }>;

  commitments: Array<{
    id: string;
    idMinisterio: string;
    title: string;
    description: string;
    date: string;
    time: string;
    responsible_user: string;
    createdBy: string;
    createdAt: string;
  }>;

  musics: Array<{
    id: string;
    idMinisterio: string;
    title: string;
    artist: string;
    durationInSeconds: number;
    album?: string;
    bpm: number;
    key: string;
    albumCover?: string;
    youtubeLink?: string;
    lyricsLink?: string;
    chordsLink?: string;
    createdBy: string;
    createdAt: string;
  }>;

  scheduleMusics: Array<{
    id: string;
    idAgendamento: string;
    idMusica: string;
    ordem: number;
  }>;

  scheduleAttendances: Array<{
    id: string;
    idAgendamento: string;
    idUsuario: string;
    confirmado: boolean;
    justificativa?: string;
  }>;
  
  settings: {
    theme: 'light' | 'dark' | 'auto';
    notifications: boolean;
    language: string;
  };
}

// Cache padrão vazio
const DEFAULT_CACHE: LocalCacheData = {
  user: null,
  auth: null,
  churches: [],
  ministries: [],
  ministryMembers: [],
  warnings: [],
  schedules: [],
  commitments: [],
  musics: [],
  scheduleMusics: [],
  scheduleAttendances: [],
  settings: {
    theme: 'auto',
    notifications: true,
    language: 'pt-BR',
  },
};

// Carrega todo o cache
export const loadCache = async (): Promise<LocalCacheData> => {
  try {
    const jsonValue = await AsyncStorage.getItem(CACHE_KEY);
    if (jsonValue != null) {
      console.log('📦 Cache carregado do AsyncStorage');
      return JSON.parse(jsonValue);
    }
    console.log('📦 Cache vazio, retornando padrão');
    return DEFAULT_CACHE;
  } catch (error) {
    console.warn('⚠️ Erro ao carregar cache (AsyncStorage indisponível?):', error);
    return DEFAULT_CACHE;
  }
};

// Salva todo o cache
export const saveCache = async (data: LocalCacheData): Promise<void> => {
  try {
    const jsonValue = JSON.stringify(data);
    await AsyncStorage.setItem(CACHE_KEY, jsonValue);
    console.log('💾 Cache salvo no AsyncStorage');
  } catch (error) {
    console.warn('⚠️ Erro ao salvar cache (AsyncStorage indisponível?):', error);
    // Não lança erro para não quebrar o fluxo
  }
};

// Atualiza parte do cache
export const updateCache = async (
  updates: Partial<LocalCacheData>
): Promise<LocalCacheData> => {
  try {
    const currentCache = await loadCache();
    const newCache = { ...currentCache, ...updates };
    await saveCache(newCache);
    console.log('✅ Cache atualizado:', Object.keys(updates));
    return newCache;
  } catch (error) {
    console.error('❌ Erro ao atualizar cache:', error);
    throw error;
  }
};

// Limpa todo o cache
export const clearCache = async (): Promise<void> => {
  try {
    await AsyncStorage.removeItem(CACHE_KEY);
    console.log('🗑️ Cache limpo');
  } catch (error) {
    console.error('❌ Erro ao limpar cache:', error);
    throw error;
  }
};

// ========== Funções específicas para User ==========

export const saveUser = async (user: LocalCacheData['user']): Promise<void> => {
  await updateCache({ user });
};

export const getUser = async (): Promise<LocalCacheData['user']> => {
  const cache = await loadCache();
  return cache.user;
};

export const updateUser = async (updates: Partial<NonNullable<LocalCacheData['user']>>): Promise<void> => {
  const cache = await loadCache();
  if (cache.user) {
    cache.user = { ...cache.user, ...updates };
    await saveCache(cache);
  }
};

export const clearUser = async (): Promise<void> => {
  await updateCache({ user: null, auth: null });
  console.log('🗑️ Usuário removido do cache');
};

// ========== Funções específicas para Auth ==========

export const saveAuth = async (auth: LocalCacheData['auth']): Promise<void> => {
  await updateCache({ auth });
};

export const getAuth = async (): Promise<LocalCacheData['auth']> => {
  const cache = await loadCache();
  return cache.auth;
};

export const clearAuth = async (): Promise<void> => {
  await updateCache({ auth: null });
};

// ========== Funções específicas para Churches ==========

export const addChurch = async (church: LocalCacheData['churches'][0]): Promise<void> => {
  const cache = await loadCache();
  cache.churches.push(church);
  await saveCache(cache);
  console.log('⛪ Igreja adicionada ao cache:', church.nome);
};

export const getChurches = async (): Promise<LocalCacheData['churches']> => {
  const cache = await loadCache();
  return cache.churches;
};

export const getChurchById = async (id: string): Promise<LocalCacheData['churches'][0] | null> => {
  const cache = await loadCache();
  return cache.churches.find(c => c.id === id) || null;
};

export const updateChurch = async (
  id: string,
  updates: Partial<LocalCacheData['churches'][0]>
): Promise<void> => {
  const cache = await loadCache();
  const index = cache.churches.findIndex(c => c.id === id);
  if (index !== -1) {
    cache.churches[index] = { ...cache.churches[index], ...updates };
    await saveCache(cache);
    console.log('⛪ Igreja atualizada:', id);
  }
};

export const deleteChurch = async (id: string): Promise<void> => {
  const cache = await loadCache();
  cache.churches = cache.churches.filter(c => c.id !== id);
  await saveCache(cache);
  console.log('🗑️ Igreja removida:', id);
};

// ========== Funções específicas para Ministries ==========

export const addMinistry = async (ministry: LocalCacheData['ministries'][0]): Promise<void> => {
  const cache = await loadCache();
  cache.ministries.push(ministry);
  await saveCache(cache);
  console.log('🙏 Ministério adicionado ao cache:', ministry.nome);
};

export const getMinistries = async (churchId?: string): Promise<LocalCacheData['ministries']> => {
  const cache = await loadCache();
  if (churchId) {
    return cache.ministries.filter(m => m.idIgreja === churchId);
  }
  return cache.ministries;
};

export const getMinistryById = async (id: string): Promise<LocalCacheData['ministries'][0] | null> => {
  const cache = await loadCache();
  return cache.ministries.find(m => m.id === id) || null;
};

export const updateMinistry = async (
  id: string,
  updates: Partial<LocalCacheData['ministries'][0]>
): Promise<void> => {
  const cache = await loadCache();
  const index = cache.ministries.findIndex(m => m.id === id);
  if (index !== -1) {
    cache.ministries[index] = { ...cache.ministries[index], ...updates };
    await saveCache(cache);
    console.log('🙏 Ministério atualizado:', id);
  }
};

export const deleteMinistry = async (id: string): Promise<void> => {
  const cache = await loadCache();
  cache.ministries = cache.ministries.filter(m => m.id !== id);
  await saveCache(cache);
  console.log('🗑️ Ministério removido:', id);
};

// ========== Funções específicas para Settings ==========

export const saveSettings = async (settings: Partial<LocalCacheData['settings']>): Promise<void> => {
  const cache = await loadCache();
  cache.settings = { ...cache.settings, ...settings };
  await saveCache(cache);
  console.log('⚙️ Configurações salvas');
};

export const getSettings = async (): Promise<LocalCacheData['settings']> => {
  const cache = await loadCache();
  return cache.settings;
};

// ========== Debug e utilidades ==========

export const getCacheSize = async (): Promise<number> => {
  try {
    const jsonValue = await AsyncStorage.getItem(CACHE_KEY);
    if (jsonValue != null) {
      return new Blob([jsonValue]).size;
    }
    return 0;
  } catch (error) {
    console.error('❌ Erro ao calcular tamanho do cache:', error);
    return 0;
  }
};

export const exportCache = async (): Promise<string> => {
  const cache = await loadCache();
  return JSON.stringify(cache, null, 2);
};

export const importCache = async (jsonString: string): Promise<void> => {
  try {
    const data = JSON.parse(jsonString) as LocalCacheData;
    await saveCache(data);
    console.log('✅ Cache importado com sucesso');
  } catch (error) {
    console.error('❌ Erro ao importar cache:', error);
    throw new Error('JSON inválido');
  }
};

// Gera ID único
export const generateId = (): string => {
  return `${Date.now()}-${Math.random().toString(36).substr(2, 9)}`;
};

// Gera código de ministério de 6 caracteres (letras maiúsculas e números)
// Evita caracteres confusos: 0, O, 1, I
export const generateMinistryCode = (): string => {
  const characters = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789';
  let code = '';
  for (let i = 0; i < 6; i++) {
    code += characters.charAt(Math.floor(Math.random() * characters.length));
  }
  return code;
};

// ========== Armazenamento temporário para fluxo de registro ==========

const TEMP_CREDENTIALS_KEY = '@harppia_temp_credentials';

interface TempCredentials {
  email?: string;
  senha?: string;
  telefone?: string;
}

// Salva credenciais temporárias (usado no fluxo de registro)
export const saveTempCredentials = async (credentials: TempCredentials): Promise<void> => {
  try {
    const jsonValue = JSON.stringify(credentials);
    await AsyncStorage.setItem(TEMP_CREDENTIALS_KEY, jsonValue);
    console.log('💾 Credenciais temporárias salvas');
  } catch (error) {
    console.warn('⚠️ Erro ao salvar credenciais temporárias:', error);
  }
};

// Recupera credenciais temporárias
export const getTempCredentials = async (): Promise<TempCredentials | null> => {
  try {
    const jsonValue = await AsyncStorage.getItem(TEMP_CREDENTIALS_KEY);
    if (jsonValue != null) {
      console.log('📦 Credenciais temporárias carregadas');
      return JSON.parse(jsonValue);
    }
    return null;
  } catch (error) {
    console.warn('⚠️ Erro ao carregar credenciais temporárias:', error);
    return null;
  }
};

// Limpa credenciais temporárias
export const clearTempCredentials = async (): Promise<void> => {
  try {
    await AsyncStorage.removeItem(TEMP_CREDENTIALS_KEY);
    console.log('🗑️ Credenciais temporárias limpas');
  } catch (error) {
    console.warn('⚠️ Erro ao limpar credenciais temporárias:', error);
  }
};

// ====================================
// Funções de Membros de Ministério
// ====================================

// Adiciona um membro ao ministério
export const addMinistryMember = async (member: {
  id: string;
  idUsuario: string;
  idMinisterio: string;
  papel: 'lider' | 'ministro' | 'levita';
  dataEntrada: string;
}): Promise<void> => {
  try {
    const cache = await loadCache();
    
    // Inicializa arrays se não existirem
    if (!cache.ministries) {
      cache.ministries = [];
    }
    if (!cache.ministryMembers) {
      cache.ministryMembers = [];
    }
    
    // Verifica se o ministério existe
    const ministryExists = cache.ministries.some((m) => m.id === member.idMinisterio);
    if (!ministryExists) {
      throw new Error('Ministério não encontrado. Certifique-se de criar um ministério primeiro.');
    }
    
    // Verifica se o usuário tem id igual ao do cache (single user system)
    if (cache.user && cache.user.id !== member.idUsuario) {
      console.warn('⚠️ ID do usuário não corresponde ao usuário logado');
    }
    
    // Verifica se já existe um membro com este usuário neste ministério
    const memberExists = cache.ministryMembers.some(
      (m) => m.idUsuario === member.idUsuario && m.idMinisterio === member.idMinisterio
    );
    if (memberExists) {
      throw new Error('Usuário já é membro deste ministério');
    }
    
    // Se o papel for líder, verifica se já existe um líder
    if (member.papel === 'lider') {
      const leaderExists = cache.ministryMembers.some(
        (m) => m.idMinisterio === member.idMinisterio && m.papel === 'lider'
      );
      if (leaderExists) {
        throw new Error('Este ministério já possui um líder');
      }
    }
    
    cache.ministryMembers.push(member);
    await saveCache(cache);
    console.log('✅ Membro adicionado ao ministério:', member);
  } catch (error) {
    console.error('❌ Erro ao adicionar membro ao ministério:', error);
    throw error;
  }
};

// Retorna todos os membros de um ministério
export const getMinistryMembers = async (ministryId: string) => {
  try {
    const cache = await loadCache();
    const members = cache.ministryMembers.filter((m) => m.idMinisterio === ministryId);
    
    // Nota: Para enriquecer com dados completos dos usuários, 
    // seria necessário um array de usuários ou integração com backend
    // Por enquanto, retorna apenas os dados básicos dos membros
    return members;
  } catch (error) {
    console.error('❌ Erro ao buscar membros do ministério:', error);
    return [];
  }
};

// Retorna o papel de um usuário em um ministério específico
export const getMemberRole = async (userId: string, ministryId: string) => {
  try {
    const cache = await loadCache();
    const member = cache.ministryMembers.find(
      (m) => m.idUsuario === userId && m.idMinisterio === ministryId
    );
    return member?.papel || null;
  } catch (error) {
    console.error('❌ Erro ao buscar papel do membro:', error);
    return null;
  }
};

// Atualiza o papel de um membro
export const updateMemberRole = async (
  userId: string,
  ministryId: string,
  newRole: 'lider' | 'ministro' | 'levita'
): Promise<void> => {
  try {
    const cache = await loadCache();
    
    const memberIndex = cache.ministryMembers.findIndex(
      (m) => m.idUsuario === userId && m.idMinisterio === ministryId
    );
    
    if (memberIndex === -1) {
      throw new Error('Membro não encontrado');
    }
    
    // Se o novo papel for líder, verifica se já existe um líder
    if (newRole === 'lider') {
      const leaderExists = cache.ministryMembers.some(
        (m) => m.idMinisterio === ministryId && m.papel === 'lider' && m.idUsuario !== userId
      );
      if (leaderExists) {
        throw new Error('Este ministério já possui um líder');
      }
    }
    
    cache.ministryMembers[memberIndex].papel = newRole;
    await saveCache(cache);
    console.log('✅ Papel do membro atualizado:', { userId, ministryId, newRole });
  } catch (error) {
    console.error('❌ Erro ao atualizar papel do membro:', error);
    throw error;
  }
};

// Remove um membro de um ministério
export const removeMember = async (userId: string, ministryId: string): Promise<void> => {
  try {
    const cache = await loadCache();
    
    const memberIndex = cache.ministryMembers.findIndex(
      (m) => m.idUsuario === userId && m.idMinisterio === ministryId
    );
    
    if (memberIndex === -1) {
      throw new Error('Membro não encontrado');
    }
    
    // Verifica se é o líder
    if (cache.ministryMembers[memberIndex].papel === 'lider') {
      throw new Error('Não é possível remover o líder. Transfira a liderança antes de remover.');
    }
    
    cache.ministryMembers.splice(memberIndex, 1);
    await saveCache(cache);
    console.log('✅ Membro removido do ministério:', { userId, ministryId });
  } catch (error) {
    console.error('❌ Erro ao remover membro:', error);
    throw error;
  }
};

// Retorna todos os ministérios de um usuário
export const getUserMinistries = async (userId: string) => {
  try {
    const cache = await loadCache();
    const userMemberships = cache.ministryMembers.filter((m) => m.idUsuario === userId);
    
    // Enriquece com dados do ministério
    const enrichedMinistries = userMemberships.map((membership) => {
      const ministry = cache.ministries.find((m) => m.id === membership.idMinisterio);
      const church = cache.churches.find((c) => c.id === ministry?.idIgreja);
      return {
        ...membership,
        nomeMinisterio: ministry?.nome || 'Ministério desconhecido',
        fotoMinisterio: ministry?.foto || null,
        nomeIgreja: church?.nome || 'Igreja desconhecida',
      };
    });
    
    return enrichedMinistries;
  } catch (error) {
    console.error('❌ Erro ao buscar ministérios do usuário:', error);
    return [];
  }
};

// ====================================
// Funções de Avisos
// ====================================

export const addWarning = async (warning: {
  id: string;
  idMinisterio: string;
  title: string;
  description: string;
  date: string;
  createdBy: string;
  createdAt: string;
}): Promise<void> => {
  try {
    const cache = await loadCache();
    if (!cache.warnings) cache.warnings = [];
    cache.warnings.push(warning);
    await saveCache(cache);
    console.log('✅ Aviso adicionado:', warning.title);
  } catch (error) {
    console.error('❌ Erro ao adicionar aviso:', error);
    throw error;
  }
};

export const getWarningsByMinistry = async (ministryId: string) => {
  try {
    const cache = await loadCache();
    return cache.warnings?.filter((w) => w.idMinisterio === ministryId) || [];
  } catch (error) {
    console.error('❌ Erro ao buscar avisos:', error);
    return [];
  }
};

// ====================================
// Funções de Agendamentos
// ====================================

export const addSchedule = async (schedule: {
  id: string;
  idMinisterio: string;
  title: string;
  description: string;
  date: string;
  time: string;
  createdBy: string;
  createdAt: string;
}): Promise<void> => {
  try {
    const cache = await loadCache();
    if (!cache.schedules) cache.schedules = [];
    cache.schedules.push(schedule);
    await saveCache(cache);
    console.log('✅ Agendamento adicionado:', schedule.title);
  } catch (error) {
    console.error('❌ Erro ao adicionar agendamento:', error);
    throw error;
  }
};

export const getSchedulesByMinistry = async (ministryId: string) => {
  try {
    const cache = await loadCache();
    return cache.schedules?.filter((s) => s.idMinisterio === ministryId) || [];
  } catch (error) {
    console.error('❌ Erro ao buscar agendamentos:', error);
    return [];
  }
};

// ====================================
// Funções de Compromissos
// ====================================

export const addCommitment = async (commitment: {
  id: string;
  idMinisterio: string;
  title: string;
  description: string;
  date: string;
  time: string;
  responsible_user: string;
  createdBy: string;
  createdAt: string;
}): Promise<void> => {
  try {
    const cache = await loadCache();
    if (!cache.commitments) cache.commitments = [];
    cache.commitments.push(commitment);
    await saveCache(cache);
    console.log('✅ Compromisso adicionado:', commitment.title);
  } catch (error) {
    console.error('❌ Erro ao adicionar compromisso:', error);
    throw error;
  }
};

export const getCommitmentsByMinistry = async (ministryId: string) => {
  try {
    const cache = await loadCache();
    return cache.commitments?.filter((c) => c.idMinisterio === ministryId) || [];
  } catch (error) {
    console.error('❌ Erro ao buscar compromissos:', error);
    return [];
  }
};

// ====================================
// Funções de Músicas
// ====================================

export const addMusic = async (music: {
  id: string;
  idMinisterio: string;
  title: string;
  artist: string;
  durationInSeconds: number;
  album?: string;
  bpm: number;
  key: string;
  albumCover?: string;
  youtubeLink?: string;
  lyricsLink?: string;
  chordsLink?: string;
  createdBy: string;
  createdAt: string;
}): Promise<void> => {
  try {
    const cache = await loadCache();
    if (!cache.musics) cache.musics = [];
    cache.musics.push(music);
    await saveCache(cache);
    console.log('✅ Música adicionada:', music.title);
  } catch (error) {
    console.error('❌ Erro ao adicionar música:', error);
    throw error;
  }
};

export const getMusicsByMinistry = async (ministryId: string) => {
  try {
    const cache = await loadCache();
    return cache.musics?.filter((m) => m.idMinisterio === ministryId) || [];
  } catch (error) {
    console.error('❌ Erro ao buscar músicas:', error);
    return [];
  }
};
