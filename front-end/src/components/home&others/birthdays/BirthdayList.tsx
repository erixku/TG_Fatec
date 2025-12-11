import React from 'react';
import { View, Text, FlatList, Image, useColorScheme, Linking, Pressable } from 'react-native';
import { UserIcon, EnvelopeIcon, PhoneIcon, CakeIcon } from 'react-native-heroicons/solid';
import { BirthdayUser } from '@/app/homeMenu/birthdays';
import dayjs from 'dayjs';

interface BirthdayListProps {
    birthdays: BirthdayUser[];
}

export default function BirthdayList({ birthdays }: BirthdayListProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8';

    const handleCall = (phoneNumber: string) => {
        Linking.openURL(`tel:${phoneNumber}`);
    };

    const handleEmail = (email: string) => {
        Linking.openURL(`mailto:${email}`);
    };

    const renderBirthdayItem = ({ item }: { item: BirthdayUser }) => {
        const birthDate = dayjs(item.dataNascimento);
        const formattedDate = birthDate.format('DD/MM');
        const displayName = item.nomeSocial || item.nome;
        
        const getDaysText = () => {
            if (item.daysUntilBirthday === 0) return 'Hoje!';
            if (item.daysUntilBirthday === 1) return 'Amanhã';
            return `Em ${item.daysUntilBirthday} dias`;
        };

        return (
            <View className="mb-4 p-4 rounded-xl bg-slate-100 dark:bg-slate-800">
                <View className="flex-row gap-4">
                    {/* Foto do usuário */}
                    <View className="w-20 h-20 rounded-full overflow-hidden bg-slate-200 dark:bg-slate-600 items-center justify-center">
                        {item.foto ? (
                            <Image 
                                source={{ uri: item.foto }} 
                                className="w-full h-full"
                                resizeMode="cover"
                            />
                        ) : (
                            <UserIcon color={baseColor} size={40} />
                        )}
                    </View>

                    {/* Informações do usuário */}
                    <View className="flex-1 justify-between">
                        {/* Nome */}
                        <Text className="font-nunito-bold text-lg text-slate-900 dark:text-blue-100" numberOfLines={1}>
                            {displayName}
                        </Text>

                        {/* Data de aniversário e dias até */}
                        <View className="flex-row items-center justify-between">
                            <Text className="font-nunito text-base text-slate-900/70 dark:text-blue-100/70">
                                {formattedDate}
                            </Text>
                            <View className="flex-row items-center gap-1">
                                <CakeIcon color={item.daysUntilBirthday === 0 ? '#ef4444' : contrastColor} size={16} />
                                <Text className={`font-nunito-semibold text-sm ${item.daysUntilBirthday === 0 ? 'text-red-500' : 'text-slate-900 dark:text-blue-100'}`}>
                                    {getDaysText()}
                                </Text>
                            </View>
                        </View>

                        {/* Email e Telefone */}
                        <View className="flex-row items-center gap-4 mt-2">
                            <Pressable 
                                onPress={() => handleEmail(item.email)}
                                className="flex-row items-center gap-1 flex-1"
                            >
                                <EnvelopeIcon color={contrastColor} size={14} />
                                <Text 
                                    className="font-nunito text-sm text-slate-900 dark:text-blue-100"
                                    numberOfLines={1}
                                >
                                    {item.email}
                                </Text>
                            </Pressable>

                            <Pressable 
                                onPress={() => handleCall(item.telefone)}
                                className="flex-row items-center gap-1"
                            >
                                <PhoneIcon color={contrastColor} size={14} />
                                <Text className="font-nunito text-sm text-slate-900 dark:text-blue-100">
                                    {item.telefone}
                                </Text>
                            </Pressable>
                        </View>
                    </View>
                </View>
            </View>
        );
    };

    if (birthdays.length === 0) {
        return (
            <View className="flex-1 items-center justify-center">
                <CakeIcon color={baseColor} size={64} opacity={0.3} />
                <Text className="font-nunito text-lg text-slate-900/50 dark:text-blue-100/50 mt-4">
                    Nenhum aniversariante encontrado
                </Text>
            </View>
        );
    }

    return (
        <FlatList
            data={birthdays}
            keyExtractor={(item) => item.id}
            renderItem={renderBirthdayItem}
            showsVerticalScrollIndicator={false}
            contentContainerStyle={{ paddingBottom: 20 }}
        />
    );
}
