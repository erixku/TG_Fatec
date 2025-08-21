import React, { useState } from "react";
import { View, Text, 
        TextInput, TextInputProps, 
        useColorScheme} from "react-native"
import { EyeIcon, EyeSlashIcon, ChevronDownIcon } from "react-native-heroicons/solid"

interface CustomTextInputProps extends TextInputProps {
    label?: string,
    required?: boolean,
}

interface CustomPasswordTextInputProps extends TextInputProps {
    label?: string,
    required?: boolean,
}

export function CustomTextInput({
    label,
    required,
    ...rest
}:CustomTextInputProps) {
    const colorScheme = useColorScheme();

    const cursorColor:string = colorScheme === 'dark' ? '#a5b4fc' : '#4338cs';

    return(
        <View className="flex flex-col-reverse w-full gap-y-2">
            <TextInput className={"px-2 border rounded-lg border-slate-900 dark:border-blue-100 bg-slate-200 dark:bg-slate-700 text-blue-100 dark:placeholder:text-blue-50/50 truncate peer"} 
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

export function CustomPasswordTextInput({
    label,
    required,
    ...rest
}:CustomPasswordTextInputProps) {
    const [isPasswordVisible, setIsPasswordVisible] = useState<boolean>(false);
    const colorScheme = useColorScheme();
    
    const baseColor:string = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const cursorColor:string = colorScheme === 'dark' ? '#a5b4fc' : '#4338cs';
    
    return(
        <View className="flex flex-col-reverse w-full gap-y-2">
            <View className="flex flex-row gap-x-2 items-center justify-between">
                <TextInput className="flex-1 px-2 border rounded-lg border-slate-900 dark:border-blue-100 bg-slate-200 dark:bg-slate-700 text-slate-900 dark:text-blue-100 placeholder:text-slate-900/50 dark:placeholder:text-blue-50/50 truncate peer" 
                    cursorColor={cursorColor}
                    selectionColor={cursorColor} 
                    secureTextEntry={isPasswordVisible}
                    {...rest}/>
                {isPasswordVisible?(<EyeIcon color={baseColor} onPress={() => setIsPasswordVisible(false)}/>):(<EyeSlashIcon color={baseColor} onPress={() => setIsPasswordVisible(true)}/>)}
            </View>
            {label?(
                <View className="flex flex-row gap-x-0.5">
                    <Text className={`font-nunito text-xl text-slate-900 dark:text-blue-100 peer-invalid:text-rose-300`}>{label}</Text>
                    {required?(<Text className="text-rose-500">*</Text>):''}
                </View>
            ):''}
        </View>
    );
}