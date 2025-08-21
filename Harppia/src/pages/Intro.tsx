import React, { Component, useEffect, useState } from "react";
import { View, BackHandler } from "react-native";
import Welcome from "./Welcome";
import Register from "./Register";
import Login from "./Login";
import RegisterFormUser from "@/components/login&register/RegisterFormUser";
import RegisterFormEmail from "@/components/login&register/RegisterFormEmail";
import Auth from "@/components/login&register/Auth";
import ResetPassword from "@/components/login&register/ResetPassword";


export default function Intro() {
    const [currentView, setCurrentView] = useState('welcome');
    const [animationDirection, setAnimationDirection] = useState('forward');
    const [activeFlow, setActiveFlow] = useState<'Register'|'Login'|null>(null);

    const handleBack = () => {
        setAnimationDirection('backward');

        requestAnimationFrame(() => {
            if(currentForm && currentForm.previous){
                setCurrentView(currentForm.previous);
            } else{
                setCurrentView('welcome');
            }
        })
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
            next: 'register_auth',
            previous: 'register'
        },
        'register_auth': {
            component: <Auth />,
            next: 'register_enterChurch',
            previous: 'register_email'
        }
    }

    const loginForms = {
        'login_auth': {
            component: <Auth />,
            next: 'reset_password',
            previous: 'login'
        },
        'login_reset_password': {
            component: <ResetPassword />,
            next: null,
            previous: 'login_auth'
        }
    }

    const flows = {
        Register: registerForms,
        Login: loginForms
    }
    const currentForm = activeFlow ? flows[activeFlow][currentView] : null;

    return(
        <View className="flex flex-1 items-center justify-center w-[80%] lg:w-[50%]">
            {currentView === 'welcome' && 
                (<Welcome 
                onRegisterPress={() => {
                    setActiveFlow('Register');
                    setAnimationDirection('forward');
                    setCurrentView('register')
                }}
                onLoginPress={() => {
                    setActiveFlow(null);
                    setAnimationDirection('forward');
                    setCurrentView('login');
                }}/>)
            }

            {currentForm && 
                (<Register 
                    key={currentView} 
                    direction={animationDirection} 
                    onBack={handleBack} 
                    onNext={() => setCurrentView(currentForm.next)}>
                        {currentForm.component}
                </Register>)
            }

            {currentView === 'login' && 
                (<Login 
                    onBack={handleBack} 
                    onForgotPasswordPress={() => {
                        setActiveFlow('Login');
                        setAnimationDirection('forward');
                        setCurrentView('login_auth');
                    }} />)}
        </View>
    );
}