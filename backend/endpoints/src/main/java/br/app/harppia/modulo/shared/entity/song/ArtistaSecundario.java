package br.app.harppia.modulo.shared.entity.song;

import java.time.OffsetDateTime;

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

@Entity(name = "tb_artista_secundario")
@Table(name = "tb_artista_secundario", schema = "song")
@Getter
@Setter
@ToString(of = {"id", "musica", "nome"})
@EqualsAndHashCode(of = "id")
public class ArtistaSecundario {

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
	@Column(nullable = false, insertable = false)
	private OffsetDateTime updatedAt;
	
	@Column()
	private OffsetDateTime deletedAt;
	
	//--------------------//
	// DADOS DOS ARTISTAS //
	//--------------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	private String nome;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "mus_id", nullable = false)
	private Musica musica;
}
