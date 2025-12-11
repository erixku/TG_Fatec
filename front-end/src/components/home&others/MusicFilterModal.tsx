import React, { useState, useEffect } from 'react';
import { View, Text, Modal, Pressable, ScrollView } from 'react-native';
import { XMarkIcon } from 'react-native-heroicons/solid';
import CustomDropdown from '../CustomDropdown';
import { CustomTextInput } from '../CustomInput';
import { getUser } from '@/services/localCache';

export interface MusicFilterOptions {
    createdBy?: string;
    album?: string;
    key?: string;
    minBpm?: number;
    maxBpm?: number;
}

interface MusicFilterModalProps {
    visible: boolean;
    onClose: () => void;
    onApplyFilters: (filters: MusicFilterOptions) => void;
    ministryId: string | null;
    availableAlbums?: string[];
    availableKeys?: string[];
}

export default function MusicFilterModal({ 
    visible, 
    onClose, 
    onApplyFilters,
    ministryId,
    availableAlbums = [],
    availableKeys = []
}: MusicFilterModalProps) {
    const [selectedUser, setSelectedUser] = useState<string>('');
    const [selectedAlbum, setSelectedAlbum] = useState<string>('');
    const [selectedKey, setSelectedKey] = useState<string>('');
    const [minBpm, setMinBpm] = useState<string>('');
    const [maxBpm, setMaxBpm] = useState<string>('');
    const [users, setUsers] = useState<{ label: string; value: string }[]>([]);

    useEffect(() => {
        if (visible && ministryId) {
            loadMinistryMembers();
        }
    }, [visible, ministryId]);

    const loadMinistryMembers = async () => {
        try {
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
        const filters: MusicFilterOptions = {};
        
        if (selectedUser) {
            filters.createdBy = selectedUser;
        }
        
        if (selectedAlbum) {
            filters.album = selectedAlbum;
        }
        
        if (selectedKey) {
            filters.key = selectedKey;
        }
        
        if (minBpm) {
            const num = parseInt(minBpm);
            if (!isNaN(num) && num > 0) {
                filters.minBpm = num;
            }
        }
        
        if (maxBpm) {
            const num = parseInt(maxBpm);
            if (!isNaN(num) && num > 0) {
                filters.maxBpm = num;
            }
        }

        onApplyFilters(filters);
        onClose();
    };

    const handleClear = () => {
        setSelectedUser('');
        setSelectedAlbum('');
        setSelectedKey('');
        setMinBpm('');
        setMaxBpm('');
        onApplyFilters({});
        onClose();
    };

    const albumOptions = ['Todos', ...availableAlbums];
    const keyOptions = ['Todos', ...availableKeys];

    return (
        <Modal visible={visible} transparent animationType="fade">
            <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
                <Pressable
                    className="absolute inset-0"
                    onPress={onClose}
                />
                <View className="w-[90%] max-w-md bg-slate-50 dark:bg-slate-700 rounded-xl p-6 shadow-xl max-h-[80%]">
                    {/* Cabeçalho */}
                    <View className="flex-row justify-between items-center mb-6">
                        <Text className="text-2xl font-nunito-semibold dark:text-blue-100 text-slate-900">
                            Filtrar Músicas
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
                                    value={users.find(u => u.value === selectedUser)?.label || 'Todos'}
                                />
                            </View>

                            {/* Filtro por álbum */}
                            {availableAlbums.length > 0 && (
                                <View>
                                    <Text className="text-lg font-nunito-semibold dark:text-blue-100 text-slate-900 mb-2">
                                        Álbum
                                    </Text>
                                    <CustomDropdown
                                        options={albumOptions}
                                        onChange={(value) => {
                                            setSelectedAlbum(value === 'Todos' ? '' : value);
                                        }}
                                        value={selectedAlbum || 'Todos'}
                                    />
                                </View>
                            )}

                            {/* Filtro por tom */}
                            {availableKeys.length > 0 && (
                                <View>
                                    <Text className="text-lg font-nunito-semibold dark:text-blue-100 text-slate-900 mb-2">
                                        Tom
                                    </Text>
                                    <CustomDropdown
                                        options={keyOptions}
                                        onChange={(value) => {
                                            setSelectedKey(value === 'Todos' ? '' : value);
                                        }}
                                        value={selectedKey || 'Todos'}
                                    />
                                </View>
                            )}

                            {/* Filtro por BPM */}
                            <View>
                                <Text className="text-lg font-nunito-semibold dark:text-blue-100 text-slate-900 mb-2">
                                    BPM (Batidas Por Minuto)
                                </Text>
                                
                                <View className="gap-3">
                                    <View>
                                        <CustomTextInput
                                            label="BPM mínimo"
                                            value={minBpm}
                                            onChangeText={setMinBpm}
                                            placeholder="Ex: 60"
                                            keyboardType="numeric"
                                        />
                                    </View>

                                    <View>
                                        <CustomTextInput
                                            label="BPM máximo"
                                            value={maxBpm}
                                            onChangeText={setMaxBpm}
                                            placeholder="Ex: 120"
                                            keyboardType="numeric"
                                        />
                                    </View>
                                </View>
                            </View>

                            {/* Botões de ação */}
                            <View className="flex-row gap-3 mt-4 mb-2">
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
