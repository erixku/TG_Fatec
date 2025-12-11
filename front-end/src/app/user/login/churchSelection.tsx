import React, { useRef, useState, useEffect } from "react";
import { View, Pressable, useColorScheme, ScrollView, LayoutChangeEvent, Text, ActivityIndicator } from "react-native";
import { useRouter } from "expo-router";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon, PlusIcon, UserGroupIcon } from "react-native-heroicons/solid";
import ResetPassword from "@/components/login&register/ResetPassword";
import ChurchItens from "@/components/church/ChurchItem";
import { ChurchType, itensChurch } from "@/mocks/itensChurch";
import { MinisteryType, itensMinistery } from "@/mocks/itensMinistery";
import MinisteryList from "@/components/church/MinisteryList";
import { getChurches, getMinistries, getUser, loadCache } from "@/services/localCache";

export default function RegisterChurch() {
    const colorScheme = useColorScheme();
    const router = useRouter();
    const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
    const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

    const [step, setStep] = useState<'church' | 'ministry'>('church');
    const [selectedChurch, setSelectedChurch] = useState<any>(null);
    const [selectedMinistry, setSelectedMinistry] = useState<any>(null);
    const [userChurches, setUserChurches] = useState<any[]>([]);
    const [userMinistries, setUserMinistries] = useState<any[]>([]);
    const [isLoading, setIsLoading] = useState(true);

    // Carrega igrejas e ministérios do usuário do cache
    useEffect(() => {
        loadUserData();
    }, []);

    const loadUserData = async () => {
        try {
            console.log('📦 [ChurchSelection] Carregando dados do usuário do cache...');
            setIsLoading(true);

            const user = await getUser();
            if (!user) {
                console.warn('⚠️ [ChurchSelection] Usuário não encontrado no cache');
                setUserChurches([]);
                setUserMinistries([]);
                setIsLoading(false);
                return;
            }

            console.log('👤 [ChurchSelection] Usuário:', user.nome);

            // Busca todas as igrejas, ministérios e membros
            const allChurches = await getChurches();
            const allMinistries = await getMinistries();
            const cache = await loadCache();
            
            console.log('⛪ [ChurchSelection] Total de igrejas no cache:', allChurches.length);

            // Busca ministérios onde o usuário é membro
            const userMinistryIds = cache.ministryMembers
                .filter(member => member.idUsuario === user.id)
                .map(member => member.idMinisterio);

            console.log('🎵 [ChurchSelection] Ministérios do usuário (como membro):', userMinistryIds.length);

            // Busca IDs das igrejas dos ministérios do usuário
            const churchIdsFromMinistries = allMinistries
                .filter(m => userMinistryIds.includes(m.id))
                .map(m => m.idIgreja);

            // Filtra igrejas onde o usuário é criador, dono OU membro de algum ministério
            const churches = allChurches.filter(
                church => 
                    church.idCriador === user.id || 
                    church.idDono === user.id ||
                    churchIdsFromMinistries.includes(church.id)
            );
            console.log('⛪ [ChurchSelection] Igrejas do usuário:', churches.length);

            // Mapeia dados do cache para formato compatível com o componente
            const mappedChurches = churches.map(church => ({
                ...church,
                uuid: church.id, // Adiciona uuid para compatibilidade
            }));
            setUserChurches(mappedChurches);

            if (churches.length > 0) {
                // Busca ministérios das igrejas do usuário OU onde ele é membro
                const churchIds = churches.map(c => c.id);
                const ministries = allMinistries.filter(m => 
                    churchIds.includes(m.idIgreja) || userMinistryIds.includes(m.id)
                );
                console.log('🙏 [ChurchSelection] Ministérios do usuário:', ministries.length);
                
                // Mapeia dados do cache para formato compatível com o componente
                const mappedMinistries = ministries.map(ministry => ({
                    ...ministry,
                    uuid: ministry.id, // Adiciona uuid para compatibilidade
                    codigo: ministry.id.substring(0, 8), // Gera código baseado no ID
                }));
                setUserMinistries(mappedMinistries);
            } else {
                setUserMinistries([]);
            }

        } catch (error) {
            console.error('❌ [ChurchSelection] Erro ao carregar dados:', error);
            setUserChurches([]);
            setUserMinistries([]);
        } finally {
            setIsLoading(false);
        }
    };

    // Filtra ministérios da igreja selecionada
    const filteredMinistries = selectedChurch 
        ? userMinistries.filter(ministry => ministry.idIgreja === selectedChurch.id)
        : [];

    const handleChurchSelect = (church: any) => {
        setSelectedChurch(church);
        setSelectedMinistry(null); // Reset ministério ao trocar igreja
        setStep('ministry');
    };

    const handleMinistrySelect = (ministry: any) => {
        setSelectedMinistry(ministry);
    };

    const handleBack = () => {
        if (step === 'ministry') {
            setStep('church');
            setSelectedMinistry(null);
        } else {
            router.back();
        }
    };

    const handleNext = async () => {
        if (!selectedChurch || !selectedMinistry) {
            console.log('⚠️ Selecione uma igreja e um ministério');
            return;
        }

        console.log('✅ Igreja selecionada:', selectedChurch.nome);
        console.log('✅ Ministério selecionado:', selectedMinistry.nome);
        
        // TODO: Salvar seleção no cache e redirecionar para home
        router.replace('/homeMenu/(tabs)/');
    };

    return(
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
            <View className="flex-1 p-10 max-h-[45rem] w-full max-w-[80%] justify-between gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
                {/* Cabeçalho */}
                <View className="flex-row items-center justify-center">
                    <Pressable onPress={handleBack} className="absolute left-0">
                        <ArrowLeftIcon color={baseColor} />
                    </Pressable>
                    <View className="scale-75">
                        <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor} />
                    </View>
                </View>

                {/* Título da etapa */}
                <View className="items-center">
                    {selectedChurch && step === 'ministry' && (
                        <Text className="font-nunito text-slate-700 dark:text-blue-200 mt-1">
                            {selectedChurch.nome}
                        </Text>
                    )}
                </View>

                {/* Lista de Igrejas ou Ministérios */}
                <View className="flex-1">
                    {isLoading ? (
                        <View className="flex-1 items-center justify-center">
                            <ActivityIndicator size="large" color={contrastColor} />
                            <Text className="font-nunito text-slate-700 dark:text-slate-300 mt-4">
                                Carregando...
                            </Text>
                        </View>
                    ) : step === 'church' ? (
                        userChurches.length === 0 ? (
                            <View className="flex-1 items-center justify-center gap-y-6 px-8">
                                <Text className="font-nunito-semibold text-xl text-center text-slate-700 dark:text-slate-300">
                                    Você não possui nenhuma igreja ou ministério associado
                                </Text>
                                <Text className="font-nunito text-base text-center text-slate-600 dark:text-slate-400">
                                    Crie uma nova igreja ou entre em uma já existente
                                </Text>
                                
                                <View className="w-full gap-y-3 mt-4">
                                    <Pressable 
                                        onPress={() => router.push('/church/register')} 
                                        className="flex-row p-3 px-6 h-12 gap-x-4 w-full items-center justify-center bg-blue-400 dark:bg-blue-600 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl"
                                    >
                                        <PlusIcon color={baseColor} size={20} />
                                        <Text className="font-nunito text-lg text-slate-900 dark:text-blue-100">
                                            Cadastrar nova igreja
                                        </Text>
                                    </Pressable>

                                    <Pressable 
                                        onPress={() => router.push('/church/login')} 
                                        className="flex-row p-3 px-6 h-12 gap-x-4 w-full items-center justify-center bg-blue-400 dark:bg-blue-600 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl"
                                    >
                                        <UserGroupIcon color={baseColor} size={20} />
                                        <Text className="font-nunito text-lg text-slate-900 dark:text-blue-100">
                                            Entrar em uma igreja
                                        </Text>
                                    </Pressable>
                                </View>
                            </View>
                        ) : (
                            <ChurchItens churches={userChurches} onSelectChurch={handleChurchSelect} />
                        )
                    ) : (
                        <MinisteryList 
                            ministries={filteredMinistries} 
                            selectedMinistry={selectedMinistry}
                            onSelectMinistry={handleMinistrySelect}
                        />
                    )}
                </View>

                {/* Botão Próximo (apenas na etapa de ministério) */}
                <View className="flex items-center w-full">
                    {step === 'ministry' && (
                        <CustomButton 
                            label="Confirmar"
                            onPress={handleNext}
                            disabled={!selectedMinistry}
                        />
                    )}
                </View>
            </View>
        </View>
    )
}