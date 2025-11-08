import { RegisterUserFormData } from "@/schemas/registerUserSchema";
import React from "react";
import { useFormContext, Controller } from "react-hook-form";
import { View, Text } from "react-native";
import { CustomPasswordTextInput, CustomTextInput } from "../CustomInput";

export default function RegisterFormEmail() {
    const { control, formState: {errors}, getValues} = useFormContext<RegisterUserFormData>();
    return(
        <View className="flex flex-1 w-full justify-center mt-4 gap-y-3">
            <Controller 
                control={control}
                name="email"
                render={({field, fieldState}) => (
                    <CustomTextInput
                        value={field.value}
                        onChangeText={field.onChange}
                        label="E-mail"
                        required
                        placeholder="Digite seu e-mail"
                        error={fieldState.error?.message}
                    />
                )}
            />

            <Controller 
                control={control}
                name="confirm_email"
                rules={{
                    required: "Confirme seu e-mail",
                    validate: (value) => value === getValues("email") || "Os e-mails não coincidem"
                }}
                render={({field, fieldState}) => (
                    <CustomTextInput
                        value={field.value}
                        onChangeText={field.onChange}
                        label="Confirme seu e-mail"
                        required
                        placeholder="Digite seu e-mail"
                        error={fieldState.error?.message}
                    />
                )}
            />

            <Controller 
                control={control}
                name="senha"
                render={({field, fieldState}) => (
                    <CustomPasswordTextInput 
                        value={field.value}
                        onChangeText={field.onChange}
                        label="Senha"
                        required
                        placeholder="Defina a sua senha"
                        error={fieldState.error?.message}
                    />
                )}
            />

            <Controller 
                control={control}
                name="confirm_senha"
                rules={{
                    required: "Confirme a sua senha",
                    validate: (value) => value === getValues("senha") || "Senhas não coincidem"
                }}
                render={({field, fieldState}) => (
                    <CustomPasswordTextInput 
                        value={field.value}
                        onChangeText={field.onChange}
                        label="Confirme sua senha"
                        required
                        placeholder="Defina a sua senha"
                        error={fieldState.error?.message}
                    />
                )}
            />
        </View>
    )
}