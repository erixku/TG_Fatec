import React, { useState } from "react";
import { View, Text, useColorScheme, Button } from "react-native";
import AnimatedScreenWrapper from "../../components/home&others/AnimatedScreenWrapper";

export default function HomePage() {

    return (
        <AnimatedScreenWrapper>
            <View className="flex-col flex-1 items-center mt-2 bg-slate-300 dark:bg-slate-800">
                <View className="flex-1 w-full items-center pb-28">
                    <View className="flex p-10 h-full w-[90%] lg:w-[50%] justify-center gap-y-4 rounded-xl bg-blue-400">
                        
                    </View>
                </View>
            </View>
        </AnimatedScreenWrapper>
    );
}