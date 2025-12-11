import React from 'react';
import { View } from 'react-native';
import { useForm, Controller } from 'react-hook-form';
import { CustomTextInput, CustomMaskedInput } from '@/components/CustomInput';
import FormModal from './FormModal';

interface MusicFormData {
  title: string;
  album: string;
  artist: string;
  duration: string; // formato MM:SS
  key: string;
  bpm: string;
  youtubeLink?: string;
  lyricsLink?: string;
  chordsLink?: string;
}

interface MusicFormModalProps {
  visible: boolean;
  onClose: () => void;
  onSubmit: (data: MusicFormData) => void | Promise<void>;
}

/**
 * Modal de formulário para cadastrar músicas
 * Campos obrigatórios: título, álbum, artista, duração, tom, BPM
 * Campos opcionais: links (YouTube, letra, cifra)
 * A imagem é capturada automaticamente via iTunes API
 */
export default function MusicFormModal({
  visible,
  onClose,
  onSubmit,
}: MusicFormModalProps) {
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<MusicFormData>({
    defaultValues: {
      title: '',
      album: '',
      artist: '',
      duration: '',
      key: '',
      bpm: '',
      youtubeLink: '',
      lyricsLink: '',
      chordsLink: '',
    },
  });

  const handleClose = () => {
    reset();
    onClose();
  };

  const handleFormSubmit = async (data: MusicFormData) => {
    try {
      await onSubmit(data);
      reset();
      onClose();
    } catch (error) {
      console.error('❌ Erro ao cadastrar música:', error);
    }
  };

  return (
    <FormModal
      visible={visible}
      onClose={handleClose}
      title="Nova Música"
      onSubmit={handleSubmit(handleFormSubmit)}
      submitLabel="Cadastrar"
      isSubmitting={isSubmitting}
    >
      <View className="gap-4">
        <Controller
          control={control}
          name="title"
          rules={{
            required: 'Título da música é obrigatório',
            minLength: {
              value: 2,
              message: 'Título deve ter pelo menos 2 caracteres',
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Título da Música"
              required
              value={value}
              onChangeText={onChange}
              error={errors.title?.message}
              placeholder="Ex: Quão Grande É o Meu Deus"
            />
          )}
        />

        <Controller
          control={control}
          name="album"
          rules={{
            required: 'Álbum é obrigatório',
            minLength: {
              value: 2,
              message: 'Nome do álbum deve ter pelo menos 2 caracteres',
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Álbum"
              required
              value={value}
              onChangeText={onChange}
              error={errors.album?.message}
              placeholder="Ex: Quão Grande É o Meu Deus"
            />
          )}
        />

        <Controller
          control={control}
          name="artist"
          rules={{
            required: 'Artista principal é obrigatório',
            minLength: {
              value: 2,
              message: 'Nome do artista deve ter pelo menos 2 caracteres',
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Artista Principal"
              required
              value={value}
              onChangeText={onChange}
              error={errors.artist?.message}
              placeholder="Ex: Soraya Moraes"
            />
          )}
        />

        <Controller
          control={control}
          name="duration"
          rules={{
            required: 'Duração é obrigatória',
            validate: (value) => {
              const regex = /^([0-9]|[0-5][0-9]):([0-5][0-9])$/;
              return regex.test(value) || 'Duração inválida (use formato MM:SS)';
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomMaskedInput
              label="Duração"
              required
              value={value}
              onChangeText={(formatted) => onChange(formatted)}
              mask="99:99"
              error={errors.duration?.message}
              placeholder="Ex: 05:15"
              keyboardType="numeric"
            />
          )}
        />

        <Controller
          control={control}
          name="key"
          rules={{
            required: 'Tom é obrigatório',
            minLength: {
              value: 1,
              message: 'Tom deve ter pelo menos 1 caractere',
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Tom"
              required
              value={value}
              onChangeText={onChange}
              error={errors.key?.message}
              placeholder="Ex: G major, E minor, C#"
            />
          )}
        />

        <Controller
          control={control}
          name="bpm"
          rules={{
            required: 'BPM é obrigatório',
            validate: (value) => {
              const num = parseInt(value);
              return (!isNaN(num) && num > 0 && num <= 300) || 'BPM deve ser entre 1 e 300';
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="BPM (Batidas Por Minuto)"
              required
              value={value}
              onChangeText={onChange}
              error={errors.bpm?.message}
              placeholder="Ex: 72"
              keyboardType="numeric"
            />
          )}
        />

        <Controller
          control={control}
          name="youtubeLink"
          rules={{
            validate: (value) => {
              if (!value) return true;
              const regex = /^(https?:\/\/)?(www\.)?(youtube\.com|youtu\.be)\/.+$/;
              return regex.test(value) || 'Link do YouTube inválido';
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Link do YouTube"
              value={value}
              onChangeText={onChange}
              error={errors.youtubeLink?.message}
              placeholder="https://www.youtube.com/watch?v=..."
            />
          )}
        />

        <Controller
          control={control}
          name="lyricsLink"
          rules={{
            validate: (value) => {
              if (!value) return true;
              const regex = /^https?:\/\/.+$/;
              return regex.test(value) || 'Link inválido (deve começar com http:// ou https://)';
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Link da Letra"
              value={value}
              onChangeText={onChange}
              error={errors.lyricsLink?.message}
              placeholder="https://www.letras.mus.br/..."
            />
          )}
        />

        <Controller
          control={control}
          name="chordsLink"
          rules={{
            validate: (value) => {
              if (!value) return true;
              const regex = /^https?:\/\/.+$/;
              return regex.test(value) || 'Link inválido (deve começar com http:// ou https://)';
            },
          }}
          render={({ field: { onChange, value } }) => (
            <CustomTextInput
              label="Link da Cifra"
              value={value}
              onChangeText={onChange}
              error={errors.chordsLink?.message}
              placeholder="https://www.cifraclub.com.br/..."
            />
          )}
        />
      </View>
    </FormModal>
  );
}
