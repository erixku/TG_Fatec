import React, { useRef, useState, useEffect } from "react";
import { View, Pressable, useColorScheme, ScrollView, LayoutChangeEvent, Alert } from "react-native";
import { useRouter, useLocalSearchParams } from "expo-router";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import { useFormContext } from "react-hook-form";
import { RegisterChurchFormData } from "@/schemas/registerChurchSchema";
import RegisterFormChurch from "@/components/church/RegisterFormChurch";
import RegisterFormMinistery from "@/components/church/RegisterFormMinistery";
import { registerChurch } from "@/api/registerChurch";
import { registerMinistry } from "@/api/registerMinistery";
import { getStoredTokens } from "@/api/loginUser";
import { useMinisteryForm } from "../_layout";

export default function RegisterChurch() {
    const colorScheme = useColorScheme();
    const router = useRouter();
    const { userId: userIdParam } = useLocalSearchParams<{ userId?: string }>();
    
    console.log('📝 [RegisterChurch] userId recebido via params:', userIdParam);
    
    const churchForm = useFormContext<RegisterChurchFormData>();
    const ministryForm = useMinisteryForm();
    const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
    const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

    const scrollViewRef = useRef<ScrollView>(null);
    const [step, setStep] = useState(0);
    const [containerWidth, setContainerWidth] = useState(0);
    const [isLoading, setIsLoading] = useState(false);
    const [churchId, setChurchId] = useState<string | null>(null);

    const steps = [
        { component: <RegisterFormChurch /> },
        { component: <RegisterFormMinistery /> }
    ];

    useEffect(() => {
        if (containerWidth > 0) {
            scrollViewRef.current?.scrollTo({ x: step * (containerWidth + 20), animated: true });
        }
    }, [step, containerWidth]);

    const handleLayout = (event: LayoutChangeEvent) => {
        const { width } = event.nativeEvent.layout;
        setContainerWidth(width || 1);
    };

    const handleBack = () => {
        if (step > 0) {
            setStep(prev => prev - 1);
        } else {
            router.back();
        }
    };

    const handleNext = async () => {
        if (isLoading) return;

        if (step === 0) {
            // Primeira etapa: Cadastrar igreja
            const isValid = await churchForm.trigger();
            
            if (!isValid) {
                Alert.alert('Atenção', 'Por favor, preencha todos os campos obrigatórios.');
                return;
            }

            setIsLoading(true);
            try {
                const churchData = churchForm.getValues();
                
                // Usa o userId recebido via params (do cadastro) ou busca no SecureStore (se logado)
                let userId = userIdParam;
                
                if (!userId) {
                    console.log('⚠️ userId não recebido via params, buscando no SecureStore...');
                    const tokens = await getStoredTokens();
                    userId = tokens.userId || undefined;
                }
                
                if (!userId) {
                    Alert.alert('Erro', 'Usuário não identificado. Faça login novamente.');
                    router.replace('/user/login');
                    return;
                }

                console.log('📋 Cadastrando igreja com userId:', userId);
                const churchResponse = await registerChurch(churchData);
                
                // Extrai o ID da igreja criada - API retorna 'idIgreja'
                const newChurchId = churchResponse.idIgreja;
                
                if (!newChurchId) {
                    throw new Error('ID da igreja não retornado pela API');
                }

                console.log('✅ Igreja cadastrada! ID:', newChurchId);
                setChurchId(newChurchId);
                
                // Renova o access token para obter as novas permissões da igreja criada
                console.log('🔄 Renovando token para obter permissões da nova igreja...');
                const { refreshAccessToken } = await import('@/api/loginUser');
                const newTokens = await refreshAccessToken();
                
                if (newTokens) {
                    console.log('✅ Token renovado! Novas permissões carregadas');
                } else {
                    console.warn('⚠️ Não foi possível renovar o token, mas continuando...');
                }
                
                // Avança para próxima etapa
                const nextStep = step + 1;
                setStep(nextStep);
                scrollViewRef.current?.scrollTo({ x: nextStep * (containerWidth + 20), animated: true });

            } catch (error: any) {
                console.error('❌ Erro ao cadastrar igreja:', error);
                Alert.alert('Erro no Cadastro', error.message || 'Não foi possível cadastrar a igreja.');
            } finally {
                setIsLoading(false);
            }
        } else {
            // Segunda etapa: Cadastrar ministério
            const isValid = await ministryForm.trigger();
            
            if (!isValid) {
                Alert.alert('Atenção', 'Por favor, preencha todos os campos obrigatórios.');
                return;
            }

            if (!churchId) {
                Alert.alert('Erro', 'ID da igreja não encontrado. Tente novamente.');
                setStep(0);
                return;
            }

            setIsLoading(true);
            try {
                const ministryData = ministryForm.getValues();
                
                // Usa o userId recebido via params (do cadastro) ou busca no SecureStore (se logado)
                let userId = userIdParam;
                
                if (!userId) {
                    console.log('⚠️ userId não recebido via params, buscando no SecureStore...');
                    const tokens = await getStoredTokens();
                    userId = tokens.userId || undefined;
                }
                
                if (!userId) {
                    Alert.alert('Erro', 'Usuário não identificado. Faça login novamente.');
                    router.replace('/user/login');
                    return;
                }

                console.log('📋 Cadastrando ministério com userId:', userId);
                
                // Adiciona idIgreja ao ministryData
                const ministryDataWithChurch = {
                    ...ministryData,
                    idIgreja: churchId
                };
                
                await registerMinistry(ministryDataWithChurch);
                
                console.log('✅ Ministério cadastrado com sucesso!');
                
                Alert.alert(
                    'Sucesso!',
                    'Igreja e ministério cadastrados com sucesso!',
                    [
                        {
                            text: 'OK',
                            onPress: () => {
                                churchForm.reset();
                                ministryForm.reset();
                                router.replace('/homeMenu/(tabs)/');
                            }
                        }
                    ]
                );

            } catch (error: any) {
                console.error('❌ Erro ao cadastrar ministério:', error);
                Alert.alert('Erro no Cadastro', error.message || 'Não foi possível cadastrar o ministério.');
            } finally {
                setIsLoading(false);
            }
        }
    };

    return(
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
            <View id="register-container" className="flex-1 p-10 max-h-[45rem] w-full max-w-[80%] justify-between gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
                {/* Cabeçalho */}
                <View className="flex-row items-center justify-center">
                    <Pressable onPress={handleBack} className="absolute left-0">
                        <ArrowLeftIcon color={baseColor} />
                    </Pressable>
                    <View className="scale-75">
                        <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor} />
                    </View>
                </View>

                {/* Formulário de Etapas */}
                <View className="flex-1" onLayout={handleLayout}>
                    <ScrollView 
                        ref={scrollViewRef} 
                        horizontal 
                        pagingEnabled 
                        scrollEnabled={false} 
                        showsHorizontalScrollIndicator={false}
                    >
                        {steps.map((stepInfo, index) => (
                            <React.Fragment key={index}>
                                <View key={index} style={{ width: containerWidth }}>
                                    {stepInfo.component}
                                </View>
                                {index < steps.length - 1 && (<View style={{ width: 20 }} />)}
                            </React.Fragment>
                        ))}
                    </ScrollView>
                </View>

                {/* Rodapé */}
                <View className="flex justify-center flex-row gap-x-4">
                    <CustomButton 
                        label={isLoading ? "Cadastrando..." : (step === steps.length - 1 ? "Finalizar" : "Próximo")} 
                        onPress={handleNext}
                        disabled={isLoading}
                    />
                </View>
            </View>
        </View>
    )
}