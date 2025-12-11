import React, { useEffect, useState } from "react";
import { View, Text, ScrollView, FlatList, SectionList } from "react-native";
import ProfileInfo from "./ProfileInfo";
import MinisteryItem from "@/components/church/MinisteryItem";
import { itensArtists } from "@/mocks/itensArtists";
import MusicListItem from "./MusicListItem";
import { getChurches, getMinistries, getUserMinistries, getUser, loadCache, getMusicsByMinistry, getMinistryMembers } from "@/services/localCache";
import dayjs from "dayjs";

interface UserData {
    id: string;
    nome: string;
    nomeSocial?: string;
    email: string;
    telefone: string;
    cpf: string;
    dataNascimento: string;
    sexo: string;
    foto?: string;
    endereco: {
        cep: string;
        uf: string;
        cidade: string;
        bairro: string;
        rua: string;
        numero: string;
        complemento?: string;
    };
}

type ProfileBodyProps = {
    isHidden: boolean;
    user: UserData | null;
}

type MinisterySectionItem = {
    id: string;
    uuid: string;
    nome: string;
    descricao: string;
    foto?: string;
    memberCount: number;
    church: {
        id: string;
        uuid: string;
        nome: string;
        foto?: string;
    } | null;
};

type MusicItem = {
    id: number | string;
    title: string;
    artistIds: number[];
    durationInSeconds: number;
    album?: string;
    durationMinutes: number;
    bpm: number;
    key: string;
    youtubeLink?: string;
    lyricsLink?: string;
    chordsLink?: string;
    createdBy?: string;
    coverUri?: string;
};

type MusicSectionItem = { musics: MusicItem[]; artists: typeof itensArtists };
type SectionItem = MinisterySectionItem | MusicSectionItem;
type SectionData = {
  title: string;
  type: "ministeries" | "musics";
  data: SectionItem[];
};

