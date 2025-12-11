import React from "react";
import { View, Text, Image } from "react-native";
import { UserIcon } from "react-native-heroicons/solid";

type ProfileHeaderProps = {
    uriLink?: string;
    name?: string;
    email?: string;
    iconsColor: string;
}

export default function ProfileHeader({uriLink, name = "Nome do Usuário",email = "e-mail do usuário", iconsColor}: ProfileHeaderProps) {
    console.log('🖼️ ProfileHeader - URI recebida:', uriLink);
    
    return(
        <View className="items-center w-full mb-4 gap-y-5">
            <View className="h-[150px] w-[150px] rounded-full">
                {uriLink ? (
                    <Image 
                        source={{uri: uriLink}} 
                        className="h-full w-full rounded-full"
                        onError={(error) => console.error('❌ Erro ao carregar imagem:', error)}
                        onLoad={() => console.log('✅ Imagem carregada com sucesso')}
                    />
                ):(
                    <View className="h-full w-full items-center justify-center bg-slate-200 dark:bg-slate-800 rounded-full">
                        <UserIcon size={80} color={iconsColor}/>
                    </View>
                )}
            </View>
            <View className="flex items-center">
                <Text 
                    className="text-2xl font-nunito-semibold dark:text-blue-100 text-slate-900"
                    numberOfLines={2}
                    ellipsizeMode="tail"
                >
                    {name}
                </Text>
                <Text 
                    className="text-xl font-nunito-semibold dark:text-blue-100/70 text-slate-900/70"
                    numberOfLines={1}
                    ellipsizeMode="tail"
                >
                    {email}
                </Text>
            </View>
        </View>
    )
}