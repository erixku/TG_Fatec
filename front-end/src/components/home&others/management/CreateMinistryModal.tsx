import React from 'react';
import { View, Text, Modal, Pressable, useColorScheme, Alert } from 'react-native';
import { XMarkIcon } from 'react-native-heroicons/solid';
import { FormProvider, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { CustomButton } from '@/components/CustomButtom';
import { registerMinisterySchema, RegisterMinisteryFormData } from '@/schemas/registerMinisterySchema';
import RegisterFormMinistery from '@/components/church/RegisterFormMinistery';
import { addMinistry, generateId, generateMinistryCode, loadCache, addMinistryMember } from '@/services/localCache';

interface CreateMinistryModalProps {
    visible: boolean;
    onClose: () => void;
    onMinistryCreated: () => void;
    churchId: string | null;
}

export default function CreateMinistryModal({ 
    visible, 
    onClose, 
    onMinistryCreated,
    churchId 
}: CreateMinistryModalProps) {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    const methods = useForm<RegisterMinisteryFormData>({
        resolver: zodResolver(registerMinisterySchema),
        defaultValues: {
            nome: '',
            descricao: '',
        },
    });

    const { handleSubmit, reset } = methods;

    const onSubmit = async (data: RegisterMinisteryFormData) => {
        try {
            if (!churchId) {
                Alert.alert('Erro', 'Igreja não encontrada');
                return;
            }

            const cache = await loadCache();
            const user = cache.user;

            if (!user) {
                Alert.alert('Erro', 'Usuário não encontrado');
                return;
            }

            const newMinistry = {
                id: generateId(),
                idCriador: user.id,
                idIgreja: churchId,
                nome: data.nome,
                descricao: data.descricao,
                codigo: generateMinistryCode(),
                foto: data.arquivo?.caminho,
                createdAt: new Date().toISOString(),
            };

            await addMinistry(newMinistry);

            // Adiciona o criador como líder automaticamente
            await addMinistryMember({
                id: generateId(),
                idUsuario: user.id,
                idMinisterio: newMinistry.id,
                papel: 'lider',
                dataEntrada: new Date().toISOString(),
            });

            Alert.alert('Sucesso', 'Ministério criado com sucesso!');
            reset();
            onMinistryCreated();
        } catch (error: any) {
            console.error('Erro ao criar ministério:', error);
            Alert.alert('Erro', error.message || 'Não foi possível criar o ministério');
        }
    };

    return (
        <Modal
            visible={visible}
            transparent
            animationType="fade"
            onRequestClose={onClose}
        >
            <View className="flex-1 bg-slate-900/40 justify-center items-center p-6">
                <View 
                    className="bg-slate-50 dark:bg-slate-700 rounded-xl p-6 w-full max-w-md max-h-[80%]"
                >
                    {/* Cabeçalho */}
                    <View className="flex-row items-center justify-between mb-6">
                        <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                            Criar Ministério
                        </Text>
                        <Pressable onPress={onClose}>
                            <XMarkIcon color={baseColor} size={24} />
                        </Pressable>
                    </View>

                    {/* Formulário */}
                    <FormProvider {...methods}>
                        <RegisterFormMinistery />
                    </FormProvider>

                    {/* Botões */}
                    <View className="flex-row gap-3 justify-end mt-6">
                        <Pressable
                            onPress={onClose}
                            className="px-4 py-2 rounded-xl bg-slate-200 dark:bg-slate-600"
                        >
                            <Text className="font-nunito-semibold text-slate-900 dark:text-blue-100">
                                Cancelar
                            </Text>
                        </Pressable>
                        <CustomButton
                            label="Criar"
                            onPress={handleSubmit(onSubmit)}
                        />
                    </View>
                </View>
            </View>
        </Modal>
    );
}
