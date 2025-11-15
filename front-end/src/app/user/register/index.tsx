
import React, { useRef, useState, useEffect } from 'react';
import { View, Pressable, useColorScheme, ScrollView, LayoutChangeEvent, Alert } from 'react-native';
import { useRouter } from 'expo-router';
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import RegisterFormUser from "@/components/login&register/RegisterFormUser";
import RegisterFormEmail from "@/components/login&register/RegisterFormEmail";
import { useFormContext } from "react-hook-form";
import { RegisterUserFormData } from "@/schemas/registerUserSchema";
import axios from 'axios';
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
          const formData = getValues();
          const responseData = await registerUser(formData);

          // Se a resposta for sucesso (status 2xx)
          console.log("Cadastro realizado com sucesso!", responseData);
          router.push({
            pathname: "/user",
            params: { email: formData.email, telefone: formData.telefone, rota: "register" },
          });
          reset(); // Limpa o formulário
        } catch (error: any) {
          // Loga o erro no console para depuração, independentemente do tipo.
          console.error("Falha no processo de cadastro:", error);

          let errorMessage = "Não foi possível conectar ao servidor. Tente novamente.";
          if (axios.isAxiosError(error)) {
              errorMessage = error.response?.data?.message || error.message || errorMessage;
          }
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
          <CustomButton
            label='Teste'
            onPress={async() => {
              try {
                const r = await axios.get("https://harppia-endpoints.onrender.com");
                console.log("OK", r.data);
              } catch (e) {
                console.log("ERRO GET:", e);
              }

              const test = new FormData();
              test.append("foo", "bar");

              await axios.post(
                "https://harppia-endpoints.onrender.com/v1/users/register",
                test,
                {
                  headers: { Accept: "application/json" },
                  transformRequest: (d) => d,
                }
              );
            }}
          />
        </View>
      </View>
    </View>
  );
}