import React, { useState } from 'react';
import { View, Text, useColorScheme, ScrollView, Pressable } from 'react-native';
import { useRouter } from 'expo-router';
import { ArrowLeftIcon, PlusIcon } from 'react-native-heroicons/solid';
import { CustomButton } from '@/components/CustomButtom';
import CustomDropdown from '@/components/CustomDropdown';
import CustomCheckbox from '@/components/CustomCheckbox';
import CustomTimePicker from '@/components/CustomTimePicker';
import WeekdaySelector from '@/components/home&others/WeekdaySelector';

export default function NotificationSettings() {
    const colorScheme = useColorScheme();
    const router = useRouter();

    // Estados
    const [notificationSound, setNotificationSound] = useState('Padrão');
    const [showOnLockScreen, setShowOnLockScreen] = useState(true);
    const [doNotDisturbByTime, setDoNotDisturbByTime] = useState(false);
    const [startTime, setStartTime] = useState('22:00');
    const [endTime, setEndTime] = useState('07:00');
    const [timeWeekdays, setTimeWeekdays] = useState<number[]>([]);
    const [doNotDisturbByDay, setDoNotDisturbByDay] = useState(false);
    const [dayWeekdays, setDayWeekdays] = useState<number[]>([]);

    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const soundOptions = ['Padrão', 'Silencioso', 'Campainha 1', 'Campainha 2', 'Notificação 1'];

    const handleSave = () => {
        console.log('Salvando configurações de notificação:', {
            notificationSound,
            showOnLockScreen,
            doNotDisturbByTime,
            startTime,
            endTime,
            timeWeekdays,
            doNotDisturbByDay,
            dayWeekdays,
        });
        router.back();
    };

    const handleAddSound = () => {
        console.log('Adicionar novo som de notificação');
        // Implementar lógica de adicionar som personalizado
    };

    return (
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800 p-6">
            {/* Cartão flutuante centralizado com 80% da altura */}
            <View className="bg-slate-50 dark:bg-slate-700 rounded-xl shadow-md p-6 w-full h-[80%]">
                {/* Cabeçalho */}
                <View className="flex-row items-center mb-6">
                    <Pressable onPress={() => router.back()} className="mr-4">
                        <ArrowLeftIcon color={baseColor} size={24} />
                    </Pressable>
                    <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                        Configurações de Notificações
                    </Text>
                </View>

                {/* ScrollView do Formulário */}
                <ScrollView 
                    className="flex-1"
                    showsVerticalScrollIndicator={false}
                    contentContainerStyle={{ paddingBottom: 20 }}
                >
                    <View className="gap-4 items-center">
                        {/* Sons de notificação */}
                        <View className="w-full">
                            <View className="flex-row items-end gap-2">
                                <View className="flex-1">
                                    <CustomDropdown
                                        value={notificationSound}
                                        onChange={setNotificationSound}
                                        label="Sons de notificação"
                                        options={soundOptions}
                                        required
                                    />
                                </View>
                                <Pressable 
                                    onPress={handleAddSound}
                                    className="h-[38px] w-[38px] items-center justify-center mb-0"
                                >
                                    <PlusIcon color={colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8'} size={20} />
                                </Pressable>
                            </View>
                        </View>

                        {/* Aparecer na tela de bloqueio */}
                        <View className="w-full">
                            <CustomCheckbox
                                label="Aparecer na tela de bloqueio"
                                value={showOnLockScreen}
                                onChange={setShowOnLockScreen}
                            />
                        </View>

                        {/* Não perturbar por horário */}
                        <View className="w-full gap-3">
                            <CustomCheckbox
                                label="Não perturbar por horário"
                                value={doNotDisturbByTime}
                                onChange={setDoNotDisturbByTime}
                            />

                            {doNotDisturbByTime && (
                                <View className="ml-6 gap-4 p-4 rounded-xl bg-slate-100 dark:bg-slate-800">
                                    {/* Horário de início */}
                                    <CustomTimePicker
                                        label="Horário de início"
                                        value={startTime}
                                        onChange={setStartTime}
                                    />

                                    {/* Horário de fim */}
                                    <CustomTimePicker
                                        label="Horário de fim"
                                        value={endTime}
                                        onChange={setEndTime}
                                    />

                                    {/* Dias da semana */}
                                    <View>
                                        <Text className="font-nunito-semibold text-base text-slate-900 dark:text-blue-100 mb-2">
                                            Dias da semana
                                        </Text>
                                        <WeekdaySelector
                                            selectedDays={timeWeekdays}
                                            onChange={setTimeWeekdays}
                                        />
                                    </View>
                                </View>
                            )}
                        </View>

                        {/* Não perturbar por dia */}
                        <View className="w-full gap-3">
                            <CustomCheckbox
                                label="Não perturbar por dia"
                                value={doNotDisturbByDay}
                                onChange={setDoNotDisturbByDay}
                            />

                            {doNotDisturbByDay && (
                                <View className="ml-6 gap-4 p-4 rounded-xl bg-slate-100 dark:bg-slate-800">
                                    <Text className="font-nunito-semibold text-base text-slate-900 dark:text-blue-100">
                                        Dias da semana
                                    </Text>
                                    <WeekdaySelector
                                        selectedDays={dayWeekdays}
                                        onChange={setDayWeekdays}
                                    />
                                </View>
                            )}
                        </View>
                    </View>
                </ScrollView>

                {/* Botão de Salvar Centralizado */}
                <View className="items-center mt-6">
                    <CustomButton
                        label="Salvar"
                        onPress={handleSave}
                    />
                </View>
            </View>
        </View>
    );
}
