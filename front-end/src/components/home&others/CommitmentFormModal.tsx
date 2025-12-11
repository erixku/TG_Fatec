import React from 'react';
import { View } from 'react-native';
import { useForm, Controller } from 'react-hook-form';
import { CustomTextInput, CustomMaskedInput } from '@/components/CustomInput';
import CustomDatePicker from '@/components/CustomDatePicker';
import FormModal from './FormModal';

interface CommitmentFormData {
  title: string;
  description: string;
  date: Date;
  time: string;
  responsible_user: string;
}

interface CommitmentFormModalProps {
  visible: boolean;
  onClose: () => void;
  onSubmit: (data: CommitmentFormData) => void | Promise<void>;
}

/**
 * Modal de formulário para criar compromissos
 */
export default function CommitmentFormModal({
  visible,
  onClose,
  onSubmit,
}: CommitmentFormModalProps) {
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<CommitmentFormData>({
    defaultValues: {
      title: '',
      description: '',
      date: new Date(),
      time: '',
      responsible_user: '',
    },
  });

  const handleClose = () => {
    reset();
    onClose();
  };

  const handleFormSubmit = async (data: CommitmentFormData) => {
    try {
      await onSubmit(data);
      reset();
      onClose();
    } catch (error) {
      console.error('❌ Erro ao salvar compromisso:', error);
    }
  };

  return (
    <FormModal
      visible={visible}
      onClose={handleClose}
      title="Novo Compromisso"
      onSubmit={handleSubmit(handleFormSubmit)}
      submitLabel="Criar"
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
              placeholder="Ex: Ministração Culto de Domingo"
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
              placeholder="Descreva o compromisso..."
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
              placeholder="Ex: 09:30"
              keyboardType="numeric"
            />
          )}
        />

        <Controller
          control={control}
          name="responsible_user"
          rules={{
            required: 'Responsável é obrigatório',
            minLength: {
              value: 3,
              message: 'Nome deve ter pelo menos 3 caracteres',
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Responsável"
              required
              value={value}
              onChangeText={onChange}
              error={errors.responsible_user?.message}
              placeholder="Ex: Líder João"
            />
          )}
        />
      </View>
    </FormModal>
  );
}
