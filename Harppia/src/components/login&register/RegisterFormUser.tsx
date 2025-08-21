import React from "react";
import { View, Text, ScrollView } from "react-native";
import { useForm } from "react-hook-form";
import { CustomPasswordTextInput, CustomTextInput } from "../CustomInput";
import CustomDropdown from "../CustomDropdown";
import CustomDatePicker from "../CustomDatePicker";

export default function RegisterFormUser() {
    const { control, formState: { errors } } = useForm()
    return(
        <View className="flex flex-1 w-full sm:w-[50%] lg:w-[50%] bg-indigo-700 mt-4">
            <ScrollView
            showsVerticalScrollIndicator={false}
                contentContainerClassName="pb-10 bg-blue-500 items-center gap-y-3"
                >
                    <CustomTextInput label="Olá" placeholder="Resiliência" required={false}/>
                    <CustomPasswordTextInput label="Digite a sua senha" placeholder="Teste"/>
                    <CustomDropdown label="Selecione seu sexo" />
                    <CustomDatePicker />
            </ScrollView>
        </View>
    )
}