import React, { useState } from "react";
import { View, useColorScheme, Pressable, Alert } from "react-native";
import ThemedHarppiaLogo from "@/components/ThemedHarppiaLogo";
import { CustomButton } from "@/components/CustomButtom";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import { useRouter, useLocalSearchParams } from "expo-router";
import Auth from "@/components/login&register/Auth";
import { loginUser } from "@/api/loginUser";
import { getTempCredentials, clearTempCredentials } from "@/services/localCache";

export default function AuthScreen() {
  const colorScheme = useColorScheme();
  const router = useRouter();
  const params = useLocalSearchParams();
  const { email, telefone, rota, userId } = useLocalSearchParams<{ email?: string|null; telefone?: string|null; rota: string; userId?: string }>();

  const [isLoading, setIsLoading] = useState(false);

  const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";
  const contrastColor = colorScheme === "dark" ? "#93c5fd" : "#1d4ed8";

  const handleNext = async () => {
    if (isLoading) return;

    if (rota === "register") {
      setIsLoading(true);
      try {
        console.log('🔐 [AuthScreen] Fazendo login após cadastro...');

        // Recupera credenciais do cache local
        const tempCreds = await getTempCredentials();
        const storedEmail = tempCreds?.email;
        const storedSenha = tempCreds?.senha;

        console.log('📧 [AuthScreen] Email recuperado:', storedEmail);

        if (!storedEmail || !storedSenha) {
          Alert.alert('Erro', 'Credenciais não encontradas. Faça login manualmente.');
          router.replace('/user/login');
          return;
        }

        // Faz login para obter tokens e userId
        const loginResponse = await loginUser({
          email: storedEmail,
          senha: storedSenha
        });

        console.log('✅ [AuthScreen] Login realizado com sucesso!');
        console.log('👤 [AuthScreen] UserID:', loginResponse.id);
        console.log('🔑 [AuthScreen] Tokens salvos no SecureStore');

        // Tokens já foram salvos pela função loginUser()
        // Agora redireciona com o userId
        router.push({
          pathname: "/church",
          params: { userId: loginResponse.id }
        });

        // Limpa credenciais temporárias
        await clearTempCredentials();
        console.log('🧹 [AuthScreen] Credenciais temporárias limpas');

      } catch (error: any) {
        console.error('❌ [AuthScreen] Erro no login:', error);
        Alert.alert(
          'Erro ao fazer login',
          error.message || 'Não foi possível fazer login. Tente novamente.',
          [
            {
              text: 'Ir para Login',
              onPress: () => router.replace('/user/login')
            },
            {
              text: 'Tentar Novamente',
              style: 'cancel'
            }
          ]
        );
      } finally {
        setIsLoading(false);
      }
    } else {
      // Fluxo de reset de senha
      router.push("/user/login/ResetPassword");
    }
  };

  return (
    <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800">
      <View className="flex p-10 h-[45rem] w-full max-w-[80%] justify-between items-center gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
        <View className="flex-1 w-full justify-between items-strech gap-y-4">
          {/* Cabeçalho */}
          <View className="flex flex-row items-center justify-center gap-y-2">
            <Pressable onPress={() => router.back()}>
              <ArrowLeftIcon color={baseColor} />
            </Pressable>
            <View className="scale-75">
              <ThemedHarppiaLogo
                baseColor={baseColor}
                contrastColor={contrastColor}
              />
            </View>
          </View>

          {/* Formulário */}
          <Auth email={email} telefone={telefone} />

          {/* Rodapé */}
          <View className="flex justify-center flex-row gap-x-4 mt-5">
            <CustomButton
              label={isLoading ? "Autenticando..." : "Próximo"}
              onPress={handleNext}
              disabled={isLoading}
            />
          </View>
        </View>
      </View>
    </View>
  );
}