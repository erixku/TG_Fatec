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

@Entity(name = "tb_faixa_elencada")
@Table(name = "tb_faixa_elencada", schema = "schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "updatedBy", "posicao", "observacao", "idAtividade"})
@EqualsAndHashCode(of = "id")
public class FaixaElencadaEntity {
	
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
	
	//----------------//
	// DADOS DA FAIXA //
	//----------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(name = "posicao", nullable = false)
	private Integer posicao;
	
	@Column(name = "observacao", nullable = false)
	private String observacao;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "ati_id", nullable = false, updatable = false)
	private Integer idAtividade;
	
	@Column(name = "s_church_t_tb_faixa_c_faixa", nullable = false, updatable = false)
	private Integer idFaixa;
}

