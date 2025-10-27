package br.app.harppia.modulo.music.infrastructure.repository.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.music.infrastructure.repository.enums.ETonalidadeMusica;
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

@Entity(name = "tb_parte")
@Table(name = "tb_parte", schema = "song")
@Getter
@Setter
@ToString(of = {"id", "musica", "posicao"})
@EqualsAndHashCode(of = "id")
public class FragmentoMusica {
	
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

	//--------------------------//
	// DADOS DA PARTE DA MÚSICA //
	//--------------------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	private Integer posicao;
	
	@Column(nullable = false)	
	private String parte;

	@Column(nullable = false)
	private Integer bpm;

	@Column(nullable = false)
	private ETonalidadeMusica tonalidade;

	//--------------------//
	// CHAVE ESTRANGEIRAS //
	//--------------------//
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "mus_id")
	private Musica musica;
}
