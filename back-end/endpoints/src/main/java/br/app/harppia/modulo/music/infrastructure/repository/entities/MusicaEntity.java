package br.app.harppia.modulo.music.infrastructure.repository.entities;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.generator.EventType;
import org.hibernate.type.SqlTypes;

import br.app.harppia.defaults.custom.converters.enums.tonalidade.ConversorEnumTonalidadeMusica;
import br.app.harppia.modulo.music.infrastructure.repository.enums.ETonalidadeMusica;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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
	@Generated(event = EventType.INSERT)
	@Column(name = "id", unique = true, insertable = false, updatable = false)
	private Integer id;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Column(name = "created_at", insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", insertable = false, updatable = false)
	private OffsetDateTime updatedAt;
	
	@Column(name = "deleted_at", insertable = false)
	private OffsetDateTime deletedAt;
	
	@Column(name = "created_by", nullable = false)
	private UUID createdBy;
	
	//-----------------//
	// DADOS DA MUSICA //
	//-----------------//
	@Column(name = "is_deleted", insertable = false)
	private Boolean isDeleted;
	
	@Column(name = "nome", nullable = false)	
	private String nome;

	@Column(name = "artista", nullable = false)	
	private String artista;
	
	@Column(name = "tem_artista_secundario", nullable = false)	
	private Boolean temArtistaSecundario;

	@Column(name = "album")
	private String album;
	
	@JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
	@Column(name = "duracao", nullable = false)	
	private Duration duracao;

	@Generated(event = EventType.INSERT)
	@Column(name = "duracao_em_segundos", insertable = false)
	private Short duracaoEmSegundos;
	
	@Column(name = "bpm")
	private Integer bpm;
	
	@Convert(converter = ConversorEnumTonalidadeMusica.class)
	@Column(name = "tonalidade")
	@ColumnTransformer(write = "CAST(? AS utils.s_song_e_tonalidade)")
	private ETonalidadeMusica tonalidade;
	
	@Column(name = "link_musica", columnDefinition = "\"utils\".\"domain_link\"" , nullable = false)
	private String linkMusica;
	
	@Column(name = "link_letra", columnDefinition = "\"utils\".\"domain_link\"")
	private String linkLetra;
	
	@Column(name = "link_cifra", columnDefinition = "\"utils\".\"domain_link\"")
	private String linkCifra;
	
	@Column(name = "link_partitura", columnDefinition = "\"utils\".\"domain_link\"")
	private String linkPartitura;
	
	@Column(name = "parte_de_medley", nullable = false)
	private Boolean compoeMedley;
	
	//-------------------//
	// CHAVE ESTRANGEIRA //
	//-------------------//
	@Column(name = "s_storage_t_tb_arquivo_c_foto")
	private UUID idCapa;
}
