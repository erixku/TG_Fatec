import { Tabs } from "expo-router";
import React from "react";
import { KeyboardAvoidingView, Platform, useColorScheme, BackHandler } from "react-native";
import { StatusBar } from "expo-status-bar";
import { useFocusEffect } from "@react-navigation/native";
import CustomTabBar from "@/components/home&others/CustomTabBar"
import CustomHeader from "@/components/home&others/CustomHeader";

export default function HomeMenuLayout() {
    const colorScheme = useColorScheme();

    // Impede navegação para trás (especialmente para login)
    useFocusEffect(
        React.useCallback(() => {
            const backAction = () => {
                return true; // Previne o comportamento padrão do botão voltar
            };

            const backHandler = BackHandler.addEventListener(
                "hardwareBackPress",
                backAction
            );

            return () => backHandler.remove();
        }, [])
    );

    return (
        <>
            <StatusBar style={colorScheme === 'dark' ? 'light' : 'dark'} />
            <KeyboardAvoidingView
                style={{ flex: 1 }}
                behavior={Platform.OS === "ios" ? "padding" : "height"}
                enabled
            >
                <Tabs
                    tabBar={(props) => <CustomTabBar {...props}/>}
                    screenOptions={{headerShown: true, header: () => <CustomHeader/>}}
                >
                    <Tabs.Screen name="index" />
                    <Tabs.Screen name='schedule' />
                </Tabs>
            </KeyboardAvoidingView>
        </>
    );
}
