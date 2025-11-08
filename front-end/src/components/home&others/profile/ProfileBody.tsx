import React from "react";
import { View, Text, ScrollView, FlatList, SectionList } from "react-native";
import ProfileInfo from "./ProfileInfo";
import MinisteryItem from "@/components/church/MinisteryItem";
import { itensMinistery, MinisteryType } from "@/mocks/itensMinistery";
import { itensChurch, ChurchType } from "@/mocks/itensChurch";
import { itensArtists } from "@/mocks/itensArtists";
import { itensMusics } from "@/mocks/itensMusics";
import MusicItens from "../album/MuiscsItens";
import MusicListItem from "./MusicListItem";

type ProfileBodyProps = {
    isHidden: boolean;
}

type MinisterySectionItem = MinisteryType & { church: ChurchType | null };
type MusicSectionItem = { musics: typeof itensMusics; artists: typeof itensArtists };
type SectionItem = MinisterySectionItem | MusicSectionItem;
type SectionData = {
  title: string;
  type: "ministeries" | "musics";
  data: SectionItem[];
};

function connectMinisteriesWithChurches() {
  return itensMinistery.map((ministery) => {
    const church = itensChurch.find((church) => church.uuid === ministery.igr_uuid);
    return {
      ...ministery,
      church: church || null, // Adiciona os dados da igreja correspondente ou null se não encontrar
    };
  });
}

export default function ProfileBody({isHidden = false}: ProfileBodyProps) {
    const ministeriesWithChurches = connectMinisteriesWithChurches();
    
    return (
        <SectionList
            ListHeaderComponent={() => (
                <View className="flex-col w-full gap-2 py-2 px-3">
                    <ProfileInfo label="Nome social" value="Cleiton Rasta da Silva" />
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo label="Data de nascimento" value="16122005" mask="99/99/9999" confidential={isHidden} />
                        <ProfileInfo label="CPF" value="45869578522" confidential={isHidden} mask="999.999.999-99"/>
                    </View>
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo label="Telefone" value="+5511999999999" mask="+99 (99) 99999-9999" confidential={isHidden}/>
                        <ProfileInfo label="Gênero" value="Masculino" />
                    </View>
                    <View className="flex-1 h-0.5 bg-slate-900 dark:bg-blue-100 my-2"/>
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo label="CEP" value="07776440" mask="99999-999" confidential={isHidden}/>
                        <ProfileInfo label="Número" value="316" confidential={isHidden}/>
                    </View>
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo label="Logradouro" value="Rua João de Barro" confidential={isHidden}/>
                        <ProfileInfo label="Bairro" value="Vila São Francisco" confidential={isHidden}/>
                    </View>
                    <View className="flex-row w-fit gap-3">
                        <ProfileInfo label="Cidade" value="Caieiras" confidential={isHidden}/>
                        <ProfileInfo label="Estado" value="São Paulo" confidential={isHidden}/>
                    </View>
                    <View className="flex-1 h-0.5 bg-slate-900 dark:bg-blue-100 my-2"/>
                </View>
            )}
            sections={[
                {
                    title: "Igrejas e Ministérios",
                    type: "ministeries",
                    data: ministeriesWithChurches,
                },
                {
                    title: "Músicas do usuários",
                    type: "musics",
                    data: [{musics: itensMusics, artists: itensArtists}],
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
            }}
            contentContainerClassName="gap-2 px-3"
        />

    )
}