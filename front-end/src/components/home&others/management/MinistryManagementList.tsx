import React from 'react';
import { View, Text, FlatList, Pressable, useColorScheme, Alert, Share } from 'react-native';
import { BuildingLibraryIcon, UserGroupIcon, ClipboardDocumentIcon } from 'react-native-heroicons/solid';
import { MinistryManagementData } from '@/app/homeMenu/management';

interface MinistryManagementListProps {
    ministries: MinistryManagementData[];
    onRefresh: () => void;
}

export default function MinistryManagementList({ ministries, onRefresh }: MinistryManagementListProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8';

    const handleCopyCode = (code: string, name: string) => {
        Alert.alert(
            'Código do Ministério',
            `${code}\n\nToque e segure para copiar`,
            [{ text: 'OK' }]
        );
    };

    const handleShareCode = async (code: string, name: string) => {
        try {
            await Share.share({
                message: `Junte-se ao ministério ${name}! Use o código: ${code}`,
            });
        } catch (error) {
            console.error('Erro ao compartilhar:', error);
        }
    };

    const renderMinistryItem = ({ item }: { item: MinistryManagementData }) => {
        return (
            <View className="mb-4 p-4 rounded-xl bg-slate-100 dark:bg-slate-800">
                {/* Nome do ministério */}
                <Text className="font-nunito-bold text-lg text-slate-900 dark:text-blue-100 mb-2">
                    {item.nome}
                </Text>

                {/* Igreja */}
                <View className="flex-row items-center gap-2 mb-2">
                    <BuildingLibraryIcon color={contrastColor} size={16} />
                    <Text className="font-nunito text-sm text-slate-900 dark:text-blue-100">
                        {item.churchName}
                    </Text>
                </View>

                {/* Número de membros */}
                <View className="flex-row items-center gap-2 mb-3">
                    <UserGroupIcon color={contrastColor} size={16} />
                    <Text className="font-nunito text-sm text-slate-900 dark:text-blue-100">
                        {item.memberCount} {item.memberCount === 1 ? 'membro' : 'membros'}
                    </Text>
                </View>

                {/* Código do ministério */}
                <View className="flex-row items-center justify-between p-3 rounded-xl bg-slate-200 dark:bg-slate-600">
                    <View className="flex-1">
                        <Text className="font-nunito text-xs text-slate-900/70 dark:text-blue-100/70 mb-1">
                            Código do Ministério
                        </Text>
                        <Text className="font-nunito-bold text-base text-slate-900 dark:text-blue-100" numberOfLines={1}>
                            {item.codigo}
                        </Text>
                    </View>
                    <View className="flex-row gap-2">
                        <Pressable 
                            onPress={() => handleCopyCode(item.codigo, item.nome)}
                            className="p-2 rounded-xl bg-blue-700 dark:bg-blue-300"
                        >
                            <ClipboardDocumentIcon color={colorScheme === 'dark' ? '#0f172a' : '#dbeafe'} size={20} />
                        </Pressable>
                    </View>
                </View>
            </View>
        );
    };

    if (ministries.length === 0) {
        return (
            <View className="flex-1 items-center justify-center">
                <BuildingLibraryIcon color={baseColor} size={64} opacity={0.3} />
                <Text className="font-nunito text-lg text-slate-900/50 dark:text-blue-100/50 mt-4">
                    Nenhum ministério encontrado
                </Text>
            </View>
        );
    }

    return (
        <FlatList
            data={ministries}
            keyExtractor={(item) => item.id}
            renderItem={renderMinistryItem}
            showsVerticalScrollIndicator={false}
            contentContainerStyle={{ paddingBottom: 20 }}
        />
    );
}
