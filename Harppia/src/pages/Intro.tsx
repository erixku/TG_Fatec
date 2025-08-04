import React, { useEffect, useCallback, useState } from "react";
import { View, Text, useColorScheme, BackHandler } from "react-native";
import ThemedHarppiaLogo from '@/components/ThemedHarppiaLogo'
import CustomButton from "@/components/CustomButtom";
import Animated, { SlideInRight, SlideOutLeft } from "react-native-reanimated";
import Welcome from "./Welcome";
import SignIn from "./SignIn";
import Login from "./Login";


export default function Intro() {
    const [currentView, setCurrentView] = useState('welcome');

    const handleNavigation = (view) => {
        setCurrentView(view);
    }

    const handleBack = () => {
        setCurrentView('welcome');
    }

    useEffect(() => {
        const onBackPress = () => {
            if (currentView === 'login' || currentView === 'signin') {
                handleBack();
                return true;
            } else {
                return false;
            }
        };

        const subscription = BackHandler.addEventListener(
            'hardwareBackPress', onBackPress
        );

        return () => subscription.remove();
    }, [currentView, handleBack])

    return(
        <View className="flex flex-1 items-center justify-center w-[80%] lg:w-[50%]">
            {currentView === 'welcome' && (<Welcome onNavigate={handleNavigation}/>)}
            {currentView === 'signin' && (<SignIn onBack={handleBack}/>)}
            {currentView === 'login' && (<Login onBack={handleBack}/>)}
        </View>
    );
}