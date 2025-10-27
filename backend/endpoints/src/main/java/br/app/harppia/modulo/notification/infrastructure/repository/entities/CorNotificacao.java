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

/**
 * Essa classe representa a entidade que armazena a cor a ser emitida
 * quando uma notificação surgir.
 */
@Entity(name = "tb_cor")
@Table(name = "tb_cor", schema = "notification")
@Getter
@Setter
@ToString(of = {"id", "nome", "isDeleted"})
@EqualsAndHashCode(of = "id")
public class CorNotificacao {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;
	
	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt = null;
	
	//--------------//
	// DADOS DA COR //
	//--------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "s_storage_t_tb_arquivo_c_icone")
	private UUID idIcone;
}