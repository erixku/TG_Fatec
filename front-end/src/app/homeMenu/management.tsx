import React, { useEffect, useState } from 'react';
import { View, Text, useColorScheme, Pressable, ScrollView, Alert } from 'react-native';
import { useRouter } from 'expo-router';
import { ArrowLeftIcon, PlusIcon, UserGroupIcon, BuildingLibraryIcon } from 'react-native-heroicons/solid';
import { loadCache, getUser, getMinistries, getChurches, getMinistryMembers, getMemberRole } from '@/services/localCache';
import ManagementTabs from '@/components/home&others/management/ManagementTabs';
import MinistryManagementList from '@/components/home&others/management/MinistryManagementList';
import MemberRoleList from '@/components/home&others/management/MemberRoleList';
import CreateMinistryModal from '@/components/home&others/management/CreateMinistryModal';

export type MinistryManagementData = {
    id: string;
    nome: string;
    descricao: string;
    codigo: string;
    foto?: string;
    memberCount: number;
    churchName: string;
};

export type MemberRoleData = {
    id: string;
    userId: string;
    userName: string;
    userEmail: string;
    userPhoto?: string;
    role: 'lider' | 'ministro' | 'levita';
    ministryId: string;
    ministryName: string;
};

export default function Management() {
    const colorScheme = useColorScheme();
    const router = useRouter();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const [isLeader, setIsLeader] = useState(false);
    const [loading, setLoading] = useState(true);
    const [activeTab, setActiveTab] = useState<'ministries' | 'members'>('ministries');
    const [ministries, setMinistries] = useState<MinistryManagementData[]>([]);
    const [members, setMembers] = useState<MemberRoleData[]>([]);
    const [userChurchId, setUserChurchId] = useState<string | null>(null);
    const [showCreateMinistryModal, setShowCreateMinistryModal] = useState(false);

    useEffect(() => {
        checkLeadershipAndLoadData();
    }, []);

    const checkLeadershipAndLoadData = async () => {
        try {
            const user = await getUser();
            if (!user) {
                Alert.alert('Erro', 'Usuário não encontrado');
                router.back();
                return;
            }

            const cache = await loadCache();
            
            // Verifica se é líder de algum ministério
            const leaderMemberships = cache.ministryMembers.filter(
                member => member.idUsuario === user.id && member.papel === 'lider'
            );

            const createdMinistries = cache.ministries.filter(
                ministry => ministry.idCriador === user.id
            );

            if (leaderMemberships.length === 0 && createdMinistries.length === 0) {
                Alert.alert(
                    'Acesso Negado',
                    'Você precisa ser líder de um ministério para acessar esta página.',
                    [{ text: 'OK', onPress: () => router.back() }]
                );
                return;
            }

            setIsLeader(true);

            // Carrega dados dos ministérios onde é líder
            const leaderMinistryIds = [
                ...leaderMemberships.map(m => m.idMinisterio),
                ...createdMinistries.map(m => m.id)
            ];

            const allMinistries = await getMinistries();
            const allChurches = await getChurches();

            const leaderMinistries = allMinistries.filter(m => 
                leaderMinistryIds.includes(m.id)
            );

            // Busca igreja do usuário (primeira igreja onde é líder)
            if (leaderMinistries.length > 0) {
                setUserChurchId(leaderMinistries[0].idIgreja);
            }

            // Formata dados dos ministérios
            const ministriesData: MinistryManagementData[] = await Promise.all(
                leaderMinistries.map(async (ministry) => {
                    const church = allChurches.find(c => c.id === ministry.idIgreja);
                    const membersCount = await getMinistryMembers(ministry.id);
                    
                    return {
                        id: ministry.id,
                        nome: ministry.nome,
                        descricao: ministry.descricao,
                        codigo: ministry.id, // Usando ID como código
                        foto: ministry.foto,
                        memberCount: membersCount.length,
                        churchName: church?.nome || 'Igreja não encontrada',
                    };
                })
            );

            setMinistries(ministriesData);

            // Carrega membros de todos os ministérios onde é líder
            const allMembers: MemberRoleData[] = [];
            
            for (const ministry of leaderMinistries) {
                const ministryMembers = await getMinistryMembers(ministry.id);
                
                for (const member of ministryMembers) {
                    // Busca dados do usuário (simulado - em produção viria da API)
                    const memberUser = cache.user?.id === member.idUsuario ? cache.user : null;
                    
                    if (memberUser) {
                        allMembers.push({
                            id: member.id,
                            userId: member.idUsuario,
                            userName: memberUser.nomeSocial || memberUser.nome,
                            userEmail: memberUser.email,
                            userPhoto: memberUser.foto,
                            role: member.papel,
                            ministryId: ministry.id,
                            ministryName: ministry.nome,
                        });
                    }
                }
            }

            setMembers(allMembers);
            setLoading(false);
        } catch (error) {
            console.error('❌ Erro ao verificar liderança:', error);
            Alert.alert('Erro', 'Não foi possível carregar os dados');
            router.back();
        }
    };

    const handleCreateMinistry = () => {
        setShowCreateMinistryModal(true);
    };

    const handleMinistryCreated = () => {
        checkLeadershipAndLoadData();
        setShowCreateMinistryModal(false);
    };

    if (loading) {
        return (
            <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
                <Text className="font-nunito text-lg text-slate-900 dark:text-blue-100">
                    Carregando...
                </Text>
            </View>
        );
    }

    if (!isLeader) {
        return null;
    }

    return (
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800 p-6">
            {/* Cartão flutuante centralizado com 80% da altura */}
            <View className="bg-slate-50 dark:bg-slate-700 rounded-xl shadow-md p-6 w-full h-[80%]">
                {/* Cabeçalho */}
                <View className="flex-row items-center justify-between mb-6">
                    <View className="flex-row items-center flex-1">
                        <Pressable onPress={() => router.back()} className="mr-4">
                            <ArrowLeftIcon color={baseColor} size={24} />
                        </Pressable>
                        <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                            Gerenciamento
                        </Text>
                    </View>

                    {/* Botão de criar ministério */}
                    {activeTab === 'ministries' && (
                        <Pressable 
                            onPress={handleCreateMinistry}
                            className="p-2 rounded-xl bg-blue-700 dark:bg-blue-300"
                        >
                            <PlusIcon color={colorScheme === 'dark' ? '#0f172a' : '#dbeafe'} size={20} />
                        </Pressable>
                    )}
                </View>

                {/* Tabs */}
                <ManagementTabs activeTab={activeTab} onTabChange={setActiveTab} />

                {/* Conteúdo */}
                <View className="flex-1 mt-4">
                    {activeTab === 'ministries' ? (
                        <MinistryManagementList 
                            ministries={ministries}
                            onRefresh={checkLeadershipAndLoadData}
                        />
                    ) : (
                        <MemberRoleList 
                            members={members}
                            ministries={ministries}
                            onRefresh={checkLeadershipAndLoadData}
                        />
                    )}
                </View>
            </View>

            {/* Modal de criar ministério */}
            <CreateMinistryModal
                visible={showCreateMinistryModal}
                onClose={() => setShowCreateMinistryModal(false)}
                onMinistryCreated={handleMinistryCreated}
                churchId={userChurchId}
            />
        </View>
    );
}
