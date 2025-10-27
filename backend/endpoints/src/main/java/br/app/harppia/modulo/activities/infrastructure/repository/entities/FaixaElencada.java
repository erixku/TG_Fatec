package br.app.harppia.modulo.activities.infrastructure.repository.entities;

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

@Entity(name = "tb_faixa_elencada")
@Table(name = "tb_faixa_elencada", schema = "schedule")
@Getter
@Setter
@ToString(of = {"id", "updatedByMinId", "posicao", "observacao", "atividade"})
@EqualsAndHashCode(of = "id")
public class FaixaElencada {
	
	@SuppressWarnings("unused")
	private static long serialVersion = 1L;
	
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
	
	@Column(name = "created_by_min", nullable = false)
	private UUID createdByMinId;
	
	@Column(name = "updated_by_min", nullable = false)
	private UUID updatedByMinId;
	
	@Column(name = "deleted_by_min")
	private UUID deletedByMinId;
	
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
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ati_id", nullable = false)
	private Atividade atividade;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "s_church_t_tb_faixa_c_faixa", nullable = false)
	private FaixaElencada faixa;
}

