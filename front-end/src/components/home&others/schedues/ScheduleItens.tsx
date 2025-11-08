import React, { useState } from "react";
import { View, Text, useColorScheme, Pressable, ListRenderItem, FlatList, Modal, ScrollView } from 'react-native';
import { CakeIcon, ChevronRightIcon, MusicalNoteIcon, UsersIcon } from "react-native-heroicons/solid";

type GenericSchedule = {
    _id?: string | number;
    key?: string | number;
    title?: string;
    description?: string;
    date?: string;
    time?: string;
    responsible_user?: string;
    type?: string
};

interface ScheduleItensProps{
    schedules: GenericSchedule[];
    commitmend?: boolean;
}

export default function ScheduleItens({schedules, commitmend}: ScheduleItensProps) {
    const [open, setOpen] = useState<boolean>(false)
    const [selectedSchedule, setSelectedSchedule] = useState<GenericSchedule | null>(null)

    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const keyExtractor = (item: GenericSchedule, index: number) => String(item._id ?? item.key ?? index);

    const renderItem: ListRenderItem<GenericSchedule> = ({ item }) => {
        const date = new Date(item.date)
        const day = new Intl.DateTimeFormat("pt-br", {day: "2-digit"}).format(date);
        const month = new Intl.DateTimeFormat("pt-br", {month: "short"}).format(date);

        return (
            <Pressable onPress={() => { setSelectedSchedule(item); setOpen(true); }} className="flex-col w-full h-32 p-4 gap-y-3 items-start justify-center rounded-xl bg-slate-200 dark:bg-slate-700">
                <View className="flex-row items-center justify-between gap-x-5">
                    <View className="flex-col max-w-[70%] items-start gap-2">
                        <Text className="text-xl font-nunito-semibold text-ellipsis overflow-hidden dark:text-blue-100 text-slate-900" numberOfLines={1} ellipsizeMode="tail">{item.title}</Text>
                        <Text className="text-lg font-nunito text-ellipsis overflow-hidden dark:text-blue-100 text-slate-900" numberOfLines={3} ellipsizeMode="tail">{item.description}</Text>
                    </View>
                    <View className="flex-1 items-center gap-2">
                        <View className="flex-col gap-2 w-[50%] pb-2 items-center border-b border-slate-900 dark:border-blue-100">
                            <Text className="text-lg font-nunito-semibold dark:text-blue-100 text-slate-900">{month}</Text>
                            <Text className="font-nunito-semibold dark:text-blue-100 text-slate-900">{day}</Text>
                        </View>
                        <Text className="font-nunito-semibold dark:text-blue-100 text-slate-900">{item.time}</Text>
                    </View>
                </View>
            </Pressable>
        );
    };

    return(
        <View className="w-full">
            <FlatList
                data={schedules}
                keyExtractor={keyExtractor}
                renderItem={renderItem}
                contentContainerClassName="gap-1"
                className="rounded-xl"
                showsVerticalScrollIndicator={true}
            />
            <Modal visible={open} transparent animationType="fade">
                <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
                    <Pressable
                        className="absolute inset-0"
                        onPress={() => { setOpen(false); setSelectedSchedule(null); }}
                    />
                    <View className="w-[90%] h-[75%] py-6 items-start justify-between gap-4 bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl">
                        {selectedSchedule && (
                            <>
                                <View className="gap-2 flex-1 border-b border-slate-900 dark:border-blue-100 pb-4">
                                    <Text className="font-nunito-semibold text-2xl dark:text-blue-100 text-slate-900">{selectedSchedule.title}</Text>
                                    {!!selectedSchedule.description && (
                                        <ScrollView showsVerticalScrollIndicator={false} className="flex-1 w-full">
                                            <Text className="font-nunito-light text-xl mt-2 dark:text-blue-100/90 text-slate-900/90">{selectedSchedule.description}</Text>
                                        </ScrollView>
                                    )}
                                </View>
                                <View className="flex-wrap w-full">
                                    <View className="gap-2 items-center">
                                        <Pressable className="flex-row w-full justify-between">
                                            {selectedSchedule.type=="commitmend"?(    
                                                <View className="flex-row items-center gap-2">
                                                    <MusicalNoteIcon size={16} color={baseColor}/>
                                                    <Text className="font-nunito text-xl dark:text-blue-100 text-slate-900">Músicas elencadas</Text>
                                                </View>
                                            ):(
                                                <View className="flex-row items-center gap-2">
                                                    <CakeIcon size={16} color={baseColor}/>
                                                    <Text className="font-nunito text-xl dark:text-blue-100 text-slate-900">Alimentos do evento</Text>
                                                </View>
                                            )}
                                            <ChevronRightIcon color={baseColor}/>
                                        </Pressable>
                                        <Pressable className="flex-row w-full justify-between">
                                            <View className="flex-row items-center gap-2">
                                                <UsersIcon size={16} color={baseColor}/>
                                                <Text className="font-nunito text-xl dark:text-blue-100 text-slate-900">Presenças confirmadas</Text>
                                            </View>
                                            <ChevronRightIcon color={baseColor}/>
                                        </Pressable>
                                        <Pressable className={"flex items-center justify-center p-2 max-h-10 h-10 w-fit rounded-xl bg-blue-700 dark:bg-blue-300 active:bg-blue-800 dark:active:bg-blue-200 active:scale-110"}>
                                            <Text className="text-xl text-blue-100 dark:text-slate-900"> Comunicar ausência </Text>
                                        </Pressable>
                                    </View>
                                </View>
                                <View className="flex-1 w-full max-h-[10%] justify-end">
                                    {!!selectedSchedule.date && (
                                        <Text className="font-nunito-light mt-3 dark:text-blue-100/70 text-slate-900/70">
                                           Data agendada: {selectedSchedule.date}{!!selectedSchedule.time && (", " + selectedSchedule.time)}
                                        </Text>
                                    )}
                                    {!!selectedSchedule.responsible_user && (
                                        <Text className="font-nunito-light mt-2 dark:text-blue-100/70 text-slate-900/70">Responsável: {selectedSchedule.responsible_user}</Text>
                                    )}
                                </View>
                            </>
                        )}
                    </View>
                </View>
            </Modal>
        </View>
    )
}