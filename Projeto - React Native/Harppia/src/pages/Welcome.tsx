import React from "react";
import { View, Text, useColorScheme } from "react-native";
import ThemedHarppiaLogo from '@/components/ThemedHarppiaLogo'

export default function Welcome() {
    const colorScheme = useColorScheme();

    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a'
    const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8'

    return(
        <View className="flex flex-1 items-center justify-center w-[80%]">
            <View className="flex p-10 justify-center rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
                <View className="flex items-center justify-center gap-y-2">
                    <Text className="text-center font-nunito text-slate-900 dark:text-blue-100">Seja bem-vindo ao</Text>
                    <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor}/>
                </View>
                <View className="flex items-center justify-center gap-y-3 mt-4">
                    <Text className="text-center font-nunito-light text-slate-900 dark:text-blue-100"> 
                        O seu aplicativo de gerenciamento de escalas para os ministérios de louvor em igrejas, voltado para auxiliar os líderes de cada ministérios e seus músicos, fornecendo ferramentas úteis para cada função dentro do ministérios.
                    </Text>
                    <Text className="text-center font-nunito-light text-slate-900 dark:text-blue-100"> 
                        Para os administradores, é possível verificar a frequência de cada membro nos encontros programados pela igreja, seja em uma consagração, ensaio ou mesmo um culto normal.
                    </Text>
                    <Text className="text-center font-nunito-light text-slate-900 dark:text-blue-100"> 
                        Se já possui uma conta, entre em nossa aplicação com sua conta registrada, caso seja novo na aplicação, cadastre-se e desfrute de nosso sistema!
                    </Text>
                </View>
            </View>
        </View>
    );
}