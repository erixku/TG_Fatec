import 'react-native-gesture-handler'; // deve ser o primeiro import
import "../global.css";
import { useEffect, useState } from 'react';
import { Stack, useRouter, useSegments } from 'expo-router';
import { useColorScheme } from 'nativewind';
import { StatusBar } from 'expo-status-bar';
import * as SplashScreen from 'expo-splash-screen';
import * as SystemUI from 'expo-system-ui';
import { useFonts } from "expo-font";
import { KeyboardAvoidingView } from "react-native";
import { DarkTheme, DefaultTheme, ThemeProvider } from "@react-navigation/native"
import React from "react";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import { checkAuthentication } from '@/api/loginUser';

// Impede que a tela de splash se esconda automaticamente.
SplashScreen.preventAutoHideAsync();

// Carrega polyfills apenas em runtime (evita executar require de módulos
// nativos durante a análise de rotas pelo Metro bundler.

export default function Layout() {
  const router = useRouter();
  const segments = useSegments();
  const [isAuthChecked, setIsAuthChecked] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // Load polyfills dynamically at runtime so Metro route parsing doesn't
  // attempt to require native modules during bundling.
  useEffect(() => {
    (async () => {
      try {
        const poly = await import('../polyfills');
        if (poly && typeof poly.initBlobPolyfills === 'function') {
          await poly.initBlobPolyfills();
        }
      } catch (e) {
        // eslint-disable-next-line no-console
        console.info('polyfills not loaded at runtime:', e);
      }
    })();
  }, []);

  // Verifica autenticação ao iniciar o app
  useEffect(() => {
    checkAuthOnStartup();
  }, []);

  // Redireciona baseado no estado de autenticação
  useEffect(() => {
    if (!isAuthChecked) return;

    const inAuthGroup = segments[0] === 'user' || segments[0] === 'church';
    const inHomeGroup = segments[0] === 'homeMenu';
    const inIndexPage = segments.length === 0; // Página inicial

    console.log('📍 Segmentos atuais:', segments);
    console.log('🔐 Autenticado:', isAuthenticated);
    console.log('📂 Grupo:', { inAuthGroup, inHomeGroup, inIndexPage });

    // Permite navegação livre para telas de auth (user/church) a partir da página inicial
    if (inAuthGroup) {
      console.log('✅ Navegação para tela de autenticação permitida');
      return;
    }

    // Se mudou para homeMenu, reverifica autenticação para garantir estado atualizado
    if (inHomeGroup) {
      console.log('🔄 Reverificando autenticação ao entrar em homeMenu...');
      checkAuthOnStartup();
      return; // Aguarda a reverificação antes de redirecionar
    }

    if (!isAuthenticated && inHomeGroup) {
      // Usuário não autenticado tentando acessar home
      console.log('➡️ Redirecionando para login (não autenticado)');
      router.replace('/user/login');
    }
    // Removido o redirecionamento automático de usuários autenticados das telas de auth
    // Isso permite que usuários autenticados naveguem livremente para login/registro se desejarem
  }, [isAuthChecked, isAuthenticated, segments]);

  const checkAuthOnStartup = async () => {
    try {
      console.log('🔍 Verificando autenticação ao iniciar app...');
      const isAuth = await checkAuthentication();
      
      if (isAuth) {
        console.log('✅ Usuário autenticado');
        setIsAuthenticated(true);
      } else {
        console.log('❌ Usuário não autenticado');
        setIsAuthenticated(false);
      }
    } catch (error) {
      console.warn('⚠️ Erro ao verificar autenticação:', error);
      setIsAuthenticated(false);
    } finally {
      setIsAuthChecked(true);
    }
  };

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
    if (!fontsLoaded || !isAuthChecked) return;

    // assim que as fonts estiverem prontas e auth verificada:
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
  }, [fontsLoaded, isAuthChecked, myTheme.colors.background]);

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