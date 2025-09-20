import React, { useEffect, useRef, useState } from "react";
import { View, Pressable, Text, useColorScheme, Image, ScrollView } from "react-native";
import { Controller, useFormContext, useWatch } from "react-hook-form";
import { CustomMaskedInput, CustomTextInput } from "../CustomInput";
import CustomDropdown from "../CustomDropdown";
import CustomDatePicker from "../CustomDatePicker";
import PhotoPicker from "../PhotoPicker";
import { useCepValidation } from "@/schemas/functions/useCepValidation";
import { RegisterChurchFormData } from "@/schemas/registerChurchSchema";
import { PhotoIcon } from "react-native-heroicons/solid";
import { denominacoes } from "@/schemas/enums/churchEnums";
import useCnpjValidation from "@/schemas/functions/useCnpjValidation";

export default function RegisterFormChurch() {
    const [modalVisible, setModalVisible] = useState<boolean>(false);
    const { control, formState: {errors}, setValue, getValues } = useFormContext<RegisterChurchFormData>();

    const colorScheme = useColorScheme();
    const rawCPFRef = useRef("");
    const {handleCepChange, endereco, error: cepError} = useCepValidation();
    const {validateCnpj, loading, error: cnpjError, data } = useCnpjValidation();
    const denominacaoValue = useWatch({name: "denominacao"});

    useEffect(() => {
        if(endereco) {
            // Usando os nomes corretos dos campos do schema de endereço
            setValue("endereco.rua", endereco.logradouro);
            setValue("endereco.bairro", endereco.bairro);
            setValue("endereco.cidade", endereco.localidade);
            setValue("endereco.uf", endereco.estado);
        }

        if (cepError) console.log('Possível erro no CEP: '+cepError);

        if(data) {
            console.log("Dados do CNPJ validados:", data);
        } else if (cnpjError) {
            console.log("Erro na validação do CNPJ:", cnpjError);
        } else if (loading) {
            console.log("Validando CNPJ...");
        }
    }, [endereco, data, loading, cepError, cnpjError]);


    const iconsColor = colorScheme == 'dark' ? '#dbeafe':'#0f172a'
    
    return(
        <ScrollView
            className="flex-1 w-full sm:w-[50%] lg:w-[50%] mt-4"
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
                                    const fileInfo: RegisterChurchFormData["arquivo"] = {
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
                        label="Nome da Igreja"
                        placeholder="Digite o nome da igreja"
                        value={field.value}
                        onChangeText={field.onChange}
                        error={fieldState.error?.message}
                        required
                    />
                )}
            />
            <Controller 
                control={control}
                name='cnpj'
                render={({field, fieldState}) => (
                    <CustomMaskedInput
                        label="CNPJ"
                        placeholder="00.000.000/0000-00"
                        onChangeText={(formated, extracted) => {
                            validateCnpj(extracted);
                            field.onChange(extracted);
                        }}
                        mask="99.999.999/9999-99"
                        keyboardType="numeric"
                        required
                        error={fieldState.error?.message}
                        value={field.value}
                    />
                )}
            />
            <Controller 
                control={control}
                name='denominacao'
                render={({field, fieldState}) => (
                    <CustomDropdown 
                        label="Denominação"
                        options={denominacoes}
                        required
                        value={field.value}
                        onChange={field.onChange}
                        error={fieldState.error?.message}
                    />
                )}
            />
            {denominacaoValue === "Outra" && (

                <Controller 
                    control={control}
                    name="outra_denominacao"
                    render={({field, fieldState}) => (
                        
                        <CustomTextInput 
                            label="Outra denominação"
                            placeholder="Digite a denominação (se aplicável)"
                            value={field.value}
                            onChangeText={field.onChange }
                            error={fieldState.error?.message}
                        />
                    )}
                />
            )}
            <Controller 
                control={control}
                name="endereco.cep"
                render={({field, fieldState}) => (
                    <CustomMaskedInput
                        value={field.value}
                        label="CEP"
                        placeholder="Digite seu CEP"
                        required
                        keyboardType="numeric"
                        onChangeText={(formatted, extracted) => {
                            if (extracted?.length === 8) handleCepChange(extracted);
                            setValue("endereco.cep", extracted);
                        }}
                        mask="99999-999"
                        error={fieldState.error?.message}
                    />
                )}
            />

            <Controller 
                control={control}
                name="endereco.rua"
                render={({field, fieldState}) => (
                    <CustomTextInput
                        value={field.value}
                        onChangeText={field.onChange}
                        label="Logradouro"
                        placeholder="Digite o nome de seu logradouro"
                        required
                        error={fieldState.error?.message}
                        disabled
                    />
                )}
            />

            <Controller 
                control={control}
                name="endereco.numero"
                render={({field, fieldState}) => (
                    <CustomTextInput
                        value={field.value}
                        onChangeText={field.onChange}
                        label="Número"
                        placeholder="Digite o número da sua casa"
                        keyboardType="numeric"
                        error={fieldState.error?.message}
                    />
                )}
            />

            <Controller 
                control={control}
                name="endereco.complemento"
                render={({field, fieldState}) => (
                    <CustomTextInput
                        value={field.value}
                        onChangeText={field.onChange}
                        label="Complemento"
                        placeholder="Descreva o complemento"
                        error={fieldState.error?.message}
                    />
                )}
            />

            <Controller 
                control={control}
                name="endereco.bairro"
                render={({field, fieldState}) => (
                    <CustomTextInput
                        value={field.value}
                        onChangeText={field.onChange}
                        label="Bairro"
                        placeholder="Digite o nome do seu bairro"
                        required
                        error={fieldState.error?.message}
                        disabled
                    />
                )}
            />

            <Controller 
                control={control}
                name="endereco.cidade"
                render={({field, fieldState}) => (
                    <CustomTextInput
                        value={field.value}
                        onChangeText={field.onChange}
                        label="Cidade"
                        placeholder="Digite o nome da sua cidade"
                        required
                        error={fieldState.error?.message}
                        disabled
                    />
                )}
            />

            <Controller 
                control={control}
                name="endereco.uf"
                render={({field, fieldState}) => (
                    <CustomTextInput
                        value={field.value}
                        onChangeText={field.onChange}
                        label="Estado"
                        placeholder="Digite o nome do seu estado"
                        required
                        error={fieldState.error?.message}
                        disabled
                    />
                )}
            />
        </ScrollView>
    )
}
