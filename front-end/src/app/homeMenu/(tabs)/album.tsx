import React, { useState, useEffect, useMemo } from "react";
import { View, Text, useColorScheme, Button, Pressable } from "react-native";
import AnimatedScreenWrapper from "../../../components/home&others/AnimatedScreenWrapper";
import MusicItens, { LayoutType } from "@/components/home&others/album/MuiscsItens";
import { itensArtists } from "@/mocks/itensArtists";
import { ChevronRightIcon, UserIcon, UsersIcon, Squares2X2Icon, ListBulletIcon, FunnelIcon, PlusIcon } from "react-native-heroicons/solid";
import SortPopUp from "@/components/home&others/SortPopUp";
import MusicFormModal from "@/components/home&others/MusicFormModal";
import MusicFilterModal, { MusicFilterOptions } from "@/components/home&others/MusicFilterModal";
import { getAlbumCover } from "@/api/getAlbumCover";
import { addMusic, getUser, getUserMinistries, getMusicsByMinistry } from "@/services/localCache";

type MusicType = {
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
};

export default function HomePage() {
    const colorScheme = useColorScheme();
    const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';
    const [isGallery, setIsGallery] = useState<boolean>(false);
    const [isPublic, setIsPublic] = useState<boolean>(false);
    const [layout, setLayout] = useState<LayoutType>('list');
    const [isSortVisible, setIsSortVisible] = useState<boolean>(false);
    const [sortOption, setSortOption] = useState<string>('Mais Recentes');
    const [showMusicModal, setShowMusicModal] = useState<boolean>(false);
    const [showFilterModal, setShowFilterModal] = useState<boolean>(false);
    const [ministryId, setMinistryId] = useState<string | null>(null);
    const [musics, setMusics] = useState<MusicType[]>([]);
    const [allMusics, setAllMusics] = useState<MusicType[]>([]);
    const [activeFilters, setActiveFilters] = useState<MusicFilterOptions>({});

    useEffect(() => {
        loadMusicData();
    }, []);

    const loadMusicData = async () => {
        try {
            const user = await getUser();
            if (!user) {
                console.log('⚠️ Usuário não encontrado');
                return;
            }

            const userMinistries = await getUserMinistries(user.id);
            if (userMinistries.length === 0) {
                console.log('⚠️ Usuário não tem ministérios');
                return;
            }

            // Pega o primeiro ministério do usuário
            const currentMinistryId = userMinistries[0].idMinisterio;
            setMinistryId(currentMinistryId);
            console.log('🎵 Ministério carregado para músicas:', currentMinistryId);

            // Carrega músicas do ministério
            const musicData = await getMusicsByMinistry(currentMinistryId);
            
            // Converte para formato compatível com MusicItens
            const formattedMusics: MusicType[] = musicData.map((m) => ({
                id: m.id,
                title: m.title,
                artistIds: [1], // Temporário, até ter sistema de artistas
                durationInSeconds: m.durationInSeconds,
                album: m.album,
                durationMinutes: m.durationInSeconds / 60,
                bpm: m.bpm,
                key: m.key,
                youtubeLink: m.youtubeLink,
                lyricsLink: m.lyricsLink,
                chordsLink: m.chordsLink,
                createdBy: m.createdBy,
            }));

            setAllMusics(formattedMusics);
            setMusics(formattedMusics);
            console.log('🎵 Músicas carregadas:', formattedMusics.length);
        } catch (error) {
            console.error('❌ Erro ao carregar músicas:', error);
        }
    };

    // Get unique values for filter dropdowns
    const availableAlbums = useMemo(() => {
        const albums = allMusics.map(m => m.album).filter((a): a is string => !!a);
        return [...new Set(albums)];
    }, [allMusics]);

    const availableKeys = useMemo(() => {
        const keys = allMusics.map(m => m.key).filter(Boolean);
        return [...new Set(keys)];
    }, [allMusics]);

    // Apply filters when activeFilters or allMusics change
    useEffect(() => {
        applyFiltersAndSort();
    }, [activeFilters, allMusics, sortOption]);

    const applyFiltersAndSort = () => {
        let filtered = [...allMusics];

        // Filter by creator
        if (activeFilters.createdBy) {
            filtered = filtered.filter(m => m.createdBy === activeFilters.createdBy);
        }

        // Filter by album
        if (activeFilters.album) {
            filtered = filtered.filter(m => m.album === activeFilters.album);
        }

        // Filter by key
        if (activeFilters.key) {
            filtered = filtered.filter(m => m.key === activeFilters.key);
        }

        // Filter by BPM range
        if (activeFilters.minBpm !== undefined) {
            filtered = filtered.filter(m => m.bpm >= activeFilters.minBpm!);
        }
        if (activeFilters.maxBpm !== undefined) {
            filtered = filtered.filter(m => m.bpm <= activeFilters.maxBpm!);
        }

        // Apply sorting
        filtered.sort((a, b) => {
            switch (sortOption) {
                case 'Mais Recentes':
                    // Sort by creation date (newest first)
                    return new Date(b.createdBy || '').getTime() - new Date(a.createdBy || '').getTime();
                
                case 'Mais Antigos':
                    // Sort by creation date (oldest first)
                    return new Date(a.createdBy || '').getTime() - new Date(b.createdBy || '').getTime();
                
                case 'A-Z':
                    // Sort by title alphabetically
                    return a.title.localeCompare(b.title);
                
                case 'Z-A':
                    // Sort by title reverse alphabetically
                    return b.title.localeCompare(a.title);
                
                default:
                    return 0;
            }
        });

        setMusics(filtered);
        console.log('🎵 Filtros e ordenação aplicados:', { filters: activeFilters, sort: sortOption, result: filtered.length });
    };

    const handleApplyFilters = (filters: MusicFilterOptions) => {
        setActiveFilters(filters);
    };

    const generateId = () => Math.random().toString(36).substr(2, 9);

    const handleCreateMusic = async (data: {
        title: string;
        album: string;
        artist: string;
        duration: string;
        key: string;
        bpm: string;
        youtubeLink?: string;
        lyricsLink?: string;
        chordsLink?: string;
    }) => {
        try {
            if (!ministryId) {
                console.error('❌ Ministério não definido');
                return;
            }

            const user = await getUser();
            if (!user) {
                console.error('❌ Usuário não encontrado');
                return;
            }

            // Converter duração MM:SS para segundos
            const [minutes, seconds] = data.duration.split(':').map(Number);
            const durationInSeconds = (minutes * 60) + seconds;

            // Buscar capa do álbum via iTunes API
            let albumCover: string | undefined;
            try {
                const albumData = await getAlbumCover(data.title, data.artist);
                albumCover = albumData?.artworkUrl100;
                console.log('🎨 Capa do álbum encontrada:', albumCover);
            } catch (error) {
                console.warn('⚠️ Não foi possível buscar capa do álbum:', error);
            }

            const musicId = generateId();
            await addMusic({
                id: musicId,
                idMinisterio: ministryId,
                title: data.title,
                artist: data.artist,
                album: data.album,
                durationInSeconds,
                bpm: parseInt(data.bpm),
                key: data.key,
                albumCover,
                youtubeLink: data.youtubeLink,
                lyricsLink: data.lyricsLink,
                chordsLink: data.chordsLink,
                createdBy: user.id,
                createdAt: new Date().toISOString(),
            });

            console.log('✅ Música cadastrada com sucesso');
            await loadMusicData();
            setShowMusicModal(false);
        } catch (error) {
            console.error('❌ Erro ao cadastrar música:', error);
        }
    };
    
    return (
        <AnimatedScreenWrapper>
            <View className="flex-col flex-1 items-center mt-2 bg-slate-300 dark:bg-slate-800">
                <View className="flex-1 w-full items-center pb-28">
                    <View className="flex py-10 h-full w-[90%] lg:w-[50%] justify-center gap-y-4 rounded-xl">
                        <View className=" flex-1 flex-col gap-y-2">
                            <View className="flex-row justify-between">
                                <Pressable className="flex-row items-center gap-x-2" onPress={() => setIsSortVisible(true)}>
                                    <Text className="text-2xl font-nunito-semibold dark:text-blue-100 text-slate-900">{sortOption}</Text>
                                    <ChevronRightIcon size={20} color={baseColor} />
                                </Pressable>
                                <View className="flex-row items-center gap-x-2">
                                    <Pressable onPress={() => setShowMusicModal(true)}>
                                        <PlusIcon size={20} color={baseColor} />
                                    </Pressable>
                                    <Pressable onPress={() => setShowFilterModal(true)}>
                                        <FunnelIcon color={baseColor} size={20} />
                                    </Pressable>
                                    <Pressable onPress={() => setIsPublic(!isPublic)}>
                                        {isPublic ? <UsersIcon color={baseColor} size={20} /> : <UserIcon color={baseColor} size={20} />}
                                    </Pressable>
                                    <Pressable onPress={() => {
                                        setIsGallery(!isGallery);
                                        if (layout === 'list') setLayout('grid');
                                        else setLayout('list');
                                    } }>
                                        {isGallery ? <Squares2X2Icon color={baseColor} size={20} /> : <ListBulletIcon color={baseColor} size={20} />}
                                    </Pressable>
                                </View>
                            </View>
                            {musics.length === 0 ? (
                                <View className="flex-1 items-center justify-center p-8">
                                    <Text className="text-xl font-nunito-semibold text-center dark:text-blue-100 text-slate-900">
                                        Nenhuma música cadastrada ainda
                                    </Text>
                                </View>
                            ) : (
                                <MusicItens musics={musics} artists={itensArtists} public={isPublic} gallery={isGallery} layout={layout}/>
                            )}
                        </View>
                    </View>
                </View>
            </View>
            <SortPopUp 
                visible={isSortVisible}
                options={['Mais Recentes', 'Mais Antigos', 'A-Z', 'Z-A']}
                onClose={() => setIsSortVisible(false)}
                onSelect={(option:string) => {
                    setSortOption(option);
                    setIsSortVisible(false);
                    console.log('Opção de ordenação selecionada:', option);
                }}
            />

            {/* Modal de formulário de música */}
            <MusicFormModal
                visible={showMusicModal}
                onClose={() => setShowMusicModal(false)}
                onSubmit={handleCreateMusic}
            />

            {/* Modal de filtros */}
            <MusicFilterModal
                visible={showFilterModal}
                onClose={() => setShowFilterModal(false)}
                onApplyFilters={handleApplyFilters}
                ministryId={ministryId}
                availableAlbums={availableAlbums}
                availableKeys={availableKeys}
            />
        </AnimatedScreenWrapper>
    );
}