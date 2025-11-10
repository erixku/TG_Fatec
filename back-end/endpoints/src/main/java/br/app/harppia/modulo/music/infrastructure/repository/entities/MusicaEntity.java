package br.app.harppia.modulo.music.infrastructure.repository.entities;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.generator.EventType;
import org.hibernate.type.SqlTypes;

import br.app.harppia.modulo.music.infrastructure.repository.enums.ETonalidadeMusica;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@Column(name = "id", nullable = false)
	private Integer id;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime updatedAt;
	
	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt;
	
	@Column(name = "created_by", nullable = false)
	private UUID createdBy;
	
	//-----------------//
	// DADOS DA MUSICA //
	//-----------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(name = "nome", nullable = false)	
	private String nome;

	@Column(name = "artista", nullable = false)	
	private String artista;
	
	@Column(name = "tem_artista_secundario", nullable = false)	
	private Boolean temArtistaSecundario;

	@JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
	@Column(name = "duracao", nullable = false)	
	private Duration duracao;

	@Generated(event = EventType.INSERT)
	@Column(name = "duracao_em_segundos", insertable = false)
	private Short duracaoEmSegundos;
	
	@Column(name = "bpm")
	private Integer bpm;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tonalidade")
	private ETonalidadeMusica tonalidade;
	
	@JdbcTypeCode(SqlTypes.DISTINCT)
	@Column(name = "link_musica", columnDefinition = "\"utils\".\"domain_link\"" , nullable = false)
	private String linkMusica;
	
	@JdbcTypeCode(SqlTypes.DISTINCT)
	@Column(name = "link_letra", columnDefinition = "\"utils\".\"domain_link\"")
	private String linkLetra;
	
	@JdbcTypeCode(SqlTypes.DISTINCT)
	@Column(name = "link_cifra", columnDefinition = "\"utils\".\"domain_link\"")
	private String linkCifra;
	
	@JdbcTypeCode(SqlTypes.DISTINCT)
	@Column(name = "link_partitura", columnDefinition = "\"utils\".\"domain_link\"")
	private String linkPartitura;
	
	@Column(name = "compoe_medley", nullable = false)
	private Boolean compoeMedley;
	
	//-------------------//
	// CHAVE ESTRANGEIRA //
	//-------------------//
	@Column(name = "s_storage_t_tb_arquivo_c_foto")
	private UUID idCapa;
}
