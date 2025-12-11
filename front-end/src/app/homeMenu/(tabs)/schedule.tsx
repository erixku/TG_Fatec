import React, { useState, useEffect } from "react";
import { View, Text, useColorScheme, Button, Pressable } from "react-native";
import AnimatedScreenWrapper from "../../../components/home&others/AnimatedScreenWrapper";
import CustomCalendar from "@/components/CustomCalendar";
import dayjs from "dayjs";
import { CalendarIcon, ChevronRightIcon, FunnelIcon, ListBulletIcon, PlusIcon } from "react-native-heroicons/solid";
import { boolean } from "zod";
import ScheduleItens from "@/components/home&others/schedues/ScheduleItens";
import SortPopUp from "@/components/home&others/SortPopUp";
import { 
    getSchedulesByMinistry, 
    getCommitmentsByMinistry, 
    getUser, 
    getUserMinistries,
    addSchedule,
    addCommitment,
    generateId
} from "@/services/localCache";
import { useMinistryRole } from "@/hooks/useMinistryRole";
import SelectTypeModal from "@/components/home&others/SelectTypeModal";
import ScheduleFormModal from "@/components/home&others/ScheduleFormModal";
import CommitmentFormModal from "@/components/home&others/CommitmentFormModal";
import FilterModal, { FilterOptions } from "@/components/home&others/FilterModal";

type GenericSchedule = {
    _id?: string | number;
    key?: string | number;
    title?: string;
    description?: string;
    date?: string;
    time?: string;
    responsible_user?: string;
    type?: string;
    createdAt?: string;
    createdBy?: string;
};

