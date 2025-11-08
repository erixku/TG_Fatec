import React, { useEffect, useState } from "react";
import { View, Text, Pressable, useColorScheme } from "react-native";
import { Image } from "expo-image";
import { ExclamationCircleIcon } from "react-native-heroicons/solid";
import { GenericMusic } from "@/components/home&others/album/MuiscsItens";
import { itensArtists } from "@/mocks/itensArtists";
import { getAlbumCoverSafe } from "@/services/itunes";

export interface MusicListItemProps {
  music: GenericMusic;
  artists: typeof itensArtists;
  onPress?: () => void;
}

export default function MusicListItem({
  music: initialMusic,
  artists,
  onPress,
}: MusicListItemProps) {
  const [music, setMusic] = useState(initialMusic);
  const colorScheme = useColorScheme();
  const baseColor = colorScheme === 'dark' ? '#dbeafe' : '#0f172a';

  const artistNames = music.artistIds
    ?.map(id => artists.find(artist => artist.id === id)?.name)
    .filter(Boolean)
    .join(", ") || "Artista desconhecido";

    useEffect(() => {
    let mounted = true;

    const fetchCover = async () => {
      if (music.coverUri) return;

      const cover = await getAlbumCoverSafe(artistNames, music.album);
      
      if (mounted) {
        setMusic(prev => ({
          ...prev,
          coverUri: cover ?? "https://via.placeholder.com/600x600.png?text=Sem+Capa"
        }));
      }
    };

    fetchCover();

    return () => { mounted = false; };
  }, [music.coverUri, artistNames, music.album]);
  return (
    <Pressable
      onPress={onPress}
      className="flex-col w-full h-28 p-4 gap-y-3 items-start justify-center rounded-xl bg-slate-200 dark:bg-slate-700"
    >
      <View className="flex-row items-center justify-between gap-x-5 w-full">
        <View className="h-full aspect-square overflow-hidden rounded-xl bg-slate-400">
          {music.coverUri ? (
            <Image
              source={music.coverUri}
              style={{ width: "100%", height: "100%" }}
              contentFit="cover"
              transition={200}
            />
          ) : (
            <View className="flex-1 justify-center items-center">
              <ExclamationCircleIcon size={32} color={baseColor} />
            </View>
          )}
        </View>
        <View className="flex-col flex-1 items-start gap-2">
          <Text className="font-nunito-semibold text-xl dark:text-blue-100 text-slate-900" numberOfLines={1} ellipsizeMode="tail">{music.title}</Text>
          <Text className="font-nunito text-lg text-slate-800 dark:text-blue-200" numberOfLines={1} ellipsizeMode="tail">{artistNames}</Text>
          <Text className="font-nunito text-slate-600 dark:text-slate-300" numberOfLines={2} ellipsizeMode="tail">
            {music.album}
          </Text>
        </View>
      </View>
    </Pressable>
  );
}