import { useColorScheme } from "react-native";

const colorScheme = useColorScheme();
    
export const baseColor:string = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
export const contrastColor:string = colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8';