package br.app.harppia.modulo.shared.entity.notification;

import java.time.LocalDateTime;

import br.app.harppia.modulo.shared.entity.auth.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 * Representa a entidade que armazena as preferências e configurações do
 * aplicativo dos usuários no banco de dados.
 */
@Entity(name = "tb_configuracao_por_usuario")
@Table(name = "tb_configuracao_por_usuario", schema = "notification")
public class ConfiguracaoNotificacaoUsuario {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Boolean ativarNotificacoes = true;
	
	@Column(nullable = false)
	private Boolean mostrarEmTelaBloqueio = true;
	
	@Column(nullable = false)
	private Boolean naoPertubarHorario = false;
	
	@Column(nullable = false)
	private LocalDateTime periodo = null;
	
	@Column(nullable = false)
	private Boolean naoPertubarDia = false;
	
	// Chaves estrangeiras //
	@JoinColumn(name = "cor_pop_up", nullable = false)
	private CorNotificacao corPopUp;
	
	@JoinColumn(nullable = false)
	private CorNotificacao corLed;
	
	@JoinColumn(name = "s_auth_t_tb_usuario_c_lev", nullable = false)
	private Usuario donoConfig;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAtivarNotificacoes() {
		return ativarNotificacoes;
	}

	public void setAtivarNotificacoes(Boolean ativarNotificacoes) {
		this.ativarNotificacoes = ativarNotificacoes;
	}

	public Boolean getMostrarEmTelaBloqueio() {
		return mostrarEmTelaBloqueio;
	}

	public void setMostrarEmTelaBloqueio(Boolean mostrarEmTelaBloqueio) {
		this.mostrarEmTelaBloqueio = mostrarEmTelaBloqueio;
	}

	public Boolean getNaoPertubarHorario() {
		return naoPertubarHorario;
	}

	public void setNaoPertubarHorario(Boolean naoPertubarHorario) {
		this.naoPertubarHorario = naoPertubarHorario;
	}

	public LocalDateTime getPeriodo() {
		return periodo;
	}

	public void setPeriodo(LocalDateTime periodo) {
		this.periodo = periodo;
	}

	public Boolean getNaoPertubarDia() {
		return naoPertubarDia;
	}

	public void setNaoPertubarDia(Boolean naoPertubarDia) {
		this.naoPertubarDia = naoPertubarDia;
	}

	public CorNotificacao getCorPopUp() {
		return corPopUp;
	}

	public void setCorPopUp(CorNotificacao corPopUp) {
		this.corPopUp = corPopUp;
	}

	public CorNotificacao getCorLed() {
		return corLed;
	}

	public void setCorLed(CorNotificacao corLed) {
		this.corLed = corLed;
	}

	public Usuario getDonoConfig() {
		return donoConfig;
	}

	public void setDonoConfig(Usuario donoConfig) {
		this.donoConfig = donoConfig;
	}
}
