import React from "react";
import { View, useColorScheme, Pressable } from "react-native";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import { useRouter, useLocalSearchParams } from "expo-router";
import Auth from "@/components/login&register/Auth";

export default function AuthScreen() {
  const colorScheme = useColorScheme();
  const router = useRouter();
  const params = useLocalSearchParams();
  const { email, telefone, rota } = useLocalSearchParams<{ email?: string|null; telefone?: string|null; rota: string }>();

  const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
  const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

  return (
    <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
      <View className="flex p-10 h-[45rem] w-full max-w-[80%] justify-between items-center gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
        <View className="flex-1 w-full justify-between items-strech gap-y-4">
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
          <Auth email={email} telefone={telefone} />

          {/* Rodapé */}
          <View className="flex justify-center flex-row gap-x-4 mt-5">
            <CustomButton
              label="Próximo"
              onPress={() => {rota === "register"? router.push("/church") : router.push("/user/login/ResetPassword")}}
            />
          </View>
        </View>
      </View>
    </View>
  );
}