import React, { useState } from "react";
import { View, Text, useColorScheme, Button, Pressable } from "react-native";
import { Image } from "expo-image";
import AnimatedScreenWrapper from "../../../components/home&others/AnimatedScreenWrapper";
import PhotoPicker from "@/components/PhotoPicker";
import { RegisterUserFormData } from "@/schemas/registerUserSchema";
import { EyeIcon, EyeSlashIcon, UserIcon } from "react-native-heroicons/solid";
import ProfileHeader from "@/components/home&others/profile/ProfileHeader";
import ProfileBody from "@/components/home&others/profile/ProfileBody";

export default function HomePage() {
    const [modalVisible, setModalVisible] = useState(false);
    const [field, setField] = useState<string>("");
    const [isHidden, setIsHidden] = useState<boolean>(false);
    const colorScheme = useColorScheme();
    const iconsColor = colorScheme === 'dark' ? '#93c5fd' : '#1e40af';
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    return (
        <AnimatedScreenWrapper>
            <View className="flex-col flex-1 items-center mt-2 bg-slate-300 dark:bg-slate-800">
                <View className="flex-1 w-full items-center pb-28">
                    <View className="flex pt-10 h-full w-[90%] lg:w-[50%] justify-start gap-y-4 rounded-xl">

                        <ProfileHeader iconsColor={iconsColor}/>
                        <View className="flex-row justify-end w-full gap-7">
                            <Pressable onPress={() => setIsHidden(!isHidden)}>
                                {isHidden ? (<EyeIcon color={baseColor} size={20}/>):(<EyeSlashIcon color={baseColor} size={20}/>)}
                            </Pressable>
                        </View>
                        <ProfileBody isHidden={isHidden} />
                    </View>
                </View>
            </View>
        </AnimatedScreenWrapper>
    );
}