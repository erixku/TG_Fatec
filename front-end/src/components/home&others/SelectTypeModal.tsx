import React from 'react';
import { View, Text, Modal, Pressable } from 'react-native';
import { CalendarDaysIcon, ClipboardDocumentCheckIcon, XMarkIcon } from 'react-native-heroicons/solid';

interface SelectTypeModalProps {
    visible: boolean;
    onClose: () => void;
    onSelectSchedule: () => void;
    onSelectCommitment: () => void;
}

export default function SelectTypeModal({ 
    visible, 
    onClose, 
    onSelectSchedule, 
    onSelectCommitment 
}: SelectTypeModalProps) {
    return (
        <Modal visible={visible} transparent animationType="fade">
            <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
                <Pressable
                    className="absolute inset-0"
                    onPress={onClose}
                />
                <View className="w-[90%] max-w-md bg-slate-50 dark:bg-slate-700 rounded-xl p-6 shadow-xl">
                    <View className="flex-row justify-between items-center mb-6">
                        <Text className="text-2xl font-nunito-semibold dark:text-blue-100 text-slate-900">
                            O que deseja criar?
                        </Text>
                        <Pressable onPress={onClose}>
                            <XMarkIcon size={24} color="#64748b" />
                        </Pressable>
                    </View>

                    <View className="gap-4">
                        <Pressable
                            onPress={() => {
                                onSelectSchedule();
                                onClose();
                            }}
                            className="flex-row items-center p-4 bg-slate-200 dark:bg-slate-600 rounded-xl active:opacity-70"
                        >
                            <CalendarDaysIcon size={32} color="#3b82f6" />
                            <View className="ml-4 flex-1">
                                <Text className="text-xl font-nunito-semibold dark:text-blue-100 text-slate-900">
                                    Agendamento
                                </Text>
                                <Text className="text-sm font-nunito dark:text-blue-100/70 text-slate-900/70">
                                    Evento ou atividade programada
                                </Text>
                            </View>
                        </Pressable>

                        <Pressable
                            onPress={() => {
                                onSelectCommitment();
                                onClose();
                            }}
                            className="flex-row items-center p-4 bg-slate-200 dark:bg-slate-600 rounded-xl active:opacity-70"
                        >
                            <ClipboardDocumentCheckIcon size={32} color="#8b5cf6" />
                            <View className="ml-4 flex-1">
                                <Text className="text-xl font-nunito-semibold dark:text-blue-100 text-slate-900">
                                    Compromisso
                                </Text>
                                <Text className="text-sm font-nunito dark:text-blue-100/70 text-slate-900/70">
                                    Tarefa com responsável definido
                                </Text>
                            </View>
                        </Pressable>
                    </View>
                </View>
            </View>
        </Modal>
    );
}
