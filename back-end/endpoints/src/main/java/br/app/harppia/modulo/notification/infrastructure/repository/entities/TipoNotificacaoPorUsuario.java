package br.app.harppia.modulo.notification.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_tipo_por_usuario")
@Table(name = "tb_tipo_por_usuario", schema = "notification")
@Getter
@Setter
@ToString(of = {"id", "isDisabled", "tipoNotificacao", "idUsuario"})
@EqualsAndHashCode(of = "id")
public class TipoNotificacaoPorUsuario {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 3L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// --------------//
	// DADOS DE LOGS //
	// --------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;

	// --------------------------//
	// DADOS DO TIPO POR USUARIO //
	// --------------------------//
	@Column(name = "is_disabled", nullable = false)
	private Boolean isDisabled = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tip_id", nullable = false)
	private TipoNotificacao tipoNotificacao;

	@Column(name = "s_auth_t_tb_usuario_c_lev", nullable = false)
	private UUID idUsuario;
}
