package br.app.harppia.modulo.music.infrastructure.repository.entities;

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

@Entity(name = "tb_medley_ass_musica")
@Table(name = "tb_medley_ass_musica", schema = "song")
@Getter
@Setter
@ToString(of = {"id", "musica", "medley", "posicao"})
@EqualsAndHashCode(of = "id")
public class MedleyAssMusica {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)	
	private OffsetDateTime updatedAt;
	
	@Column
	private OffsetDateTime deletedAt;
	
	//---------------------//
	// DADOS DA ASSOCIAÇÃO //
	//---------------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	private Integer posicao;
	
	//-----//
	// FKs //
	//-----//
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "med_id", nullable = false)
	private Medley medley;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mus_id", nullable = false)
	private Musica musica;
}
