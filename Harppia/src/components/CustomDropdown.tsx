import React, { useState } from "react";
import { View, Text, Pressable, FlatList, Modal, useColorScheme } from "react-native";
import { ChevronDownIcon } from "react-native-heroicons/solid";
import { baseColor } from "@/assets/iconsColor"; 

interface CustomDropdownProps {
    label?: string;
    required?:boolean;
}

export default function CustomDropdown({label, required}:CustomDropdownProps) {
    const [selected, setSelected] = useState<string>(null);
    const [open, setOpen] = useState<boolean>(false);

    const options: string[] = ['Masculino', 'Feminino'];

    const colorScheme = useColorScheme();
    
    const baseColor:string = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    return(
        <View className="flex flex-col gap-y-2">
            {label?(
                <View className="flex flex-row gap-x-0.5">
                    <Text className={`font-nunito text-xl text-slate-900 dark:text-blue-100 peer-invalid:text-rose-300`}>{label}</Text>
                    {required?(<Text className="text-rose-500">*</Text>):''}
                </View>
            ):''}
            <View className="h-12">
                <Pressable
                    onPress={() => setOpen(true)}
                    className="flex flex-row items-center justify-between px-2 w-full h-full border border-slate-900 dark:border-blue-100 rounded-lg bg-slate-200 dark:bg-slate-700">
                        <Text className={`font-nunito ${selected?'text-slate-900 dark:text-blue-100':'text-slate-900/50 dark:text-blue-100/50'}`}>
                            { selected || 'Selecione uma opção'}
                        </Text>
                        <ChevronDownIcon size={20} color={baseColor} />
                </Pressable>

                <Modal visible={open} transparent animationType="fade">
                    <Pressable className="flex-1 px-6 bg-slate-900/40 justify-center items-center" onPress={() => setOpen(false)}>
                        <View className="w-[80%] bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl">
                            <FlatList data={options} keyExtractor={(item) => item}
                                renderItem={({item}) => (
                                    <Pressable className="p-3 rounded-xl active:bg-indigo-300 dark:active:bg-indigo-700"
                                        onPress={() => {
                                            setSelected(item);
                                            setOpen(false);
                                        }}>
                                            <Text className="font-nunito text-slate-900 dark:text-blue-100">{item}</Text>
                                    </Pressable>
                                )}>
                            </FlatList>
                        </View>
                    </Pressable>
                </Modal>
            </View>
        </View>
    )
}