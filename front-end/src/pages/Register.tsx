import React, { useState } from "react";
import { View, Text, useColorScheme, Pressable, KeyboardAvoidingView, Platform } from "react-native";
import ThemedHarppiaLogo from '@/components/ThemedHarppiaLogo'
import { CustomButton } from "@/components/CustomButtom";
import Animated, { SlideInRight, SlideInLeft, SlideOutRight, SlideOutLeft } from "react-native-reanimated";
import { ArrowLeftIcon } from "react-native-heroicons/solid";
import { baseColor, contrastColor } from "@/assets/iconsColor";
import { zodResolver } from "@hookform/resolvers/zod";
import { RegisterFormData, registerSchema } from "@/schemas/registerSchema";
import { FormProvider, useForm } from "react-hook-form";

export default function Register({onBack, onNext, children, direction}) {
    const animations = {
        forward: {
            entering: SlideInRight.duration(300),
            exiting: SlideOutLeft.duration(300),
        },
        backward: {
            entering: SlideInLeft.duration(300),
            exiting: SlideOutRight.duration(300),
        }
    }

    const colorScheme = useColorScheme();

    const baseColor:string = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const contrastColor:string = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8';

    const currentAnimation = animations[direction] || animations.forward;

    return(
        <Animated.View entering={currentAnimation.entering} exiting={currentAnimation.exiting} 
            className="flex p-10 h-[45rem] w-full justify-between items-center gap-y-4 rounded-xl bg-slate-50 shadow-md dark:bg-slate-700 dark:shadow-slate-400">
            <View className="flex flex-row items-center justify-center gap-y-2">
                <Pressable onPress={onBack}>
                    <ArrowLeftIcon color={baseColor}/>
                </Pressable>
                <View className="scale-75">
                    <ThemedHarppiaLogo baseColor={baseColor} contrastColor={contrastColor}/>
                </View>
            </View>
                {children}
            <View className="flex justify-center flex-row gap-x-4 mt-5">
                <CustomButton label="Próximo" onPress={onNext}/>
            </View>
        </Animated.View>
    );
}