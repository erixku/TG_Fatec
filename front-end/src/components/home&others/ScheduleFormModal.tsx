import React from 'react';
import { View } from 'react-native';
import { useForm, Controller } from 'react-hook-form';
import { CustomTextInput, CustomMaskedInput } from '@/components/CustomInput';
import CustomDatePicker from '@/components/CustomDatePicker';
import FormModal from './FormModal';

interface ScheduleFormData {
  title: string;
  description: string;
  date: Date;
  time: string;
}

interface ScheduleFormModalProps {
  visible: boolean;
  onClose: () => void;
  onSubmit: (data: ScheduleFormData) => void | Promise<void>;
}

/**
 * Modal de formulário para criar agendamentos
 */
export default function ScheduleFormModal({
  visible,
  onClose,
  onSubmit,
}: ScheduleFormModalProps) {
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<ScheduleFormData>({
    defaultValues: {
      title: '',
      description: '',
      date: new Date(),
      time: '',
    },
  });

  const handleClose = () => {
    reset();
    onClose();
  };

  const handleFormSubmit = async (data: ScheduleFormData) => {
    try {
      await onSubmit(data);
      reset();
      onClose();
    } catch (error) {
      console.error('❌ Erro ao salvar agendamento:', error);
    }
  };

  return (
    <FormModal
      visible={visible}
      onClose={handleClose}
      title="Novo Agendamento"
      onSubmit={handleSubmit(handleFormSubmit)}
      submitLabel="Agendar"
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
              placeholder="Ex: Ensaio Geral"
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
              placeholder="Descreva o agendamento..."
              multiline
              numberOfLines={4}
              textAlignVertical="top"
              style={{ minHeight: 100 }}
            />
          )}
        />

        <Controller
          control={control}
          name="date"
          rules={{ required: 'Data é obrigatória' }}
          render={({ field: { onChange, value } }) => (
            <CustomDatePicker
              label="Data"
              required
              value={value}
              onChange={onChange}
              error={errors.date?.message}
            />
          )}
        />

        <Controller
          control={control}
          name="time"
          rules={{
            required: 'Horário é obrigatório',
            validate: (value) => {
              const regex = /^([01]\d|2[0-3]):([0-5]\d)$/;
              return regex.test(value) || 'Horário inválido (use formato HH:MM)';
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomMaskedInput
              label="Horário"
              required
              value={value}
              onChangeText={(formatted) => onChange(formatted)}
              mask="99:99"
              error={errors.time?.message}
              placeholder="Ex: 19:30"
              keyboardType="numeric"
            />
          )}
        />
      </View>
    </FormModal>
  );
}
