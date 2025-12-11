import React from 'react';
import { View, Text, Pressable, useColorScheme } from 'react-native';

interface WeekdaySelectorProps {
    selectedDays: number[]; // 0 = Domingo, 1 = Segunda, ..., 6 = Sábado
    onChange: (days: number[]) => void;
}

const WEEKDAYS = ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'];
const WEEKDAY_NAMES = ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'];

export default function WeekdaySelector({ selectedDays, onChange }: WeekdaySelectorProps) {
    const colorScheme = useColorScheme();

    const toggleDay = (dayIndex: number) => {
        if (selectedDays.includes(dayIndex)) {
            onChange(selectedDays.filter(d => d !== dayIndex));
        } else {
            onChange([...selectedDays, dayIndex].sort());
        }
    };

    return (
        <View className="flex-row justify-between gap-2">
            {WEEKDAYS.map((day, index) => {
                const isSelected = selectedDays.includes(index);
                const bgColor = isSelected 
                    ? (colorScheme === 'dark' ? 'bg-blue-300' : 'bg-blue-700')
                    : 'bg-slate-200 dark:bg-slate-600';
                const textColor = isSelected
                    ? 'text-blue-100 dark:text-slate-900'
                    : 'text-slate-900 dark:text-blue-100';

                return (
                    <Pressable
                        key={index}
                        onPress={() => toggleDay(index)}
                        className={`flex-1 aspect-square items-center justify-center rounded-xl ${bgColor} active:opacity-80`}
                        accessibilityLabel={WEEKDAY_NAMES[index]}
                    >
                        <Text className={`font-nunito-bold text-lg ${textColor}`}>
                            {day}
                        </Text>
                    </Pressable>
                );
            })}
        </View>
    );
}
