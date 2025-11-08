package br.app.harppia.modulo.igreja.infrastructure.repository.entities;

import java.time.OffsetDateTime;

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

@Entity(name = "tb_instrumento_modelo")
@Table(name = "tb_instrumento_modelo", schema = "church")
@Getter
@Setter
@ToString(of = { "id", "nome", "isDeleted" })
@EqualsAndHashCode(of = "id")
public class ModeloInstrumentoEntity {

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
	@Column(name = "updated_at", nullable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt;

	//--------------------------------//
	// DADOS DO MODELO DO INSTRUMENTO //
	//--------------------------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;

	@Column(name = "nome", nullable = false)
	private String nome;

	//-----//
	// FKs //
	//-----//
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ins_mar_id", nullable = false)
	private MarcaInstrumentoEntity instrumentoMarca;
}
