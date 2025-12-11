import React from 'react';
import { View, Text, Modal, Pressable, ScrollView, useColorScheme, KeyboardAvoidingView, Platform } from 'react-native';
import { XMarkIcon } from 'react-native-heroicons/solid';
import { CustomButton } from '@/components/CustomButtom';

interface FormModalProps {
  visible: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
  onSubmit: () => void;
  submitLabel?: string;
  isSubmitting?: boolean;
}

/**
 * Modal padrão do sistema para formulários
 * Usado para avisos, agendamentos e compromissos
 */
export default function FormModal({
  visible,
  onClose,
  title,
  children,
  onSubmit,
  submitLabel = 'Salvar',
  isSubmitting = false,
}: FormModalProps) {
  const colorScheme = useColorScheme();
  const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

  return (
    <Modal visible={visible} transparent animationType="fade">
      <KeyboardAvoidingView 
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
        className="flex-1"
      >
        <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
          {/* Backdrop */}
          <Pressable
            className="absolute inset-0"
            onPress={onClose}
          />

          {/* Modal Content */}
          <View className="w-[95%] max-w-2xl h-[80%] bg-slate-50 dark:bg-slate-700 rounded-xl shadow-xl flex-col">
            {/* Header */}
            <View className="flex-row items-center justify-between p-4 border-b border-slate-200 dark:border-slate-600">
              <Text className="font-nunito-semibold text-2xl dark:text-blue-100 text-slate-900">
                {title}
              </Text>
              <Pressable onPress={onClose} className="p-2 active:scale-110">
                <XMarkIcon size={24} color={baseColor} />
              </Pressable>
            </View>

            {/* Form Content */}
            <ScrollView 
              className="flex-1 p-4"
              showsVerticalScrollIndicator={false}
              contentContainerStyle={{ gap: 16, paddingBottom: 80 }}
              keyboardShouldPersistTaps="handled"
            >
              {children}
            </ScrollView>

            {/* Footer */}
            <View className="flex-row gap-3 p-4 border-t border-slate-200 dark:border-slate-600 justify-end">
              <Pressable
                onPress={onClose}
                disabled={isSubmitting}
                className="px-6 py-3 rounded-xl bg-slate-300 dark:bg-slate-600 active:bg-slate-400 dark:active:bg-slate-500 active:scale-95"
              >
                <Text className="font-nunito-semibold text-lg text-slate-900 dark:text-blue-100">
                  Cancelar
                </Text>
              </Pressable>
              <Pressable
                onPress={onSubmit}
                disabled={isSubmitting}
                className="px-6 py-3 rounded-xl bg-blue-700 dark:bg-blue-300 active:bg-blue-800 dark:active:bg-blue-200 active:scale-95 disabled:opacity-50"
              >
                <Text className="font-nunito-semibold text-lg text-blue-100 dark:text-slate-900">
                  {isSubmitting ? 'Salvando...' : submitLabel}
                </Text>
              </Pressable>
            </View>
          </View>
        </View>
      </KeyboardAvoidingView>
    </Modal>
  );
}
