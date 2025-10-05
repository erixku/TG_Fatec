package br.app.harppia.modulo.shared.entity.notification;

import java.time.LocalDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.auth.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity(name = "tb_tipo_por_usuario")
@Table(name = "tb_tipo_por_usuario", schema = "notification")
@Getter
@Setter
@ToString(of = {"id", "isDisabled", "tipoNotificacao", "usuario"})
@EqualsAndHashCode(of = "id")
public class TipoNotificacaoPorUsuario {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// --------------//
	// DADOS DE LOGS //
	// --------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private LocalDateTime updatedAt;

	// --------------------------//
	// DADOS DO TIPO POR USUARIO //
	// --------------------------//
	@Column(nullable = false)
	private Boolean isDisabled = false;

	@JoinColumn(name = "tip_id", nullable = false)
	private TipoNotificacao tipoNotificacao;

	@JoinColumn(name = "s_auth_t_tb_usuario_c_lev", nullable = false)
	private Usuario usuario;
}
