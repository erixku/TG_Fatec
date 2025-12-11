import React, { useState } from "react";
import { View, Text, useColorScheme, Pressable, ScrollView, Alert } from "react-native";
import ThemedHarppiaLogo from '@/components/ThemedHarppiaLogo'
import { CustomButton } from "@/components/CustomButtom";
import Animated, { SlideInRight, SlideOutLeft, SlideOutRight } from "react-native-reanimated";
import { ArrowLeftIcon, IdentificationIcon, PhoneIcon, EnvelopeIcon } from "react-native-heroicons/solid"
import { CustomMaskedInput, CustomPasswordTextInput, CustomTextInput } from "@/components/CustomInput";
import { useRouter, useNavigation } from "expo-router";
import { useFormContext } from "react-hook-form";
import { RegisterUserFormData } from "@/schemas/registerUserSchema";
import { findUserByKey } from "@/api/findUser";
import { loginUser } from "@/api/loginUser";

export default function Register() {
  const colorScheme = useColorScheme();
  const router = useRouter();
  const [email, setEmail] = useState<string>(null);
  const [password, setPassword] = useState<string>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [cellphone, setCellphone] = useState<string>(null);
  const [cpf, setCpf] = useState<string>(null);
  const { trigger, setError } = useFormContext<RegisterUserFormData>()

  const [loginMode, setLoginMode] = useState<'email' | 'cellphone' | 'cpf'>('email')

  const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
  const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

  async function handleLogin() {
    // Previne múltiplos cliques
    if (isLoading) return;

    // Validação básica dos campos
    if (!password || password.trim() === '') {
      Alert.alert('Erro', 'Por favor, digite sua senha.');
      return;
    }

    let key: string = '';
    
    switch (loginMode) {
      case 'email':
        if (!email || email.trim() === '') {
          Alert.alert('Erro', 'Por favor, digite seu e-mail.');
          return;
        }
        key = email;
        break;
      case 'cellphone':
        if (!cellphone || cellphone.trim() === '') {
          Alert.alert('Erro', 'Por favor, digite seu telefone.');
          return;
        }
        key = cellphone;
        break;
      case 'cpf':
        if (!cpf || cpf.trim() === '') {
          Alert.alert('Erro', 'Por favor, digite seu CPF.');
          return;
        }
        key = cpf;
        break;
    }

    setIsLoading(true);

    try {
      console.log('🔐 Tentando login com:', loginMode);
      
      // Montar credenciais de acordo com o método de login
      const credentials: any = {
        senha: password
      };

      switch (loginMode) {
        case 'email':
          credentials.email = email;
          break;
        case 'cellphone':
          credentials.telefone = cellphone;
          break;
        case 'cpf':
          credentials.cpf = cpf;
          break;
      }

      const response = await loginUser(credentials);

      console.log('✅ Login bem-sucedido!');
      router.replace('/user/login/churchSelection');
      
    } catch (error: any) {
      console.error('❌ Erro no login:', error);
      
      const errorMessage = error.message || "Não foi possível realizar o login.";
      
      Alert.alert(
        "Erro no Login",
        errorMessage,
        [{ text: 'OK' }]
      );
    } finally {
      setIsLoading(false);
    }
  }
  
  return (
    <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
      <View className="flex justify-between p-10 h-[45rem] max-w-[80%] gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
        <View className="flex flex-row items-center justify-center gap-y-2">
            <Pressable onPress={() => router.back()}>
              <ArrowLeftIcon color={"#dbeafe"}/>
            </Pressable>
            <View className="scale-75">
                <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor}/>
            </View>
        </View>
        <View className="flex flex-1 h-full items-center justify-center gap-y-3 mt-4">
            {loginMode === 'cellphone' ? (
                <CustomMaskedInput
                    label="Telefone"
                    value={cellphone}
                    placeholder="(DDD) 9####-####"
                    onChangeText={(formatted, extracted) => {setCellphone(extracted)}}
                    keyboardType="numeric"
                    mask="(99) 99999-9999"
                />
            ) : loginMode === 'cpf' ? (
                <CustomMaskedInput
                    label="CPF"
                    value={cpf}
                    placeholder="Digite seu CPF"
                    onChangeText={(formatted, extracted) => {setCpf(extracted)}}
                    keyboardType="numeric"
                    mask="999.999.999-99"
                />
            ) : (
                <CustomTextInput 
                    label="E-mail"
                    value={email}
                    onChangeText={(text) => setEmail(text)}
                    placeholder="Digite seu e-mail"
                />
            )}
            <View className="w-full items-start gap-y-3">
                <CustomPasswordTextInput 
                    label="Senha"
                    onChangeText={(text) => setPassword(text)}
                    placeholder="Digite sua senha"
                />
                <Pressable onPress={() => router.push({pathname: '/user', params: {rota: 'login'}})}>
                    <Text className="text-center text-lg font-nunito-light text-slate-900 dark:text-blue-100"> 
                        Esqueci minha senha
                    </Text>
                </Pressable>
            </View>
            <View className="flex-row items-center">
                <View className="flex-1 h-0.5 bg-slate-900 dark:bg-blue-100"/>
                <Text className="mx-2 font-nunito-bold text-slate-900 dark:text-blue-100">Ou</Text>
                <View className="flex-1 h-0.5 bg-slate-900 dark:bg-blue-100"/>
            </View>
            
            {loginMode === 'cellphone' ? (
                <Pressable onPress={() => setLoginMode('email')} className="flex-row p-2 h-10 gap-x-4 w-60 items-center justify-center border-2 border-slate-900 dark:border-blue-100 active:border-blue-300 active:dark:border-blue-700 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl">
                    <EnvelopeIcon size={20} color={baseColor} />
                    <Text className="font-nunito text-lg dark:text-blue-100">Entrar com o e-mail</Text>
                </Pressable>
            ) : loginMode === 'cpf' ? (
                <Pressable onPress={() => setLoginMode('cellphone')} className="flex-row p-2 h-10 gap-x-4 w-60 items-center justify-center border-2 border-slate-900 dark:border-blue-100 active:border-blue-300 active:dark:border-blue-700 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl">
                    <PhoneIcon size={20} color={baseColor} />
                    <Text className="font-nunito text-lg dark:text-blue-100">Entrar com o telefone</Text>
                </Pressable>
            ) : (
                <Pressable onPress={() => setLoginMode('cellphone')} className="flex-row p-2 h-10 gap-x-4 w-60 items-center justify-center border-2 border-slate-900 dark:border-blue-100 active:border-blue-300 active:dark:border-blue-700 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl">
                    <PhoneIcon size={20} color={baseColor} />
                    <Text className="font-nunito text-lg dark:text-blue-100">Entrar com o telefone</Text>
                </Pressable>
            )}

            {loginMode === 'cellphone' ? (
                <Pressable onPress={() => setLoginMode('cpf')} className="flex-row p-2 h-10 gap-x-4 w-60 items-center justify-center border-2 border-slate-900 dark:border-blue-100 active:border-blue-300 active:dark:border-blue-700 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl">
                    <IdentificationIcon size={20} color={baseColor} />
                    <Text className="font-nunito text-lg dark:text-blue-100">Entrar com o CPF</Text>
                </Pressable>
            ) : loginMode === 'cpf' ? (
                <Pressable onPress={() => setLoginMode('email')} className="flex-row p-2 h-10 gap-x-4 w-60 items-center justify-center border-2 border-slate-900 dark:border-blue-100 active:border-blue-300 active:dark:border-blue-700 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl">
                    <EnvelopeIcon size={20} color={baseColor} />
                    <Text className="font-nunito text-lg dark:text-blue-100">Entrar com o e-mail</Text>
                </Pressable>
            ) : (
                <Pressable onPress={() => setLoginMode('cpf')} className="flex-row p-2 h-10 gap-x-4 w-60 items-center justify-center border-2 border-slate-900 dark:border-blue-100 active:border-blue-300 active:dark:border-blue-700 active:bg-blue-300 active:dark:bg-blue-700 rounded-xl">
                    <IdentificationIcon size={20} color={baseColor} />
                    <Text className="font-nunito text-lg dark:text-blue-100">Entrar com o CPF</Text>
                </Pressable>
            )}

        </View>
        <View className="flex justify-center flex-row gap-x-4 mt-5">
            <CustomButton 
              label={isLoading ? "Entrando..." : "Entrar"} 
              onPress={handleLogin}
              disabled={isLoading}
            />
        </View>
      </View>
    </View>
  );
}
