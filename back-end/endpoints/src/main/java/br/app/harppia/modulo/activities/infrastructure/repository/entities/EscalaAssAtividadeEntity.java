package br.app.harppia.modulo.activities.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_escala_ass_atividade")
@Table(name = "tb_escala_ass_atividade", schema = "schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "updatedBy", "posicao", "idEscala", "idAtividade"})
@EqualsAndHashCode(of = "id")
public class EscalaAssAtividadeEntity {

	@SuppressWarnings("unused")
	private static long serialVersion = 3L;
	
	@Id
	@Generated(event = EventType.INSERT)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Column(name = "created_at", insertable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	@Column(name = "updated_at", insertable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "deleted_at", insertable = false)
	private OffsetDateTime deletedAt;
	
	@Column(name = "created_by", nullable = false, updatable = false)
	private UUID createdBy;
	
	@Column(name = "updated_by", nullable = false)
	private UUID updatedBy;
	
	@Column(name = "deleted_by", insertable = false)
	private UUID deletedBy;
	
	//-----------------//
	// DADOS DA ESCALA //
	//-----------------//
	@Column(name = "is_deleted", insertable = false)
	private Boolean isDeleted;
	
	@Column(name = "posicao", nullable = false)
	private Integer posicao;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "esc_id", nullable = false)
	private Integer idEscala;
	
	@Column(name = "ati_id", nullable = false)
	private Integer idAtividade;
}
