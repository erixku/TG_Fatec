import { Stack } from "expo-router";
import React, { createContext, useContext } from "react";
import { KeyboardAvoidingView, Platform, useColorScheme } from "react-native";
import { useForm, FormProvider, UseFormReturn } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { RegisterChurchFormInput, registerChurchSchema } from "@/schemas/registerChurchSchema";
import { RegisterMinisteryFormData, registerMinisterySchema } from "@/schemas/registerMinisterySchema";

// Context para o formulário de ministério
const MinisteryFormContext = createContext<UseFormReturn<RegisterMinisteryFormData> | null>(null);

export const useMinisteryForm = () => {
    const context = useContext(MinisteryFormContext);
    if (!context) {
        throw new Error('useMinisteryForm must be used within MinisteryFormProvider');
    }
    return context;
};

export default function RegisterLayout() {
    const colorScheme = useColorScheme();

    // Inicializa o formulário de igreja
    const churchMethods = useForm<RegisterChurchFormInput>({
      resolver: zodResolver(registerChurchSchema),
      mode: 'onChange',
    });

    // Inicializa o formulário de ministério
    const ministeryMethods = useForm<RegisterMinisteryFormData>({
      resolver: zodResolver(registerMinisterySchema),
      mode: 'onChange',
    });

    // Cor de fundo consistente com o layout global
    const backgroundColor = colorScheme === "dark" ? "#1e293b" : "#dbeafe";

    return (
        // Envolvemos com ambos os FormProviders
        <FormProvider {...churchMethods}>
            <MinisteryFormContext.Provider value={ministeryMethods}>
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
            </MinisteryFormContext.Provider>
        </FormProvider>
    );
}