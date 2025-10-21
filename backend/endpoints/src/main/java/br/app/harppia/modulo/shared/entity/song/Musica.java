package br.app.harppia.modulo.shared.entity.song;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.song.enums.ETonalidadeMusica;
import br.app.harppia.modulo.shared.entity.storage.Arquivo;
import br.app.harppia.modulo.usuario.domain.entities.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Musica {
	
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
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by_usu", nullable = false)
	private Usuario createdBy;
	
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
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "s_storage_t_tb_arquivo_c_foto")
	private Arquivo capa;
}
