import 'react-native-gesture-handler'; // deve ser o primeiro import
import "../global.css";
import { useEffect } from 'react';
import { Stack } from 'expo-router';
import { useColorScheme } from 'nativewind';
import { StatusBar } from 'expo-status-bar';
import * as SplashScreen from 'expo-splash-screen';
import * as SystemUI from 'expo-system-ui';
import { useFonts } from "expo-font";
import { KeyboardAvoidingView } from "react-native";
import { DarkTheme, DefaultTheme, ThemeProvider } from "@react-navigation/native"
import React from "react";
import { GestureHandlerRootView } from "react-native-gesture-handler";

// Impede que a tela de splash se esconda automaticamente.
SplashScreen.preventAutoHideAsync();

export default function Layout() {
  const { colorScheme } = useColorScheme();
  const baseColor = colorScheme === 'light' ? '#cbd5e1' : '#1e293b'
  const myTheme = {
    ...(colorScheme === 'dark' ? DarkTheme:DefaultTheme),
    colors: {
      ...(colorScheme === 'dark' ? DarkTheme.colors:DefaultTheme.colors),
      card: colorScheme === 'dark'? '#1e293b' : '#cbd5e1',
      background: colorScheme === 'dark'? '#1e293b' : '#cbd5e1'
    }
  }

  const [fontsLoaded, fontError] = useFonts({
    //Família Nunito
    'Nunito-Ligth': require('../../assets/fonts/Nunito/Nunito-Light.ttf'),
    'Nunito-Regular': require('../../assets/fonts/Nunito/Nunito-Regular.ttf'),
    'Nunito-SemiBold': require('../../assets/fonts/Nunito/Nunito-SemiBold.ttf'),
    'Nunito-Bold': require('../../assets/fonts/Nunito/Nunito-Bold.ttf'),

    //Família Playwrite IT Trad
    'Playwrite-Thin': require('../../assets/fonts/Playwrite_IT_Trad/PlaywriteITTrad-Thin.ttf'),
    'Playwrite-ExtraLight': require('../../assets/fonts/Playwrite_IT_Trad/PlaywriteITTrad-ExtraLight.ttf'),
    'Playwrite-Light': require('../../assets/fonts/Playwrite_IT_Trad/PlaywriteITTrad-Light.ttf'),
    'Playwrite-Regular': require('../../assets/fonts/Playwrite_IT_Trad/PlaywriteITTrad-Regular.ttf'),
  });

  useEffect(() => {
    if (!fontsLoaded) return;

    // assim que as fonts estiverem prontas:
    (async () => {
      try {
        // define a cor do root view (fora da árvore React) — evita o branco entre splash e app
        await SystemUI.setBackgroundColorAsync(myTheme.colors.background);
      } catch (e) {
        // se API não funcionar em alguma SDK/versão, apenas continue
        console.warn("SystemUI.setBackgroundColorAsync failed:", e);
      } finally {
        // esconde a splash apenas quando tudo estiver pronto
        await SplashScreen.hideAsync();
      }
    })();
  }, [fontsLoaded, myTheme.colors.background]);

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      {/* A StatusBar agora se adapta automaticamente ao tema */}
      <StatusBar style={colorScheme === 'dark' ? 'light' : 'dark'} />

      {/* Usamos Stack em vez de Slot para ter mais controle sobre a navegação, 
        como títulos de página e botões de voltar no futuro. 
        Com 'headerShown: false', o visual inicial do seu app continua o mesmo (sem cabeçalho).
      */}
      <ThemeProvider value={myTheme}>
        <Stack
          screenOptions={{
            animation: "slide_from_right",
            headerShown: false,
            contentStyle: { 
              flex: 1, 
              backgroundColor: myTheme.colors.background },
          }}
        > 
          <Stack.Screen name="index" />
        </Stack>
      </ThemeProvider>
    </GestureHandlerRootView>
  );
}