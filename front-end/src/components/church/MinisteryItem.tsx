
import { ChurchType } from "@/mocks/itensChurch";
import { MinisteryType } from "@/mocks/itensMinistery";
import React from "react";
import {View, Text, FlatList, ListRenderItem, useColorScheme, Image} from "react-native";
import { PhotoIcon } from "react-native-heroicons/solid";

interface MinisteryItemProps{
    ministery: {
        id: string;
        uuid: string;
        nome: string;
        descricao: string;
        foto?: string;
        memberCount?: number;
        church: ChurchType | null;
    };
}

export default function MinisteryItem({ministery}: MinisteryItemProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    console.log('🎵 MinisteryItem - Ministério:', ministery.nome);
    console.log('🖼️ MinisteryItem - Foto:', ministery.foto);
    console.log('🖼️ MinisteryItem - Tem foto?:', !!ministery.foto);
    console.log('🖼️ MinisteryItem - Tipo da foto:', typeof ministery.foto);

    return(
        <View className="flex-row h-24 p-2 gap-2 items-center bg-blue-100 dark:bg-slate-700 rounded-xl">
            <View className="w-[35%] items-center justify-center rounded-xl h-full bg-slate-200 dark:bg-slate-800 overflow-hidden">
                {ministery.foto ? (
                    <Image 
                        source={{uri: ministery.foto}} 
                        className="h-full w-full"
                        resizeMode="cover"
                        onError={(error) => {
                            console.error('❌ MinisteryItem - Erro ao carregar imagem:', error.nativeEvent.error);
                            console.error('   URI:', ministery.foto);
                        }}
                        onLoad={() => console.log('✅ MinisteryItem - Imagem carregada com sucesso:', ministery.nome)}
                    />
                ) : (
                    <PhotoIcon color={baseColor} size={36}/>
                )}
            </View>
            <View className="flex-1 h-full justify-between">
                <View>
                    <Text className="font-nunito-bold text-xl dark:text-blue-100 text-slate-900" ellipsizeMode="tail" numberOfLines={1}>{ministery.nome}</Text>
                    <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900" ellipsizeMode="tail" numberOfLines={1}>{ministery.church?.nome}</Text>
                </View>
                <Text className="font-nunito-light dark:text-blue-100 text-slate-900">
                    {ministery.memberCount !== undefined ? `${ministery.memberCount} ${ministery.memberCount === 1 ? 'membro' : 'membros'}` : '0 membros'}
                </Text>
            </View>
        </View>
    )
}