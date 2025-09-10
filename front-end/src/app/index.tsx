import Welcome from "@/pages/Intro";
import { Link } from "expo-router";
import React from "react";
import { Text, View } from "react-native";
import { useSafeAreaInsets } from "react-native-safe-area-context";
import { NavigationContainer } from "@react-navigation/native"

export default function Page() {
  return (
    <View className="flex flex-1 justify-center items-center gap-y-2 w-full bg-slate-200 dark:bg-slate-800">
      <Welcome/>
    </View>
  );
}

// Código para definir o cabeçalho
// function Header() {
//   const { top } = useSafeAreaInsets();
//   return (<></>);
// }
//
// tem que colocar isso na função Page:
// <Header />

// Código para definir o rodapé
// function Footer() {
//   const { bottom } = useSafeAreaInsets();
//   return (<></>);
// }
//
// tem que colocar isso na função Page:
// <Footer />
