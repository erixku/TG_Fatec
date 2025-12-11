import React, { useState, useEffect } from 'react';
import { View, Text, Modal, Pressable, ScrollView, useColorScheme, Alert, Image } from 'react-native';
import { XMarkIcon, PlusIcon, TrashIcon, CheckIcon } from 'react-native-heroicons/solid';
import { loadCache, saveCache, generateId, getUser } from '@/services/localCache';

interface ScheduleMusicListModalProps {
    visible: boolean;
    onClose: () => void;
    scheduleId: string;
    userRole?: 'lider' | 'ministro' | 'levita';
}

type ScheduleMusic = {
    id: string;
    idAgendamento: string;
    idMusica: string;
    ordem: number;
};

type Music = {
    id: string;
    title: string;
    artist: string;
    album?: string;
    albumCover?: string;
    bpm?: number;
    key?: string;
};

export default function ScheduleMusicListModal({
    visible,
    onClose,
    scheduleId,
    userRole
}: ScheduleMusicListModalProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const [scheduleMusics, setScheduleMusics] = useState<ScheduleMusic[]>([]);
    const [availableMusics, setAvailableMusics] = useState<Music[]>([]);
    const [musicsDetails, setMusicsDetails] = useState<Music[]>([]);
    const [showAddModal, setShowAddModal] = useState(false);
    const [selectedMusicIds, setSelectedMusicIds] = useState<string[]>([]);
    const [ministryId, setMinistryId] = useState<string>('');
    const canEdit = userRole === 'lider' || userRole === 'ministro';

    // Debug quando showAddModal muda
    useEffect(() => {
        if (showAddModal) {
            console.log('🎵 Modal de seleção ABERTO');
            console.log('🎵 availableMusics no momento da abertura:', availableMusics.length, availableMusics);
        }
    }, [showAddModal]);

    useEffect(() => {
        if (visible) {
            loadScheduleMusics();
        }
    }, [visible, scheduleId]);

    useEffect(() => {
        console.log('🎵 STATE CHANGED - scheduleMusics:', scheduleMusics.length, 'itens');
        console.log('🎵 STATE CHANGED - availableMusics:', availableMusics.length, 'itens');
        if (availableMusics.length > 0) {
            console.log('🎵 Primeira música disponível:', availableMusics[0].title);
        }
    }, [scheduleMusics, availableMusics]);

    const loadScheduleMusics = async () => {
        try {
            const cache = await loadCache();
            console.log('🎵 ScheduleMusicListModal - scheduleId:', scheduleId);
            console.log('🎵 Cache scheduleMusics:', cache.scheduleMusics);
            console.log('🎵 Cache musics:', cache.musics);
            
            // Garante que scheduleMusics existe
            if (!cache.scheduleMusics) {
                cache.scheduleMusics = [];
                await saveCache(cache);
            }
            
            // Carrega músicas do agendamento
            const scheduleMusicsList = cache.scheduleMusics.filter(sm => sm.idAgendamento === scheduleId);
            console.log('🎵 Músicas do agendamento filtradas:', scheduleMusicsList);
            setScheduleMusics(scheduleMusicsList);

            // Carrega detalhes das músicas
            const musicIds = scheduleMusicsList.map(sm => sm.idMusica);
            const details = cache.musics.filter(m => musicIds.includes(m.id));
            console.log('🎵 Detalhes das músicas:', details);
            setMusicsDetails(details);

            // Carrega músicas disponíveis para adicionar (todas do ministério)
            const user = await getUser();
            if (user) {
                const userMinistries = cache.ministryMembers
                    .filter(m => m.idUsuario === user.id)
                    .map(m => m.idMinisterio);
                
                console.log('🎵 Ministérios do usuário:', userMinistries);
                
                if (userMinistries.length > 0) {
                    const currentMinistryId = userMinistries[0];
                    setMinistryId(currentMinistryId);
                    
                    // Carrega TODAS as músicas do ministério, independente de quem criou
                    const available = cache.musics.filter(m => 
                        !musicIds.includes(m.id) && m.idMinisterio === currentMinistryId
                    );
                    console.log('🎵 Músicas disponíveis para adicionar:', available);
                    setAvailableMusics(available);
                }
            }
        } catch (error) {
            console.error('Erro ao carregar músicas do agendamento:', error);
        }
    };

    const handleAddSelectedMusics = async () => {
        try {
            if (selectedMusicIds.length === 0) {
                Alert.alert('Atenção', 'Selecione pelo menos uma música');
                return;
            }

            const cache = await loadCache();
            
            const newScheduleMusics = selectedMusicIds.map((musicId, index) => ({
                id: generateId(),
                idAgendamento: scheduleId,
                idMusica: musicId,
                ordem: scheduleMusics.length + index + 1
            }));

            const updatedScheduleMusics = [...(cache.scheduleMusics || []), ...newScheduleMusics];
            
            await saveCache({
                ...cache,
                scheduleMusics: updatedScheduleMusics
            });

            setShowAddModal(false);
            setSelectedMusicIds([]);
            loadScheduleMusics();
            const count = selectedMusicIds.length;
            Alert.alert('Sucesso', `${count} música${count > 1 ? 's adicionadas' : ' adicionada'} ao agendamento!`);
        } catch (error) {
            console.error('Erro ao adicionar músicas:', error);
            Alert.alert('Erro', 'Não foi possível adicionar as músicas');
        }
    };

    const toggleMusicSelection = (musicId: string) => {
        setSelectedMusicIds(prev => 
            prev.includes(musicId) 
                ? prev.filter(id => id !== musicId)
                : [...prev, musicId]
        );
    };

    const handleRemoveMusic = async (scheduleMusicId: string) => {
        try {
            const cache = await loadCache();
            
            const updatedScheduleMusics = (cache.scheduleMusics || []).filter(
                sm => sm.id !== scheduleMusicId
            );
            
            await saveCache({
                ...cache,
                scheduleMusics: updatedScheduleMusics
            });

            loadScheduleMusics();
            Alert.alert('Sucesso', 'Música removida do agendamento!');
        } catch (error) {
            console.error('Erro ao remover música:', error);
            Alert.alert('Erro', 'Não foi possível remover a música');
        }
    };

    const getMusicDetails = (musicId: string): Music | undefined => {
        return musicsDetails.find(m => m.id === musicId);
    };

    console.log('🎵 RENDER ScheduleMusicListModal - scheduleMusics.length:', scheduleMusics.length);
    console.log('🎵 RENDER ScheduleMusicListModal - availableMusics.length:', availableMusics.length);
    console.log('🎵 RENDER ScheduleMusicListModal - canEdit:', canEdit);

    return (
        <>
            <Modal visible={visible} transparent animationType="fade">
                <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
                    <View className="w-[90%] max-h-[70%] bg-slate-50 dark:bg-slate-700 rounded-xl p-6 shadow-xl">
                        {/* Header */}
                        <View className="flex-row items-center justify-between mb-4">
                            <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                                Músicas Elencadas
                            </Text>
                            <Pressable onPress={onClose}>
                                <XMarkIcon size={24} color={baseColor} />
                            </Pressable>
                        </View>

                        {/* Lista de músicas */}
                        <ScrollView 
                            style={{minHeight: 200, maxHeight: 300}}
                            showsVerticalScrollIndicator={false}
                            className="mb-4"
                        >
                            {scheduleMusics.length === 0 ? (
                                <Text className="font-nunito text-center text-slate-600 dark:text-blue-200 py-8">
                                    Nenhuma música adicionada
                                </Text>
                            ) : (
                                scheduleMusics.map((sm, index) => {
                                    const music = getMusicDetails(sm.idMusica);
                                    return (
                                        <View
                                            key={sm.id}
                                            className="flex-row items-center justify-between p-3 mb-2 bg-slate-200 dark:bg-slate-600 rounded-xl"
                                        >
                                            <View className="flex-1">
                                                <Text className="font-nunito-semibold text-lg text-slate-900 dark:text-blue-100">
                                                    {index + 1}. {music?.title || 'Música não encontrada'}
                                                </Text>
                                                <Text className="font-nunito text-sm text-slate-600 dark:text-blue-200">
                                                    {music?.artist || ''}
                                                </Text>
                                            </View>
                                            {canEdit && (
                                                <Pressable
                                                    onPress={() => handleRemoveMusic(sm.id)}
                                                    className="p-2"
                                                >
                                                    <TrashIcon size={20} color="#ef4444" />
                                                </Pressable>
                                            )}
                                        </View>
                                    );
                                })
                            )}
                        </ScrollView>

                        {/* Botão Adicionar Música - SEMPRE visível quando canEdit */}
                        {canEdit && (
                            <Pressable
                                onPress={() => setShowAddModal(true)}
                                className="flex-row items-center justify-center gap-2 p-4 bg-blue-500 dark:bg-blue-700 rounded-xl"
                            >
                                <PlusIcon size={20} color="#fff" />
                                <Text className="font-nunito-semibold text-white">
                                    Adicionar Música
                                </Text>
                            </Pressable>
                        )}
                    </View>
                </View>
            </Modal>

            {/* Modal de seleção de música */}
            <Modal visible={showAddModal} transparent animationType="fade">
                <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
                    <View className="w-[90%] max-h-[70%] bg-slate-50 dark:bg-slate-700 rounded-xl p-6 shadow-xl">
                        <View className="flex-row items-center justify-between mb-4">
                            <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                                Selecionar Músicas
                            </Text>
                            <Pressable onPress={() => {
                                console.log('🎵 Fechando modal de seleção');
                                setShowAddModal(false);
                                setSelectedMusicIds([]);
                            }}>
                                <XMarkIcon size={24} color={baseColor} />
                            </Pressable>
                        </View>

                        <ScrollView 
                            style={{minHeight: 200, maxHeight: 400}}
                            showsVerticalScrollIndicator={false}
                            className="mb-4"
                        >
                            {availableMusics.length === 0 ? (
                                <Text className="font-nunito text-center text-slate-600 dark:text-blue-200 py-8">
                                    Nenhuma música disponível
                                </Text>
                            ) : (
                                availableMusics.map((music) => {
                                    const isSelected = selectedMusicIds.includes(music.id);
                                    return (
                                        <Pressable
                                            key={music.id}
                                            onPress={() => toggleMusicSelection(music.id)}
                                            className={`flex-row items-center p-3 mb-2 rounded-xl ${
                                                isSelected 
                                                    ? 'bg-blue-500 dark:bg-blue-700' 
                                                    : 'bg-slate-200 dark:bg-slate-600'
                                            }`}
                                        >
                                            {/* Foto de capa */}
                                            <View className="w-16 h-16 rounded-lg bg-slate-300 dark:bg-slate-500 mr-3 items-center justify-center overflow-hidden">
                                                {music.albumCover ? (
                                                    <Image 
                                                        source={{ uri: music.albumCover }} 
                                                        className="w-full h-full"
                                                        resizeMode="cover"
                                                    />
                                                ) : (
                                                    <Text className="font-nunito-bold text-3xl text-white">
                                                        {music.album ? music.album.charAt(0).toUpperCase() : '🎵'}
                                                    </Text>
                                                )}
                                            </View>

                                            <View className="flex-1">
                                                <Text className={`font-nunito-semibold text-lg ${
                                                    isSelected 
                                                        ? 'text-white' 
                                                        : 'text-slate-900 dark:text-blue-100'
                                                }`}>
                                                    {music.title}
                                                </Text>
                                                <Text className={`font-nunito text-sm ${
                                                    isSelected 
                                                        ? 'text-white/90' 
                                                        : 'text-slate-600 dark:text-blue-200'
                                                }`}>
                                                    {music.artist}
                                                </Text>
                                            </View>
                                            {isSelected && (
                                                <CheckIcon size={24} color="#fff" />
                                            )}
                                        </Pressable>
                                    );
                                })
                            )}
                        </ScrollView>

                        {selectedMusicIds.length > 0 && (
                            <Pressable
                                onPress={handleAddSelectedMusics}
                                className="flex-row items-center justify-center gap-2 p-4 bg-green-500 dark:bg-green-700 rounded-xl"
                            >
                                <PlusIcon size={20} color="#fff" />
                                <Text className="font-nunito-semibold text-white">
                                    Adicionar {selectedMusicIds.length} música{selectedMusicIds.length > 1 ? 's' : ''}
                                </Text>
                            </Pressable>
                        )}
                    </View>
                </View>
            </Modal>
        </>
    );
}
