import React, { useState, useEffect } from 'react';
import { View, Text, Modal, Pressable, ScrollView, useColorScheme, Image } from 'react-native';
import { XMarkIcon, CheckIcon } from 'react-native-heroicons/solid';
import { XMarkIcon as XMarkIconOutline } from 'react-native-heroicons/outline';
import { loadCache, getUser } from '@/services/localCache';

interface ConfirmedMembersModalProps {
    visible: boolean;
    onClose: () => void;
    scheduleId: string;
    ministryId: string;
}

type Member = {
    id: string;
    nome: string;
    nomeSocial?: string;
    email: string;
    telefone: string;
    foto?: string;
    isConfirmed: boolean;
    isCurrentUser: boolean;
};

export default function ConfirmedMembersModal({
    visible,
    onClose,
    scheduleId,
    ministryId
}: ConfirmedMembersModalProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const [members, setMembers] = useState<Member[]>([]);
    const [allUsers, setAllUsers] = useState<any[]>([]);

    useEffect(() => {
        if (visible) {
            loadMembers();
        }
    }, [visible, scheduleId, ministryId]);

    useEffect(() => {
        console.log('👥 STATE CHANGED - members:', members.length, 'itens');
        if (members.length > 0) {
            console.log('👥 Primeiro membro:', members[0]);
        }
    }, [members]);

    const loadMembers = async () => {
        try {
            const cache = await loadCache();
            const currentUser = await getUser();
            
            console.log('👥 ConfirmedMembersModal - scheduleId:', scheduleId);
            console.log('👥 ConfirmedMembersModal - ministryId:', ministryId);
            console.log('👥 Cache ministryMembers:', cache.ministryMembers);
            console.log('👥 Cache scheduleAttendances:', cache.scheduleAttendances);
            
            if (!currentUser) return;

            // Busca todos os membros do ministério
            const ministryMembers = cache.ministryMembers.filter(
                m => m.idMinisterio === ministryId
            );
            console.log('👥 Membros do ministério filtrados:', ministryMembers);

            // Busca presenças do agendamento
            const scheduleAttendances = cache.scheduleAttendances?.filter(
                a => a.idAgendamento === scheduleId
            ) || [];
            console.log('👥 Presenças do agendamento:', scheduleAttendances);

            // Para cada membro, precisamos buscar os dados completos do usuário
            // Como não temos um array de users no cache, vamos simular dados para membros que não são o usuário atual
            const membersData: Member[] = ministryMembers.map(member => {
                const attendance = scheduleAttendances.find(a => a.idUsuario === member.idUsuario);
                const isCurrentUser = member.idUsuario === currentUser.id;
                
                if (isCurrentUser) {
                    return {
                        id: currentUser.id,
                        nome: currentUser.nome,
                        nomeSocial: currentUser.nomeSocial,
                        email: currentUser.email,
                        telefone: currentUser.telefone,
                        foto: currentUser.foto,
                        isConfirmed: attendance?.confirmado !== false,
                        isCurrentUser: true
                    };
                } else {
                    // Para outros membros, usamos dados simulados
                    // Em produção, isso viria de uma API
                    return {
                        id: member.idUsuario,
                        nome: `Membro ${member.idUsuario.substring(0, 8)}`,
                        email: `membro${member.idUsuario.substring(0, 4)}@email.com`,
                        telefone: '(11) 9****-****',
                        isConfirmed: attendance?.confirmado !== false,
                        isCurrentUser: false
                    };
                }
            });

            console.log('👥 Membros finais carregados:', membersData);
            setMembers(membersData);
        } catch (error) {
            console.error('Erro ao carregar membros:', error);
        }
    };

    const confirmedCount = members.filter(m => m.isConfirmed).length;
    const totalCount = members.length;

    console.log('👥 RENDER ConfirmedMembersModal - members.length:', members.length);
    console.log('👥 RENDER ConfirmedMembersModal - members:', JSON.stringify(members, null, 2));

    return (
        <Modal visible={visible} transparent animationType="fade">
            <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
                <View className="w-[90%] max-h-[80%] bg-slate-50 dark:bg-slate-700 rounded-xl p-6 shadow-xl">
                    {/* Header */}
                    <View className="flex-row items-center justify-between mb-4">
                        <View className="flex-1">
                            <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                                Presenças Confirmadas
                            </Text>
                            <Text className="font-nunito text-sm text-slate-600 dark:text-blue-200 mt-1">
                                {confirmedCount} de {totalCount} membros confirmados
                            </Text>
                        </View>
                        <Pressable onPress={onClose}>
                            <XMarkIcon size={24} color={baseColor} />
                        </Pressable>
                    </View>

                    {/* Lista de membros */}
                    <ScrollView 
                        style={{minHeight: 300}}
                        showsVerticalScrollIndicator={false}
                    >
                        {members.length === 0 ? (
                            <Text className="font-nunito text-center text-slate-600 dark:text-blue-200 py-8">
                                Nenhum membro encontrado
                            </Text>
                        ) : (
                            members.map((member) => {
                                const cardBgColor = member.isConfirmed
                                    ? 'bg-slate-200 dark:bg-slate-600'
                                    : 'bg-slate-300 dark:bg-slate-800';
                                
                                return (
                                    <View
                                        key={member.id}
                                        className={`flex-row items-center p-4 mb-3 rounded-xl ${cardBgColor}`}
                                    >
                                        {/* Foto */}
                                        <View className="w-16 h-16 rounded-full bg-slate-400 dark:bg-slate-500 mr-4 items-center justify-center overflow-hidden">
                                            {member.foto ? (
                                                <Image 
                                                    source={{ uri: member.foto }} 
                                                    className="w-full h-full"
                                                    resizeMode="cover"
                                                />
                                            ) : (
                                                <Text className="font-nunito-bold text-2xl text-white">
                                                    {member.nome.charAt(0).toUpperCase()}
                                                </Text>
                                            )}
                                        </View>

                                        {/* Informações */}
                                        <View className="flex-1">
                                            <Text className="font-nunito-bold text-lg text-slate-900 dark:text-blue-100">
                                                {member.nomeSocial || member.nome}
                                            </Text>
                                            <Text className="font-nunito text-sm text-slate-600 dark:text-blue-200">
                                                {member.email}
                                            </Text>
                                            <Text className="font-nunito text-sm text-slate-600 dark:text-blue-200">
                                                {member.telefone}
                                            </Text>
                                        </View>

                                        {/* Ícone de status */}
                                        <View className="ml-3">
                                            {member.isConfirmed ? (
                                                <View className="w-10 h-10 items-center justify-center bg-blue-500 rounded-full">
                                                    <CheckIcon size={24} color="#ffffff" />
                                                </View>
                                            ) : (
                                                <View className="w-10 h-10 items-center justify-center bg-red-500 rounded-full">
                                                    <XMarkIconOutline size={24} color="#ffffff" strokeWidth={3} />
                                                </View>
                                            )}
                                        </View>
                                    </View>
                                );
                            })
                        )}
                    </ScrollView>
                </View>
            </View>
        </Modal>
    );
}
