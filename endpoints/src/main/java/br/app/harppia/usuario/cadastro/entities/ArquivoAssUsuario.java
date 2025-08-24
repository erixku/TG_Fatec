package br.app.harppia.usuario.cadastro.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "tb_arquivo_ass_usuario")
@Table(name = "tb_arquivo_ass_usuario", schema = "storage")
public class ArquivoAssUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(optional = false)
	@JoinColumn(name = "arq_UUID", referencedColumnName = "uuid", nullable = false, unique = true)
	private Arquivo arqUUID;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "schema_auth_usuario_lev", referencedColumnName = "uuid", nullable = false, unique = true)	
	private Usuario usuarioUUID;
}
