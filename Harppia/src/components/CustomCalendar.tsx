import React, { useState } from "react";
import { useColorScheme, View } from "react-native";
import DateTimePicker, { DateType, useDefaultClassNames } from "react-native-ui-datepicker";
import dayjs from "dayjs";

interface CustomCalendarProps {
    onDateChange: (date:DateType) => void;
    mode?: "single"|"range"|"multiple";
    date: DateType;
}

export default function CustomCalendar({onDateChange, mode="single", date}: CustomCalendarProps) {
    const colorScheme = useColorScheme();
    const [open, setOpen] = useState(false);
    const defaultClassNames = useDefaultClassNames();
    const colors = {
        primary: colorScheme === 'dark' ? '#93c5fd' : '#1d4ed8',
        secondary: colorScheme === 'dark' ? '#a5b4fc' : '#4338ca',
        background: colorScheme === 'dark' ? '#1e293b' : '#f8fafc',
        component_bg: colorScheme === 'dark' ? '#334155' : '#0f172a',
        contrast: colorScheme === 'dark' ? '#dbeafe' : '#1d4ed8',
    }
    /*
        bg-slate-50: #f8fafc
        bg-slate-200: #e2e8f0
        bg-slate-900: #0f172a
        bg-blue-700: #1d4ed8
        bg-indigo-700: #4338ca
    */

    /*
        bg-slate-800: #1e293b
        bg-slate-700: #334155
        bg-blue-100: #dbeafe
        bg-blue-300: #93c5fd
        bg-indigo-300: #a5b4fc
    */

    return(
        <View className="bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl" >
            <DateTimePicker
                mode="single"
                date={date}
                onChange={(param) => {
                    onDateChange(param.date ?? null);
                }}
                classNames={{
                    ...defaultClassNames,
                    day_label: 'text-slate-900 dark:text-blue-100',
                    weekday_label: 'text-slate-900 dark:text-blue-100',
                    today: 'rounded-3xl border border-2 border-blue-700 dark:border-blue-300',
                    today_label: 'font-extrabold',
                    outside_label: 'text-slate-900/50 dark:text-blue-100/50',
                    selected: 'rounded-3xl bg-indigo-700 dark:bg-indigo-300',
                    selected_label: 'text-blue-100 dark:text-slate-900',
                    month_label: 'text-slate-900 dark:text-blue-100',
                    month_selector_label: 'text-slate-900 dark:text-blue-100',
                    year_label: 'text-slate-900 dark:text-blue-100',
                    year_selector_label: 'text-slate-900 dark:text-blue-100',
                }}
                locale="pt"
                weekdaysFormat="short"
                initialView="year"
                showOutsideDays={true}
            />
        </View>
    )
}
