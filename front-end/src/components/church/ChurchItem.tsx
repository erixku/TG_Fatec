import { ChurchType } from "@/mocks/itensChurch";
import React, { useState } from "react";
import { View, Text, useColorScheme, Pressable, ListRenderItem, FlatList, Modal, ScrollView } from 'react-native';
import { CakeIcon, ChevronRightIcon, MusicalNoteIcon, UsersIcon } from "react-native-heroicons/solid";
import { Image } from "expo-image";

type ChurchObject = {
    uuid?: string | number;
    cnpj?: string | number;
    name?: string;
    denomination?: string;
    other_denomination?: string | null;
};

interface ChurchItensProps{
    churches: ChurchType[];
    onSelectChurch?: (church: ChurchType) => void;
}

export default function ChurchItens({churches, onSelectChurch}: ChurchItensProps) {
    const [open, setOpen] = useState<boolean>(false)
    const [selectedChurch, setSelectedChurch] = useState<ChurchObject | null>(null)

    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const keyExtractor = (item: ChurchType, index: number) => String(item.uuid ?? index);

    const handleChurchPress = (item: ChurchType) => {
        if (onSelectChurch) {
            onSelectChurch(item);
        } else {
            setSelectedChurch(item);
            setOpen(true);
        }
    };

    const renderItem: ListRenderItem<ChurchType> = ({ item }) => {
        // @ts-ignore - item pode ter foto do cache
        const churchPhoto = item.foto;
        
        return (
            <Pressable onPress={() => handleChurchPress(item)} className="flex-col w-full h-48 items-start justify-center rounded-xl bg-slate-100 dark:bg-slate-800">
                <View className="flex-1 w-full rounded-t-xl overflow-hidden bg-slate-200 dark:bg-slate-700 items-center justify-center">
                    {churchPhoto ? (
                        <Image 
                            source={{ uri: churchPhoto }} 
                            style={{ width: '100%', height: '100%' }}
                            contentFit="cover"
                        />
                    ) : (
                        <Image 
                            source={require('@/assets/churchMockupTest.png')} 
                            style={{ width: '100%', height: '100%' }}
                            contentFit="cover"
                        />
                    )}
                </View>
                <View className="flex-row items-center justify-between gap-x-5  p-4 ">
                    <View className="flex-col w-full items-start gap-2">
                        <Text className="font-nunito-semibold dark:text-blue-100 text-slate-900" numberOfLines={1}>{item.nome}</Text>
                        <Text className="font-nunito text-ellipsis overflow-hidden dark:text-blue-100 text-slate-900" numberOfLines={3} ellipsizeMode="tail">{item.denominacao}</Text>
                    </View>
                </View>
            </Pressable>
        );
    };

    return(
        <View className="w-full">
            <FlatList
                data={churches}
                keyExtractor={keyExtractor}
                renderItem={renderItem}
                contentContainerClassName="gap-2"
                className="rounded-xl"
                showsVerticalScrollIndicator={true}
            />
        </View>
    )
}