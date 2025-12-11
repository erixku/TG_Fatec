import React, { useState, useEffect, useRef } from 'react';
import { View, Text, useColorScheme, Pressable, Vibration, TextInput } from 'react-native';
import { PlayIcon, PauseIcon, MinusIcon, PlusIcon, ArrowLeftIcon } from 'react-native-heroicons/solid';
import { useRouter, Link } from 'expo-router';

function MetronomePage() {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const [bpm, setBpm] = useState(120); // BPM padrão
    const [isPlaying, setIsPlaying] = useState(false);
    const [currentBeat, setCurrentBeat] = useState(0);
    const intervalRef = useRef<NodeJS.Timeout | null>(null);

    // Calcula o intervalo em ms baseado no BPM
    const getInterval = () => {
        return (60 / bpm) * 1000;
    };

    // Função para tocar o metrônomo
    const tick = () => {
        // Vibra o celular
        Vibration.vibrate(50); // Vibração de 50ms
        
        // Atualiza o beat visual
        setCurrentBeat(prev => (prev + 1) % 4);
    };

    // Inicia/para o metrônomo
    useEffect(() => {
        if (isPlaying) {
            // Toca imediatamente ao iniciar
            tick();
            
            // Configura o intervalo
            intervalRef.current = setInterval(tick, getInterval());
        } else {
            // Para o metrônomo
            if (intervalRef.current) {
                clearInterval(intervalRef.current);
                intervalRef.current = null;
            }
            setCurrentBeat(0);
        }

        return () => {
            if (intervalRef.current) {
                clearInterval(intervalRef.current);
            }
        };
    }, [isPlaying, bpm]);

    const togglePlay = () => {
        setIsPlaying(!isPlaying);
    };

    const increaseBpm = () => {
        if (bpm < 300) setBpm(bpm + 1);
    };

    const decreaseBpm = () => {
        if (bpm > 30) setBpm(bpm - 1);
    };

    return (
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800 p-6">
            {/* Cartão flutuante centralizado com 80% da altura */}
            <View className="bg-slate-50 dark:bg-slate-700 rounded-xl shadow-md p-6 w-full h-[80%]">
                {/* Cabeçalho */}
                <View className="flex-row items-center justify-between mb-6">
                    <View className="flex-row items-center flex-1">
                        <Link href="/homeMenu/tools" asChild>
                            <Pressable className="mr-4">
                                <ArrowLeftIcon color={baseColor} size={24} />
                            </Pressable>
                        </Link>
                        <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                            Metrônomo
                        </Text>
                    </View>
                </View>

                {/* Display BPM */}
                <View className="items-center mb-8">
                    <Text className="font-nunito-bold text-7xl text-slate-900 dark:text-blue-100">
                        {bpm}
                    </Text>
                    <Text className="font-nunito-semibold text-xl text-slate-600 dark:text-blue-200 mt-2">
                        BPM
                    </Text>
                </View>

                {/* Indicador visual de batida */}
                <View className="flex-row justify-center gap-3 mb-8">
                    {[0, 1, 2, 3].map((beat) => (
                        <View
                            key={beat}
                            className={`w-4 h-4 rounded-full ${
                                isPlaying && currentBeat === beat
                                    ? 'bg-blue-500'
                                    : 'bg-slate-400 dark:bg-slate-600'
                            }`}
                        />
                    ))}
                </View>

                {/* Controles de BPM */}
                <View className="mb-8">
                    <View className="flex-row items-center justify-center gap-6 mb-4">
                        <Pressable
                            onPress={decreaseBpm}
                            className="bg-slate-300 dark:bg-slate-600 p-4 rounded-full"
                        >
                            <MinusIcon size={24} color={baseColor} />
                        </Pressable>

                        {/* Input de BPM */}
                        <View className="flex-1 mx-4">
                            <TextInput
                                value={bpm.toString()}
                                onChangeText={(text) => {
                                    const num = parseInt(text) || 30;
                                    if (num >= 30 && num <= 300) {
                                        setBpm(num);
                                    }
                                }}
                                keyboardType="number-pad"
                                className="bg-slate-200 dark:bg-slate-600 text-center text-2xl font-nunito-bold text-slate-900 dark:text-blue-100 p-3 rounded-xl"
                                maxLength={3}
                            />
                        </View>

                        <Pressable
                            onPress={increaseBpm}
                            className="bg-slate-300 dark:bg-slate-600 p-4 rounded-full"
                        >
                            <PlusIcon size={24} color={baseColor} />
                        </Pressable>
                    </View>

                    {/* Faixa de BPM */}
                    <View className="flex-row justify-between px-4">
                        <Text className="font-nunito text-sm text-slate-600 dark:text-blue-200">30</Text>
                        <Text className="font-nunito text-sm text-slate-600 dark:text-blue-200">300</Text>
                    </View>
                </View>

                {/* Presets de BPM */}
                <View className="flex-row flex-wrap justify-center gap-3 mb-8">
                    {[60, 80, 100, 120, 140, 160].map((preset) => (
                        <Pressable
                            key={preset}
                            onPress={() => setBpm(preset)}
                            className={`px-4 py-2 rounded-full ${
                                bpm === preset
                                    ? 'bg-blue-500'
                                    : 'bg-slate-300 dark:bg-slate-600'
                            }`}
                        >
                            <Text
                                className={`font-nunito-semibold ${
                                    bpm === preset
                                        ? 'text-white'
                                        : 'text-slate-900 dark:text-blue-100'
                                }`}
                            >
                                {preset}
                            </Text>
                        </Pressable>
                    ))}
                </View>

                {/* Botão Play/Pause */}
                <Pressable
                    onPress={togglePlay}
                    className="bg-blue-500 dark:bg-blue-700 p-6 rounded-full items-center justify-center self-center w-24 h-24"
                >
                    {isPlaying ? (
                        <PauseIcon size={40} color="#fff" />
                    ) : (
                        <PlayIcon size={40} color="#fff" />
                    )}
                </Pressable>
            </View>
        </View>
    );
}

export default MetronomePage;
