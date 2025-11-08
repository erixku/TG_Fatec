import React, { useState } from "react";
import { Pressable, View, Text, useColorScheme, Modal } from "react-native";
import { CalendarIcon } from "react-native-heroicons/solid";
import CustomCalendar from "./CustomCalendar";
import { DateType } from "react-native-ui-datepicker";
import dayjs, { FormatObject } from "dayjs";
import { Control, Controller, FieldValue, FieldValues, Path } from "react-hook-form";
import { RegisterUserFormData } from "@/schemas/registerUserSchema";

interface CustomDatePickerProps{
    label?:string;
    required?:boolean;
    onChange: (date: DateType) => void;
    error?: string;
    value: DateType | null;
}

export default function CustomDatePicker({label, required, onChange, error, value}:CustomDatePickerProps) {
    const [open, setOpen] = useState<boolean>(false);
    const colorScheme = useColorScheme();
        
    const baseColor:string = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const formatedDate = value && dayjs(value).isValid() ? dayjs(value).locale('pt-br').format('DD/MM/YYYY') : 'Selecione uma data'        
            
    return(
        <View className="flex flex-col gap-y-2">
            {label?(
                <View className="flex flex-row gap-x-0.5">
                    <Text className={`font-nunito text-xl text-slate-900 dark:text-blue-100 peer-invalid:text-rose-300`}>{label}</Text>
                    {required?(<Text className="text-rose-500">*</Text>):''}
                </View>
            ):''}
            <View className="h-[38px]">
                <Pressable
                    onPress={() => setOpen(true)}
                    className="flex flex-row items-center justify-between px-2 w-full h-full border border-slate-900 dark:border-blue-100 rounded-lg bg-slate-200 dark:bg-slate-700">
                        <Text className={`font-nunito ${value?'text-slate-900 dark:text-blue-100':'text-slate-900/50 dark:text-blue-100/50'}`}>
                            { formatedDate }
                        </Text>
                        <CalendarIcon size={20} color={baseColor} />
                </Pressable>

                {error ? (
                    <Text className="font-nunito text-md text-rose-500">{error}</Text>
                ):null}

                <Modal visible={open} transparent animationType="fade">
                    <Pressable className="flex-1 px-6 bg-slate-900/40 justify-center items-center" onPress={() => setOpen(false)}>
                        <View className="w-[90%] bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl" >
                            <CustomCalendar 
                                onDateChange={(date) => {
                                    setOpen(false);
                                    onChange(date)
                                }}
                                date={value ? new Date(value.valueOf()) : new Date()}
                                mode="single"
                                initialView="year"
                            />
                        </View>
                    </Pressable>
                </Modal>
            </View>
        </View>
    )
}