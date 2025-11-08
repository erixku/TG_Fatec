import React, { useState } from "react";
import { View, Text, useColorScheme, Button } from "react-native";
import AnimatedScreenWrapper from "../../../components/home&others/AnimatedScreenWrapper";
import ScheduleList from "@/components/home&others/start/SchedulesList";
import { mockItens } from "@/mocks/itensScheduleCommitmend";

export default function HomePage() {
    
    return (
        <AnimatedScreenWrapper>
            <View className="flex-col flex-1 items-center mt-2 bg-slate-300 dark:bg-slate-800">
                <View className="flex-1 w-full items-center pb-28">
                    <View className="flex py-10 h-full w-[90%] lg:w-[50%] items-center justify-center gap-y-4 rounded-xl">
                        <ScheduleList warning label="Avisos" schedules={mockItens}/>
                        <ScheduleList label="Agendamentos" schedules={mockItens}/>
                        <ScheduleList commitmend label="Compromissos" schedules={mockItens}/>
                    </View>
                </View>
            </View>
        </AnimatedScreenWrapper>
    );
}