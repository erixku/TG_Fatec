import React, { useState } from "react";
import { View, Text, 
        TextInput, TextInputProps, 
        useColorScheme} from "react-native"
import { EyeIcon, EyeSlashIcon, ChevronDownIcon } from "react-native-heroicons/solid"
import { MaskedTextInput, MaskedTextInputProps } from "react-native-mask-text";

interface CustomTextInputProps extends TextInputProps {
    label?: string,
    required?: boolean,
    value?: string;
    onChangeText?: (text: string) => void
    error?: string,
    disabled?: boolean,
}
interface CustomMaskedInputProps extends MaskedTextInputProps {
    label?: string,
    required?: boolean,
    value?: string;
    onChangeText: (formated: string, extracted:string) => void
    error?: string,
    disabled?: boolean,
}

interface CustomPasswordTextInputProps extends TextInputProps {
    label?: string,
    required?: boolean,
    value?: string;
    onChangeText?: (text: string) => void
    error?: string,
    disabled?: boolean,
}

export function CustomTextInput({
    label,
    required,
    value,
    onChangeText,
    error,
    disabled,
    ...rest
}:CustomTextInputProps) {
    const colorScheme = useColorScheme();

    const cursorColor:string = colorScheme === 'dark' ? '#a5b4fc' : '#4338cs';

    return(
        <View className="flex flex-col-reverse w-full gap-y-2">
            {error?(
                <Text className="font-nunito text-md text-rose-500">{error}</Text>
            ):null}
            <View className="flex flex-row w-full gap-x-2 items-center justify-between">
                <TextInput
                    value={value}
                    onChangeText={onChangeText}
                    className={`h-[38px] w-full px-2 border rounded-lg ${disabled? 'border-slate-900/50 dark:border-blue-50/50 text-slate-900/50 dark:text-blue-100/50 bg-slate-200/50 dark:bg-slate-700/50':'border-slate-900 dark:border-blue-100 text-slate-900 dark:text-blue-100 bg-slate-200 dark:bg-slate-700'} placeholder:text-slate-900/50 dark:placeholder:text-blue-50/50 truncate peer`}
                    cursorColor={cursorColor}
                    selectionColor={cursorColor}
                    editable={!disabled}
                    {...rest}/>
            </View>
            {label?(
                <View className="flex flex-row w-full gap-x-0.5">
                    <Text className={`font-nunito text-xl ${disabled? 'text-slate-900/50 dark:text-blue-50/50':'text-slate-900 dark:text-blue-100'} peer-invalid:text-rose-300`}>{label}</Text>
                    {required?(<Text className={`${disabled? 'text-rose-500/50':'text-rose-500'}`}>*</Text>):''}
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
    disabled,
    ...rest
}:CustomMaskedInputProps) {
    const colorScheme = useColorScheme();

    const cursorColor:string = colorScheme === 'dark' ? '#a5b4fc' : '#4338cs';

    return(
        <View className="flex flex-col-reverse w-full self-stretch gap-y-2">
            {error?(
                <Text className="font-nunito text-md text-rose-500">{error}</Text>
            ):null}
            <MaskedTextInput
                style={{
                    paddingHorizontal: 8,
                    height: 38,
                    innerWidth: '100%',
                    borderWidth: 1.2,
                    borderRadius: 8,
                    borderColor: disabled
                        ? colorScheme === "dark" ? "#64748b" : "#94a3b8"
                        : colorScheme === "dark" ? "#dbeafe" : "#0f172a",
                    backgroundColor: disabled
                        ? colorScheme === "dark" ? "#1e293b" : "#e5e7eb"
                        : colorScheme === "dark" ? "#334155" : "#e2e8f0",
                    color: disabled
                        ? colorScheme === "dark" ? "#64748b" : "#94a3b8"
                        : colorScheme === "dark" ? "#dbeafe" : "#0f172a",
                }}
                placeholderTextColor={
                    colorScheme === "dark" ? "rgba(239, 246, 255, 0.5)" : "rgba(15, 23, 42, 0.5)"
                }
                value={value}
                cursorColor={cursorColor}
                selectionColor={cursorColor}
                onChangeText={onChangeText}
                editable={!disabled}
                {...rest}/>
            {label?(
                <View className="flex flex-row w-full gap-x-0.5">
                    <Text className={`font-nunito text-xl ${disabled? 'text-slate-900/50 dark:text-blue-50/50':'text-slate-900 dark:text-blue-100'} peer-invalid:text-rose-300`}>{label}</Text>
                    {required?(<Text className={`${disabled? 'text-rose-500/50':'text-rose-500'}`}>*</Text>):''}
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
    disabled,
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
            <View className="flex flex-row w-full gap-x-2 items-center justify-between">
                <TextInput 
                    value={value}
                    onChangeText={onChangeText}
                    className={`flex-1 px-2 border rounded-lg ${disabled? 'border-slate-900/50 dark:border-blue-50/50 text-slate-900/50 dark:text-blue-100/50 bg-slate-200/50 dark:bg-slate-700/50':'border-slate-900 dark:border-blue-100 text-slate-900 dark:text-blue-100 bg-slate-200 dark:bg-slate-700'} placeholder:text-slate-900/50 dark:placeholder:text-blue-50/50 truncate peer`}
                    cursorColor={cursorColor}
                    selectionColor={cursorColor} 
                    secureTextEntry={isPasswordHide}
                    editable={!disabled}
                    {...rest}/>
                {isPasswordHide?(<EyeIcon color={baseColor} onPress={() => setIsPasswordHide(false)}/>):(<EyeSlashIcon color={baseColor} onPress={() => setIsPasswordHide(true)}/>)}
            </View>
            {label?(
                <View className="flex flex-row w-full gap-x-0.5">
                    <Text className={`font-nunito text-xl ${disabled? 'text-slate-900/50 dark:text-blue-50/50':'text-slate-900 dark:text-blue-100'} peer-invalid:text-rose-300`}>{label}</Text>
                    {required?(<Text className={`${disabled? 'text-rose-500/50':'text-rose-500'}`}>*</Text>):''}
                </View>
            ):null}
        </View>
    );
}