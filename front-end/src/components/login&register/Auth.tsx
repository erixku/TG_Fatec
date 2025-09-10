import React, { useState } from "react";
import { View, Text, Pressable } from "react-native";
import { CustomMaskedInput, CustomTextInput } from "../CustomInput";
import { CustomButton } from "../CustomButtom";

export default function Auth() {
    const [isEmail, setIsEmail] = useState<boolean>(true);

    return(
        <View className="flex flex-1 w-full items-start justify-center gap-y-2 mt-4">
            <View className="mb-2 gap-y-1">
                <Text className="text-xl font-nunito-bold text-slate-900 dark:text-blue-100"> 
                    Código de Confirmação
                </Text>
                <Text className="font-nunito-light text-slate-900 dark:text-blue-100">
                    Você receberá um código {isEmail ? "na caixa de entrada do seu e-mail" : "as mensagens de seu telefone"}
                </Text>
            </View>
            <View className="w-full gap-y-3">
                {isEmail ? (
                    <CustomTextInput 
                        label="E-mail"
                        required
                        placeholder="Digite seu e-mail"
                    />
                ) : (
                    <CustomMaskedInput
                        label="Telefone"
                        placeholder="(DDD) 9####-####"
                        required
                        onChangeText={(formatted, extracted) => {console.log(extracted)}}
                        keyboardType="numeric"
                        mask="(99) 99999-9999"
                    />
                )}
                
                <CustomTextInput 
                    placeholder="Informe o código de 6 dígitos"
                />
            </View>
            <View className="gap-y-1">
                <Pressable onPress={() => setIsEmail(!isEmail)}>
                    <View className="h-5">
                        <Text className="font-nunito-light text-blue-700 dark:text-blue-300">
                            {isEmail ? "Receber código por SMS" : "Receber código por e-mail"}
                        </Text>
                    </View>
                </Pressable>

                <Pressable>
                    <View className="h-5">
                        <Text className="font-nunito-light text-blue-700 dark:text-blue-300">
                            Reenviar código
                        </Text>
                    </View>
                </Pressable>
            </View>
        </View>
    )
}