import React, { useState } from "react";
import { View, Text, useColorScheme, Pressable } from "react-native";
import { EyeIcon, EyeSlashIcon} from "react-native-heroicons/solid"
import { MaskedTextInput } from "react-native-mask-text";
import { createElement } from "nativewind";

type ProfileInfoProps = {
    value: string;
    label: string;
    confidential?: boolean;
    mask?: string;
}


export default function ProfileInfo({value = "Teste", label = "Teste", confidential, mask}: ProfileInfoProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    return(
        <View className="flex-1 flex-col gap-1 w-full h-fit">
            <Text className="font-nunito-semibold text-lg text-slate-900 dark:text-blue-100">
                {label}
            </Text>
            <View className="flex-row gap-1 items-center justify-start gap-x-7 w-full">
                {mask ? (
                    <MaskedTextInput onChangeText={() => null} style={{ color: `${baseColor}B3`, fontFamily: 'Nunito-Regular', padding: 0, margin: 0, fontSize: 16 }} mask={confidential?("*".repeat(value.length)):(mask)} editable={false} value={confidential?("*".repeat(value.length)):(value)}/>
                ):(
                    <Text className="font-nunito text-xl text-slate-900/70 dark:text-blue-100/70">
                        {confidential?("*".repeat(value.length)):(value)}
                    </Text>
                )}
            </View>
        </View>
    )
}