
import React, { useRef, useState, useEffect } from 'react';
import { View, Pressable, useColorScheme, ScrollView, LayoutChangeEvent, Alert, Text } from 'react-native';
import { useRouter } from 'expo-router';
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import RegisterFormUser from "@/components/login&register/RegisterFormUser";
import RegisterFormEmail from "@/components/login&register/RegisterFormEmail";
import { useFormContext } from "react-hook-form";
import { RegisterUserFormData } from "@/schemas/registerUserSchema";
import { registerUser } from '@/api/registerUser';

export default function Register() {
  const colorScheme = useColorScheme();
  const router = useRouter();
  const { trigger, getValues, reset, formState: { errors } } = useFormContext<RegisterUserFormData>();

  const scrollViewRef = useRef<ScrollView>(null);
  const [step, setStep] = useState(0);
  const [containerWidth, setContainerWidth] = useState(0);
  const [isLoading, setIsLoading] = useState(false);

  const steps = [
    { component: <RegisterFormUser />, fields: ['arquivo', 'nomeCompleto', 'nomeCompletoSocial', 'cpf', 'dataNascimento', 'sexo', 'telefone', 'endereco.cep', 'endereco.rua', 'endereco.numero', 'endereco.complemento', 'endereco.bairro', 'endereco.cidade', 'endereco.uf'] },
    { component: <RegisterFormEmail />, fields: ['email', 'confirm_email', 'senha', 'confirm_senha'] }
  ];

  useEffect(() => {
    if (containerWidth > 0) {
      scrollViewRef.current?.scrollTo({ x: step * (containerWidth + 20), animated: true });
    }
  }, [step, containerWidth]);

  const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
  const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

  const handleLayout = (event: LayoutChangeEvent) => {
    const { width } = event.nativeEvent.layout;
    setContainerWidth(width||1);
  };

  const handleBack = () => {
    if (step > 0) {
      setStep(prev => prev - 1);
    } else {
      router.back();
    }
  };

  const handleNext = async () => {
    // Impede múltiplos envios enquanto a requisição está em andamento
    if (isLoading) return;

    const currentStepFields = steps[step].fields as (keyof RegisterUserFormData)[];
    const isValid = await trigger(currentStepFields);

    if (isValid) {
      if (step < steps.length - 1) {
        const nextStep = step + 1;
        setStep(nextStep);
        scrollViewRef.current?.scrollTo({ x: nextStep * (containerWidth + 20), animated: true });
      } else {
        // Última etapa: Enviar para a API
        setIsLoading(true);
          try {
            const rawFormData = getValues();
            console.log('📝 [REGISTER] Dados brutos do formulário:', {
              nomeCompleto: rawFormData.nomeCompleto,
              email: rawFormData.email,
              telefone: rawFormData.telefone,
              cpf: rawFormData.cpf,
              temFoto: !!rawFormData.arquivo?.caminho
            });

            // Valida e transforma os dados usando o schema Zod
            const { registerUserSchema } = await import('@/schemas/registerUserSchema');
            const validatedData = registerUserSchema.parse(rawFormData);
            
            console.log('✅ [REGISTER] Dados transformados pelo schema:', {
              nome: validatedData.nome,
              sobrenome: validatedData.sobrenome,
              nomeSocial: validatedData.nomeSocial,
              sobrenomeSocial: validatedData.sobrenomeSocial,
              sexo: validatedData.sexo,
              dataNascimento: validatedData.dataNascimento
            });

            const responseData = await registerUser(validatedData);

            console.log('✅ [REGISTER] Cadastro realizado com sucesso!', responseData);
            
            // Extrai o ID do usuário criado
            const userId = responseData.id;
            console.log('🆔 [REGISTER] ID do usuário criado:', userId);

            // Salva credenciais temporárias no cache local (AsyncStorage)
            const { saveTempCredentials } = await import('@/services/localCache');
            await saveTempCredentials({
              email: validatedData.email,
              senha: validatedData.senha,
              telefone: validatedData.telefone
            });
            console.log('💾 [REGISTER] Credenciais temporárias salvas no cache local');

            // Mostra sucesso ao usuário
            Alert.alert(
              'Cadastro Realizado!',
              'Sua conta foi criada com sucesso. Você será redirecionado para autenticação.',
              [{ text: 'OK' }]
            );

            router.push({
              pathname: "/user",
              params: { 
                email: validatedData.email, 
                telefone: validatedData.telefone, 
                rota: "register",
                userId: userId || ""
              },
            });
            reset(); // Limpa o formulário
        } catch (error: any) {
          // Loga o erro no console para depuração
          console.error("Falha no processo de cadastro:", error);

          const errorMessage = error?.message || "Não foi possível realizar o cadastro. Tente novamente.";
          Alert.alert("Erro no Cadastro", errorMessage);

        } finally {
          setIsLoading(false);
        }
      }
    }
  };

  return (
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
          <ScrollView ref={scrollViewRef} horizontal pagingEnabled scrollEnabled={false} showsHorizontalScrollIndicator={false} >
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
            label={step === steps.length - 1 ? "Finalizar" : "Próximo"} 
            onPress={handleNext}
            disabled={isLoading}
          />
        </View>
      </View>
    </View>
  );
}