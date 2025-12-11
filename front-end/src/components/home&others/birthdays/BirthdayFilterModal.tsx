import React, { useState } from 'react';
import { View, Text, Modal, Pressable, useColorScheme } from 'react-native';
import { XMarkIcon } from 'react-native-heroicons/solid';
import { CustomTextInput } from '@/components/CustomInput';
import { CustomButton } from '@/components/CustomButtom';
import { BirthdayFilterOptions } from '@/app/homeMenu/birthdays';

interface BirthdayFilterModalProps {
    visible: boolean;
    onClose: () => void;
    onApplyFilters: (filters: BirthdayFilterOptions) => void;
    onClearFilters: () => void;
    activeFilters: BirthdayFilterOptions;
}

export default function BirthdayFilterModal({
    visible,
    onClose,
    onApplyFilters,
    onClearFilters,
    activeFilters,
}: BirthdayFilterModalProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const [searchText, setSearchText] = useState(activeFilters.searchText || '');

    const handleApply = () => {
        onApplyFilters({
            searchText: searchText || undefined,
        });
    };

    const handleClear = () => {
        setSearchText('');
        onClearFilters();
    };

    return (
        <Modal
            visible={visible}
            transparent
            animationType="fade"
            onRequestClose={onClose}
        >
            <Pressable 
                className="flex-1 bg-slate-900/40 justify-center items-center p-6"
                onPress={onClose}
            >
                <View 
                    className="bg-slate-50 dark:bg-slate-700 rounded-xl p-6 w-full max-w-md"
                    onStartShouldSetResponder={() => true}
                >
                    {/* Cabeçalho */}
                    <View className="flex-row items-center justify-between mb-6">
                        <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                            Filtrar Aniversariantes
                        </Text>
                        <Pressable onPress={onClose}>
                            <XMarkIcon color={baseColor} size={24} />
                        </Pressable>
                    </View>

                    {/* Filtros */}
                    <View className="gap-4 mb-6">
                        {/* Busca por texto */}
                        <CustomTextInput
                            label="Buscar por nome, e-mail ou telefone"
                            placeholder="Digite para buscar..."
                            value={searchText}
                            onChangeText={setSearchText}
                        />
                    </View>

                    {/* Botões */}
                    <View className="flex-row gap-3 justify-end">
                        <Pressable
                            onPress={handleClear}
                            className="px-4 py-2 rounded-xl bg-slate-200 dark:bg-slate-600"
                        >
                            <Text className="font-nunito-semibold text-slate-900 dark:text-blue-100">
                                Limpar
                            </Text>
                        </Pressable>
                        <CustomButton
                            label="Aplicar"
                            onPress={handleApply}
                        />
                    </View>
                </View>
            </Pressable>
        </Modal>
    );
}
