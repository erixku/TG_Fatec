package br.app.harppia.modulo.usuario.shared.entity;

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
	@JoinColumn(name = "s_auth_t_tb_usuario_c_lev", referencedColumnName = "uuid", nullable = false, unique = true)	
	private Usuario usuarioUUID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Arquivo getArqUUID() {
		return arqUUID;
	}

	public void setArqUUID(Arquivo arqUUID) {
		this.arqUUID = arqUUID;
	}

	public Usuario getUsuarioUUID() {
		return usuarioUUID;
	}

	public void setUsuarioUUID(Usuario usuarioUUID) {
		this.usuarioUUID = usuarioUUID;
	}
}
