import React from "react";
import { View, Text, Pressable, useColorScheme } from "react-native";
import { useRouter, useLocalSearchParams } from "expo-router";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { CustomTextInput } from "@/components/CustomInput";
import {PlusIcon, HomeIcon, UserGroupIcon} from "react-native-heroicons/solid"

export default function ChurchMenu() {
    const router = useRouter();
    const colorScheme = useColorScheme();
    const { userId } = useLocalSearchParams<{ userId?: string }>();
    
    console.log('🏠 [ChurchMenu] userId recebido:', userId);
    
    const baseColor:string = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const contrastColor:string = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8';
    
    return (
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
            <View className="flex p-10 h-[45rem] w-[80%] lg:w-[50%] justify-between items-center gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
                <View className="flex flex-row items-center justify-center gap-y-2">
                    <Pressable onPress={() => router.back()}>
                        <ArrowLeftIcon color={baseColor}/>
                    </Pressable>
                    <View className="scale-75">
                        <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor}/>
                    </View>
                </View>
                <View className="flex flex-1 w-full items-center justify-center gap-y-3"> 
                    <Pressable onPress={() => {
                        console.log('➡️ [ChurchMenu] Navegando para /church/register com userId:', userId);
                        router.push({
                            pathname: "/church/register",
                            params: { userId: userId || "" }
                        });
                    }} className="flex-row p-2 px-6 h-10 gap-x-4 w-[90%] items-center justify-around bg-blue-400 dark:bg-blue-600 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl">
                        <PlusIcon color={baseColor}/>
                        <Text className="font-nunito text-lg text-slate-900 dark:text-blue-100">Cadastrar nova igreja</Text>
                    </Pressable>

                    <Pressable onPress={() => router.push("/church/login")} className="flex-row p-2 px-6 h-10 gap-x-4 w-[90%] items-center justify-around bg-blue-400 dark:bg-blue-600 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl">
                        <UserGroupIcon color={baseColor}/>
                        <Text className="font-nunito text-lg text-slate-900 dark:text-blue-100">Entar em uma igreja</Text>
                    </Pressable>
                    
                    <View className="flex-row items-center">
                        <View className="flex-1 h-0.5 bg-slate-900 dark:bg-blue-100"/>
                    </View>
                    <Text className="text-center text-lg font-nunito-light text-slate-900 dark:text-blue-100">Caso deseje entrar posteriormente, pode retornar à página inicial</Text>
                    <Pressable onPress={() => router.push("/")} className="flex-row p-2 px-6 h-10 gap-x-4 w-[90%] items-center justify-around bg-blue-400 dark:bg-blue-600 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl">
                        <HomeIcon color={baseColor}/> 
                        <Text className="font-nunito text-lg text-slate-900 dark:text-blue-100">Retornar à página inicial</Text>
                    </Pressable>
                </View>
            </View>
        </View>
    );
}
