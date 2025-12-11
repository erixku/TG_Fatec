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

@Entity(name = "tb_escala")
@Table(name = "tb_escala", schema = "schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "updatedBy", "nome", "quantidadeAtividades", "idIgreja"})
@EqualsAndHashCode(of = "id")
public class EscalaEntity {

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
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "quantidade_atividades", nullable = false)
	private Integer quantidadeAtividades;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "s_church_t_tb_igreja_c_igreja", nullable = false)
	private UUID idIgreja;

	@Column(name = "s_church_t_tb_ministerio_louvor_c_min_lou", nullable = false)
	private UUID idMinisterio;
}
