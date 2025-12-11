import React, { useState } from 'react';
import { View, Text, Pressable, Modal, useColorScheme } from 'react-native';
import { ClockIcon } from 'react-native-heroicons/solid';

interface CustomTimePickerProps {
    label: string;
    value: string;
    onChange: (time: string) => void;
}

export default function CustomTimePicker({ label, value, onChange }: CustomTimePickerProps) {
    const [showPicker, setShowPicker] = useState(false);
    const [selectedHour, setSelectedHour] = useState(parseInt(value.split(':')[0]));
    const [selectedMinute, setSelectedMinute] = useState(parseInt(value.split(':')[1]));
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8';

    const hours = Array.from({ length: 24 }, (_, i) => i);
    const minutes = Array.from({ length: 60 }, (_, i) => i);

    const handleConfirm = () => {
        const formattedTime = `${selectedHour.toString().padStart(2, '0')}:${selectedMinute.toString().padStart(2, '0')}`;
        onChange(formattedTime);
        setShowPicker(false);
    };

    return (
        <View>
            <Text className="font-nunito-semibold text-base text-slate-900 dark:text-blue-100 mb-2">
                {label}
            </Text>
            <Pressable
                onPress={() => setShowPicker(true)}
                className="flex-row items-center gap-3 p-3 rounded-xl bg-slate-200 dark:bg-slate-600"
            >
                <ClockIcon color={contrastColor} size={20} />
                <Text className="font-nunito text-lg text-slate-900 dark:text-blue-100">
                    {value}
                </Text>
            </Pressable>

            <Modal
                visible={showPicker}
                transparent
                animationType="fade"
                onRequestClose={() => setShowPicker(false)}
            >
                <Pressable 
                    className="flex-1 bg-slate-900/40 justify-center items-center"
                    onPress={() => setShowPicker(false)}
                >
                    <View 
                        className="bg-slate-50 dark:bg-slate-700 rounded-xl p-6 w-[80%] max-w-sm"
                        onStartShouldSetResponder={() => true}
                    >
                        <Text className="font-nunito-bold text-xl text-slate-900 dark:text-blue-100 mb-4">
                            Selecionar horário
                        </Text>

                        <View className="flex-row justify-center items-center gap-2 mb-6">
                            {/* Seletor de Horas */}
                            <View className="flex-1 h-40 rounded-xl bg-slate-200 dark:bg-slate-600 overflow-hidden">
                                <View className="flex-1 justify-center">
                                    {hours.slice(Math.max(0, selectedHour - 2), selectedHour + 3).map((hour) => (
                                        <Pressable
                                            key={hour}
                                            onPress={() => setSelectedHour(hour)}
                                            className={`p-2 items-center ${hour === selectedHour ? 'bg-blue-700 dark:bg-blue-300' : ''}`}
                                        >
                                            <Text className={`font-nunito-semibold text-lg ${hour === selectedHour ? 'text-blue-100 dark:text-slate-900' : 'text-slate-900 dark:text-blue-100'}`}>
                                                {hour.toString().padStart(2, '0')}
                                            </Text>
                                        </Pressable>
                                    ))}
                                </View>
                            </View>

                            <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">:</Text>

                            {/* Seletor de Minutos */}
                            <View className="flex-1 h-40 rounded-xl bg-slate-200 dark:bg-slate-600 overflow-hidden">
                                <View className="flex-1 justify-center">
                                    {minutes.filter(m => m % 5 === 0).slice(Math.max(0, selectedMinute / 5 - 2), selectedMinute / 5 + 3).map((minute) => (
                                        <Pressable
                                            key={minute}
                                            onPress={() => setSelectedMinute(minute)}
                                            className={`p-2 items-center ${minute === selectedMinute ? 'bg-blue-700 dark:bg-blue-300' : ''}`}
                                        >
                                            <Text className={`font-nunito-semibold text-lg ${minute === selectedMinute ? 'text-blue-100 dark:text-slate-900' : 'text-slate-900 dark:text-blue-100'}`}>
                                                {minute.toString().padStart(2, '0')}
                                            </Text>
                                        </Pressable>
                                    ))}
                                </View>
                            </View>
                        </View>

                        <View className="flex-row justify-end gap-3">
                            <Pressable
                                onPress={() => setShowPicker(false)}
                                className="px-4 py-2 rounded-xl bg-slate-200 dark:bg-slate-600"
                            >
                                <Text className="font-nunito-semibold text-slate-900 dark:text-blue-100">
                                    Cancelar
                                </Text>
                            </Pressable>
                            <Pressable
                                onPress={handleConfirm}
                                className="px-4 py-2 rounded-xl bg-blue-700 dark:bg-blue-300"
                            >
                                <Text className="font-nunito-semibold text-blue-100 dark:text-slate-900">
                                    Confirmar
                                </Text>
                            </Pressable>
                        </View>
                    </View>
                </Pressable>
            </Modal>
        </View>
    );
}
