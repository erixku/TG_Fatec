import React from "react";
import { View, useColorScheme, Pressable } from "react-native";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import { SharedElement } from "react-navigation-shared-element";
import { useRouter } from "expo-router";
import EnterChurchCode from "@/components/church/EnterChurchCode";

export default function AuthScreen() {
  const colorScheme = useColorScheme();
  const router = useRouter();

  const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
  const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

  return (
    <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
      <View className="flex p-10 h-[45rem] w-full max-w-[80%] justify-between items-center gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
        <SharedElement id="register-container">
          <View className="flex-1 w-full justify-between items-center gap-y-4">
            {/* Cabeçalho */}
            <View className="flex flex-row items-center justify-center gap-y-2">
              <Pressable onPress={() => router.back()}>
                <ArrowLeftIcon color={baseColor} />
              </Pressable>
              <View className="scale-75">
                <ThemedHarppiaLogo
                  baseColor={baseColor}
                  contrastColor={contrastColor}
                />
              </View>
            </View>

            {/* Formulário */}
            <EnterChurchCode/>

            {/* Rodapé */}
            <View className="flex justify-center flex-row gap-x-4 mt-5">
              <CustomButton
                label="Finalizar"
                onPress={() => router.push("/")}
              />
            </View>
          </View>
        </SharedElement>
      </View>
    </View>
  );
}