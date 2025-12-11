import React, { useState, useEffect } from 'react';
import { View, Text, ScrollView, TouchableOpacity, Alert, Share } from 'react-native';
import { 
  loadCache, 
  clearCache, 
  getCacheSize, 
  exportCache,
  LocalCacheData 
} from '@/services/localCache';

export default function CacheDebugScreen() {
  const [cache, setCache] = useState<LocalCacheData | null>(null);
  const [cacheSize, setCacheSize] = useState<number>(0);
  const [loading, setLoading] = useState(true);

  const loadCacheData = async () => {
    setLoading(true);
    try {
      const data = await loadCache();
      const size = await getCacheSize();
      setCache(data);
      setCacheSize(size);
    } catch (error) {
      console.error('Erro ao carregar cache:', error);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadCacheData();
  }, []);

  const handleClearCache = () => {
    Alert.alert(
      'Limpar Cache',
      'Tem certeza? Todos os dados serão perdidos!',
      [
        { text: 'Cancelar', style: 'cancel' },
        {
          text: 'Limpar',
          style: 'destructive',
          onPress: async () => {
            await clearCache();
            await loadCacheData();
            Alert.alert('Sucesso', 'Cache limpo!');
          },
        },
      ]
    );
  };

  const handleExportCache = async () => {
    try {
      const json = await exportCache();
      await Share.share({
        message: json,
        title: 'Harppia Cache Backup',
      });
    } catch (error) {
      Alert.alert('Erro', 'Não foi possível exportar o cache');
    }
  };

  if (loading) {
    return (
      <View className="flex-1 bg-white dark:bg-gray-900 items-center justify-center">
        <Text className="text-gray-600 dark:text-gray-400">Carregando cache...</Text>
      </View>
    );
  }

  const sizeInKB = (cacheSize / 1024).toFixed(2);

  return (
    <ScrollView className="flex-1 bg-white dark:bg-gray-900">
      <View className="p-4">
        {/* Header */}
        <View className="mb-6">
          <Text className="text-2xl font-bold text-gray-900 dark:text-white mb-2">
            🗄️ Cache Local
          </Text>
          <Text className="text-sm text-gray-600 dark:text-gray-400">
            Tamanho: {sizeInKB} KB
          </Text>
        </View>

        {/* Actions */}
        <View className="flex-row gap-2 mb-6">
          <TouchableOpacity
            onPress={loadCacheData}
            className="flex-1 bg-blue-500 py-3 rounded-lg items-center"
          >
            <Text className="text-white font-semibold">🔄 Recarregar</Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={handleExportCache}
            className="flex-1 bg-green-500 py-3 rounded-lg items-center"
          >
            <Text className="text-white font-semibold">📤 Exportar</Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={handleClearCache}
            className="flex-1 bg-red-500 py-3 rounded-lg items-center"
          >
            <Text className="text-white font-semibold">🗑️ Limpar</Text>
          </TouchableOpacity>
        </View>

        {/* User Info */}
        <View className="bg-gray-100 dark:bg-gray-800 p-4 rounded-lg mb-4">
          <Text className="text-lg font-bold text-gray-900 dark:text-white mb-2">
            👤 Usuário
          </Text>
          {cache?.user ? (
            <>
              <Text className="text-gray-700 dark:text-gray-300">ID: {cache.user.id}</Text>
              <Text className="text-gray-700 dark:text-gray-300">Email: {cache.user.email}</Text>
              <Text className="text-gray-700 dark:text-gray-300">Nome: {cache.user.nome}</Text>
              <Text className="text-gray-700 dark:text-gray-300">CPF: {cache.user.cpf}</Text>
              <Text className="text-gray-700 dark:text-gray-300">Telefone: {cache.user.telefone}</Text>
            </>
          ) : (
            <Text className="text-gray-500 dark:text-gray-500">Nenhum usuário logado</Text>
          )}
        </View>

        {/* Auth Info */}
        <View className="bg-gray-100 dark:bg-gray-800 p-4 rounded-lg mb-4">
          <Text className="text-lg font-bold text-gray-900 dark:text-white mb-2">
            🔐 Autenticação
          </Text>
          {cache?.auth ? (
            <>
              <Text className="text-gray-700 dark:text-gray-300 text-xs">
                Access Token: {cache.auth.accessToken.substring(0, 30)}...
              </Text>
              <Text className="text-gray-700 dark:text-gray-300 text-xs">
                Refresh Token: {cache.auth.refreshToken.substring(0, 30)}...
              </Text>
              <Text className="text-gray-700 dark:text-gray-300">
                Último login: {new Date(cache.auth.lastLogin).toLocaleString('pt-BR')}
              </Text>
            </>
          ) : (
            <Text className="text-gray-500 dark:text-gray-500">Não autenticado</Text>
          )}
        </View>

        {/* Churches */}
        <View className="bg-gray-100 dark:bg-gray-800 p-4 rounded-lg mb-4">
          <Text className="text-lg font-bold text-gray-900 dark:text-white mb-2">
            ⛪ Igrejas ({cache?.churches.length || 0})
          </Text>
          {cache?.churches && cache.churches.length > 0 ? (
            cache.churches.map((church, index) => (
              <View key={church.id} className="mb-2 pl-2 border-l-2 border-blue-500">
                <Text className="text-gray-900 dark:text-white font-semibold">
                  {index + 1}. {church.nome}
                </Text>
                <Text className="text-gray-600 dark:text-gray-400 text-xs">
                  ID: {church.id}
                </Text>
                <Text className="text-gray-600 dark:text-gray-400 text-xs">
                  CNPJ: {church.cnpj}
                </Text>
                <Text className="text-gray-600 dark:text-gray-400 text-xs">
                  {church.endereco.cidade} - {church.endereco.uf}
                </Text>
              </View>
            ))
          ) : (
            <Text className="text-gray-500 dark:text-gray-500">Nenhuma igreja cadastrada</Text>
          )}
        </View>

        {/* Ministries */}
        <View className="bg-gray-100 dark:bg-gray-800 p-4 rounded-lg mb-4">
          <Text className="text-lg font-bold text-gray-900 dark:text-white mb-2">
            🙏 Ministérios ({cache?.ministries.length || 0})
          </Text>
          {cache?.ministries && cache.ministries.length > 0 ? (
            cache.ministries.map((ministry, index) => (
              <View key={ministry.id} className="mb-2 pl-2 border-l-2 border-green-500">
                <Text className="text-gray-900 dark:text-white font-semibold">
                  {index + 1}. {ministry.nome}
                </Text>
                <Text className="text-gray-600 dark:text-gray-400 text-xs">
                  ID: {ministry.id}
                </Text>
                <Text className="text-gray-600 dark:text-gray-400 text-xs">
                  Igreja: {ministry.idIgreja}
                </Text>
                <Text className="text-gray-600 dark:text-gray-400 text-xs">
                  {ministry.descricao}
                </Text>
              </View>
            ))
          ) : (
            <Text className="text-gray-500 dark:text-gray-500">Nenhum ministério cadastrado</Text>
          )}
        </View>

        {/* Settings */}
        <View className="bg-gray-100 dark:bg-gray-800 p-4 rounded-lg mb-4">
          <Text className="text-lg font-bold text-gray-900 dark:text-white mb-2">
            ⚙️ Configurações
          </Text>
          {cache?.settings ? (
            <>
              <Text className="text-gray-700 dark:text-gray-300">
                Tema: {cache.settings.theme}
              </Text>
              <Text className="text-gray-700 dark:text-gray-300">
                Notificações: {cache.settings.notifications ? 'Ativadas' : 'Desativadas'}
              </Text>
              <Text className="text-gray-700 dark:text-gray-300">
                Idioma: {cache.settings.language}
              </Text>
            </>
          ) : (
            <Text className="text-gray-500 dark:text-gray-500">Configurações padrão</Text>
          )}
        </View>

        {/* Raw JSON Preview */}
        <View className="bg-gray-100 dark:bg-gray-800 p-4 rounded-lg mb-4">
          <Text className="text-lg font-bold text-gray-900 dark:text-white mb-2">
            📄 JSON Completo (Preview)
          </Text>
          <ScrollView horizontal>
            <Text className="text-xs text-gray-600 dark:text-gray-400 font-mono">
              {JSON.stringify(cache, null, 2).substring(0, 500)}...
            </Text>
          </ScrollView>
        </View>
      </View>
    </ScrollView>
  );
}
