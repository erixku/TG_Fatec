import React, { useState } from "react";
import { View, Text, Pressable, Alert } from "react-native";
import { CustomTextInput } from "../CustomInput";
import { joinMinistryByCode } from "@/utils/joinMinistryByCode";
import { useRouter } from "expo-router";

export default function EnterChurchCode() {
    const [ministryCode, setMinistryCode] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const router = useRouter();

    const handleJoinMinistry = async () => {
        if (!ministryCode.trim()) {
            Alert.alert("Atenção", "Por favor, informe o código do ministério");
            return;
        }

        setIsLoading(true);
        const result = await joinMinistryByCode(ministryCode.trim());
        setIsLoading(false);

        if (result.success) {
            Alert.alert("Sucesso! 🎉", result.message, [
                {
                    text: "OK",
                    onPress: () => {
                        setMinistryCode("");
                        router.replace("/homeMenu/(tabs)/home");
                    }
                }
            ]);
        } else {
            Alert.alert("Erro", result.message);
        }
    };

    return(
        <View className="flex flex-1 w-full items-start justify-center gap-y-2 mt-4">
            <View className="mb-2 gap-y-1">
                <Text className="text-xl font-nunito-bold text-slate-900 dark:text-blue-100"> 
                    Código de Ministério
                </Text>
                <Text className="font-nunito-light text-slate-900 dark:text-blue-100">
                    Informe o ID do ministério para entrar como líder
                </Text>
            </View>
            
            <View className="w-full gap-y-3">                
                <CustomTextInput 
                    placeholder="Cole o ID do ministério aqui"
                    value={ministryCode}
                    onChangeText={setMinistryCode}
                    editable={!isLoading}
                />
                
                <Pressable 
                    onPress={handleJoinMinistry}
                    disabled={isLoading}
                    className={`w-full py-3 rounded-lg ${isLoading ? 'bg-gray-400' : 'bg-blue-600'}`}
                >
                    <Text className="text-white font-nunito-bold text-center">
                        {isLoading ? 'Entrando...' : 'Entrar no Ministério'}
                    </Text>
                </Pressable>
            </View>
        </View>
    )
}