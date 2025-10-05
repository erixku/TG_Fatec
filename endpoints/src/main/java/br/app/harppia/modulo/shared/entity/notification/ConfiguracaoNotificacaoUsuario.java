package br.app.harppia.modulo.shared.entity.notification;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.auth.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa a entidade que armazena as preferências e configurações do
 * aplicativo dos usuários no banco de dados.
 */
@Entity(name = "tb_configuracao_por_usuario")
@Table(name = "tb_configuracao_por_usuario", schema = "notification")
@Getter
@Setter
@ToString(of = {"donoConfig", "updatedAt", "ativarNotificacoes", "naoPerturbarHorario", "naoPerturbarDia"})
@EqualsAndHashCode(of = {"donoConfig"})
public class ConfiguracaoNotificacaoUsuario {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@OneToOne(optional = false)
	@JoinColumn(name = "s_auth_t_tb_usuario_c_lev", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private Usuario donoConfig;
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private LocalDateTime updatedAt;
	
	//-----------------------//
	// DADOS DA CONFIGURAÇÃO //
	//-----------------------//
	@Column(nullable = false)
	private Boolean ativarNotificacoes = true;
	
	@Column(nullable = false)
	private Boolean mostrarEmTelaBloqueio = true;
	
	// Esse campo diz se o usuário <b> não </b> quer ser incomodado em
	// horários específicos.
	@Column(nullable = false)
	private Boolean naoPerturbarHorario = false;
	
	/*
	 * Esse campo representa em quais dias o usuário NÃO quer ser 
	 * perturbado. Nesse caso, a semana começa no DOMINGO.
	 * índice - dia
	 * 0 - domingo
	 * 1 - segunda
	 * 2 - terça
	 * ...
	 * 6 - sábado
	 */
	@Column(nullable = false)
	private String naoPerturbarHorarioDias = "0000000";
	
	@Column
	private LocalTime horarioInicio = null;

	@Column
	private LocalTime horarioFim = null;

	@Column
	private String fusoHorario = null;
	
	@Column(nullable = false)
	private Boolean naoPerturbarDia = false;

	@Column(nullable = false)
	private String naoPerturbarDiaDias = "0000000";
	
	//---------------------//
	// CHAVES ESTRANGEIRAS //
	//---------------------//
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private CorNotificacao corPopUp;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	private CorNotificacao corLed;
}
