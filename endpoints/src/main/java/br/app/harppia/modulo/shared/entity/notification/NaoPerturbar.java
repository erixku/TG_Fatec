package br.app.harppia.modulo.shared.entity.notification;

import br.app.harppia.modulo.shared.entity.auth.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity(name = "tb_nao_perturbar")
@Table(name = "tb_nao_perturbar", schema = "notification")
public class NaoPerturbar {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Character tipo;
	
	@Column(nullable = false)
	private Short dia;
	
	@JoinColumn(name = "s_auth_t_tb_usuario_c_lev", nullable = false)
	private Usuario donoConfig;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public Short getDia() {
		return dia;
	}

	public void setDia(Short dia) {
		this.dia = dia;
	}

	public Usuario getDonoConfig() {
		return donoConfig;
	}

	public void setDonoConfig(Usuario donoConfig) {
		this.donoConfig = donoConfig;
	}
}
