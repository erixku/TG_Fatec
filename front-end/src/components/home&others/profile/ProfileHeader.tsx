import React from "react";
import { View, Text } from "react-native";
import { UserIcon } from "react-native-heroicons/solid";
import { Image } from "expo-image";

type ProfileHeaderProps = {
    uriLink?: string;
    name?: string;
    email?: string;
    iconsColor: string;
}

export default function ProfileHeader({uriLink, name = "Nome do Usuário",email = "e-mail do usuário", iconsColor}: ProfileHeaderProps) {
    return(
        <View className="items-center w-full mb-4 gap-y-5">
            <View className="h-[150px] w-[150px] rounded-full">
                {uriLink ? (
                    <Image source={{uri: uriLink}} className="h-full w-full rounded-full"/>
                ):(
                    <View className="h-full w-full items-center justify-center bg-slate-100 dark:bg-slate-700 rounded-full">
                        <UserIcon size={80} color={iconsColor}/>
                    </View>
                )}
            </View>
            <View className="flex items-center">
                <Text className="text-2xl font-nunito-semibold dark:text-blue-100 text-slate-900">{name}</Text>
                <Text className="text-xl font-nunito-semibold dark:text-blue-100/70 text-slate-900/70">{email}</Text>
            </View>
        </View>
    )
}