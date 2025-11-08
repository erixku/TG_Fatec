import React, { useState } from "react";
import { View, Text, useColorScheme, Button, Pressable } from "react-native";
import AnimatedScreenWrapper from "../../../components/home&others/AnimatedScreenWrapper";
import CustomCalendar from "@/components/CustomCalendar";
import dayjs from "dayjs";
import { CalendarIcon, ChevronRightIcon, FunnelIcon, ListBulletIcon } from "react-native-heroicons/solid";
import { boolean } from "zod";
import ScheduleItens from "@/components/home&others/schedues/ScheduleItens";
import { mockItens } from "@/mocks/itensScheduleCommitmend";

export default function SchedulePage() {
    const [selectedDate, setSelectedDate] = useState(dayjs());
    const [isCalendar, setIsCalendar] = useState<boolean>(false)
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    return (
        <AnimatedScreenWrapper>
            <View className="flex-col flex-1 items-center mt-2 bg-slate-300 dark:bg-slate-800">
                <View className="flex-1 w-full items-center pb-28">
                    <View className="flex pt-10 h-full w-[90%] lg:w-[50%] justify-center gap-y-4 rounded-xl">
                        <View className="flex-1 flex-col gap-y-2">
                            <View className={`flex-row ${!isCalendar? "justify-between":"justify-end"}`}>
                                {!isCalendar && (        
                                    <View className="flex-row items-center gap-x-2">
                                        <Text className="text-2xl font-nunito-semibold dark:text-blue-100 text-slate-900">Recentes</Text>
                                        <ChevronRightIcon size={20} color={baseColor}/>
                                    </View>
                                )}
                                <View className="flex-row gap-x-2">
                                    <Pressable>
                                        <FunnelIcon size={20} color={baseColor} />
                                    </Pressable>
                                    <Pressable onPress={() => setIsCalendar(!isCalendar)}>
                                        {isCalendar? (<CalendarIcon size={20} color={baseColor} />):(<ListBulletIcon size={20} color={baseColor} />)}
                                    </Pressable>
                                </View>
                            </View>
                            {isCalendar? (
                                <View className="flex-1 flex-col gap-y-2">
                                    <CustomCalendar 
                                        onDateChange={(date) => {
                                            console.log(date);
                                            setSelectedDate(dayjs(date));
                                        }} 
                                        date={selectedDate}
                                        initialView="day"
                                    />
                                    <View className="flex-1 flex-col w-full">
                                        <ScheduleItens schedules={(mockItens)}/>
                                    </View>
                                </View>
                            ):(
                                <View className="flex-1 flex-col w-full">
                                    <ScheduleItens schedules={(mockItens)}/>
                                </View>
                            )}
                            
                        </View>
                    </View>
                </View>
            </View>
        </AnimatedScreenWrapper>
    );
}