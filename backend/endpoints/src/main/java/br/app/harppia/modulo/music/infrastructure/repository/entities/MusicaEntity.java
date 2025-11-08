package br.app.harppia.modulo.music.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.music.infrastructure.repository.enums.ETonalidadeMusica;
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

@Entity(name = "tb_musica")
@Table(name = "tb_musica", schema = "song")
@Getter
@Setter
@ToString(of = {"id", "nome", "artista", "duracao"})
@EqualsAndHashCode(of = "id")
public class MusicaEntity {
	
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
	
	@Column(name = "created_by", nullable = false)
	private UUID createdBy;
	
	//-----------------//
	// DADOS DA MUSICA //
	//-----------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)	
	private String nome;

	@Column(nullable = false)	
	private String artista;
	
	@Column(nullable = false)	
	private Boolean temArtistaSecundario;

	@Column(nullable = false)	
	private String duracao;

	@Generated(event = EventType.INSERT)
	@Column(insertable = false)
	private Integer duracaoEmSegundos;
	
	@Column
	private Integer bpm;
	
	@Column
	private ETonalidadeMusica tonalidade;
	
	@Column(nullable = false)
	private String linkMusica;
	
	@Column
	private String linkLetra;
	
	@Column
	private String linkCifra;
	
	@Column
	private String linkPartitura;
	
	@Column(nullable = false)
	private Boolean compoeMedley;
	
	//-------------------//
	// CHAVE ESTRANGEIRA //
	//-------------------//
	@Column(name = "s_storage_t_tb_arquivo_c_foto")
	private UUID idCapa;
}
