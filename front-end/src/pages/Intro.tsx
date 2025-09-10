import React, { Component, useEffect, useState } from "react";
import { View, BackHandler } from "react-native";
import Welcome from "./Welcome";
import Register from "./Register";
import Login from "./Login";
import RegisterFormUser from "@/components/login&register/RegisterFormUser";
import RegisterFormEmail from "@/components/login&register/RegisterFormEmail";
import Auth from "@/components/login&register/Auth";
import ResetPassword from "@/components/login&register/ResetPassword";
import { zodResolver } from "@hookform/resolvers/zod";
import { FormProvider, useForm } from "react-hook-form";
import { RegisterFormData, registerSchema } from "@/schemas/registerSchema";
import { verifyAuthCode } from "@/api/verifyAuthCode";


export default function Intro() {
    const [currentView, setCurrentView] = useState('welcome');
    const [animationDirection, setAnimationDirection] = useState('forward');
    const [activeFlow, setActiveFlow] = useState<'Register'|'Login'|null>(null);

    const methods = useForm<RegisterFormData>({
        resolver: zodResolver(registerSchema),
        defaultValues: {
            name: "",
            socialName: null,
            cpf: "",
            birthday: "",
            gender: "Masculino",
            cep: "",
            street: "",
            homeNumber: "",
            district: "",
            city: "",
            estate: "",
            cellphone: "",
            profilePicture: null,
            email: "",
            confirm_email: "",
            password: "",
            confirm_password: "",
        }
    });

    const {trigger, handleSubmit, formState:{errors}} = methods;

    const onSubmit = (data: RegisterFormData) => {
        console.log("Dados enviados: ", data);
        //chamada da API
    }

    const onError = (errors:any) => {
        console.log("Erros encontrados: " + errors);
    }

    const handleNext = async() => {
        if(!currentForm) return;
        
        console.log("\nCampos que estou passando à trigger: ", currentForm.fields)
        const isValid = await trigger(currentForm.fields);

        if(currentView === 'login_auth'){
            //Coloca a lógica de validação para a confirmação de código
            setCurrentView(currentForm.next)
        }
        
        if(isValid) {
            if(currentForm.next === 'login') {
                console.log("A senha é válida");
                methods.reset();
                setCurrentView("login");
            }
            if(currentForm.next === "register_auth") {
                // console.log("Saindo do e-mail");
                onSubmit(methods.getValues());
                methods.reset()
                setCurrentView(currentForm.next)
            }
        }
    }

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
            previous: 'welcome',
            fields: ["name", "socialName", "cpf", "birthday", "gender", "cep", "street", "homeNumber", "district", "city", "estate", "cellphone", "profilePicture"]
        },
        'register_email': {
            component: <RegisterFormEmail />,
            next: 'register_auth',
            previous: 'register',
            fields: ["email", "confirm_email", "password", "confirm_password"]
        },
        'register_auth': {
            component: <Auth />,
            next: 'register_enterChurch',
            previous: 'register_email'
        }
    }

    const loginForms = {
        'login_auth': {
            component: <Auth/>,
            next: 'login_reset_password',
            previous: 'login'
        },
        'login_reset_password': {
            component: <ResetPassword />,
            next: 'login',
            previous: 'login_auth',
            fields: ["password", "confirm_password"]
        }
    }

    const flows = {
        Register: registerForms,
        Login: loginForms
    }
    const currentForm = activeFlow ? flows[activeFlow][currentView] : null;

    return(
        <View className="flex flex-1 items-center justify-center w-[80%] lg:w-[50%]">
            <FormProvider {...methods}>
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
                        onNext={() => { handleNext(); onError }}>
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
            </FormProvider>
        </View>
    );
}