import React from 'react';
import { View, Text, ScrollView, useColorScheme, Pressable } from 'react-native';
import { useRouter, Link } from 'expo-router';
import { MusicalNoteIcon, ArrowLeftIcon } from 'react-native-heroicons/solid';

function ToolsPage() {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const cardBg = colorScheme === 'dark' ? 'bg-slate-700' : 'bg-slate-200';
    const router = useRouter();

    const tools = [
        {
            id: 1,
            name: 'Metrônomo',
            description: 'Metrônomo com vibração e velocidade ajustável',
            icon: MusicalNoteIcon,
            route: '/homeMenu/metronome'
        }
    ];

    return (
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800 p-6">
            {/* Cartão flutuante centralizado com 80% da altura */}
            <View className="bg-slate-50 dark:bg-slate-700 rounded-xl shadow-md p-6 w-full h-[80%]">
                {/* Cabeçalho */}
                <View className="flex-row items-center justify-between mb-6">
                    <View className="flex-row items-center flex-1">
                        <Pressable onPress={() => router.back()} className="mr-4">
                            <ArrowLeftIcon color={baseColor} size={24} />
                        </Pressable>
                        <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                            Ferramentas
                        </Text>
                    </View>
                </View>

                {/* Lista de ferramentas */}
                <ScrollView className="flex-1" showsVerticalScrollIndicator={false}>
                    <View className="gap-4">
                        {tools.map((tool) => {
                            const IconComponent = tool.icon;
                            return (
                                <Link key={tool.id} href={tool.route} asChild>
                                    <Pressable
                                        className={`${cardBg} p-5 rounded-xl flex-row items-center gap-4`}
                                    >
                                        <View className="bg-blue-500 dark:bg-blue-700 p-3 rounded-full">
                                            <IconComponent size={24} color="#fff" />
                                        </View>
                                        <View className="flex-1">
                                            <Text className="font-nunito-bold text-xl text-slate-900 dark:text-blue-100">
                                                {tool.name}
                                            </Text>
                                            <Text className="font-nunito text-sm text-slate-600 dark:text-blue-200 mt-1">
                                                {tool.description}
                                            </Text>
                                        </View>
                                    </Pressable>
                                </Link>
                            );
                        })}
                    </View>
                </ScrollView>
            </View>
        </View>
    );
}

export default ToolsPage;