export default function ProfileBody({isHidden = false, user}: ProfileBodyProps) {
    const [ministeriesWithChurches, setMinisteriesWithChurches] = useState<MinisterySectionItem[]>([]);
    const [userMusics, setUserMusics] = useState<MusicItem[]>([]);

    useEffect(() => {
        loadMinistries();
        loadUserMusics();
    }, [user]);

    const loadMinistries = async () => {
        try {
            if (!user) return;

            const [ministries, churches, cache] = await Promise.all([
                getMinistries(),
                getChurches(),
                loadCache()
            ]);

            console.log('🎵 [ProfileBody] Total de ministérios no cache:', ministries.length);
            console.log('⛪ [ProfileBody] Total de igrejas no cache:', churches.length);

            // Busca IDs dos ministérios onde o usuário é membro
            const userMinistryIds = cache.ministryMembers
                .filter(member => member.idUsuario === user.id)
                .map(member => member.idMinisterio);

            console.log('👥 [ProfileBody] Ministérios do usuário (como membro):', userMinistryIds.length);

            // Filtra ministérios do usuário (onde ele é criador OU membro)
            const userMinistries = ministries.filter(m => 
                m.idCriador === user.id || userMinistryIds.includes(m.id)
            );

            console.log('✅ [ProfileBody] Ministérios filtrados para o usuário:', userMinistries.length);

            const mapped = await Promise.all(userMinistries.map(async (ministry) => {
                const church = churches.find((c) => c.id === ministry.idIgreja);
                const members = await getMinistryMembers(ministry.id);
                const memberCount = members.length;
                
                console.log(`  - Ministério: ${ministry.nome}, Igreja: ${church?.nome || 'Não encontrada'}, Membros: ${memberCount}`);
                console.log(`  - Foto do ministério: ${ministry.foto || 'SEM FOTO'}`);
                
                return {
                    id: ministry.id,
                    uuid: ministry.id,
                    nome: ministry.nome,
                    descricao: ministry.descricao,
                    foto: ministry.foto,
                    memberCount,
                    church: church ? {
                        id: church.id,
                        uuid: church.id,
                        nome: church.nome,
                        foto: church.foto,
                    } : null,
                };
            }));

            setMinisteriesWithChurches(mapped);
        } catch (error) {
            console.error('❌ Erro ao carregar ministérios:', error);
        }
    };

    const loadUserMusics = async () => {
        try {
            if (!user) return;

            const cache = await loadCache();
            
            // Busca todos os ministérios do usuário
            const userMinistryIds = cache.ministryMembers
                .filter(member => member.idUsuario === user.id)
                .map(member => member.idMinisterio);

            const ministries = await getMinistries();
            const allUserMinistryIds = [
                ...ministries.filter(m => m.idCriador === user.id).map(m => m.id),
                ...userMinistryIds
            ];

            console.log('🎵 [ProfileBody] Buscando músicas dos ministérios do usuário:', allUserMinistryIds);

            // Busca todas as músicas de todos os ministérios do usuário
            const allMusicsPromises = allUserMinistryIds.map(id => getMusicsByMinistry(id));
            const allMinistryMusics = await Promise.all(allMusicsPromises);
            const flattenedMusics = allMinistryMusics.flat();

            // Filtra apenas as músicas criadas pelo usuário
            const userCreatedMusics = flattenedMusics.filter(m => m.createdBy === user.id);

            console.log('🎵 [ProfileBody] Total de músicas do usuário:', userCreatedMusics.length);

            // Formata para o formato esperado pelo componente
            const formattedMusics: MusicItem[] = userCreatedMusics.map(m => ({
                id: m.id,
                title: m.title,
                artistIds: [1], // Temporário
                durationInSeconds: m.durationInSeconds,
                album: m.album,
                durationMinutes: m.durationInSeconds / 60,
                bpm: m.bpm,
                key: m.key,
                youtubeLink: m.youtubeLink,
                lyricsLink: m.lyricsLink,
                chordsLink: m.chordsLink,
                createdBy: m.createdBy,
                coverUri: m.albumCover,
            }));

            setUserMusics(formattedMusics);
        } catch (error) {
            console.error('❌ Erro ao carregar músicas do usuário:', error);
        }
    };

    // Formata a data de nascimento para exibição
    const formatDate = (dateString: string) => {
        if (!dateString) return '';
        const formatted = dayjs(dateString).format('DD/MM/YYYY');
        console.log('📅 Formatando data:', dateString, '→', formatted);
        return formatted.replace(/\//g, ''); // Remove barras para usar com máscara
    };

    // Formata o telefone para exibição (retorna apenas números, a máscara é aplicada pelo componente)
    const formatPhone = (phone: string) => {
        if (!phone) return '';
        return phone.replace(/\D/g, '');
    };

    // Formata o sexo para exibição
    const formatSexo = (sexo: string | undefined) => {
        if (!sexo) return 'Não informado';
        if (sexo === 'M') return 'Masculino';
        if (sexo === 'F') return 'Feminino';
        return 'Outro';
    };

    // Formata o CPF removendo caracteres especiais
    const formatCpf = (cpf: string) => {
        if (!cpf) return '';
        return cpf.replace(/\D/g, '');
    };
    
    // Log dos dados do usuário para debug
    useEffect(() => {
        if (user) {
            console.log('📋 ProfileBody - Dados do usuário:', {
                nome: user.nome,
                email: user.email,
                telefone: user.telefone,
                cpf: user.cpf,
                dataNascimento: user.dataNascimento,
                sexo: user.sexo,
                endereco: user.endereco
            });
        }
    }, [user]);
    
    return (
        <SectionList
            ListHeaderComponent={() => (
                <View className="flex-col w-full gap-2 py-2 px-3">
                    <ProfileInfo label={user?.nomeSocial ? "Nome social" : "Nome completo"} value={user?.nomeSocial || user?.nome || 'Não informado'} />
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo 
                            label="Data de nascimento" 
                            value={user?.dataNascimento ? formatDate(user.dataNascimento) : ''} 
                            mask="99/99/9999" 
                            confidential={isHidden} 
                        />
                        <ProfileInfo 
                            label="CPF" 
                            value={user?.cpf ? formatCpf(user.cpf) : ''} 
                            confidential={isHidden} 
                            mask="999.999.999-99"
                        />
                    </View>
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo 
                            label="Telefone" 
                            value={user?.telefone ? formatPhone(user.telefone) : ''} 
                            mask="(99) 99999-9999" 
                            confidential={isHidden}
                        />
                        <ProfileInfo label="Gênero" value={formatSexo(user?.sexo)} />
                    </View>
                    <View className="flex-1 h-0.5 bg-slate-900 dark:bg-blue-100 my-2"/>
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo 
                            label="CEP" 
                            value={user?.endereco?.cep || ''} 
                            mask="99999-999" 
                            confidential={isHidden}
                        />
                        <ProfileInfo 
                            label="Número" 
                            value={user?.endereco?.numero || ''} 
                            confidential={isHidden}
                        />
                    </View>
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo 
                            label="Logradouro" 
                            value={user?.endereco?.rua || ''} 
                            confidential={isHidden}
                        />
                        <ProfileInfo 
                            label="Bairro" 
                            value={user?.endereco?.bairro || ''} 
                            confidential={isHidden}
                        />
                    </View>
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo 
                            label="Cidade" 
                            value={user?.endereco?.cidade || ''} 
                            confidential={isHidden}
                        />
                        <ProfileInfo 
                            label="Estado" 
                            value={user?.endereco?.uf || ''} 
                            confidential={isHidden}
                        />
                    </View>
                    <View className="flex-1 h-0.5 bg-slate-900 dark:bg-blue-100 my-2"/>
                </View>
            )}
            sections={[
                {
                    title: "Igrejas e Ministérios",
                    type: "ministeries",
                    data: ministeriesWithChurches.length > 0 ? ministeriesWithChurches : [],
                },
                {
                    title: "Músicas do usuário",
                    type: "musics",
                    data: userMusics.length > 0 ? [{ musics: userMusics, artists: itensArtists }] : [],
                }
            ] as SectionData[]}
            keyExtractor={(item, index) => "uuid" in item ? item.uuid : `music-section-${index}`}
            renderItem={({item, section}) => {
                if(section.type === "ministeries" && "uuid" in item){
                    return <MinisteryItem ministery={item as MinisterySectionItem}/>;
                }
                if(section.type === "musics" && "musics" in item){
                    return (
                        <View className="gap-3">
                            {item.musics.map((music) => (
                                <MusicListItem
                                key={music.id ?? music.title}
                                music={music}
                                artists={item.artists}
                                />
                            ))}
                        </View>
                    );
                }
                return null;
            }}
            renderSectionHeader={({ section }) => (
                <View className="py-2 px-3">
                    <Text className="text-xl font-nunito-semibold dark:text-blue-100 text-slate-900">
                        {section.title}
                    </Text>
                    {section.type === "ministeries" && ministeriesWithChurches.length === 0 && (
                        <Text className="text-base font-nunito dark:text-blue-100/70 text-slate-900/70 mt-2">
                            Nenhum ministério associado
                        </Text>
                    )}
                    {section.type === "musics" && userMusics.length === 0 && (
                        <Text className="text-base font-nunito dark:text-blue-100/70 text-slate-900/70 mt-2">
                            Nenhuma música cadastrada
                        </Text>
                    )}
                </View>
            )}
            contentContainerClassName="gap-2 px-3"
        />

    )
}