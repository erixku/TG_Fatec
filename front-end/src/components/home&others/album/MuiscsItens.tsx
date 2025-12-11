import React, { useEffect, useMemo, useState } from "react";
import { View, Text, useColorScheme, Pressable, FlatList, Modal, Linking, Alert, ScrollView } from "react-native";
import { Image } from "expo-image";
import { ChevronRightIcon, ExclamationCircleIcon, UsersIcon } from "react-native-heroicons/solid";
import { getAlbumCoverSafe } from "@/services/itunes";
import { loadCache } from "@/services/localCache";

export type GenericMusic = {
  _id?: number;
  _key?: number;
  title: string;
  artistIds?: number[];
  durationInSeconds?: number;
  album: string;
  coverUri?: string;
  bpm?: number;
  key?: string;
  durationMinutes?: number;
  youtubeLink?: string;
  lyricsLink?: string;
  chordsLink?: string;
  createdBy?: string;
};

export type LayoutType = 'list' | 'grid'

type Artists = { id: number; name: string };
type ArtistMap = Record<number, string>;

export interface MusicItensProps {
  musics: GenericMusic[];
  artists: Artists[];
  gallery?: boolean;
  public?: boolean;
  layout?: LayoutType;
  profile?: boolean;
}

export const getArtistNames = (song: GenericMusic, artistsLookupMap: ArtistMap) => {
  if (!song.artistIds?.length) {
    return {
      combined: "Artista Desconhecido",
      mainArtist: "Artista Desconhecido",
      featuredArtists: [] as string[],
    };
  }

  const allNames = song.artistIds.map(id => artistsLookupMap[id]).filter(Boolean);
  const [mainArtist, ...featuredArtists] = allNames;

  return {
    combined: allNames.join(", "),
    mainArtist,
    featuredArtists,
  };
};

