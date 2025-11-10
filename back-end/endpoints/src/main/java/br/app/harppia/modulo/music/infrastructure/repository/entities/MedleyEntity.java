package br.app.harppia.modulo.music.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

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

@Entity(name = "tb_medley")
@Table(name = "tb_medley", schema = "song")
@Getter
@Setter
@ToString(of = {"id", "nome", "quantidadeMusicas"})
@EqualsAndHashCode(of = "id")
public class MedleyEntity {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)	
	private OffsetDateTime updatedAt;
	
	@Column
	private OffsetDateTime deletedAt;
	
	@Column(name = "created_by", nullable = false)
	private UUID createdBy;
	
	//-----------------//
	// DADOS DO MEDLEY //
	//-----------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Integer quantidadeMusicas;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "s_storage_t_tb_arquivo_c_foto")
	private UUID idFoto;
}
