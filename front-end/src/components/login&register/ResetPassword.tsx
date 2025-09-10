import { RegisterFormData } from "@/schemas/registerSchema";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";
import { View, Text } from "react-native";
import { CustomPasswordTextInput } from "../CustomInput";

export default function ResetPassword() {
    const { control, formState: {errors}, getValues } = useFormContext<RegisterFormData>();
    
    return(
        <View className="flex flex-1 w-full items-center justify-center gap-y-2 mt-4">
            <Controller 
                control={control}
                name="password"
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
                name="confirm_password"
                rules={{
                    required: "Confirme a sua senha",
                    validate: (value) => value === getValues("password") || "Senhas não coincidem"
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