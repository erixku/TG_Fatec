import React, { useState } from "react";
import { View, Text, Pressable } from "react-native";
import { CustomMaskedInput, CustomTextInput } from "../CustomInput";

export default function EnterChurchCode() {
    return(
        <View className="flex flex-1 w-full items-start justify-center gap-y-2 mt-4">
            <View className="mb-2 gap-y-1">
                <Text className="text-xl font-nunito-bold text-slate-900 dark:text-blue-100"> 
                    Código de Ministério
                </Text>
                <Text className="font-nunito-light text-slate-900 dark:text-blue-100">
                    Informa o código de entrada para o ministério de sua igreja
                </Text>
            </View>
            <View className="w-full gap-y-3">                
                <CustomTextInput 
                    placeholder="Informe o código de 6 dígitos"
                />
            </View>
        </View>
    )
}