import React, { useState } from "react";
import { View, Text, useColorScheme, Pressable, ListRenderItem, FlatList, Modal, ScrollView } from 'react-native';
import { CakeIcon, ChevronRightIcon, MusicalNoteIcon, UsersIcon } from "react-native-heroicons/solid";

type ChurchObject = {
    uuid?: string | number;
    cnpj?: string | number;
    name?: string;
    denomination?: string;
    other_denomination?: string | null;
};

interface ChurchItensProps{
    churches: ChurchObject[];
}

export default function ChurchItens({churches}: ChurchItensProps) {
    const [open, setOpen] = useState<boolean>(false)
    const [selectedChurch, setSelectedChurch] = useState<ChurchObject | null>(null)

    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const keyExtractor = (item: ChurchObject, index: number) => String(item.uuid ?? index);

    const renderItem: ListRenderItem<ChurchObject> = ({ item }) => {
        return (
            <Pressable onPress={() => { setSelectedChurch(item); setOpen(true); }} className="flex-col w-full h-32 p-4 gap-y-3 items-start justify-center rounded-xl bg-slate-200 dark:bg-slate-700">
                <View className="flex-row items-center justify-between gap-x-5">
                    <View className="flex-col max-w-[70%] items-start gap-2">
                        <Text className="font-nunito-semibold dark:text-blue-100 text-slate-900">{item.name}</Text>
                        <Text className="font-nunito text-ellipsis overflow-hidden dark:text-blue-100 text-slate-900" numberOfLines={3} ellipsizeMode="tail">{item.denomination}</Text>
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
                contentContainerClassName="gap-1"
                className="rounded-xl"
                showsVerticalScrollIndicator={true}
            />
        </View>
    )
}