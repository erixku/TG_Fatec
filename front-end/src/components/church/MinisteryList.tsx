import { MinisteryType } from "@/mocks/itensMinistery";
import React from "react";
import { View, Text, useColorScheme, Pressable, ListRenderItem, FlatList } from 'react-native';
import { PhotoIcon } from "react-native-heroicons/solid";
import { Image } from "expo-image";

interface MinisteryListProps {
    ministries: MinisteryType[];
    selectedMinistry: MinisteryType | null;
    onSelectMinistry: (ministry: MinisteryType) => void;
}

export default function MinisteryList({ ministries, selectedMinistry, onSelectMinistry }: MinisteryListProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const keyExtractor = (item: MinisteryType, index: number) => String(item.uuid ?? index);

    const renderItem: ListRenderItem<MinisteryType> = ({ item }) => {
        const isSelected = selectedMinistry?.uuid === item.uuid;
        // @ts-ignore - item pode ter foto do cache
        const ministryPhoto = item.foto;

        return (
            <Pressable 
                onPress={() => onSelectMinistry(item)} 
                className={`flex-row h-24 p-2 gap-2 items-center rounded-xl ${
                    isSelected 
                        ? 'bg-blue-200 dark:bg-blue-900 border-2 border-blue-500' 
                        : 'bg-blue-100 dark:bg-slate-800'
                }`}
            >
                <View className="w-[35%] items-center justify-center rounded-xl h-full bg-slate-200 dark:bg-slate-700 overflow-hidden">
                    {ministryPhoto ? (
                        <Image 
                            source={{ uri: ministryPhoto }} 
                            style={{ width: '100%', height: '100%' }}
                            contentFit="cover"
                        />
                    ) : (
                        <PhotoIcon color={baseColor} size={36} />
                    )}
                </View>
                <View className="flex-1 h-full justify-between py-1">
                    <View>
                        <Text 
                            className="font-nunito-bold text-xl dark:text-blue-100 text-slate-900" 
                            ellipsizeMode="tail" 
                            numberOfLines={1}
                        >
                            {item.nome}
                        </Text>
                        <Text 
                            className="font-nunito text-sm dark:text-blue-100 text-slate-700" 
                            ellipsizeMode="tail" 
                            numberOfLines={1}
                        >
                            {item.codigo}
                        </Text>
                    </View>
                    <Text className="font-nunito-light text-sm dark:text-blue-100 text-slate-900">
                        {item.descricao}
                    </Text>
                </View>
            </Pressable>
        );
    };

    if (ministries.length === 0) {
        return (
            <View className="flex-1 items-center justify-center">
                <Text className="font-nunito-semibold text-lg text-slate-500 dark:text-slate-400">
                    Nenhum ministério encontrado para esta igreja
                </Text>
            </View>
        );
    }

    return (
        <View className="w-full h-full">
            <FlatList
                data={ministries}
                keyExtractor={keyExtractor}
                renderItem={renderItem}
                contentContainerClassName="gap-3 pb-4"
                showsVerticalScrollIndicator={true}
            />
        </View>
    );
}
