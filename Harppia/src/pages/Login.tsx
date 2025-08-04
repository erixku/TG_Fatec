import React, { useState } from "react";
import { View, Text, useColorScheme } from "react-native";
import ThemedHarppiaLogo from '@/components/ThemedHarppiaLogo'
import CustomButton from "@/components/CustomButtom";
import Animated, { SlideInRight, SlideOutLeft, SlideOutRight } from "react-native-reanimated";
import { ArrowLeftIcon } from "react-native-heroicons/solid"

export default function Login({onBack}) {
    const colorScheme = useColorScheme();
    
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a'
    const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8'

    return(
        <Animated.View entering={SlideInRight.duration(300)} exiting={SlideOutRight.duration(300)} className="flex justify-between p-10 h-[45rem] gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
            <View className="flex flex-row items-center justify-center gap-y-2">
                <ArrowLeftIcon color={"#dbeafe"} onTouchEnd={onBack}/>
                <View className="scale-75">
                    <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor}/>
                </View>
            </View>
            <View className="flex items-center justify-center gap-y-3 mt-4">
                <Text className="text-center font-nunito-light text-slate-900 dark:text-blue-100"> 
                    Tela de Login
                </Text>
            </View>
            <View className="flex justify-center flex-row gap-x-4 mt-5">
                <CustomButton label="Entrar"/>
            </View>
        </Animated.View>
    );
}