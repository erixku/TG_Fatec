import React from "react";
import { View, Text } from "react-native";

export default function Welcome() {
    return(
        <View className="flex flex-1 justify-center items-center gap-y-2 w-full bg-slate-200">
            <View className="flex p-10 w-[80%] rounded-xl bg-slate-50 shadow-md">
                <Text className="font-hand">Oi</Text>
            </View>
        </View>
    );
}