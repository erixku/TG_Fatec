package br.app.harppia.modulo.file.domain.entities;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString(of = {"nomeArquivo"})
@EqualsAndHashCode(of = "nomeArquivo")
public class Arquivo {
	private MultipartFile arquivo;
	private String linkPublico;
	private String nomeArquivo;
}
