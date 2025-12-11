import React, { useRef, useState, useEffect } from "react";
import { View, Pressable, useColorScheme, ScrollView, LayoutChangeEvent, Alert, Text } from "react-native";
import { useRouter, useLocalSearchParams } from "expo-router";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import { useFormContext } from "react-hook-form";
import ResetPassword from "@/components/login&register/ResetPassword";
import { RegisterUserFormData } from "@/schemas/registerUserSchema";
import { CustomTextInput } from "@/components/CustomInput";
import { resetPassword } from "@/api/resetPassword";
import { findUserByKey } from "@/api/findUser";

export default function ResetPasswordScreen() {
    const colorScheme = useColorScheme();
    const router = useRouter();
    const params = useLocalSearchParams();
    const { trigger, getValues, reset } = useFormContext<RegisterUserFormData>();
    const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
    const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";
    
    const [email, setEmail] = useState<string>('');
    const [isLoading, setIsLoading] = useState(false);
    const [step, setStep] = useState<'email' | 'password'>('email');

    const handleValidateEmail = async () => {
        if (!email || email.trim() === '') {
            Alert.alert('Erro', 'Por favor, digite seu e-mail.');
            return;
        }

        setIsLoading(true);
        try {
            // Verifica se o usuário existe
            const user = await findUserByKey(email, 'email');
            
            if (!user) {
                Alert.alert('Erro', 'E-mail não encontrado.');
                return;
            }

            console.log('✅ E-mail validado:', email);
            setStep('password');
        } catch (error: any) {
            Alert.alert('Erro', error.message || 'Erro ao validar e-mail.');
        } finally {
            setIsLoading(false);
        }
    };

    const handleSubmit = async () => {
        const isValid = await trigger(['senha', 'confirm_senha']);
        
        if (!isValid) {
            Alert.alert('Erro', 'Por favor, corrija os erros no formulário.');
            return;
        }

        const formData = getValues();
        setIsLoading(true);

        try {
            await resetPassword({
                email: email,
                newPassword: formData.senha
            });

            Alert.alert(
                'Sucesso',
                'Senha alterada com sucesso!',
                [
                    {
                        text: 'OK',
                        onPress: () => {
                            reset();
                            router.replace("/user/login");
                        }
                    }
                ]
            );
        } catch (error: any) {
            Alert.alert('Erro', error.message || 'Erro ao alterar senha.');
        } finally {
            setIsLoading(false);
        }
    }

    return(
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
            <View className="flex-1 p-10 max-h-[45rem] w-full max-w-[80%] justify-between gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
                {/* Cabeçalho */}
                <View className="flex-row items-center justify-center">
                    <Pressable onPress={() => {
                        if (step === 'password') {
                            setStep('email');
                        } else {
                            router.back();
                            reset();
                        }
                    }} className="absolute left-0">
                        <ArrowLeftIcon color={baseColor} />
                    </Pressable>
                    <View className="scale-75">
                        <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor} />
                    </View>
                </View>

                {/* Conteúdo */}
                <View className="flex-1 w-full justify-center">
                    {step === 'email' ? (
                        <View className="gap-y-4">
                            <View className="mb-2">
                                <Text className="text-2xl font-nunito-bold text-slate-900 dark:text-blue-100 text-center">
                                    Recuperar Senha
                                </Text>
                                <Text className="font-nunito text-slate-600 dark:text-blue-200 text-center mt-2">
                                    Digite seu e-mail cadastrado
                                </Text>
                            </View>
                            <CustomTextInput 
                                label="E-mail"
                                value={email}
                                onChangeText={setEmail}
                                placeholder="Digite seu e-mail"
                                keyboardType="email-address"
                                autoCapitalize="none"
                            />
                        </View>
                    ) : (
                        <View className="gap-y-2">
                            <View className="mb-2">
                                <Text className="text-2xl font-nunito-bold text-slate-900 dark:text-blue-100 text-center">
                                    Nova Senha
                                </Text>
                                <Text className="font-nunito text-slate-600 dark:text-blue-200 text-center mt-2">
                                    Crie uma nova senha segura
                                </Text>
                            </View>
                            <ResetPassword />
                        </View>
                    )}
                </View>

                {/* Rodapé */}
                <View className="flex justify-center flex-row gap-x-4">
                    {step === 'email' ? (
                        <CustomButton 
                            label={isLoading ? "Validando..." : "Continuar"} 
                            onPress={handleValidateEmail}
                            disabled={isLoading}
                        />
                    ) : (
                        <CustomButton 
                            label={isLoading ? "Salvando..." : "Alterar Senha"} 
                            onPress={handleSubmit}
                            disabled={isLoading}
                        />
                    )}
                </View>
            </View>
        </View>
    )
}