import React, { useState } from 'react';
import { View, Text, Modal, Pressable, TextInput, useColorScheme, Alert } from 'react-native';
import { XMarkIcon } from 'react-native-heroicons/solid';
import { loadCache, saveCache, getUser } from '@/services/localCache';

interface AbsenceJustificationModalProps {
    visible: boolean;
    onClose: () => void;
    scheduleId: string;
    scheduleTitle: string;
}

export default function AbsenceJustificationModal({ 
    visible, 
    onClose, 
    scheduleId,
    scheduleTitle 
}: AbsenceJustificationModalProps) {
    const [justification, setJustification] = useState('');
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const handleSubmit = async () => {
        if (!justification.trim()) {
            Alert.alert('Atenção', 'Por favor, informe uma justificativa para sua ausência.');
            return;
        }

        try {
            const cache = await loadCache();
            const user = await getUser();
            
            if (!user) {
                Alert.alert('Erro', 'Usuário não encontrado');
                return;
            }

            const existingAttendance = cache.scheduleAttendances?.find(
                a => a.idAgendamento === scheduleId && a.idUsuario === user.id
            );

            if (existingAttendance) {
                existingAttendance.confirmado = false;
                existingAttendance.justificativa = justification.trim();
            } else {
                cache.scheduleAttendances = cache.scheduleAttendances || [];
                cache.scheduleAttendances.push({
                    id: `attendance_${Date.now()}`,
                    idAgendamento: scheduleId,
                    idUsuario: user.id,
                    confirmado: false,
                    justificativa: justification.trim()
                });
            }

            await saveCache(cache);
            Alert.alert('Sucesso', 'Ausência comunicada com sucesso!');
            setJustification('');
            onClose();
        } catch (error) {
            console.error('❌ Erro ao comunicar ausência:', error);
            Alert.alert('Erro', 'Não foi possível comunicar a ausência');
        }
    };

    const handleClose = () => {
        setJustification('');
        onClose();
    };

    return (
        <Modal visible={visible} transparent animationType="fade">
            <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
                <View className="w-[90%] max-h-[60%] py-6 bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl">
                    <View className="flex-row items-center justify-between w-full mb-4">
                        <Text className="font-nunito-semibold text-xl dark:text-blue-100 text-slate-900 flex-1">
                            Comunicar Ausência
                        </Text>
                        <Pressable onPress={handleClose} className="p-2">
                            <XMarkIcon size={24} color={baseColor} />
                        </Pressable>
                    </View>

                    <Text className="font-nunito text-base dark:text-blue-100/90 text-slate-900/90 mb-2">
                        Agendamento: {scheduleTitle}
                    </Text>

                    <Text className="font-nunito text-base dark:text-blue-100 text-slate-900 mb-2">
                        Justificativa:
                    </Text>

                    <TextInput
                        className="w-full min-h-[120px] p-3 border border-slate-300 dark:border-slate-500 rounded-xl bg-white dark:bg-slate-800 dark:text-blue-100 text-slate-900 font-nunito text-base"
                        placeholder="Informe o motivo da sua ausência..."
                        placeholderTextColor={colorScheme === 'dark' ? '#94a3b8' : '#64748b'}
                        multiline
                        numberOfLines={5}
                        textAlignVertical="top"
                        value={justification}
                        onChangeText={setJustification}
                    />

                    <View className="flex-row gap-3 mt-6">
                        <Pressable
                            onPress={handleClose}
                            className="flex-1 items-center justify-center p-3 rounded-xl bg-slate-300 dark:bg-slate-600 active:bg-slate-400 dark:active:bg-slate-500"
                        >
                            <Text className="font-nunito-semibold text-base dark:text-blue-100 text-slate-900">
                                Cancelar
                            </Text>
                        </Pressable>

                        <Pressable
                            onPress={handleSubmit}
                            className="flex-1 items-center justify-center p-3 rounded-xl bg-red-500 dark:bg-red-400 active:bg-red-600 dark:active:bg-red-500"
                        >
                            <Text className="font-nunito-semibold text-base text-white dark:text-slate-900">
                                Confirmar Ausência
                            </Text>
                        </Pressable>
                    </View>
                </View>
            </View>
        </Modal>
    );
}
