package br.app.harppia.modulo.shared.entity.notification;

import java.time.LocalDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.storage.Arquivo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_tipo")
@Table(name = "tb_tipo", schema = "notification")
@Getter
@Setter
@ToString(of = {"id", "isDeleted", "nome"})
@EqualsAndHashCode(of = "id")
public class TipoNotificacao {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private LocalDateTime updatedAt;

	@Column
	private LocalDateTime deletedAt = null;
	
	
	//---------------//
	// DADOS DO TIPO //
	//---------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	private String nome;

	@JoinColumn(name = "s_storage_t_tb_arquivo_c_icone", nullable = false)
	private Arquivo icone;
}
