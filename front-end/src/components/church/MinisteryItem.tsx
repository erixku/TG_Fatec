
import { ChurchType } from "@/mocks/itensChurch";
import { MinisteryType } from "@/mocks/itensMinistery";
import React from "react";
import {View, Text, FlatList, ListRenderItem, useColorScheme} from "react-native";
import { PhotoIcon } from "react-native-heroicons/solid";

interface MinisteryItemProps{
    ministery: MinisteryType & { church: ChurchType | null; };
}

export default function MinisteryItem({ministery}: MinisteryItemProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    return(
        <View className="flex-row h-24 p-2 gap-2 items-center bg-blue-100 dark:bg-slate-700 rounded-xl">
            <View className="w-[35%] items-center justify-center rounded-xl h-full bg-slate-200 dark:bg-slate-800">
                <PhotoIcon color={baseColor} size={36}/>
            </View>
            <View className="flex-1 h-full justify-between">
                <View>
                    <Text className="font-nunito-bold text-xl dark:text-blue-100 text-slate-900" ellipsizeMode="tail" numberOfLines={1}>{ministery.nome}</Text>
                    <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900" ellipsizeMode="tail" numberOfLines={1}>{ministery.church?.nome}</Text>
                </View>
                <Text className="font-nunito-light dark:text-blue-100 text-slate-900">20 membros</Text>
            </View>
        </View>
    )
}