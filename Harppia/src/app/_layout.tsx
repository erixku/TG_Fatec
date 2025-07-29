import "../global.css";

import { useEffect } from 'react';
import { Stack } from 'expo-router';
import { useColorScheme } from 'nativewind';
import { StatusBar } from 'expo-status-bar';
import * as SplashScreen from 'expo-splash-screen';
import { useFonts } from "expo-font";

// Impede que a tela de splash se esconda automaticamente.
SplashScreen.preventAutoHideAsync();

export default function Layout() {
  const { colorScheme } = useColorScheme();

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
    if(fontsLoaded || fontError) {
      SplashScreen.hideAsync();
    }
  }, [fontsLoaded, fontError]);

  return (
    <>
      {/* A StatusBar agora se adapta automaticamente ao tema */}
      <StatusBar style={colorScheme === 'dark' ? 'light' : 'dark'} />

      {/* Usamos Stack em vez de Slot para ter mais controle sobre a navegação, 
        como títulos de página e botões de voltar no futuro. 
        Com 'headerShown: false', o visual inicial do seu app continua o mesmo (sem cabeçalho).
      */}
      <Stack screenOptions={{ headerShown: false }} />
    </>
  );
}