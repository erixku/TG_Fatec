import React from 'react';
import { View, Text, Pressable, useColorScheme } from 'react-native';
import { CheckIcon } from 'react-native-heroicons/solid';

interface CustomCheckboxProps {
    label: string;
    value: boolean;
    onChange: (value: boolean) => void;
}

export default function CustomCheckbox({ label, value, onChange }: CustomCheckboxProps) {
    const colorScheme = useColorScheme();
    const checkboxBg = value 
        ? (colorScheme === 'dark' ? 'bg-blue-300' : 'bg-blue-700')
        : 'bg-slate-200 dark:bg-slate-600';
    const checkColor = colorScheme === 'dark' ? '#0f172a' : '#dbeafe';

    return (
        <Pressable 
            onPress={() => onChange(!value)}
            className="flex-row items-center justify-between"
        >
            <Text className="font-nunito text-lg text-slate-900 dark:text-blue-100">
                {label}
            </Text>
            <View className={`h-6 w-6 rounded items-center justify-center ${checkboxBg}`}>
                {value && <CheckIcon color={checkColor} size={16} />}
            </View>
        </Pressable>
    );
}
