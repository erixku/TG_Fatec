import React from 'react';
import { View, Text, Pressable, useColorScheme } from 'react-native';
import { HomeIcon, CalendarIcon, Cog6ToothIcon, MusicalNoteIcon } from 'react-native-heroicons/solid';
import { useRouter } from 'expo-router';

// Um helper para mapear os nomes das rotas para ícones e textos
const tabRoutes = {
  'index': { label: 'Início', icon: HomeIcon },
  'schedule': { label: 'Agenda', icon: CalendarIcon },
  'album': { label: 'Álbum', icon: MusicalNoteIcon },
  'settings': { label: 'Ajustes', icon: Cog6ToothIcon },
};

export default function CustomTabBar({ state, descriptors, navigation }) {
  const router = useRouter();
  const currentRouteName = state.routes[state.index]?.name || 'index';

  return (
    // O Container principal da Tab Bar
    <View className="absolute bottom-5 left-5 right-5 h-20 rounded-full bg-blue-100 shadow-md dark:bg-slate-700 dark:shadow-blue-100 flex-row justify-around items-center">
      {state.routes.map((route, index) => {
        const { options } = descriptors[route.key];
        const routeInfo = tabRoutes[route.name];
        const colorScheme = useColorScheme()

        if (!routeInfo) return null; // Ignora rotas não mapeadas

        const { label, icon: Icon } = routeInfo;
        const isFocused = currentRouteName === route.name;

        const onPress = () => {
          if (!isFocused) {
            navigation.navigate(route.name);
          }
        };
        /*  */
        const color = colorScheme === 'dark'? isFocused? '#3b82f6':'#64748b' : isFocused?'#1e40af':'#94a3b8' // Cor para ativo vs. inativo

        return (
          <Pressable
            key={route.key}
            accessibilityRole="button"
            accessibilityState={isFocused ? { selected: true } : {}}
            accessibilityLabel={options.tabBarAccessibilityLabel || route.name}
            testID={options.tabBarTestID}
            onPress={onPress}
            className="flex-1 items-center justify-center gap-y-1"
          >
            <Icon color={color} size={28} />
            <Text style={{ color }} className="text-xs font-semibold">
              {label}
            </Text>
          </Pressable>
        );
      })}
    </View>
  );
}