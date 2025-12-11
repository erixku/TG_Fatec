import React, { useState, useEffect } from 'react';
import { View, Text, Modal, Pressable, ScrollView } from 'react-native';
import { XMarkIcon } from 'react-native-heroicons/solid';
import CustomDropdown from '../CustomDropdown';
import { CustomMaskedInput } from '../CustomInput';
import { getUser } from '@/services/localCache';
import dayjs from 'dayjs';

export interface FilterOptions {
    createdBy?: string;
    startTime?: string;
    endTime?: string;
}

interface FilterModalProps {
    visible: boolean;
    onClose: () => void;
    onApplyFilters: (filters: FilterOptions) => void;
    ministryId: string | null;
}

export default function FilterModal({ 
    visible, 
    onClose, 
    onApplyFilters,
    ministryId
}: FilterModalProps) {
    const [selectedUser, setSelectedUser] = useState<string>('');
    const [startTime, setStartTime] = useState<string>('');
    const [endTime, setEndTime] = useState<string>('');
    const [users, setUsers] = useState<{ label: string; value: string }[]>([]);

    useEffect(() => {
        if (visible && ministryId) {
            loadMinistryMembers();
        }
    }, [visible, ministryId]);

    const loadMinistryMembers = async () => {
        try {
            // Aqui você pode implementar a lógica para buscar membros do ministério
            // Por enquanto, vamos buscar o usuário atual
            const currentUser = await getUser();
            if (currentUser) {
                setUsers([
                    { label: 'Todos', value: '' },
                    { label: currentUser.nome, value: currentUser.id }
                ]);
            }
        } catch (error) {
            console.error('❌ Erro ao carregar membros:', error);
        }
    };

    const handleApply = () => {
        const filters: FilterOptions = {};
        
        if (selectedUser) {
            filters.createdBy = selectedUser;
        }
        
        // Valida formato de horário (HH:MM)
        const timeRegex = /^([01]\d|2[0-3]):([0-5]\d)$/;
        
        if (startTime && timeRegex.test(startTime)) {
            filters.startTime = startTime;
        }
        
        if (endTime && timeRegex.test(endTime)) {
            filters.endTime = endTime;
        }

        onApplyFilters(filters);
        onClose();
    };

    const handleClear = () => {
        setSelectedUser('');
        setStartTime('');
        setEndTime('');
        onApplyFilters({});
        onClose();
    };

    return (
        <Modal visible={visible} transparent animationType="fade">
            <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
                <Pressable
                    className="absolute inset-0"
                    onPress={onClose}
                />
                <View className="w-[90%] max-w-md bg-slate-50 dark:bg-slate-700 rounded-xl p-6 shadow-xl">
                    {/* Cabeçalho */}
                    <View className="flex-row justify-between items-center mb-6">
                        <Text className="text-2xl font-nunito-semibold dark:text-blue-100 text-slate-900">
                            Filtrar
                        </Text>
                        <Pressable onPress={onClose}>
                            <XMarkIcon size={24} color="#64748b" />
                        </Pressable>
                    </View>

                    <ScrollView showsVerticalScrollIndicator={false}>
                        <View className="gap-4">
                            {/* Filtro por usuário criador */}
                            <View>
                                <Text className="text-lg font-nunito-semibold dark:text-blue-100 text-slate-900 mb-2">
                                    Criado por
                                </Text>
                                <CustomDropdown
                                    options={users.map(u => u.label)}
                                    onChange={(label) => {
                                        const user = users.find(u => u.label === label);
                                        setSelectedUser(user?.value || '');
                                    }}
                                    value={users.find(u => u.value === selectedUser)?.label || ''}
                                />
                            </View>

                            {/* Filtro por horário */}
                            <View>
                                <Text className="text-lg font-nunito-semibold dark:text-blue-100 text-slate-900 mb-2">
                                    Horário
                                </Text>
                                
                                <View className="gap-3">
                                    <View>
                                        <CustomMaskedInput
                                            label="Horário inicial"
                                            value={startTime}
                                            onChangeText={setStartTime}
                                            mask="99:99"
                                            placeholder="Ex: 08:00"
                                            keyboardType="numeric"
                                        />
                                    </View>

                                    <View>
                                        <CustomMaskedInput
                                            label="Horário final"
                                            value={endTime}
                                            onChangeText={setEndTime}
                                            mask="99:99"
                                            placeholder="Ex: 18:00"
                                            keyboardType="numeric"
                                        />
                                    </View>
                                </View>
                            </View>

                            {/* Botões de ação */}
                            <View className="flex-row gap-3 mt-4">
                                <View className="flex-1">
                                    <Pressable
                                        onPress={handleClear}
                                        className="bg-slate-400 dark:bg-slate-600 py-3 rounded-lg items-center"
                                    >
                                        <Text className="text-lg font-nunito-semibold text-slate-900 dark:text-blue-100">
                                            Limpar
                                        </Text>
                                    </Pressable>
                                </View>
                                <View className="flex-1">
                                    <Pressable
                                        onPress={handleApply}
                                        className="bg-blue-700 dark:bg-blue-300 py-3 rounded-lg items-center"
                                    >
                                        <Text className="text-lg font-nunito-semibold text-blue-100 dark:text-slate-900">
                                            Aplicar
                                        </Text>
                                    </Pressable>
                                </View>
                            </View>
                        </View>
                    </ScrollView>
                </View>
            </View>
        </Modal>
    );
}
