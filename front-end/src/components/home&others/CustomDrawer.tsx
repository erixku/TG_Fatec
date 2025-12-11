import React, { useState } from 'react';
import { View, Text, TouchableOpacity, useColorScheme, Pressable, Modal, BackHandler } from 'react-native';
import { XMarkIcon, UserCircleIcon, BellIcon, Cog8ToothIcon, CakeIcon, ArrowLeftEndOnRectangleIcon, WrenchIcon, UserMinusIcon } from 'react-native-heroicons/solid';
import type { DrawerContentComponentProps } from '@react-navigation/drawer';
import { useRouter } from 'expo-router';
import { logoutUser } from '@/api/loginUser';

export default function CustomDrawerContent(props: DrawerContentComponentProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const contrastColor = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8';
    const { navigation }= props;
    const [modalExitOpen, setModalExitOpen] = useState(false);
    const router = useRouter();

    return (
        <View className='flex-1 p-6 justify-between mt-5'>
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
                        <Pressable className='flex-row gap-3 items-center' onPress={() => {
                            navigation.closeDrawer();
                            router.push('/homeMenu/profileSettings');
                        }}>
                            <UserCircleIcon size={16} color={contrastColor}/>
                            <Text className='font-nunito-sembold text-2xl textslate-900 dark:text-blue-100'>Perfil</Text>
                        </Pressable>
                        <Pressable className='flex-row gap-3 items-center' onPress={() => {
                            navigation.closeDrawer();
                            router.push('/homeMenu/notificationSettings');
                        }}>
                            <BellIcon size={16} color={contrastColor}/>
                            <Text className='font-nunito-sembold text-2xl text-slate-900 dark:text-blue-100'>Notificações</Text>
                        </Pressable>
                    </View>
                </View>
                <Pressable className='flex-row gap-3 items-center' onPress={() => {
                    navigation.closeDrawer();
                    router.push('/homeMenu/birthdays');
                }}>
                    <CakeIcon size={20} color={contrastColor}/>
                    <Text className='font-nunito-semibold text-2xl text-slate-900 dark:text-blue-100'>Aniversáriantes</Text>
                </Pressable>
                <Pressable className='flex-row gap-3 items-center' onPress={() => {
                    navigation.closeDrawer();
                    router.push('/homeMenu/management');
                }}>
                    <Cog8ToothIcon size={20} color={contrastColor}/>
                    <Text className='font-nunito-semibold text-2xl text-slate-900 dark:text-blue-100'>Gerenciamento</Text>
                </Pressable>
                <Pressable className='flex-row gap-3 items-center' onPress={() => {
                    navigation.closeDrawer();
                    router.push('/homeMenu/tools');
                }}>
                    <WrenchIcon size={20} color={contrastColor}/>
                    <Text className='font-nunito-semibold text-2xl text-slate-900 dark:text-blue-100'>Ferramentas</Text>
                </Pressable>
            </View>
            <View className='gap-8 mb-5'>
                <Pressable className='flex-row gap-3 items-center' onPress={async () => {
                    navigation.closeDrawer();
                    await logoutUser();
                    router.replace('/');
                }}>
                    <UserMinusIcon size={20} color={contrastColor}/>
                    <Text className='font-nunito-semibold text-2xl text-slate-900 dark:text-blue-100'>Sair da conta</Text>
                </Pressable>
                <Pressable className='flex-row gap-3 items-center' onPress={() => {
                        setModalExitOpen(true);
                        navigation.closeDrawer();
                    }}>
                    <ArrowLeftEndOnRectangleIcon size={20} color={contrastColor}/>
                    <Text className='font-nunito-semibold text-2xl text-slate-900 dark:text-blue-100'>Sair da aplicação</Text>
                </Pressable>
            </View>

            <Modal transparent animationType="fade" visible={modalExitOpen}>
                <Pressable className="flex-1 px-6 bg-slate-900/40 justify-center items-center" onPress={() => setModalExitOpen(false)}>
                    <View className="w-[80%] bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl">
                        <Text className="font-nunito-semibold text-lg text-slate-900 dark:text-blue-100 mb-4">Tem certeza que deseja sair da aplicação?</Text>
                        <View className="flex-row justify-center gap-4">
                            <Pressable className="px-4 py-2 rounded-xl bg-slate-200 dark:bg-slate-600" onPress={() => setModalExitOpen(false)}>
                                <Text className="font-nunito-semibold text-slate-900 dark:text-blue-100">Cancelar</Text>
                            </Pressable>
                            <Pressable className="px-4 py-2 rounded-xl bg-red-500 dark:bg-red-700" onPress={() => {
                                BackHandler.exitApp();
                            }}>
                                <Text className="font-nunito-semibold text-white">Sair</Text>
                            </Pressable>
                        </View>
                    </View>
                </Pressable>
            </Modal>
        </View>
    )
}