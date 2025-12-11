import React from 'react';
import { View } from 'react-native';
import { useForm, Controller } from 'react-hook-form';
import { CustomTextInput } from '@/components/CustomInput';
import FormModal from './FormModal';

interface WarningFormData {
  title: string;
  description: string;
}

interface WarningFormModalProps {
  visible: boolean;
  onClose: () => void;
  onSubmit: (data: WarningFormData) => void | Promise<void>;
}

/**
 * Modal de formulário para criar avisos
 */
export default function WarningFormModal({
  visible,
  onClose,
  onSubmit,
}: WarningFormModalProps) {
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<WarningFormData>({
    defaultValues: {
      title: '',
      description: '',
    },
  });

  const handleClose = () => {
    reset();
    onClose();
  };

  const handleFormSubmit = async (data: WarningFormData) => {
    try {
      await onSubmit(data);
      reset();
      onClose();
    } catch (error) {
      console.error('❌ Erro ao salvar aviso:', error);
    }
  };

  return (
    <FormModal
      visible={visible}
      onClose={handleClose}
      title="Novo Aviso"
      onSubmit={handleSubmit(handleFormSubmit)}
      submitLabel="Publicar"
      isSubmitting={isSubmitting}
    >
      <View className="gap-4">
        <Controller
          control={control}
          name="title"
          rules={{
            required: 'Título é obrigatório',
            minLength: {
              value: 3,
              message: 'Título deve ter pelo menos 3 caracteres',
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Título"
              required
              value={value}
              onChangeText={onChange}
              error={errors.title?.message}
              placeholder="Ex: Reunião importante"
            />
          )}
        />

        <Controller
          control={control}
          name="description"
          rules={{
            required: 'Descrição é obrigatória',
            minLength: {
              value: 10,
              message: 'Descrição deve ter pelo menos 10 caracteres',
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Descrição"
              required
              value={value}
              onChangeText={onChange}
              error={errors.description?.message}
              placeholder="Descreva o aviso..."
              multiline
              numberOfLines={6}
              textAlignVertical="top"
              style={{ minHeight: 120 }}
            />
          )}
        />
      </View>
    </FormModal>
  );
}
