import React from 'react';
import { View, Text, TouchableOpacity, useColorScheme, Pressable} from 'react-native';
import { XMarkIcon, UserCircleIcon, BellIcon, HandRaisedIcon, CakeIcon, ArrowLeftEndOnRectangleIcon, ChatBubbleOvalLeftEllipsisIcon, UserMinusIcon } from 'react-native-heroicons/solid';
import type { DrawerContentComponentProps } from '@react-navigation/drawer';

export default function CustomDrawerContent(props: DrawerContentComponentProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8';
    const { navigation }= props

    return (
        <View className='flex-1 p-6 justify-between'>
            <View className='gap-8'>
                <View className='flex-row gap-5 items-center'>
                    <Pressable onPress={() => navigation.closeDrawer()}>
                        <XMarkIcon size={25} color={baseColor}/>
                    </Pressable>
                    <Text className='font-nunito-bold text-3xl text-slate-900 dark:text-blue-100'>Menu Lateral</Text>
                </View>
                <View className='p-3 gap-3 rounded-xl bg-slate-200 dark:bg-slate-700'>
                    <Text className='font-nunito-semibold text-slate-900/70 dark:text-blue-100/70'>Configurações</Text>
                    <View className='gap-5'>
                        <Pressable className='flex-row gap-3 items-center'>
                            <UserCircleIcon size={16} color={contrastColor}/>
                            <Text className='font-nunito-sembold text-2xl textslate-900 dark:text-blue-100'>Perfil</Text>
                        </Pressable>
                        <Pressable className='flex-row gap-3 items-center'>
                            <HandRaisedIcon size={16} color={contrastColor}/>
                            <Text className='font-nunito-sembold text-2xl textslate-900 dark:text-blue-100'>Acessibilidade</Text>
                        </Pressable>
                        <Pressable className='flex-row gap-3 items-center'>
                            <BellIcon size={16} color={contrastColor}/>
                            <Text className='font-nunito-sembold text-2xl text-slate-900 dark:text-blue-100'>Notificações</Text>
                        </Pressable>
                    </View>
                </View>
                <Pressable className='flex-row gap-3 items-center'>
                    <CakeIcon size={20} color={contrastColor}/>
                    <Text className='font-nunito-semibold text-2xl text-slate-900 dark:text-blue-100'>Aniversatiantes</Text>
                </Pressable>
                <Pressable className='flex-row gap-3 items-center'>
                    <ChatBubbleOvalLeftEllipsisIcon size={20} color={contrastColor}/>
                    <Text className='font-nunito-semibold text-2xl text-slate-900 dark:text-blue-100'>Conversas</Text>
                </Pressable>
            </View>
            <View className='gap-8 mb-5'>
                <Pressable className='flex-row gap-3 items-center'>
                    <UserMinusIcon size={20} color={contrastColor}/>
                    <Text className='font-nunito-semibold text-2xl text-slate-900 dark:text-blue-100'>Sair da conta</Text>
                </Pressable>
                <Pressable className='flex-row gap-3 items-center'>
                    <ArrowLeftEndOnRectangleIcon size={20} color={contrastColor}/>
                    <Text className='font-nunito-semibold text-2xl text-slate-900 dark:text-blue-100'>Sair da aplicação</Text>
                </Pressable>
            </View>
        </View>
    )
}