import React, { useEffect, useCallback, useState } from "react";
import { View, Text, useColorScheme, BackHandler } from "react-native";
import ThemedHarppiaLogo from '@/components/ThemedHarppiaLogo'
import CustomButton from "@/components/CustomButtom";
import Animated, { SlideInRight, SlideOutLeft } from "react-native-reanimated";
import Welcome from "./Welcome";
import Register from "./Register";
import Login from "./Login";
import RegisterFormUser from "@/components/RegisterFormUser";
import RegisterFormEmail from "@/components/RegisterFormEmail";
import Auth from "@/components/Auth";


export default function Intro() {
    const [currentView, setCurrentView] = useState('welcome');
    const [animationDirection, setAnimationDirection] = useState('forward');

    const handleNavigation = (view) => {
        setAnimationDirection('forward');
        setCurrentView(view);
    }

    const handleBack = () => {
        if(currentForm && currentForm.previous){
            setAnimationDirection('backward');
            setCurrentView(currentForm.previous);
        } else{
            setAnimationDirection('backward');
            setCurrentView('welcome');
        }
    }

    useEffect(() => {
        const onBackPress = () => {
            if (currentForm && currentForm.previous) {
                setCurrentView(currentForm.previous);
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

    const registerForms = {
        'register': {
            component: <RegisterFormUser />,
            next: 'register_email',
            previous: 'welcome'
        },
        'register_email': {
            component: <RegisterFormEmail />,
            next: 'auth',
            previous: 'register'
        },
        'auth': {
            component: <Auth />,
            next: 'register_enterChurch',
            previous: 'register_email'
        }
    }

    const currentForm = registerForms[currentView];

    return(
        <View className="flex flex-1 items-center justify-center w-[80%] lg:w-[50%]">
            {currentView === 'welcome' && (<Welcome onNavigate={handleNavigation} />)}
            {currentForm && (<Register key={currentView} direction={animationDirection} onBack={handleBack} onNext={() => setCurrentView(currentForm.next)}>{currentForm.component}</Register>)}
            {currentView === 'login' && (<Login onBack={handleBack} onNavigate={handleNavigation} />)}
        </View>
    );
}