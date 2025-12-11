import React, { useEffect, useRef, useState } from "react";
import { View, Pressable, Text, useColorScheme, Image, ScrollView } from "react-native";
import { Controller, useFormContext, useWatch } from "react-hook-form";
import { CustomMaskedInput, CustomTextInput } from "../CustomInput";
import { RegisterMinisteryFormData } from "@/schemas/registerMinisterySchema";
import PhotoPicker from "../PhotoPicker";
import { PhotoIcon } from "react-native-heroicons/solid";
import { useMinisteryForm } from "@/app/church/_layout";

export default function RegisterFormMinistery() {
    const { control, formState: {errors}, setValue, getValues } = useMinisteryForm();
    const [modalVisible, setModalVisible] = useState<boolean>(false);
    const colorScheme = useColorScheme();
    const iconsColor = colorScheme == 'dark' ? '#dbeafe':'#0f172a';
    
    return(
        <View className="flex-1 w-full">
            <ScrollView
                className="w-full mt-4"
                keyboardShouldPersistTaps="handled"
                showsVerticalScrollIndicator={false}
                contentContainerClassName="pb-10 items-center gap-y-3"
            >
                <Controller 
                    control={control}
                    name="arquivo"
                    render={({field, fieldState}) => (
                        <View className="items-center w-full mb-4 gap-y-5">
                            <Pressable onPress={() => setModalVisible(true)} className="h-[150px] w-[250px] rounded-xl">
                                {field.value?.caminho ? (
                                    <Image source={{uri: field.value.caminho}} className="h-full w-full rounded-xl"/>
                                ) : (
                                    <View className="h-full w-full items-center justify-center bg-slate-200 dark:bg-slate-800 rounded-xl">
                                        <PhotoIcon size={80} color={iconsColor}/>
                                    </View>
                                )}
                            </Pressable>
                            {fieldState.error?.message && <Text className="font-nunito-bold text-red-500">{fieldState.error.message}</Text>}
                            <Text className="font-nunito-light text-slate-900 dark:text-blue-100">Selecione uma imagem de no máximo 5mb</Text>

                            <PhotoPicker 
                                visible={modalVisible}
                                onClose={() => setModalVisible(false)}
                                onChange={(asset) => {
                                    if (asset) {
                                        const fileInfo: RegisterMinisteryFormData["arquivo"] = {
                                            caminho: asset.uri,
                                            tipoArquivo: asset.mimeType,
                                            mimeType: asset.mimeType,
                                            tamanhoEmBytes: String(asset.fileSize),
                                            bucketArquivo: {
                                                nome: asset.fileName
                                            }
                                        };
                                        field.onChange(fileInfo);
                                    }
                                    setModalVisible(false);
                                }}
                                value={field.value?.caminho}
                                aspect={[16, 9]}
                            />
                        </View>
                    )}
                />

                <Controller 
                control={control}
                name="nome"
                render={({field, fieldState}) => (
                    <CustomTextInput
                        label="Nome do ministério"
                        placeholder="Digite o nome do ministério"
                        value={field.value}
                        onChangeText={field.onChange}
                        error={fieldState.error?.message}
                        required
                    />
                )}
            />

                <Controller 
                    control={control}
                    name="descricao"
                    render={({field, fieldState}) => (
                        <CustomTextInput
                            value={field.value}
                            onChangeText={field.onChange}
                            label="Descrição"
                            placeholder="Digite a descrição do ministério"
                            required
                            error={fieldState.error?.message}
                            multiline
                            numberOfLines={4}
                            textAlignVertical="top"
                        />
                    )}
                />
            </ScrollView>
        </View>
    )
}
