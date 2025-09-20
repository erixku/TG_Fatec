import React, { useState } from "react";
import { Modal, Pressable, Text, useColorScheme, View } from "react-native"
import { PhotoIcon, CameraIcon } from "react-native-heroicons/solid";
import * as ImagePicker from 'expo-image-picker';

type PhotoPickerProps = {
    visible: boolean;
    onClose: () => void;
    onChange: (asset: ImagePicker.ImagePickerAsset) => void;
    value?: string;
    aspect?: [number, number];
}

export default function PhotoPicker ({
    visible, 
    onClose, 
    onChange,
    value,
    aspect = [1, 1]
}: PhotoPickerProps) {
    const colorScheme = useColorScheme();
    const iconsColor = colorScheme == 'dark' ? '#dbeafe':'#0f172a';
    
    //Coletar foto de galeria
    const pickImage = async () => {
        const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();
        if(status !== 'granted') {
            alert("É necessário a permissão de acesso à galeria");
            return;
        }

        const result = await ImagePicker.launchImageLibraryAsync({
            mediaTypes: ImagePicker.MediaTypeOptions.Images,
            allowsEditing: true,
            aspect: aspect,
            quality: 1,
        });

        if(!result.canceled) {
            onChange(result.assets[0]);
            onClose();
        }
    }

    const takePhoto = async () => {
        const { status } = await ImagePicker.requestCameraPermissionsAsync();
        if(status !== 'granted') {
            alert("É necessário a permissão de acesso à câmera");
            return;
        }

        const result = await ImagePicker.launchCameraAsync({
            mediaTypes: ImagePicker.MediaTypeOptions.Images,
            allowsEditing: true,
            aspect: aspect,
            quality: 1
        });

        if(!result.canceled) {
            onChange(result.assets[0]);
            onClose();
        }
    }

    return(
        <Modal visible={visible} transparent animationType="fade">
            <Pressable className="flex-1 px-6 bg-slate-900/40 justify-center items-center" onPress={onClose}>
                <View className="w-[80%] bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl">
                    <Pressable className="flex flex-row p-3 items-center justify-start gap-x-3 rounded-xl active:bg-indigo-300 dark:active:bg-indigo-700"
                        onPress={() => { pickImage() }}>
                            <PhotoIcon size={20} color={iconsColor}/>
                            <Text className="font-nunito text-slate-900 dark:text-blue-100">Escolher da galeria</Text>
                    </Pressable>

                    <Pressable className="flex flex-row p-3 items-center justify-start gap-x-3 rounded-xl active:bg-indigo-300 dark:active:bg-indigo-700"
                        onPress={() => { takePhoto() }}>
                            <CameraIcon size={20} color={iconsColor}/>
                            <Text className="font-nunito text-slate-900 dark:text-blue-100">Tirar foto</Text>
                    </Pressable>
                </View>
            </Pressable>
        </Modal>
    )
}