export default function MusicItens({ musics, artists, gallery: isGallery = false, public: isPublic = false, layout: layoutProp = 'list', profile: isProfile = false}: MusicItensProps) {
  const [musicList, setMusicList] = useState<GenericMusic[]>([]);
  const [open, setOpen] = useState(false);
  const [selectedMusic, setSelectedMusic] = useState<GenericMusic | null>(null);
  const [creatorName, setCreatorName] = useState<string>('Carregando...');
  const [creatorNamesMap, setCreatorNamesMap] = useState<Record<string, string>>({});
  
  const numColumns = layoutProp === 'list' ? 1 : 3;

  const colorScheme = useColorScheme();
  const baseColor = colorScheme === "dark" ? "#dbeafe" : "#0f172a";

  // Load all creator names when component mounts or musics change
  useEffect(() => {
    const loadCreatorNames = async () => {
      try {
        const cache = await loadCache();
        const namesMap: Record<string, string> = {};
        
        // Get unique creator IDs from musics
        const creatorIds = [...new Set(musics.map(m => m.createdBy).filter(Boolean))];
        
        // For now, we only have the current user in cache
        // In the future, this should load all users from a users collection
        if (cache.user) {
          creatorIds.forEach(id => {
            if (id === cache.user?.id) {
              namesMap[id] = cache.user.nomeSocial || cache.user.nome;
            } else {
              namesMap[id] = 'Usuário desconhecido';
            }
          });
        }
        
        setCreatorNamesMap(namesMap);
        console.log('🎵 Nomes dos criadores carregados:', namesMap);
      } catch (error) {
        console.error('Erro ao carregar nomes dos criadores:', error);
      }
    };
    loadCreatorNames();
  }, [musics]);

  useEffect(() => {
    const loadCreatorName = async () => {
      if (selectedMusic?.createdBy) {
        try {
          const cache = await loadCache();
          const creator = cache.user?.id === selectedMusic.createdBy ? cache.user : null;
          if (creator) {
            setCreatorName(creator.nomeSocial || creator.nome);
          } else {
            setCreatorName('Usuário desconhecido');
          }
        } catch (error) {
          console.error('Erro ao carregar nome do criador:', error);
          setCreatorName('Erro ao carregar');
        }
      }
    };
    loadCreatorName();
  }, [selectedMusic]);

  const artistMap = useMemo<ArtistMap>(() => {
    return artists.reduce((m, a) => { m[a.id] = a.name; return m; }, {} as ArtistMap);
  }, [artists]);

  useEffect(() => {
    let mounted = true;

    const fetchCovers = async () => {
      const updated = await Promise.all(
        musics.map(async (music) => {
          if (music.coverUri) return music;

          const { combined } = getArtistNames(music, artistMap);
          const cover = await getAlbumCoverSafe(combined, music.album);
          return { ...music, coverUri: cover ?? "https://via.placeholder.com/600x600.png?text=Sem+Capa" };
        })
      );

      if (mounted) setMusicList(updated);
    };

    fetchCovers();

    return () => { mounted = false; };
  }, [musics, artistMap]);

  const keyExtractor = (item: GenericMusic, index: number) => String(item._id ?? item._key ?? index);

  const navigateToUrl = async ({ url }) => {
    const supported = await Linking.canOpenURL(url);

    if (supported) {
      await Linking.openURL(url);
    } else {
      Alert.alert(`Não sei como abrir este URL: ${url}`);
    }
  }

  function formatMinutes(durationMinutes: number): string {
    const minutes = Math.floor(durationMinutes);
    const seconds = Math.round((durationMinutes - minutes) * 60);
    return `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;
  }

  const renderItem = ({ item }: { item: GenericMusic }) => {
    const { combined } = getArtistNames(item, artistMap);
    const ownerName = item.createdBy ? (creatorNamesMap[item.createdBy] || 'Carregando...') : 'Desconhecido';

    return (
      <View className={layoutProp==='grid'&&("flex-1 py-4")}>
        {isGallery?(
          <View className="flex-1">
          <Pressable
            onPress={() => { setSelectedMusic(item); setOpen(true); }}
            className="flex-col w-full rounded-xl overflow-hidden pb-1"
            style={{ flexGrow: 1 }}
          >
            <View className="aspect-square overflow-hidden rounded-xl bg-slate-400">
              {item.coverUri ? (
                <Image
                  source={item.coverUri}
                  style={{ width: "100%", height: "100%" }}
                  contentFit="cover"
                  transition={200}
                  onError={(e) => console.warn("Image load error:", e)}
                />
              ) : (
                <View className="flex-1 justify-center items-center">
                  <ExclamationCircleIcon size={32} color={baseColor} />
                </View>
              )}
            </View>

            <View className="flex-col items-center px-2 pt-2">
              <Text className="font-nunito-semibold text-xl dark:text-blue-100 text-slate-900 text-center" numberOfLines={1} ellipsizeMode="tail">
                {item.title}
              </Text>

              <Text className="font-nunito text-lg text-slate-800 dark:text-blue-200 text-center" numberOfLines={1} ellipsizeMode="tail">
                {combined}
              </Text>

              {isPublic && (
                <Text className="font-nunito text-slate-600/70 dark:text-slate-300/70 text-center" numberOfLines={1} ellipsizeMode="tail">
                  {ownerName}
                </Text>
              )}
            </View>
          </Pressable>
        </View>
        ):(
          <Pressable
            onPress={() => { setSelectedMusic(item); setOpen(true); }}
            className="flex-col w-full h-32 p-4 gap-y-3 items-start justify-center rounded-xl bg-slate-200 dark:bg-slate-700"
            >
            <View className="flex-row items-center justify-between gap-x-5 w-full">
                <View className="h-full aspect-square overflow-hidden rounded-xl bg-slate-400">
                  {item.coverUri ? (
                      <Image
                      source={item.coverUri}
                      style={{ width: "100%", height: "100%" }}
                      contentFit="cover"
                      transition={200}
                      onError={(e) => console.warn("Image load error:", e)}
                      />
                  ):(
                      <View className="flex-1 justify-center items-center">
                        <ExclamationCircleIcon size={32} color={baseColor} />
                      </View>
                  )}
                  </View>
      
                  <View className="flex-col flex-1 items-start gap-2">
                    <Text className="font-nunito-semibold text-xl dark:text-blue-100 text-slate-900">{item.title}</Text>
                    <Text className="font-nunito text-lg text-slate-800 dark:text-blue-200" numberOfLines={1} ellipsizeMode="tail">{combined}</Text>
                    <Text className="font-nunito text-slate-600 dark:text-slate-300" numberOfLines={2} ellipsizeMode="tail">
                        {item.album}
                    </Text>
                    {isPublic && (
                      <Text className="font-nunito text-xs text-slate-600/70 dark:text-slate-300/70 text-center" numberOfLines={1} ellipsizeMode="tail">
                        {ownerName}
                      </Text>
                    )}
                </View>
              </View>
          </Pressable>
        )}
      </View>
    );
  };
  
  return (
    <View className="w-full">
      <FlatList
        data={musicList}
        key={layoutProp}
        keyExtractor={keyExtractor}
        renderItem={renderItem}
        contentContainerClassName="gap-2 p-2"
        className="rounded-xl w-full"
        showsVerticalScrollIndicator={false}
        columnWrapperClassName={numColumns > 1 ? "gap-2" : ""}
        numColumns={numColumns}
        ListEmptyComponent={<Text className="text-center py-8 text-slate-500">Carregando músicas...</Text>}
      />

      <Modal visible={open} transparent animationType="fade">
        <View className="flex-1 px-6 bg-slate-900/40 justify-center items-center">
          <Pressable className="absolute inset-0" onPress={() => { setOpen(false); setSelectedMusic(null); }} />
          <View className="w-[90%] h-[75%] py-6 items-start justify-start gap-4 bg-slate-50 dark:bg-slate-700 rounded-xl p-4 shadow-xl">
            {selectedMusic ? (() => {
              const { mainArtist, featuredArtists } = getArtistNames(selectedMusic, artistMap);
              return(
                <>
                  <View className="w-full items-center gap-1">
                    <View className="gap-1 w-[70%] aspect-square items-center">
                      <Image
                        source={selectedMusic.coverUri}
                        style={{ width: "100%", height: "100%", borderRadius: 16 }}
                        contentFit="cover"/>
                    </View>
                    <Text className="font-nunito-light dark:text-blue-100/50 text-slate-900/50">{selectedMusic.album}</Text>
                    <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900">{selectedMusic.title}</Text>
                  </View>
                  <ScrollView className="flex-wrap w-full">
                    <View className="gap-2 items-start">
                      <View>
                        <Text className="font-nunito-light dark:text-blue-100/50 text-slate-900/50">Artista principal</Text>
                        <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900">{mainArtist}</Text>
                      </View>
                      {featuredArtists.length != 0 && (
                        <View>
                          <Text className="font-nunito-light dark:text-blue-100/50 text-slate-900/50">Artistas secundários</Text>
                          <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900">{featuredArtists.join(", ")}</Text>
                        </View>
                      )}
                      <View className="flex-row w-full justify-between pr-3">
                        <View>
                          <Text className="font-nunito-light dark:text-blue-100/50 text-slate-900/50">Duração</Text>
                          <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900">{formatMinutes(selectedMusic.durationMinutes)}</Text>
                        </View>

                        <View>
                          <Text className="font-nunito-light dark:text-blue-100/50 text-slate-900/50">Tom</Text>
                          <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900">{selectedMusic.key}</Text>
                        </View>


                        <View>
                          <Text className="font-nunito-light dark:text-blue-100/50 text-slate-900/50">BPM</Text>
                          <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900">{selectedMusic.bpm}</Text>
                        </View>
                      </View>

                      {selectedMusic.youtubeLink && (
                        <View>
                          <Text className="font-nunito-light dark:text-blue-100/50 text-slate-900/50">Acesso à música</Text>
                          <Pressable className="flex-row w-full justify-between items-center gap-2" onPress={() => navigateToUrl({ url: selectedMusic.youtubeLink })}>
                            <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900 overflow-clip max-w-[75%]" ellipsizeMode="tail" numberOfLines={1} >{selectedMusic.youtubeLink}</Text>
                            <ChevronRightIcon color={baseColor} size={20}/>
                          </Pressable>
                        </View>
                      )}

                      {selectedMusic.lyricsLink && (
                        <View>
                          <Text className="font-nunito-light dark:text-blue-100/50 text-slate-900/50">Acesso à letra</Text>
                          <Pressable className="flex-row w-full justify-between items-center gap-2" onPress={() => navigateToUrl({ url: selectedMusic.lyricsLink })}>
                            <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900 overflow-clip max-w-[75%]" ellipsizeMode="tail" numberOfLines={1} >{selectedMusic.lyricsLink}</Text>
                            <ChevronRightIcon color={baseColor} size={20}/>
                          </Pressable>
                        </View>
                      )}

                      {selectedMusic.chordsLink && (
                        <View>
                          <Text className="font-nunito-light dark:text-blue-100/50 text-slate-900/50">Acesso à partitura</Text>
                          <Pressable className="flex-row w-full justify-between items-center gap-2" onPress={() => navigateToUrl({ url: selectedMusic.chordsLink })}>
                            <Text className="font-nunito text-lg dark:text-blue-100 text-slate-900 overflow-clip max-w-[75%]" ellipsizeMode="tail" numberOfLines={1} >{selectedMusic.chordsLink}</Text>
                            <ChevronRightIcon color={baseColor} size={20}/>
                          </Pressable>
                        </View>
                      )}

                    </View>
                  </ScrollView>
                  <Text className="font-nunito-light mt-2 text-sm dark:text-blue-100/70 text-slate-900/70">Criado por: {creatorName}</Text>
                </>
              )
            }
            )():null}
          </View>
        </View>
      </Modal>
    </View>
  );
}
