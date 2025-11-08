package br.app.harppia.modulo.activities.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.activities.infrastructure.repository.enums.ETipoPublicacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_publicacao")
@Table(name = "tb_publicacao", schema = "schedule")
@Getter
@Setter
@ToString(of = {"id", "updatedAt", "createdBy", "titulo", "descricao"})
@EqualsAndHashCode(of = "id")
public class Publicacao {
	
	@SuppressWarnings("unused")
	private static long serialVersion = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
	private OffsetDateTime deletedAt;
	
	@Column(name = "created_by", nullable = false, updatable = false)
	private UUID createdBy;
	
	@Column(name = "updated_by", nullable = false)
	private UUID updatedBy;
	
	@Column(name = "deleted_by")
	private UUID deletedBy;
	
	//---------------------//
	// DADOS DA PUBLICAÇÃO //
	//---------------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Column(name = "tipo", nullable = false)
	private ETipoPublicacao tipo;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "s_church_t_tb_igreja_c_igreja", nullable = false)
	private UUID igreja;
}
