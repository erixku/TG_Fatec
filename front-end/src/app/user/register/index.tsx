
import React, { useRef, useState, useEffect } from "react";
import { View, Pressable, useColorScheme, ScrollView, LayoutChangeEvent } from "react-native";
import { useRouter } from "expo-router";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import RegisterFormUser from "@/components/login&register/RegisterFormUser";
import RegisterFormEmail from "@/components/login&register/RegisterFormEmail";
import { useFormContext } from "react-hook-form";
import { RegisterUserFormData } from "@/schemas/registerUserSchema";

export default function Register() {
  const colorScheme = useColorScheme();
  const router = useRouter();
  const { trigger, getValues, reset } = useFormContext<RegisterUserFormData>();

  const scrollViewRef = useRef<ScrollView>(null);
  const [step, setStep] = useState(0);
  const [containerWidth, setContainerWidth] = useState(0);

  const steps = [
    { component: <RegisterFormUser />, fields: ['arquivo', 'nomeCompleto', 'nomeCompletoSocial', 'cpf', 'dataNascimento', 'sexo', 'telefone', 'endereco.cep', 'endereco.rua', 'endereco.numero', 'endereco.complemento', 'endereco.bairro', 'endereco.cidade', 'endereco.uf'] },
    { component: <RegisterFormEmail />, fields: ['email', 'confirm_email', 'senha', 'confirm_senha'] }
  ];

  useEffect(() => {
    console.log("Step:", step, "ContainerWidth:", containerWidth);
    if (containerWidth > 0) {
      scrollViewRef.current?.scrollTo({ x: step * (containerWidth + 20), animated: true });
    }
  }, [step, containerWidth]);

  const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
  const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

  const handleLayout = (event: LayoutChangeEvent) => {
    const { width } = event.nativeEvent.layout;
    console.log("Largura do container:", width);
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
    const currentStepFields = steps[step].fields as (keyof RegisterUserFormData)[];
    const isValid = await trigger(currentStepFields);

    if (isValid) {
      if (step < steps.length - 1) {
        const nextStep = step + 1;
        setStep(nextStep);
        scrollViewRef.current?.scrollTo({ x: nextStep * (containerWidth + 20), animated: true });
      } else {
        console.log("Formulário completo e válido:", getValues());
        router.push({
          pathname: "/user",
          params: { email: getValues("email"), telefone: getValues("telefone"), rota: "register" },
        });
        reset();
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
          <CustomButton label={step === steps.length - 1 ? "Finalizar" : "Próximo"} onPress={() => handleNext()} />
        </View>
      </View>
    </View>
  );
}