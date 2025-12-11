import React, { useState, useEffect } from "react";
import { View, Text, FlatList, ListRenderItem, Pressable, Modal, useColorScheme, ScrollView, Alert } from "react-native";
import { CakeIcon, ChevronRightIcon, MusicalNoteIcon, UsersIcon, PlusIcon, XMarkIcon } from "react-native-heroicons/solid";
import ScheduleMusicListModal from '../schedues/ScheduleMusicListModal';
import ConfirmedMembersModal from '../schedues/ConfirmedMembersModal';
import AbsenceJustificationModal from '../schedues/AbsenceJustificationModal';
import { getUser, loadCache, saveCache } from '@/services/localCache';

type GenericSchedule = {
    _id?: string | number;
    key?: string | number;
    title?: string;
    description?: string;
    date?: string;
    time?: string;
    responsible_user?: string;
    type?: string;
};

interface ScheduleListProps{
    label: string;
    schedules: GenericSchedule[];
    warning?: boolean;
    commitmend?: boolean;
    showAddButton?: boolean;
    onAddPress?: () => void;
}

export default function ScheduleList({label, schedules, warning, commitmend, showAddButton = false, onAddPress}: ScheduleListProps) {
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
        loadUserRole();
    }, []);

    const loadUserRole = async () => {
        try {
            const cache = await loadCache();
            const user = await getUser();
            
            if (!user) {
                console.log('⚠️ Usuário não encontrado');
                return;
            }

            // Busca o papel do usuário em um ministério usando ministryMembers
            const userMembership = cache.ministryMembers.find(m => m.idUsuario === user.id);
            if (userMembership) {
                setUserRole(userMembership.papel);
                setMinistryId(userMembership.idMinisterio);
                console.log('👤 SchedulesList - Papel do usuário:', userMembership.papel);
                console.log('👤 SchedulesList - MinistryId:', userMembership.idMinisterio);
            }
        } catch (error) {
            console.error('❌ Erro ao carregar papel do usuário:', error);
        }
    };

    const keyExtractor = (item: GenericSchedule, index: number) => String(item._id ?? item.key ?? index);

    const renderItem: ListRenderItem<GenericSchedule> = ({ item }) => {
        return (
            <Pressable onPress={() => { setSelectedSchedule(item); setOpen(true); }} className="flex-col w-64 h-32 p-4 gap-y-3 items-start justify-center rounded-xl bg-slate-200 dark:bg-slate-700">
                <Text className="font-nunito-semibold text-lg dark:text-blue-100 text-slate-900" numberOfLines={1} ellipsizeMode="tail">{item.title}</Text>
                <Text className="font-nunito text-lg text-ellipsis overflow-hidden dark:text-blue-100 text-slate-900" numberOfLines={1} ellipsizeMode="tail">
                    {item.description}
                </Text>
                <Text className="font-nunito-light dark:text-blue-100/70 text-slate-900/70">{warning ? "Data de publicação: " : "Data agendada: "}{item.date}{!!item.time && !warning ? (", " + item.time):null}</Text>
            </Pressable>
        );
    };

    const getEmptyMessage = () => {
        if (warning) return "Nenhum aviso cadastrado";
        if (commitmend) return "Nenhum compromisso registrado";
        return "Nenhum agendamento realizado";
    };

    return(
        <View className="flex-col w-full gap-y-4 items-start justify-center rounded-xl">
            <View className="flex-row items-center justify-between w-full">
                <Text className="font-nunito text-2xl dark:text-blue-100 text-slate-900">{label}</Text>
                {showAddButton && onAddPress && (
                    <Pressable
                        onPress={onAddPress}
                        className="active:scale-110"
                    >
                        <PlusIcon size={20} color={baseColor} />
                    </Pressable>
                )}
            </View>
            {schedules.length === 0 ? (
                <View className="w-64 h-32 p-4 items-center justify-center rounded-xl bg-slate-200 dark:bg-slate-700">
                    <Text className="font-nunito text-lg text-center dark:text-blue-100/70 text-slate-900/70">
                        {getEmptyMessage()}
                    </Text>
                </View>
            ) : (
                <FlatList
                    data={schedules}
                    keyExtractor={keyExtractor}
                    renderItem={renderItem}
                    horizontal
                    contentContainerClassName="gap-3"
                    className="rounded-xl"
                    showsHorizontalScrollIndicator={true}
                />
            )}
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
                                    {!warning && (
                                        <View className="gap-2 items-center">
                                            {!commitmend && (
                                                <Pressable 
                                                    className="flex-row w-full justify-between"
                                                    onPress={() => setShowMusicModal(true)}
                                                >
                                                    <View className="flex-row items-center gap-2">
                                                        <MusicalNoteIcon size={16} color={baseColor}/>
                                                        <Text className="font-nunito text-xl dark:text-blue-100 text-slate-900">Músicas elencadas</Text>
                                                    </View>
                                                    <ChevronRightIcon color={baseColor}/>
                                                </Pressable>
                                            )}
                                            {commitmend && (
                                                <Pressable className="flex-row w-full justify-between">
                                                    <View className="flex-row items-center gap-2">
                                                        <CakeIcon size={16} color={baseColor}/>
                                                        <Text className="font-nunito text-xl dark:text-blue-100 text-slate-900">Alimentos do evento</Text>
                                                    </View>
                                                    <ChevronRightIcon color={baseColor}/>
                                                </Pressable>
                                            )}
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
                                                className={"flex items-center justify-center p-2 max-h-10 h-10 w-fit rounded-xl bg-red-500 dark:bg-red-400 active:bg-red-600 dark:active:bg-red-500 active:scale-110"}
                                                onPress={() => setShowAbsenceModal(true)}
                                            >
                                                <Text className="text-xl text-blue-100 dark:text-slate-900"> Comunicar ausência </Text>
                                            </Pressable>
                                        </View>
                                    )}
                                </View>
                                <View className="flex-1 w-full max-h-[10%] justify-end">
                                    {!!selectedSchedule.date && (
                                        <Text className="font-nunito-light mt-3 dark:text-blue-100/70 text-slate-900/70">
                                            {warning ? "Data de publicação: " : "Data agendada: "}{selectedSchedule.date}{!!selectedSchedule.time && !warning ? (", " + selectedSchedule.time):null}
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
            
            {selectedSchedule && (
                <>
                    <ScheduleMusicListModal
                        visible={showMusicModal}
                        onClose={() => setShowMusicModal(false)}
                        scheduleId={String(selectedSchedule._id)}
                        userRole={userRole}
                    />
                    <ConfirmedMembersModal
                        visible={showMembersModal}
                        onClose={() => setShowMembersModal(false)}
                        scheduleId={String(selectedSchedule._id)}
                        ministryId={ministryId}
                    />
                    <AbsenceJustificationModal
                        visible={showAbsenceModal}
                        onClose={() => setShowAbsenceModal(false)}
                        scheduleId={String(selectedSchedule._id)}
                        scheduleTitle={selectedSchedule.title || 'Agendamento'}
                    />
                </>
            )}
        </View>
    )
}