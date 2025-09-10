import React, { useState } from "react";
import { View, Text, 
        TextInput, TextInputProps, 
        useColorScheme} from "react-native"
import { EyeIcon, EyeSlashIcon, ChevronDownIcon } from "react-native-heroicons/solid"
import { MaskedTextInput, MaskedTextInputProps } from "react-native-mask-text";
import { Control, Controller, FieldValues,Path } from "react-hook-form";
import { RegisterFormData } from "@/schemas/registerSchema";

interface CustomTextInputProps extends TextInputProps {
    label?: string,
    required?: boolean,
    value?: string;
    onChangeText?: (text: string) => void
    error?: string,
}
interface CustomMaskedInputProps extends MaskedTextInputProps {
    label?: string,
    required?: boolean,
    value?: string;
    onChangeText: (formated: string, extracted:string) => void
    error?: string,
}

interface CustomPasswordTextInputProps extends TextInputProps {
    label?: string,
    required?: boolean,
    value?: string;
    onChangeText?: (text: string) => void
    error?: string,
}

export function CustomTextInput({
    label,
    required,
    value,
    onChangeText,
    error,
    ...rest
}:CustomTextInputProps) {
    const colorScheme = useColorScheme();

    const cursorColor:string = colorScheme === 'dark' ? '#a5b4fc' : '#4338cs';

    return(
        <View className="flex flex-col-reverse w-full gap-y-2">
            {error?(
                <Text className="font-nunito text-md text-rose-500">{error}</Text>
            ):null}
            <TextInput
                value={value}
                onChangeText={onChangeText}
                className="h-[38px] px-2 border rounded-lg border-slate-900 dark:border-blue-100 bg-slate-200 dark:bg-slate-700 text-slate-900 dark:text-blue-100 placeholder:text-slate-900/50 dark:placeholder:text-blue-50/50 truncate peer" 
                cursorColor={cursorColor}
                selectionColor={cursorColor}
                {...rest}/>
            {label?(
                <View className="flex flex-row gap-x-0.5">
                    <Text className={`font-nunito text-xl text-slate-900 dark:text-blue-100 peer-invalid:text-rose-300`}>{label}</Text>
                    {required?(<Text className="text-rose-500">*</Text>):''}
                </View>
            ):''}
        </View>
    );
}

export function CustomMaskedInput({
    label,
    required,
    value,
    onChangeText,
    error,
    ...rest
}:CustomMaskedInputProps) {
    const colorScheme = useColorScheme();

    const cursorColor:string = colorScheme === 'dark' ? '#a5b4fc' : '#4338cs';

    return(
        <View className="flex flex-col-reverse w-full gap-y-2">
            {error?(
                <Text className="font-nunito text-md text-rose-500">{error}</Text>
            ):null}
            <MaskedTextInput
                style={{
                    paddingHorizontal: 8,
                    height: 38,
                    borderWidth: 1.2,
                    borderRadius: 8,
                    borderColor: colorScheme === "dark" ? "#dbeafe" : "#0f172a",
                    backgroundColor: colorScheme === "dark" ? "#334155" : "#e2e8f0",
                    color: colorScheme === "dark" ? "#dbeafe" : "#0f172a",
                }}
                placeholderTextColor={
                    colorScheme === "dark" ? "rgba(239, 246, 255, 0.5)" : "rgba(15, 23, 42, 0.5)"
                }
                value={value}
                cursorColor={cursorColor}
                selectionColor={cursorColor}
                onChangeText={onChangeText}
                {...rest}/>
            {label?(
                <View className="flex flex-row gap-x-0.5">
                    <Text className={`font-nunito text-xl text-slate-900 dark:text-blue-100 peer-invalid:text-rose-300`}>{label}</Text>
                    {required?(<Text className="text-rose-500">*</Text>):''}
                </View>
            ):''}
        </View>
    );
}

export function CustomPasswordTextInput({
    label,
    required,
    value,
    onChangeText,
    error,
    ...rest
}:CustomPasswordTextInputProps) {
    const [isPasswordHide, setIsPasswordHide] = useState<boolean>(true);
    const colorScheme = useColorScheme();
    
    const baseColor:string = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const cursorColor:string = colorScheme === 'dark' ? '#a5b4fc' : '#4338cs';
    
    return(
        <View className="flex flex-col-reverse w-full gap-y-2">
            {error?(
                <Text className="font-nunito text-md text-rose-500">{error}</Text>
            ):null}
            <View className="flex flex-row gap-x-2 items-center justify-between">
                <TextInput 
                    value={value}
                    onChangeText={onChangeText}
                    className="flex-1 px-2 border rounded-lg border-slate-900 dark:border-blue-100 bg-slate-200 dark:bg-slate-700 text-slate-900 dark:text-blue-100 placeholder:text-slate-900/50 dark:placeholder:text-blue-50/50 truncate peer" 
                    cursorColor={cursorColor}
                    selectionColor={cursorColor} 
                    secureTextEntry={isPasswordHide}
                    {...rest}/>
                {isPasswordHide?(<EyeIcon color={baseColor} onPress={() => setIsPasswordHide(false)}/>):(<EyeSlashIcon color={baseColor} onPress={() => setIsPasswordHide(true)}/>)}
            </View>
            {label?(
                <View className="flex flex-row gap-x-0.5">
                    <Text className={`font-nunito text-xl text-slate-900 dark:text-blue-100 peer-invalid:text-rose-300`}>{label}</Text>
                    {required?(<Text className="text-rose-500">*</Text>):''}
                </View>
            ):null}
        </View>
    );
}