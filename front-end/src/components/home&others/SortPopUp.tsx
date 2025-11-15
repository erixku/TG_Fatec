import React from 'react';
import { View, Text, Pressable, Modal, FlatList } from 'react-native';

interface sortPopUpProps {
    options: string[];
    onSelect: (option: string) => void;
    onClose: () => void;
    visible?:boolean
}

export default function SortPopUp({options, onSelect, onClose, visible}: sortPopUpProps) {
    return (
        <Modal visible={visible} transparent animationType="fade" onRequestClose={onClose}>
            <Pressable className="flex-1 px-6 bg-slate-900/40 justify-center items-center" onPress={onClose}>
                <View className="w-[80%] max-h-[60%] bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl">
                    <FlatList data={options} keyExtractor={(item) => item}
                        renderItem={({item}) => (
                            <Pressable className="p-3 rounded-xl active:bg-indigo-300 dark:active:bg-indigo-700"
                                onPress={() => {
                                    onClose();
                                    onSelect(item);
                                }}>
                                    <Text className="font-nunito text-slate-900 dark:text-blue-100">{item}</Text>
                            </Pressable>
                        )}>
                    </FlatList>
                </View>
            </Pressable>
        </Modal>
    )
}