export default function SchedulePage() {
    const [selectedDate, setSelectedDate] = useState(dayjs());
    const [isCalendar, setIsCalendar] = useState<boolean>(false);
    const [allItems, setAllItems] = useState<GenericSchedule[]>([]);
    const [filteredItems, setFilteredItems] = useState<GenericSchedule[]>([]);
    const [ministryId, setMinistryId] = useState<string | null>(null);
    
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const [isSortVisible, setIsSortVisible] = useState<boolean>(false);
    const [sortOption, setSortOption] = useState<string>('Mais Recentes');
    
    // Estados dos modais
    const [showSelectTypeModal, setShowSelectTypeModal] = useState(false);
    const [showScheduleModal, setShowScheduleModal] = useState(false);
    const [showCommitmentModal, setShowCommitmentModal] = useState(false);
    const [showFilterModal, setShowFilterModal] = useState(false);
    
    // Estados de filtro
    const [activeFilters, setActiveFilters] = useState<FilterOptions>({});
    
    // Hook de permissões
    const { isLeader, isLoading: roleLoading } = useMinistryRole(ministryId);
    
    // Verifica se pode adicionar (líder ou admin)
    const canAdd = isLeader;

    useEffect(() => {
        loadScheduleData();
    }, []);

    useEffect(() => {
        console.log('🔄 useEffect disparado - isCalendar:', isCalendar, '| allItems:', allItems.length);
        if (allItems.length === 0) {
            console.log('⚠️ Sem itens carregados ainda');
            setFilteredItems([]);
            return;
        }
        
        if (isCalendar) {
            console.log('📅 Modo calendário - filtrando por data');
            filterBySelectedDate();
        } else {
            console.log('📋 Modo lista - ordenando todos');
            sortAllItems();
        }
    }, [selectedDate, isCalendar, sortOption, allItems, activeFilters]);

    const loadScheduleData = async () => {
        try {
            const user = await getUser();
            if (!user) {
                console.log('⚠️ Usuário não encontrado');
                return;
            }

            const userMinistries = await getUserMinistries(user.id);
            if (userMinistries.length === 0) {
                console.log('⚠️ Usuário não tem ministérios');
                return;
            }

            // Pega o primeiro ministério do usuário (ou pode implementar seleção de ministério)
            const currentMinistryId = userMinistries[0].idMinisterio;
            setMinistryId(currentMinistryId);

            const [schedules, commitments] = await Promise.all([
                getSchedulesByMinistry(currentMinistryId),
                getCommitmentsByMinistry(currentMinistryId)
            ]);

            // Mapeia agendamentos para o formato GenericSchedule
            const schedulesFormatted: GenericSchedule[] = schedules.map((s) => ({
                _id: s.id,
                key: s.id,
                title: s.title,
                description: s.description,
                date: s.date,
                time: s.time,
                type: 'schedule',
                createdAt: s.createdAt,
                createdBy: s.createdBy
            }));

            // Mapeia compromissos para o formato GenericSchedule
            const commitmentsFormatted: GenericSchedule[] = commitments.map((c) => ({
                _id: c.id,
                key: c.id,
                title: c.title,
                description: c.description,
                date: c.date,
                time: c.time,
                responsible_user: c.responsible_user,
                type: 'commitment',
                createdAt: c.createdAt,
                createdBy: c.createdBy
            }));

            const combined = [...schedulesFormatted, ...commitmentsFormatted];
            setAllItems(combined);
            console.log('✅ Dados carregados:', combined.length, 'itens');
        } catch (error) {
            console.error('❌ Erro ao carregar dados:', error);
        }
    };

    const applyFiltersToItems = (items: GenericSchedule[]) => {
        let filtered = [...items];

        // Filtro por usuário criador
        if (activeFilters.createdBy) {
            filtered = filtered.filter(item => item.createdBy === activeFilters.createdBy);
            console.log('🔍 Filtrado por criador:', activeFilters.createdBy, '- Resultado:', filtered.length);
        }

        // Filtro por horário
        if (activeFilters.startTime || activeFilters.endTime) {
            filtered = filtered.filter(item => {
                if (!item.time) return false;
                
                const itemTime = item.time; // Formato: "HH:MM"
                
                if (activeFilters.startTime) {
                    // Compara strings de horário (funciona porque formato é HH:MM)
                    if (itemTime < activeFilters.startTime) return false;
                }
                
                if (activeFilters.endTime) {
                    if (itemTime > activeFilters.endTime) return false;
                }
                
                return true;
            });
            console.log('🔍 Filtrado por horário:', activeFilters.startTime, '-', activeFilters.endTime, '- Resultado:', filtered.length);
        }

        return filtered;
    };

    const sortAllItems = () => {
        let sorted = applyFiltersToItems(allItems);
        
        // Ordenação baseada na opção selecionada
        sorted.sort((a, b) => {
            switch (sortOption) {
                case 'Mais Recentes':
                    // Ordena por data de criação (mais recente primeiro)
                    const dateARecent = new Date(a.createdAt || a.date || '');
                    const dateBRecent = new Date(b.createdAt || b.date || '');
                    return dateBRecent.getTime() - dateARecent.getTime();
                
                case 'Mais Antigos':
                    // Ordena por data de criação (mais antigo primeiro)
                    const dateAOld = new Date(a.createdAt || a.date || '');
                    const dateBOld = new Date(b.createdAt || b.date || '');
                    return dateAOld.getTime() - dateBOld.getTime();
                
                case 'A-Z':
                    // Ordena por título em ordem alfabética
                    return a.title.localeCompare(b.title);
                
                case 'Z-A':
                    // Ordena por título em ordem alfabética reversa
                    return b.title.localeCompare(a.title);
                
                default:
                    // Padrão: mais recentes
                    const dateADefault = new Date(a.createdAt || a.date || '');
                    const dateBDefault = new Date(b.createdAt || b.date || '');
                    return dateBDefault.getTime() - dateADefault.getTime();
            }
        });

        setFilteredItems(sorted);
        console.log('📊 Ordenação aplicada:', sortOption, '|', sorted.length, 'itens');
    };

    const filterBySelectedDate = () => {
        // Garantir que selectedDate é um objeto dayjs válido
        const dateObj = dayjs(selectedDate);
        const selectedDateStr = dateObj.format('YYYY-MM-DD');
        
        console.log('📅 Data selecionada (objeto):', selectedDate);
        console.log('📅 Data selecionada (string):', selectedDateStr);
        console.log('📋 Total de itens disponíveis:', allItems.length);
        
        // Primeiro aplica os filtros gerais
        let itemsToFilter = applyFiltersToItems(allItems);
        
        // Log das primeiras 5 datas para debug
        itemsToFilter.slice(0, 5).forEach((item, index) => {
            console.log(`Item ${index}: date="${item.date}" | title="${item.title}"`);
        });
        
        // Filtra itens pela data selecionada
        const filtered = itemsToFilter.filter((item) => {
            // Normaliza a data do item para comparação
            const itemDateStr = item.date ? dayjs(item.date).format('YYYY-MM-DD') : '';
            const matches = itemDateStr === selectedDateStr;
            
            if (matches) {
                console.log('✅ Match encontrado:', item.title, '|', itemDateStr, '===', selectedDateStr);
            }
            
            return matches;
        });
        
        console.log('🎯 Itens filtrados:', filtered.length);
        
        // Ordena por horário crescente
        filtered.sort((a, b) => {
            const timeA = a.time || '00:00';
            const timeB = b.time || '00:00';
            return timeA.localeCompare(timeB);
        });

        setFilteredItems(filtered);
        console.log('✅ filteredItems atualizado com', filtered.length, 'itens');
    };

    const handleApplyFilters = (filters: FilterOptions) => {
        console.log('🔍 Aplicando filtros:', filters);
        setActiveFilters(filters);
    };

    const handleCreateSchedule = async (data: { title: string; description: string; date: Date; time: string }) => {
        try {
            if (!ministryId) {
                console.error('❌ Ministério não definido');
                return;
            }

            const user = await getUser();
            if (!user) {
                console.error('❌ Usuário não encontrado');
                return;
            }

            const scheduleId = generateId();
            await addSchedule({
                id: scheduleId,
                idMinisterio: ministryId,
                title: data.title,
                description: data.description,
                date: dayjs(data.date).format('YYYY-MM-DD'),
                time: data.time,
                createdBy: user.id,
                createdAt: new Date().toISOString(),
            });

            console.log('✅ Agendamento criado com sucesso');
            await loadScheduleData();
            setShowScheduleModal(false);
        } catch (error) {
            console.error('❌ Erro ao criar agendamento:', error);
        }
    };

    const handleCreateCommitment = async (data: { 
        title: string; 
        description: string; 
        date: Date; 
        time: string; 
        responsible_user: string 
    }) => {
        try {
            if (!ministryId) {
                console.error('❌ Ministério não definido');
                return;
            }

            const user = await getUser();
            if (!user) {
                console.error('❌ Usuário não encontrado');
                return;
            }

            const commitmentId = generateId();
            await addCommitment({
                id: commitmentId,
                idMinisterio: ministryId,
                title: data.title,
                description: data.description,
                date: dayjs(data.date).format('YYYY-MM-DD'),
                time: data.time,
                responsible_user: data.responsible_user,
                createdBy: user.id,
                createdAt: new Date().toISOString(),
            });

            console.log('✅ Compromisso criado com sucesso');
            await loadScheduleData();
            setShowCommitmentModal(false);
        } catch (error) {
            console.error('❌ Erro ao criar compromisso:', error);
        }
    };

    return (
        <AnimatedScreenWrapper>
            <View className="flex-col flex-1 items-center mt-2 bg-slate-300 dark:bg-slate-800">
                <View className="flex-1 w-full items-center pb-28">
                    <View className="flex pt-10 h-full w-[90%] lg:w-[50%] justify-center gap-y-4 rounded-xl">
                        <View className="flex-1 flex-col gap-y-2">
                            <View className={`flex-row ${!isCalendar? "justify-between":"justify-end"}`}>
                                {!isCalendar && (        
                                    <Pressable className="flex-row items-center gap-x-2" onPress={() => setIsSortVisible(true)}>
                                        <Text className="text-2xl font-nunito-semibold dark:text-blue-100 text-slate-900">{sortOption}</Text>
                                        <ChevronRightIcon size={20} color={baseColor}/>
                                    </Pressable>
                                )}
                                <View className="flex-row gap-x-2">
                                    {canAdd && (
                                        <Pressable onPress={() => setShowSelectTypeModal(true)}>
                                            <PlusIcon size={20} color={baseColor} />
                                        </Pressable>
                                    )}
                                    <Pressable onPress={() => setShowFilterModal(true)}>
                                        <FunnelIcon size={20} color={baseColor} />
                                    </Pressable>
                                    <Pressable onPress={() => setIsCalendar(!isCalendar)}>
                                        {isCalendar? (<CalendarIcon size={20} color={baseColor} />):(<ListBulletIcon size={20} color={baseColor} />)}
                                    </Pressable>
                                </View>
                            </View>
                            {isCalendar? (
                                <View className="flex-1 flex-col gap-y-2">
                                    <CustomCalendar 
                                        onDateChange={(date) => {
                                            console.log('🗓️ CustomCalendar onDateChange chamado:', date);
                                            console.log('🗓️ Tipo de date recebido:', typeof date);
                                            const newDate = dayjs(date);
                                            console.log('🗓️ dayjs convertido:', newDate.format('YYYY-MM-DD'));
                                            setSelectedDate(newDate);
                                        }} 
                                        date={selectedDate}
                                        initialView="day"
                                    />
                                    <View className="flex-1 flex-col w-full">
                                        {filteredItems.length === 0 ? (
                                            <View className="flex-1 items-center justify-center p-8">
                                                <Text className="text-xl font-nunito-semibold text-center dark:text-blue-100 text-slate-900">
                                                    Não há compromissos nesse dia
                                                </Text>
                                            </View>
                                        ) : (
                                            <ScheduleItens schedules={filteredItems}/>
                                        )}
                                    </View>
                                </View>
                            ):(
                                <View className="flex-1 flex-col w-full">
                                    {filteredItems.length === 0 ? (
                                        <View className="flex-1 items-center justify-center p-8">
                                            <Text className="text-xl font-nunito-semibold text-center dark:text-blue-100 text-slate-900">
                                                Nenhum agendamento ou compromisso cadastrado
                                            </Text>
                                        </View>
                                    ) : (
                                        <ScheduleItens schedules={filteredItems}/>
                                    )}
                                </View>
                            )}
                            
                        </View>
                    </View>
                </View>
            </View>
            <SortPopUp
                visible={isSortVisible}
                options={['Mais Recentes', 'Mais Antigos', 'A-Z', 'Z-A']}
                onClose={() => setIsSortVisible(false)}
                onSelect={(option:string) => {
                    // Lógica de ordenação aqui
                    setSortOption(option);
                    console.log('Opção selecionada:', option);
                }}
            />

            {/* Modal de seleção de tipo */}
            <SelectTypeModal
                visible={showSelectTypeModal}
                onClose={() => setShowSelectTypeModal(false)}
                onSelectSchedule={() => setShowScheduleModal(true)}
                onSelectCommitment={() => setShowCommitmentModal(true)}
            />

            {/* Modal de formulário de agendamento */}
            <ScheduleFormModal
                visible={showScheduleModal}
                onClose={() => setShowScheduleModal(false)}
                onSubmit={handleCreateSchedule}
            />

            {/* Modal de formulário de compromisso */}
            <CommitmentFormModal
                visible={showCommitmentModal}
                onClose={() => setShowCommitmentModal(false)}
                onSubmit={handleCreateCommitment}
            />

            {/* Modal de filtros */}
            <FilterModal
                visible={showFilterModal}
                onClose={() => setShowFilterModal(false)}
                onApplyFilters={handleApplyFilters}
                ministryId={ministryId}
            />
        </AnimatedScreenWrapper>
    );
}