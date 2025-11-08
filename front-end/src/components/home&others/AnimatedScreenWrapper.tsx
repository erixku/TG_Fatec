import React, { useEffect } from 'react';
import { useIsFocused } from '@react-navigation/native';
import Animated, { useSharedValue, useAnimatedStyle, withTiming, Easing } from 'react-native-reanimated';

// Este componente recebe o conteúdo da sua tela como 'children'
export default function AnimatedScreenWrapper({ children }) {
  const isFocused = useIsFocused();
  
  // Criamos um valor compartilhado para a opacidade e a posição X
  const opacity = useSharedValue(0);
  const translateX = useSharedValue(20); // Começa 20px para baixo

  // Usamos useEffect para disparar a animação quando a tela ganha foco
  useEffect(() => {
    if (isFocused) {
      // Anima a entrada
      opacity.value = withTiming(1, { duration: 1000, easing: Easing.out(Easing.quad) });
      translateX.value = withTiming(0, { duration: 500, easing: Easing.out(Easing.quad) });
    } else {
      // Reseta para a posição inicial quando a tela perde o foco
      opacity.value = 0;
      translateX.value = 20;
    }
  }, [isFocused]);

  // Criamos o estilo animado que será aplicado à View
  const animatedStyle = useAnimatedStyle(() => {
    return {
      opacity: opacity.value,
      transform: [{ translateX: translateX.value }],
    };
  });

  return (
    // A View animada que envolve o conteúdo da sua tela
    <Animated.View style={[{ flex: 1 }, animatedStyle]}>
      {children}
    </Animated.View>
  );
}