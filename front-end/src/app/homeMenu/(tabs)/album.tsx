import React, { useState } from "react";
import { View, Text, useColorScheme, Button, Pressable } from "react-native";
import AnimatedScreenWrapper from "../../../components/home&others/AnimatedScreenWrapper";
import MusicItens, { LayoutType } from "@/components/home&others/album/MuiscsItens";
import { itensMusics } from "@/mocks/itensMusics";
import { itensArtists } from "@/mocks/itensArtists";
import { ChevronRightIcon, UserIcon, UsersIcon, Squares2X2Icon, ListBulletIcon, FunnelIcon } from "react-native-heroicons/solid";
import SortPopUp from "@/components/home&others/SortPopUp";
import { set } from "zod";

export default function HomePage() {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const [isGallery, setIsGallery] = useState<boolean>(false);
    const [isPublic, setIsPublic] = useState<boolean>(false);
    const [layout, setLayout] = useState<LayoutType>('list');
    const [isSortVisible, setIsSortVisible] = useState<boolean>(false);
    const [sortOption, setSortOption] = useState<string>('Mais Recentes');
    
    return (
        <AnimatedScreenWrapper>
            <View className="flex-col flex-1 items-center mt-2 bg-slate-300 dark:bg-slate-800">
                <View className="flex-1 w-full items-center pb-28">
                    <View className="flex py-10 h-full w-[90%] lg:w-[50%] justify-center gap-y-4 rounded-xl">
                        <View className=" flex-1 flex-col gap-y-2">
                            <View className="flex-row justify-between">
                                <Pressable className="flex-row items-center gap-x-2" onPress={() => setIsSortVisible(true)}>
                                    <Text className="text-2xl font-nunito-semibold dark:text-blue-100 text-slate-900">{sortOption}</Text>
                                    <ChevronRightIcon size={20} color={baseColor} />
                                </Pressable>
                                <View className="flex-row items-center gap-x-2">
                                    <FunnelIcon color={baseColor} size={20} />
                                    <Pressable onPress={() => setIsPublic(!isPublic)}>
                                        {isPublic ? <UsersIcon color={baseColor} size={20} /> : <UserIcon color={baseColor} size={20} />}
                                    </Pressable>
                                    <Pressable onPress={() => {
                                        setIsGallery(!isGallery);
                                        if (layout === 'list') setLayout('grid');
                                        else setLayout('list');
                                    } }>
                                        {isGallery ? <Squares2X2Icon color={baseColor} size={20} /> : <ListBulletIcon color={baseColor} size={20} />}
                                    </Pressable>
                                </View>
                            </View>
                            <MusicItens musics={itensMusics} artists={itensArtists} public={isPublic} gallery={isGallery} layout={layout}/>
                        </View>
                    </View>
                </View>
            </View>
            <SortPopUp 
                visible={isSortVisible}
                options={['Mais Recentes', 'Mais Antigos', 'A-Z', 'Z-A']}
                onClose={() => setIsSortVisible(false)}
                onSelect={(option:string) => {
                    // Lógica de ordenação aqui
                    setSortOption(option);
                    console.log('Opção selecionada:', option);
                }}
            />
        </AnimatedScreenWrapper>
    );
}