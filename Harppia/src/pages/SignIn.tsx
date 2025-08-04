import React, { useState } from "react";
import { View, Text, useColorScheme } from "react-native";
import ThemedHarppiaLogo from '@/components/ThemedHarppiaLogo'
import CustomButton from "@/components/CustomButtom";
import Animated, { SlideInRight, SlideOutLeft, SlideOutRight } from "react-native-reanimated";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import RegisterForm1 from "@/components/RegisterForm-1";

export default function SignIn({onBack}) {
    const colorScheme = useColorScheme();
    
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a'
    const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8'

    return(
        <Animated.View entering={SlideInRight.duration(300)} exiting={SlideInRight.duration(300)} className="flex p-10 h-[45rem] justify-between gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
            <View className="flex flex-row items-center justify-center gap-y-2">
                <ArrowLeftIcon color={"#dbeafe"} onTouchEnd={onBack}/>
                <View className="scale-75">
                    <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor}/>
                </View>
            </View>
            <RegisterForm1/>
            <View className="flex justify-center flex-row gap-x-4 mt-5">
                <CustomButton label="Próximo"/>
            </View>
        </Animated.View>
    );
}