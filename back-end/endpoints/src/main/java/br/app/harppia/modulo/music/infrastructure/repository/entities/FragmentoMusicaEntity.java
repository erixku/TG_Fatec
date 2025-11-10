package br.app.harppia.modulo.music.infrastructure.repository.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.music.infrastructure.repository.enums.ETonalidadeMusica;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class FragmentoMusicaEntity {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)	
	private OffsetDateTime updatedAt;
	
	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt;

	//--------------------------//
	// DADOS DA PARTE DA MÚSICA //
	//--------------------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(name = "posicao", nullable = false)
	private Short posicao;
	
	@Column(name = "parte", nullable = false)	
	private String parte;

	@Column(name = "bpm", nullable = false)
	private Short bpm;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ETonalidadeMusica tonalidade;

	//--------------------//
	// CHAVE ESTRANGEIRAS //
	//--------------------//
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mus_id", nullable = false)
	private MusicaEntity musica;
}
