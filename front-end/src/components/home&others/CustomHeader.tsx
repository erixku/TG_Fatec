import React from 'react';
import { View, Text, TouchableOpacity, useColorScheme, Pressable } from 'react-native';
import { Bars3Icon, BellIcon } from 'react-native-heroicons/outline';
import { useSafeAreaInsets } from 'react-native-safe-area-context';
import ThemedHarppiaLogo from '../ThemedHarppiaLogo';
import { useNavigation } from 'expo-router';
// ...existing code...

export default function CustomHeader() {
  // Este hook nos dá a altura da área segura (ex: a altura da barra de status)
  const { top } = useSafeAreaInsets();
  const colorScheme = useColorScheme();
  const navigation = useNavigation(); // Removido Drawer
        
  const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a'
  const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8'

  return (
    // A View principal que adiciona o padding do topo para não ficar sob a status bar
    <View style={{ paddingTop: top }} className="mt-5">
      <View className="h-16 flex-row items-center justify-between px-4">
        
        {/* Lado Esquerdo: Ícone de Menu (removido Drawer) */}
        <Pressable onPress={() => (navigation as any).openDrawer?.()}>
          <Bars3Icon size={30} color={baseColor} />
        </Pressable>

        {/* Centro: Seu Logo */}
        <View>
            <View className='scale-75'>
                <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor}/>
            </View>
        </View>

        {/* Lado Direito: Ícone de Chat */}
        <TouchableOpacity>
          <BellIcon size={30} color={baseColor}/>
        </TouchableOpacity>
        
      </View>
    </View>
  );
}