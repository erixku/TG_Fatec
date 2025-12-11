import React, { useEffect, useState } from 'react';
import { View, Text, useColorScheme, ScrollView, Alert, Pressable } from 'react-native';
import { useRouter } from 'expo-router';
import { ArrowLeftIcon } from 'react-native-heroicons/solid';
import { FormProvider, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { CustomButton } from '@/components/CustomButtom';
import ProfileSettingsForm from '@/components/home&others/profile/ProfileSettingsForm';
import { getUser, updateUser } from '@/services/localCache';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';

// Schema de validação (sem CPF e dataNascimento)
const profileSettingsSchema = z.object({
    arquivo: z.object({
        caminho: z.string(),
        tipoArquivo: z.string().optional().nullable(),
        mimeType: z.string().optional().nullable(),
        tamanhoEmBytes: z.string().refine((size) => (size ? parseInt(size, 10) : 0) <= 5 * 1024 * 1024, `O tamanho máximo é 5MB.`),
        bucketArquivo: z.object({
            nome: z.string().optional().nullable(),
        }),
    }).optional().nullable(),
    nomeCompleto: z.string().nonempty("Campo obrigatório"),
    nomeCompletoSocial: z.string().optional().nullable().transform(val => val === "" ? null : val),
    sexo: z.enum(["Masculino", "Feminino", "Outro"], {
        message: "Selecione um sexo",
    }),
    telefone: z.string().nonempty("Campo obrigatório"),
    endereco: z.object({
        cep: z.string().length(8, "CEP deve ter 8 dígitos").nonempty("Campo obrigatório"),
        uf: z.string().nonempty("Campo obrigatório"),
        cidade: z.string().nonempty("Campo obrigatório"),
        bairro: z.string().nonempty("Campo obrigatório"),
        rua: z.string().nonempty("Campo obrigatório"),
        numero: z.string().nullable().transform((val) => !val || val.trim() === "" ? "S/N" : val).optional(),
        complemento: z.string().max(30, "O complemento deve ter no máximo 30 caracteres").optional().nullable().transform(val => !val || val === "" ? null : val),
    }),
});

export type ProfileSettingsFormData = z.infer<typeof profileSettingsSchema>;

export default function ProfileSettings() {
    const colorScheme = useColorScheme();
    const router = useRouter();
    const [isLoading, setIsLoading] = useState(false);

    const methods = useForm<ProfileSettingsFormData>({
        resolver: zodResolver(profileSettingsSchema),
        defaultValues: {
            nomeCompleto: '',
            nomeCompletoSocial: '',
            sexo: 'Masculino',
            telefone: '',
            endereco: {
                cep: '',
                uf: '',
                cidade: '',
                bairro: '',
                rua: '',
                numero: '',
                complemento: '',
            },
        },
    });

    const { reset } = methods;

    useEffect(() => {
        loadUserData();
    }, []);

    const loadUserData = async () => {
        try {
            const user = await getUser();

            if (!user) {
                Alert.alert('Erro', 'Dados do usuário não encontrados');
                router.back();
                return;
            }

            console.log('📝 [ProfileSettings] Dados do usuário carregados:', user);

            // Mapeamento do sexo
            let sexoFormatted: "Masculino" | "Feminino" | "Outro" = "Masculino";
            if (user.sexo === 'M') sexoFormatted = "Masculino";
            else if (user.sexo === 'F') sexoFormatted = "Feminino";
            else sexoFormatted = "Outro";

            reset({
                arquivo: user.foto ? {
                    caminho: user.foto,
                    tipoArquivo: 'image/jpeg',
                    mimeType: 'image/jpeg',
                    tamanhoEmBytes: '0',
                    bucketArquivo: { nome: 'profile.jpg' }
                } : undefined,
                nomeCompleto: user.nome,
                nomeCompletoSocial: user.nomeSocial || '',
                sexo: sexoFormatted,
                telefone: user.telefone,
                endereco: {
                    cep: user.endereco.cep,
                    uf: user.endereco.uf,
                    cidade: user.endereco.cidade,
                    bairro: user.endereco.bairro,
                    rua: user.endereco.rua,
                    numero: user.endereco.numero,
                    complemento: user.endereco.complemento || '',
                },
            });
        } catch (error) {
            console.error('❌ Erro ao carregar dados do usuário:', error);
            Alert.alert('Erro', 'Não foi possível carregar os dados do usuário');
        }
    };

    const handleSave = async (data: ProfileSettingsFormData) => {
        setIsLoading(true);
        try {
            console.log('💾 [ProfileSettings] Salvando alterações:', data);

            // Mapeia sexo para letra
            const sexoMapped = data.sexo === 'Masculino' ? 'M' : data.sexo === 'Feminino' ? 'F' : 'O';

            const user = await getUser();
            if (!user) {
                throw new Error('Usuário não encontrado');
            }

            await updateUser({
                nome: data.nomeCompleto,
                nomeSocial: data.nomeCompletoSocial || undefined,
                sexo: sexoMapped,
                telefone: data.telefone,
                foto: data.arquivo?.caminho || user.foto,
                endereco: {
                    cep: data.endereco.cep,
                    uf: data.endereco.uf,
                    cidade: data.endereco.cidade,
                    bairro: data.endereco.bairro,
                    rua: data.endereco.rua,
                    numero: data.endereco.numero || 'S/N',
                    complemento: data.endereco.complemento || undefined,
                },
            });

            Alert.alert('Sucesso', 'Dados atualizados com sucesso!', [
                { text: 'OK', onPress: () => router.back() }
            ]);
        } catch (error: any) {
            console.error('❌ Erro ao salvar alterações:', error);
            Alert.alert('Erro', error?.message || 'Não foi possível salvar as alterações');
        } finally {
            setIsLoading(false);
        }
    };

    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

    return (
        <View className="flex-1 items-center justify-center bg-slate-100 dark:bg-slate-800 p-6">
            {/* Cartão flutuante centralizado com 80% da altura */}
            <View className="bg-slate-50 dark:bg-slate-700 rounded-xl shadow-md p-6 w-full h-[80%]">
                {/* Cabeçalho */}
                <View className="flex-row items-center mb-6">
                    <Pressable onPress={() => router.back()} className="mr-4">
                        <ArrowLeftIcon color={baseColor} size={24} />
                    </Pressable>
                    <Text className="font-nunito-bold text-2xl text-slate-900 dark:text-blue-100">
                        Configurações de Perfil
                    </Text>
                </View>

                {/* ScrollView do Formulário */}
                <ScrollView 
                    className="flex-1"
                    showsVerticalScrollIndicator={false}
                    contentContainerStyle={{ paddingBottom: 20 }}
                >
                    <FormProvider {...methods}>
                        <ProfileSettingsForm />
                    </FormProvider>
                </ScrollView>

                {/* Botão de Salvar Centralizado */}
                <View className="items-center mt-6">
                    <CustomButton
                        label={isLoading ? "Salvando..." : "Salvar Alterações"}
                        onPress={methods.handleSubmit(handleSave)}
                        disabled={isLoading}
                    />
                </View>
            </View>
        </View>
    );
}
