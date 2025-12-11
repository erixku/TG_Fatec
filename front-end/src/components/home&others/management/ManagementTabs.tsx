import React from 'react';
import { View, Text, Pressable, useColorScheme } from 'react-native';

interface ManagementTabsProps {
    activeTab: 'ministries' | 'members';
    onTabChange: (tab: 'ministries' | 'members') => void;
}

export default function ManagementTabs({ activeTab, onTabChange }: ManagementTabsProps) {
    const colorScheme = useColorScheme();

    const getTabStyle = (tab: 'ministries' | 'members') => {
        const isActive = activeTab === tab;
        return `flex-1 p-3 rounded-xl ${
            isActive 
                ? 'bg-blue-700 dark:bg-blue-300' 
                : 'bg-slate-200 dark:bg-slate-600'
        }`;
    };

    const getTextStyle = (tab: 'ministries' | 'members') => {
        const isActive = activeTab === tab;
        return `font-nunito-semibold text-center ${
            isActive 
                ? 'text-blue-100 dark:text-slate-900' 
                : 'text-slate-900 dark:text-blue-100'
        }`;
    };

    return (
        <View className="flex-row gap-2">
            <Pressable 
                className={getTabStyle('ministries')}
                onPress={() => onTabChange('ministries')}
            >
                <Text className={getTextStyle('ministries')}>
                    Ministérios
                </Text>
            </Pressable>
            <Pressable 
                className={getTabStyle('members')}
                onPress={() => onTabChange('members')}
            >
                <Text className={getTextStyle('members')}>
                    Membros
                </Text>
            </Pressable>
        </View>
    );
}
