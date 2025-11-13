package br.app.harppia.modulo.music.infrastructure.mapper;

import java.time.Duration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.app.harppia.defaults.custom.exceptions.GestaoMusicaException;
import br.app.harppia.modulo.music.domain.request.CadastroMusicaRequest;
import br.app.harppia.modulo.music.infrastructure.repository.entities.MusicaEntity;
import br.app.harppia.modulo.music.infrastructure.repository.enums.ETonalidadeMusica;

@Mapper(componentModel = "spring")
public abstract class MusicaMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "deletedAt", ignore = true)
	@Mapping(target = "isDeleted", ignore = true)
	@Mapping(target = "duracaoEmSegundos", ignore = true)
	
	@Mapping(source = "idUsuarioCriador", target = "createdBy")
	@Mapping(source = "parteDeMedley", target = "compoeMedley")

	@Mapping(source = "tonalidade", target = "tonalidade", qualifiedByName = "converterEnumTonalidade")
	@Mapping(source = "duracaoMusica", target = "duracao", qualifiedByName = "converterDuracao")
	public abstract MusicaEntity toEntity(CadastroMusicaRequest cadMusReq);
	
	@Named("converterDuracao")
	protected Duration map(String duracao) {
		
		if(duracao == null || duracao.isEmpty() || !duracao.contains(":"))
			throw new GestaoMusicaException("Erro ao converter a duração: formato não reconhecido!");
		
		String[] partes = duracao.split(":");

		int minutos = Integer.parseInt(partes[0]);
		int segundos = Integer.parseInt(partes[1]);

		Duration duration = Duration.ofSeconds(minutos * 60L + segundos);

		return duration;
	}
	
	@Named("converterEnumTonalidade")
	protected ETonalidadeMusica mapa(String tonalidade) {
		
		if(tonalidade == null || tonalidade.isEmpty())
			throw new GestaoMusicaException("A tonalidade não deve ser nula!");

		return ETonalidadeMusica.fromValue(tonalidade);
	}
	
}
