import React, { useRef, useState, useEffect } from "react";
import { View, Pressable, useColorScheme, ScrollView, LayoutChangeEvent } from "react-native";
import { useRouter } from "expo-router";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import ResetPassword from "@/components/login&register/ResetPassword";

export default function RegisterChurch() {
    const colorScheme = useColorScheme();
    const router = useRouter();
    const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
    const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

    const handleNext = async () => {
        console.log("Próximo passo");
    }

    return(
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
            <View className="flex-1 p-10 max-h-[45rem] w-full max-w-[80%] justify-between gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
                {/* Cabeçalho */}
                <View className="flex-row items-center justify-center">
                    <Pressable onPress={() => router.back()} className="absolute left-0">
                        <ArrowLeftIcon color={baseColor} />
                    </Pressable>
                    <View className="scale-75">
                        <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor} />
                    </View>
                </View>

                <ResetPassword/>

                {/* Rodapé */}
                <View className="flex justify-center flex-row gap-x-4">
                    <CustomButton label="Próximo" onPress={() => handleNext()}/>
                </View>
            </View>
        </View>
    )
}