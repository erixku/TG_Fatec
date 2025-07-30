import React from "react";
import { View, TouchableWithoutFeedback, Text} from "react-native";
import "../global.css";
import { TouchableWithoutFeedbackProps } from "react-native/Libraries/Components/Touchable/TouchableWithoutFeedback";

interface CustomButtomProps extends TouchableWithoutFeedbackProps {
    label: string;
}

const CustomButton = ({label}:CustomButtomProps) => {
    return(
        <TouchableWithoutFeedback>
                <View className={"flex items-center justify-center p-2 max-h-10 h-full w-auto rounded-xl bg-blue-700 dark:bg-blue-300 shadow-md shadow-blue-900 dark:shadow-blue-50 active:bg-blue-200 active:scale-110"}>
                    <Text className="text-blue-100 dark:text-slate-900"> {label} </Text>
                </View>
        </TouchableWithoutFeedback>
    )
}

export default CustomButton;