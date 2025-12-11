import React, { useEffect, useState } from 'react';
import { View, Text, useColorScheme, Pressable } from 'react-native';
import { useRouter } from 'expo-router';
import { ArrowLeftIcon, FunnelIcon } from 'react-native-heroicons/solid';
import { loadCache, getUser } from '@/services/localCache';
import BirthdayList from '@/components/home&others/birthdays/BirthdayList';
import SortPopUp from '@/components/home&others/SortPopUp';
import BirthdayFilterModal from '@/components/home&others/birthdays/BirthdayFilterModal';
import dayjs from 'dayjs';
import isBetween from 'dayjs/plugin/isBetween';

dayjs.extend(isBetween);

export type BirthdayUser = {
    id: string;
    nome: string;
    nomeSocial?: string;
    email: string;
    telefone: string;
    foto?: string;
    dataNascimento: string;
    daysUntilBirthday: number;
};

export type BirthdayFilterOptions = {
    searchText?: string;
    ministry?: string;
};

export default function Birthdays() {
    const colorScheme = useColorScheme();
    const router = useRouter();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const [allBirthdays, setAllBirthdays] = useState<BirthdayUser[]>([]);
    const [birthdays, setBirthdays] = useState<BirthdayUser[]>([]);
    const [sortOption, setSortOption] = useState<string>('Mais Próximos');
    const [isSortVisible, setIsSortVisible] = useState<boolean>(false);
    const [showFilterModal, setShowFilterModal] = useState(false);
    const [activeFilters, setActiveFilters] = useState<BirthdayFilterOptions>({});

    const sortOptions = ['Mais Próximos', 'Mais Distantes', 'A-Z', 'Z-A'];

    useEffect(() => {
        loadBirthdayData();
    }, []);

    useEffect(() => {
        applyFiltersAndSort();
    }, [allBirthdays, sortOption, activeFilters]);

    const loadBirthdayData = async () => {
        try {
            const cache = await loadCache();
            const currentUser = await getUser();

            if (!currentUser) return;

            // Busca ministérios do usuário
            const userMinistryIds = cache.ministryMembers
                .filter(member => member.idUsuario === currentUser.id)
                .map(member => member.idMinisterio);

            const ministries = cache.ministries.filter(m => m.idCriador === currentUser.id);
            const allUserMinistryIds = [
                ...ministries.map(m => m.id),
                ...userMinistryIds
            ];

            // Busca todos os membros dos ministérios do usuário
            const ministryMemberIds = cache.ministryMembers
                .filter(member => allUserMinistryIds.includes(member.idMinisterio))
                .map(member => member.idUsuario);

            // Pega usuários únicos
            const uniqueUserIds = Array.from(new Set([...ministryMemberIds, currentUser.id]));

            const today = dayjs();
            const oneMonthFromNow = today.add(1, 'month');

            // Filtra aniversariantes do próximo mês
            const birthdayUsers: BirthdayUser[] = uniqueUserIds
                .map(userId => {
                    // Busca usuário no cache (simulação - em produção viria da API)
                    const user = cache.user?.id === userId ? cache.user : null;
                    if (!user) return null;

                    const birthDate = dayjs(user.dataNascimento);
                    const thisYearBirthday = birthDate.year(today.year());
                    const nextYearBirthday = birthDate.year(today.year() + 1);

                    let upcomingBirthday = thisYearBirthday;
                    if (thisYearBirthday.isBefore(today, 'day')) {
                        upcomingBirthday = nextYearBirthday;
                    }

                    const daysUntil = upcomingBirthday.diff(today, 'day');

                    // Verifica se está no próximo mês
                    if (daysUntil >= 0 && daysUntil <= 30) {
                        return {
                            id: user.id,
                            nome: user.nome,
                            nomeSocial: user.nomeSocial,
                            email: user.email,
                            telefone: user.telefone,
                            foto: user.foto,
                            dataNascimento: user.dataNascimento,
                            daysUntilBirthday: daysUntil,
                        };
                    }

                    return null;
                })
                .filter((user): user is BirthdayUser => user !== null);

            console.log('🎂 [Birthdays] Aniversariantes encontrados:', birthdayUsers.length);
            setAllBirthdays(birthdayUsers);
        } catch (error) {
            console.error('❌ Erro ao carregar aniversariantes:', error);
        }
    };

    const applyFiltersAndSort = () => {
        let filtered = [...allBirthdays];

        // Aplicar filtro de busca por texto
        if (activeFilters.searchText) {
            const searchLower = activeFilters.searchText.toLowerCase();
            filtered = filtered.filter(user => 
                user.nome.toLowerCase().includes(searchLower) ||
                (user.nomeSocial && user.nomeSocial.toLowerCase().includes(searchLower)) ||
                user.email.toLowerCase().includes(searchLower) ||
                user.telefone.includes(searchLower)
            );
        }

        // Aplicar ordenação
        switch (sortOption) {
            case 'Mais Próximos':
                filtered.sort((a, b) => a.daysUntilBirthday - b.daysUntilBirthday);
                break;
            case 'Mais Distantes':
                filtered.sort((a, b) => b.daysUntilBirthday - a.daysUntilBirthday);
                break;
            case 'A-Z':
                filtered.sort((a, b) => {
                    const nameA = a.nomeSocial || a.nome;
                    const nameB = b.nomeSocial || b.nome;
                    return nameA.localeCompare(nameB);
                });
                break;
            case 'Z-A':
                filtered.sort((a, b) => {
                    const nameA = a.nomeSocial || a.nome;
                    const nameB = b.nomeSocial || b.nome;
                    return nameB.localeCompare(nameA);
                });
                break;
        }

        setBirthdays(filtered);
    };

    const handleApplyFilters = (filters: BirthdayFilterOptions) => {
        setActiveFilters(filters);
        setShowFilterModal(false);
    };

    const handleClearFilters = () => {
        setActiveFilters({});
        setShowFilterModal(false);
    };

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
                            Aniversariantes
                        </Text>
                    </View>

                    {/* Controles de filtro e ordenação */}
                    <View className="flex-row items-center gap-3">
                        <Pressable onPress={() => setShowFilterModal(true)}>
                            <FunnelIcon 
                                color={Object.keys(activeFilters).length > 0 ? '#3b82f6' : baseColor} 
                                size={24} 
                            />
                        </Pressable>
                        <Pressable onPress={() => setIsSortVisible(true)}>
                            <Text className="font-nunito-semibold text-sm text-slate-900 dark:text-blue-100">
                                {sortOption}
                            </Text>
                        </Pressable>
                    </View>
                </View>

                {/* Contador de aniversariantes */}
                <View className="mb-4">
                    <Text className="font-nunito text-base text-slate-900 dark:text-blue-100">
                        {birthdays.length} {birthdays.length === 1 ? 'aniversariante' : 'aniversariantes'} no próximo mês
                    </Text>
                </View>

                {/* Lista de Aniversáriantes */}
                <BirthdayList birthdays={birthdays} />
            </View>

            {/* Modal de Ordenação */}
            <SortPopUp
                visible={isSortVisible}
                options={sortOptions}
                onClose={() => setIsSortVisible(false)}
                onSelect={(option: string) => {
                    setSortOption(option);
                    setIsSortVisible(false);
                }}
            />

            {/* Modal de Filtros */}
            <BirthdayFilterModal
                visible={showFilterModal}
                onClose={() => setShowFilterModal(false)}
                onApplyFilters={handleApplyFilters}
                onClearFilters={handleClearFilters}
                activeFilters={activeFilters}
            />
        </View>
    );
}
