import React, { useState, useEffect } from "react";
import { View, Text, Pressable, ScrollView, Alert } from "react-native";
import { getMinistries } from "@/services/localCache";

interface Ministry {
    id: string;
    nome: string;
    idIgreja: string;
    idCriador: string;
}

export default function MinistryIdDisplay() {
    const [ministries, setMinistries] = useState<Ministry[]>([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        loadMinistries();
    }, []);

    const loadMinistries = async () => {
        try {
            const data = await getMinistries();
            setMinistries(data);
        } catch (error) {
            console.error('Erro ao carregar ministérios:', error);
        } finally {
            setIsLoading(false);
        }
    };

    const showId = (id: string, name: string) => {
        Alert.alert(
            `ID do Ministério: ${name}`,
            id,
            [
                { text: "OK" }
            ]
        );
        console.log(`📋 ID do Ministério "${name}": ${id}`);
    };

    if (isLoading) {
        return (
            <View className="p-4">
                <Text className="text-slate-900 dark:text-blue-100 font-nunito">
                    Carregando ministérios...
                </Text>
            </View>
        );
    }

    if (ministries.length === 0) {
        return (
            <View className="p-4">
                <Text className="text-slate-900 dark:text-blue-100 font-nunito">
                    Nenhum ministério cadastrado
                </Text>
            </View>
        );
    }

    return (
        <View className="w-full gap-y-2 mb-4">
            <Text className="text-sm font-nunito-bold text-slate-900 dark:text-blue-100">
                Ministérios Disponíveis (Toque para ver ID):
            </Text>
            <ScrollView className="max-h-40">
                {ministries.map((ministry) => (
                    <Pressable
                        key={ministry.id}
                        onPress={() => showId(ministry.id, ministry.nome)}
                        className="bg-slate-200 dark:bg-slate-600 p-3 rounded-lg mb-2"
                    >
                        <Text className="font-nunito-bold text-slate-900 dark:text-blue-100">
                            {ministry.nome}
                        </Text>
                        <Text className="font-nunito text-xs text-slate-600 dark:text-slate-300 mt-1">
                            Toque para ver o ID
                        </Text>
                    </Pressable>
                ))}
            </ScrollView>
        </View>
    );
}
