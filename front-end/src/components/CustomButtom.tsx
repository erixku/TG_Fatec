import React from "react";
import { View, Pressable, PressableProps, Text} from "react-native";
import "../global.css";
import { TouchableWithoutFeedbackProps } from "react-native/Libraries/Components/Touchable/TouchableWithoutFeedback";

interface CustomButtomProps extends PressableProps {
    label: string;
}

export function CustomButton({label, ...rest}:CustomButtomProps) {
    return(
        <Pressable {...rest} className={"flex items-center justify-center p-2 max-h-10 h-10 w-24 rounded-xl bg-blue-700 dark:bg-blue-300 active:bg-blue-800 dark:active:bg-blue-200 active:scale-110"}>
            <Text className="text-blue-100 text-lg dark:text-slate-900"> {label} </Text>
        </Pressable>
    )
}

export function CustomImageButton({label, ...rest}:CustomButtomProps) {

}