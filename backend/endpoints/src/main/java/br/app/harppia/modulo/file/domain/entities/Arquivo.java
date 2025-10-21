package br.app.harppia.modulo.file.domain.entities;

import org.springframework.web.multipart.MultipartFile;

public class Arquivo {
	
	private MultipartFile arquivo;
	private String linkPublico;
	
	public Arquivo(MultipartFile arquivo, String linkPublico) {
		this.arquivo = arquivo;
		this.linkPublico = linkPublico;
	}
	
	public MultipartFile getArquivo() {
		return arquivo;
	}
	
	public void setArquivo(MultipartFile arquivo) {
		if(arquivo != null)
			this.arquivo = arquivo;
	}
	
	public String getLinkPublico() {
		return linkPublico;
	}
	
	public void setLinkPublico(String linkPublico) {
		if(!linkPublico.trim().isEmpty())
			this.linkPublico = linkPublico;
	}
}
