package br.app.harppia.modulo.church.infrastructure.repository.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_instrumento_modelo")
@Table(name = "tb_instrumento_modelo", schema = "church")
@Getter
@Setter
@ToString(of = { "id", "nome", "isDeleted" })
@EqualsAndHashCode(of = "id")
public class ModeloInstrumentoEntity {

	@Id
	@Generated(event = EventType.INSERT)
	@Column(name = "id", insertable = false, updatable = false)
	private Integer id;

	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Column(name = "created_at", insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "deleted_at", insertable = false)
	private OffsetDateTime deletedAt;

	//--------------------------------//
	// DADOS DO MODELO DO INSTRUMENTO //
	//--------------------------------//
	@Column(name = "is_deleted", insertable = false)
	private Boolean isDeleted;

	@Column(name = "nome", nullable = false)
	private String nome;

	//-----//
	// FKs //
	//-----//
	@Column(name = "ins_mar_id", nullable = false)
	private Integer idInstrumentoMarca;
}
