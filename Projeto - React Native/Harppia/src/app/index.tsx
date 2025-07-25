import Welcome from "@/pages/Welcome";
import { Link } from "expo-router";
import React from "react";
import { Text, View } from "react-native";
import { useSafeAreaInsets } from "react-native-safe-area-context";

export default function Page() {
  return (
    <View className="flex flex-1">
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
