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

@Entity(name = "tb_notificacao")
@Table(name = "tb_notificacao", schema = "notification")
@Getter
@Setter
@ToString(of = {"id", "isReaded", "tipoNotificacao", "descricao"})
@EqualsAndHashCode(of = "id")
public class Notificacao {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	
	//----------------------//
	// DADOS DA NOTIFICAÇÃO //
	//----------------------//
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "link", nullable = false)
	private String link;
	
	@Column(name = "is_lida", nullable = false)
	private Boolean isReaded = false;
	
	// Se lê:
	// "Várias notificações sempre estarão associadas a apenas um tipo de notificação"
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tip_id", nullable = false)
	private TipoNotificacao tipoNotificacao;

	@Column(name = "s_auth_t_tb_usuario_c_notificado", nullable = false)
	private UUID idDestinatario;

	@Column(name = "s_auth_t_tb_usuario_c_notificador", nullable = false)
	private UUID idRemetente;
}
