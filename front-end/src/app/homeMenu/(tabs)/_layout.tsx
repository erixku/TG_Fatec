// ...existing code...
import { Tabs } from "expo-router";
import React, { useEffect } from "react";
import { KeyboardAvoidingView, Platform, useColorScheme, BackHandler, View, Text } from "react-native";
import { StatusBar } from "expo-status-bar";
import CustomTabBar from "@/components/home&others/CustomTabBar"
import CustomHeader from "@/components/home&others/CustomHeader";
import { HomeIcon, UserIcon, CalendarIcon, MusicalNoteIcon } from "react-native-heroicons/solid";

function TabsLayout() {
  return (
    <Tabs
      tabBar={(props) => <CustomTabBar {...props}/>}
      screenOptions={{headerShown: true, header: () => <CustomHeader/>}}
    >
      <Tabs.Screen name="index" />
      <Tabs.Screen name='schedule' />
    </Tabs>
  );
}

export default function HomeMenuLayout() {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    // Impede navegação para trás
    useEffect(() => {
        const backAction = () => true;
        const backHandler = BackHandler.addEventListener('hardwareBackPress', backAction);
        return () => backHandler.remove();
    }, []);

    return (
        <>
            <StatusBar style={colorScheme === 'dark' ? 'light' : 'dark'} />
            <KeyboardAvoidingView
                style={{ flex: 1 }}
                behavior={Platform.OS === "ios" ? "padding" : "height"}
                enabled
            >
                <TabsLayout />
            </KeyboardAvoidingView>
        </>
    );
}