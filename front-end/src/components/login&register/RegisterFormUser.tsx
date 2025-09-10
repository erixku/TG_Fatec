import React, { useEffect, useRef, useState } from "react";
import { View, Pressable, ScrollView, Text, useColorScheme, Image, Platform, KeyboardAvoidingView } from "react-native";
import { Controller, useForm, useFormContext } from "react-hook-form";
import { CustomMaskedInput, CustomTextInput } from "../CustomInput";
import CustomDropdown from "../CustomDropdown";
import CustomDatePicker from "../CustomDatePicker";
import { UserIcon } from "react-native-heroicons/solid"
import PhotoPicker from "../PhotoPicker";
import { useCepValidation } from "@/schemas/functions/useCepValidation";
import { RegisterFormData, registerSchema } from "@/schemas/registerSchema";
import { zodResolver } from "@hookform/resolvers/zod";

export default function RegisterFormUser() {
    const [modalVisible, setModalVisible] = useState<boolean>(false);
    const { control, formState: {errors} } = useFormContext<RegisterFormData>();

    const colorScheme = useColorScheme();
    const rawCPFRef = useRef("");
    const {handleCepChange, endereco, loading, error, cep} = useCepValidation();

    const {setValue} = useFormContext<RegisterFormData>();

    useEffect(() => {
        if(endereco) {
            setValue("street" ,endereco.logradouro);
            setValue("district" ,endereco.bairro);
            setValue("city" ,endereco.localidade);
            setValue("estate" ,endereco.estado);
        }

        console.log('Possível erro: '+error)
    }, [endereco])


    const iconsColor = colorScheme == 'dark' ? '#dbeafe':'#0f172a'

    return(
        <View className="flex flex-1 w-full sm:w-[50%] lg:w-[50%] mt-4">
            <KeyboardAvoidingView className="flex-1" behavior={Platform.OS === "ios" ? "padding" : "height"}>
                <ScrollView
                    showsVerticalScrollIndicator={false}
                    contentContainerClassName="pb-10 items-center gap-y-3"
                >
                    <Controller 
                        control={control}
                        name="profilePicture"
                        render={({field}) => (
                            <View className="items-center w-full mb-4 gap-y-5">
                                <Pressable onPress={() => setModalVisible(true)} className="h-[150px] w-[150px] rounded-full">
                                    {field.value ? (
                                        <Image source={{uri: field.value}} className="h-full w-full rounded-full"/>
                                    ) : (
                                        <View className="h-full w-full items-center justify-center bg-slate-200 dark:bg-slate-800 rounded-full">
                                            <UserIcon size={80} color={iconsColor}/>
                                        </View>
                                    )}
                                </Pressable>
                                <Text className="font-nunito-light text-slat-900 dark:text-blue-100">Selecione uma imagem de no máximo 2mb</Text>

                                <PhotoPicker 
                                    visible={modalVisible}
                                    onClose={() => setModalVisible(false)}
                                    onChange={(uri) => field.onChange(uri)}
                                    value={field.value}
                                />
                            </View>
                        )}
                    />

                    <Controller 
                        control={control}
                        name="name"
                        render={({field, fieldState}) => (
                            <CustomTextInput 
                                value={field.value}
                                onChangeText={field.onChange}
                                label="Nome completo" 
                                placeholder="Digite seu nome completo" 
                                required={true}
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="socialName"
                        render={({field, fieldState}) => (
                            <CustomTextInput 
                                value={field.value}
                                onChangeText={field.onChange}
                                label="Nome social" 
                                placeholder="Digite seu nome social" 
                                required={false}
                                error={fieldState.error?.message} 
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="cpf"
                        render={({field, fieldState}) => (
                            <CustomMaskedInput
                                value={field.value}
                                label="CPF"
                                placeholder="Digite seu CPF"
                                required
                                keyboardType="numeric"
                                onChangeText={(formatted, extracted) => {
                                    rawCPFRef.current = extracted;
                                    setValue("cpf", extracted);
                                }}
                                mask="999.999.999-99"
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="birthday"
                        render={({field, fieldState}) => (
                            <CustomDatePicker
                                value={field.value}
                                onChange={field.onChange}
                                label="Data de nascimento"
                                required
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="gender"
                        render={({field, fieldState}) => (
                            <CustomDropdown
                                value={field.value}
                                onChange={field.onChange}
                                label="Selecione seu sexo"
                                required
                                options={['Masculino', 'Feminino']}
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="cep"
                        render={({field, fieldState}) => (
                            <CustomMaskedInput
                                value={field.value}
                                label="CEP"
                                placeholder="Digite seu CEP"
                                required
                                keyboardType="numeric"
                                onChangeText={(formatted, extracted) => {handleCepChange(extracted); setValue("cep", extracted)}}
                                mask="99999-999"
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="street"
                        render={({field, fieldState}) => (
                            <CustomTextInput
                                value={field.value}
                                onChangeText={field.onChange}
                                label="Logradouro"
                                placeholder="Digite o nome de seu logradouro"
                                required
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="homeNumber"
                        render={({field, fieldState}) => (
                            <CustomTextInput
                                value={field.value}
                                onChangeText={field.onChange}
                                label="Número"
                                placeholder="Digite o número da sua casa"
                                required
                                keyboardType="numeric"
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="district"
                        render={({field, fieldState}) => (
                            <CustomTextInput
                                value={field.value}
                                onChangeText={field.onChange}
                                label="Bairro"
                                placeholder="Digite o nome do seu bairro"
                                required
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="city"
                        render={({field, fieldState}) => (
                            <CustomTextInput
                                value={field.value}
                                onChangeText={field.onChange}
                                label="Cidade"
                                placeholder="Digite o nome da sua cidade"
                                required
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="estate"
                        render={({field, fieldState}) => (
                            <CustomTextInput
                                value={field.value}
                                onChangeText={field.onChange}
                                label="Estado"
                                placeholder="Digite o nome do seu estado"
                                required
                                error={fieldState.error?.message}
                            />
                        )}
                    />

                    <Controller 
                        control={control}
                        name="cellphone"
                        render={({field, fieldState}) => (
                            <CustomMaskedInput
                                value={field.value}
                                label="Telefone"
                                placeholder="(DDD) 9####-####"
                                required
                                onChangeText={(formatted, extracted) => {setValue("cellphone", extracted)}}
                                keyboardType="numeric"
                                mask="(99) 99999-9999"
                                error={fieldState.error?.message}
                            />
                        )}
                    />
                </ScrollView>
            </KeyboardAvoidingView>
        </View>
    )
}