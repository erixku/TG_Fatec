import React from "react";
import { View, Text } from "react-native";

export default function Auth() {
    return(
        <View className="flex items-start justify-start bg-indigo-700 gap-y-3 mt-4">
            <Text className="text-center font-nunito-light text-slate-900 dark:text-blue-100"> 
                Tela de Autenticação
            </Text>
        </View>
    )
}