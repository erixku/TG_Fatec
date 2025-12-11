import React, { useState } from 'react';
import { View, Text, FlatList, Pressable, useColorScheme, Image, Alert } from 'react-native';
import { UserIcon } from 'react-native-heroicons/solid';
import { MemberRoleData, MinistryManagementData } from '@/app/homeMenu/management';
import { updateMemberRole } from '@/services/localCache';
import CustomDropdown from '@/components/CustomDropdown';

interface MemberRoleListProps {
    members: MemberRoleData[];
    ministries: MinistryManagementData[];
    onRefresh: () => void;
}

export default function MemberRoleList({ members, ministries, onRefresh }: MemberRoleListProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const roleOptions = ['lider', 'ministro', 'levita'];
    const roleLabels: Record<string, string> = {
        lider: 'Líder',
        ministro: 'Ministro',
        levita: 'Levita',
    };

    const handleRoleChange = async (member: MemberRoleData, newRole: 'lider' | 'ministro' | 'levita') => {
        try {
            await updateMemberRole(member.userId, member.ministryId, newRole);
            Alert.alert('Sucesso', `Papel de ${member.userName} alterado para ${roleLabels[newRole]}`);
            onRefresh();
        } catch (error: any) {
            Alert.alert('Erro', error.message || 'Não foi possível alterar o papel');
        }
    };

    const renderMemberItem = ({ item }: { item: MemberRoleData }) => {
        return (
            <View className="mb-4 p-4 rounded-xl bg-slate-100 dark:bg-slate-800">
                <View className="flex-row gap-3 items-center mb-3">
                    {/* Foto do membro */}
                    <View className="w-12 h-12 rounded-full overflow-hidden bg-slate-200 dark:bg-slate-600 items-center justify-center">
                        {item.userPhoto ? (
                            <Image 
                                source={{ uri: item.userPhoto }} 
                                className="w-full h-full"
                                resizeMode="cover"
                            />
                        ) : (
                            <UserIcon color={baseColor} size={24} />
                        )}
                    </View>

                    {/* Nome e email */}
                    <View className="flex-1">
                        <Text className="font-nunito-bold text-base text-slate-900 dark:text-blue-100" numberOfLines={1}>
                            {item.userName}
                        </Text>
                        <Text className="font-nunito text-sm text-slate-900/70 dark:text-blue-100/70" numberOfLines={1}>
                            {item.userEmail}
                        </Text>
                    </View>
                </View>

                {/* Ministério */}
                <Text className="font-nunito text-sm text-slate-900/70 dark:text-blue-100/70 mb-2">
                    {item.ministryName}
                </Text>

                {/* Seletor de papel */}
                <View>
                    <Text className="font-nunito-semibold text-sm text-slate-900 dark:text-blue-100 mb-2">
                        Papel no Ministério
                    </Text>
                    <View className="flex-row gap-2">
                        {roleOptions.map((role) => {
                            const isSelected = item.role === role;
                            return (
                                <Pressable
                                    key={role}
                                    onPress={() => handleRoleChange(item, role as 'lider' | 'ministro' | 'levita')}
                                    className={`flex-1 p-2 rounded-xl ${
                                        isSelected 
                                            ? 'bg-blue-700 dark:bg-blue-300' 
                                            : 'bg-slate-200 dark:bg-slate-600'
                                    }`}
                                >
                                    <Text className={`font-nunito-semibold text-center text-sm ${
                                        isSelected 
                                            ? 'text-blue-100 dark:text-slate-900' 
                                            : 'text-slate-900 dark:text-blue-100'
                                    }`}>
                                        {roleLabels[role]}
                                    </Text>
                                </Pressable>
                            );
                        })}
                    </View>
                </View>
            </View>
        );
    };

    if (members.length === 0) {
        return (
            <View className="flex-1 items-center justify-center">
                <UserIcon color={baseColor} size={64} opacity={0.3} />
                <Text className="font-nunito text-lg text-slate-900/50 dark:text-blue-100/50 mt-4">
                    Nenhum membro encontrado
                </Text>
            </View>
        );
    }

    return (
        <FlatList
            data={members}
            keyExtractor={(item) => item.id}
            renderItem={renderMemberItem}
            showsVerticalScrollIndicator={false}
            contentContainerStyle={{ paddingBottom: 20 }}
        />
    );
}
