import React, { useState } from "react";
import { View, Text, useColorScheme, Button } from "react-native";
import ThemedHarppiaLogo from '@/components/ThemedHarppiaLogo'
import { CustomButton } from "@/components/CustomButtom";
import Animated, { SlideInLeft, SlideInRight, SlideOutLeft } from "react-native-reanimated";
import { useRouter } from "expo-router";
import splitNames from "@/schemas/functions/splitNames";


export default function StartPage() {
    const colorScheme = useColorScheme();
        
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a'
    const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8'
    const router = useRouter();

    return (
        <View className="flex flex-1 items-center justify-center bg-slate-300 dark:bg-slate-800">
            <Animated.View entering={SlideInLeft.duration(300)} exiting={SlideOutLeft.duration(300)} className="flex p-10 w-[80%] lg:w-[50%] justify-center gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
                <View className="flex items-center justify-center gap-y-2">
                    <Text className="text-center text-xl font-nunito text-slate-900 dark:text-blue-100">Seja bem-vindo ao</Text>
                    <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor} className=""/>
                </View>
                <View className="flex items-center justify-center gap-y-3 mt-4">
                    <Text className="text-center text-lg font-nunito-light text-slate-900 dark:text-blue-100"> 
                        O seu aplicativo de gerenciamento de escalas para os ministérios de louvor em igrejas, voltado para auxiliar os líderes de cada ministérios e seus músicos, fornecendo ferramentas úteis para cada função dentro do ministérios.
                    </Text>
                    <Text className="text-center text-lg font-nunito-light text-slate-900 dark:text-blue-100"> 
                        Para os administradores, é possível verificar a frequência de cada membro nos encontros programados pela igreja, seja em uma consagração, ensaio ou mesmo um culto normal.
                    </Text>
                    <Text className="text-center text-lg font-nunito-light text-slate-900 dark:text-blue-100"> 
                        Se já possui uma conta, entre em nossa aplicação com sua conta registrada, caso seja novo na aplicação, cadastre-se e desfrute de nosso sistema!
                    </Text>
                </View>
                <View className="flex justify-center flex-row gap-x-4 mt-5">
                    <CustomButton label="Entrar" onPress={() => {router.push('/user/login')}}/>
                    <CustomButton label="Cadastrar" onPress={() => {router.push('/user/register')}}/>
                </View>
            </Animated.View>
        </View>
    );
}