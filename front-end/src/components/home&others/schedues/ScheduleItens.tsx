import React, { useState, useEffect } from "react";
import { View, Text, useColorScheme, Pressable, ListRenderItem, FlatList, Modal, ScrollView, Alert } from 'react-native';
import { CakeIcon, ChevronRightIcon, MusicalNoteIcon, UsersIcon, XMarkIcon } from "react-native-heroicons/solid";
import ScheduleMusicListModal from './ScheduleMusicListModal';
import ConfirmedMembersModal from './ConfirmedMembersModal';
import AbsenceJustificationModal from './AbsenceJustificationModal';
import { getUser, loadCache, saveCache } from '@/services/localCache';

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
    const [showMusicModal, setShowMusicModal] = useState(false);
    const [showMembersModal, setShowMembersModal] = useState(false);
    const [showAbsenceModal, setShowAbsenceModal] = useState(false);
    const [userRole, setUserRole] = useState<'lider' | 'ministro' | 'levita'>('levita');
    const [ministryId, setMinistryId] = useState<string>('');

    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    useEffect(() => {
        if (selectedSchedule) {
            loadUserRole();
        }
    }, [selectedSchedule]);

    const loadUserRole = async () => {
        try {
            const cache = await loadCache();
            const user = await getUser();
            
            if (!user || !selectedSchedule) return;

            // Busca o ministério do agendamento ou compromisso
            let ministryIdFound = '';
            const schedule = cache.schedules.find(s => s.id === String(selectedSchedule._id));
            const commitment = cache.commitments.find(c => c.id === String(selectedSchedule._id));
            
            if (schedule) {
                ministryIdFound = schedule.idMinisterio;
            } else if (commitment) {
                ministryIdFound = commitment.idMinisterio;
            }
            
            console.log('📌 ScheduleItens - ministryIdFound:', ministryIdFound);
            console.log('📌 ScheduleItens - selectedSchedule._id:', selectedSchedule._id);
            
            if (ministryIdFound) {
                setMinistryId(ministryIdFound);
                
                // Busca o papel do usuário no ministério
                const membership = cache.ministryMembers.find(
                    m => m.idUsuario === user.id && m.idMinisterio === ministryIdFound
                );
                
                if (membership) {
                    setUserRole(membership.papel);
                    console.log('📌 ScheduleItens - userRole:', membership.papel);
                }
            }
        } catch (error) {
            console.error('Erro ao carregar papel do usuário:', error);
        }
    };

    const keyExtractor = (item: GenericSchedule, index: number) => String(item._id ?? item.key ?? index);

    const renderItem: ListRenderItem<GenericSchedule> = ({ item }) => {
        // Garante que a data está no formato correto (YYYY-MM-DD ou ISO)
        const dateStr = item.date || '';
        const date = dateStr.includes('T') ? new Date(dateStr) : new Date(dateStr + 'T00:00:00');
        const day = new Intl.DateTimeFormat("pt-br", {day: "2-digit"}).format(date);
        const month = new Intl.DateTimeFormat("pt-br", {month: "short"}).format(date);

        return (
            <Pressable onPress={() => { setSelectedSchedule(item); setOpen(true); }} className="flex-col w-full h-32 p-4 gap-y-3 items-start justify-between rounded-xl bg-slate-200 dark:bg-slate-700">
                <View className="flex-row items-center justify-between gap-x-5">
                    <View className=" flex-1 flex-col items-start gap-2">
                        <Text className="text-xl font-nunito-semibold text-ellipsis overflow-hidden dark:text-blue-100 text-slate-900" numberOfLines={1} ellipsizeMode="tail">{item.title}</Text>
                        <Text className="text-lg font-nunito text-ellipsis overflow-hidden dark:text-blue-100 text-slate-900" numberOfLines={3} ellipsizeMode="tail">{item.description}</Text>
                    </View>
                    <View className="min-w-[20%]  items-center gap-2">
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
                    <View className="w-[90%] h-[75%] py-6 items-start justify-between gap-4 bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl">
                        {selectedSchedule && (
                            <>
                                <View className="gap-2 flex-1 w-full border-b border-slate-900 dark:border-blue-100 pb-4">
                                    <View className="flex-row items-center justify-between w-full mb-2">
                                        <Text className="font-nunito-semibold text-2xl dark:text-blue-100 text-slate-900 flex-1">{selectedSchedule.title}</Text>
                                        <Pressable onPress={() => { setOpen(false); setSelectedSchedule(null); }} className="p-2">
                                            <XMarkIcon size={24} color={baseColor} />
                                        </Pressable>
                                    </View>
                                    {!!selectedSchedule.description && (
                                        <ScrollView showsVerticalScrollIndicator={false} className="flex-1 w-full">
                                            <Text className="font-nunito-light text-xl mt-2 dark:text-blue-100/90 text-slate-900/90">{selectedSchedule.description}</Text>
                                        </ScrollView>
                                    )}
                                </View>
                                <View className="flex-wrap w-full">
                                    <View className="gap-2 items-center">
                                        <Pressable 
                                            className="flex-row w-full justify-between"
                                            onPress={() => {
                                                if (selectedSchedule.type === "schedule") {
                                                    setShowMusicModal(true);
                                                }
                                            }}
                                        >
                                            {selectedSchedule.type === "schedule" ? (    
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
                                        <Pressable 
                                            className="flex-row w-full justify-between"
                                            onPress={() => setShowMembersModal(true)}
                                        >
                                            <View className="flex-row items-center gap-2">
                                                <UsersIcon size={16} color={baseColor}/>
                                                <Text className="font-nunito text-xl dark:text-blue-100 text-slate-900">Presenças confirmadas</Text>
                                            </View>
                                            <ChevronRightIcon color={baseColor}/>
                                        </Pressable>
                                        <Pressable 
                                            className={"flex items-center justify-center p-2 max-h-10 h-10 w-fit rounded-xl bg-red-500 dark:bg-red-700 active:bg-red-600 dark:active:bg-red-800 active:scale-110"}
                                            onPress={() => setShowAbsenceModal(true)}
                                        >
                                            <Text className="text-xl text-white font-nunito-semibold"> Comunicar ausência </Text>
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

            {/* Modal de Músicas Elencadas */}
            {selectedSchedule && (
                <ScheduleMusicListModal
                    visible={showMusicModal}
                    onClose={() => setShowMusicModal(false)}
                    scheduleId={String(selectedSchedule._id)}
                    userRole={userRole}
                />
            )}

            {/* Modal de Presenças Confirmadas */}
            {selectedSchedule && (
                <ConfirmedMembersModal
                    visible={showMembersModal}
                    onClose={() => setShowMembersModal(false)}
                    scheduleId={String(selectedSchedule._id)}
                    ministryId={ministryId}
                />
            )}

            {/* Modal de Justificativa de Ausência */}
            {selectedSchedule && (
                <AbsenceJustificationModal
                    visible={showAbsenceModal}
                    onClose={() => setShowAbsenceModal(false)}
                    scheduleId={String(selectedSchedule._id)}
                    scheduleTitle={selectedSchedule.title || 'Agendamento'}
                />
            )}
        </View>
    )
}