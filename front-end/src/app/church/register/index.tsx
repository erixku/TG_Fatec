import React, { useRef, useState, useEffect } from "react";
import { View, Pressable, useColorScheme, ScrollView, LayoutChangeEvent } from "react-native";
import { useRouter } from "expo-router";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import { useFormContext } from "react-hook-form";
import { RegisterChurchFormData } from "@/schemas/registerChurchSchema";
import RegisterFormChurch from "@/components/church/RegisterFormChurch";

export default function RegisterChurch() {
    const colorScheme = useColorScheme();
    const router = useRouter();
    const { trigger, getValues } = useFormContext<RegisterChurchFormData>();
    const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
    const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

    const handleSubmit = async () => {
        const isValid = await trigger();
        if (isValid) {
            console.log("Dados do formulário:", getValues());
            router.push("/");
        } else {
            console.log("Erros de validação:", getValues());
        }
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

                <RegisterFormChurch/>

                {/* Rodapé */}
                <View className="flex justify-center flex-row gap-x-4">
                    <CustomButton label={"Finalzar"} onPress={() => handleSubmit()} />
                </View>
            </View>
        </View>
    )
}