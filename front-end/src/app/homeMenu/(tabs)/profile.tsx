import React, { useState, useEffect } from "react";
import { View, Text, useColorScheme, Button, Pressable, ActivityIndicator, Alert } from "react-native";
import { Image } from "expo-image";
import AnimatedScreenWrapper from "../../../components/home&others/AnimatedScreenWrapper";
import PhotoPicker from "@/components/PhotoPicker";
import { RegisterUserFormData } from "@/schemas/registerUserSchema";
import { EyeIcon, EyeSlashIcon, UserIcon } from "react-native-heroicons/solid";
import ProfileHeader from "@/components/home&others/profile/ProfileHeader";
import ProfileBody from "@/components/home&others/profile/ProfileBody";
import { getUser } from "@/services/localCache";

interface UserData {
    id: string;
    nome: string;
    nomeSocial?: string;
    email: string;
    telefone: string;
    cpf: string;
    dataNascimento: string;
    sexo: string;
    foto?: string;
    endereco: {
        cep: string;
        uf: string;
        cidade: string;
        bairro: string;
        rua: string;
        numero: string;
        complemento?: string;
    };
}

export default function ProfilePage() {
    const [modalVisible, setModalVisible] = useState(false);
    const [field, setField] = useState<string>("");
    const [isHidden, setIsHidden] = useState<boolean>(true);
    const [user, setUser] = useState<UserData | null>(null);
    const [isLoading, setIsLoading] = useState(true);
    
    const colorScheme = useColorScheme();
    const iconsColor = colorScheme === 'dark' ? '#93c5fd' : '#1e40af';
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    useEffect(() => {
        loadUserData();
    }, []);

    const loadUserData = async () => {
        try {
            setIsLoading(true);
            const userData = await getUser();
            if (userData) {
                setUser(userData);
                console.log('👤 Dados do usuário carregados:', userData.nome);
                console.log('📸 Foto do usuário:', userData.foto || 'Nenhuma foto definida');
            }
        } catch (error) {
            console.error('❌ Erro ao carregar dados do usuário:', error);
        } finally {
            setIsLoading(false);
        }
    };

    if (isLoading) {
        return (
            <AnimatedScreenWrapper>
                <View className="flex-1 items-center justify-center bg-slate-300 dark:bg-slate-800">
                    <ActivityIndicator size="large" color={iconsColor} />
                    <Text className="mt-4 text-lg font-nunito dark:text-blue-100 text-slate-900">
                        Carregando perfil...
                    </Text>
                </View>
            </AnimatedScreenWrapper>
        );
    }

    return (
        <AnimatedScreenWrapper>
            <View className="flex-col flex-1 items-center mt-2 bg-slate-300 dark:bg-slate-800">
                <View className="flex-1 w-full items-center pb-28">
                    <View className="flex pt-10 h-full w-[90%] lg:w-[50%] justify-start gap-y-4 rounded-xl">

                        <ProfileHeader 
                            iconsColor={iconsColor}
                            uriLink={user?.foto}
                            name={user?.nome}
                            email={user?.email}
                        />
                        <View className="flex-row justify-end w-full gap-7">
                            <Pressable onPress={() => setIsHidden(!isHidden)}>
                                {isHidden ? (<EyeIcon color={baseColor} size={20}/>):(<EyeSlashIcon color={baseColor} size={20}/>)}
                            </Pressable>
                        </View>
                        <ProfileBody isHidden={isHidden} user={user} />
                    </View>
                </View>
            </View>
        </AnimatedScreenWrapper>
    );
}