import { Stack } from "expo-router";
import React from "react";
import { KeyboardAvoidingView, Platform, useColorScheme } from "react-native";
import { useForm, FormProvider } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { RegisterUserFormData, registerUserSchema } from "@/schemas/registerUserSchema";

export default function RegisterLayout() {
    const colorScheme = useColorScheme();

    // Inicializa o formulário aqui para compartilhar o contexto entre as telas de registro
    const methods = useForm<RegisterUserFormData>({
      resolver: zodResolver(registerUserSchema),
      mode: 'onChange',
    });

    // Cor de fundo consistente com o layout global
  const backgroundColor = colorScheme === "dark" ? "#1e293b" : "#dbeafe";

    return (
        // Envolvemos o Stack com o FormProvider para compartilhar o contexto
        <FormProvider {...methods}>
            <KeyboardAvoidingView
                style={{ flex: 1 }}
                behavior={Platform.OS === "ios" ? "padding" : "height"}
                enabled
            >
                <Stack
                    screenOptions={{
                        headerShown: false,
                        animation: "slide_from_right",
                        presentation: "card",
                        contentStyle: { backgroundColor, flex: 1 }
                    }}
                />
            </KeyboardAvoidingView>
        </FormProvider>
    );
}