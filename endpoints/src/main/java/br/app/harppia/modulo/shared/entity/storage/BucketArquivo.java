package br.app.harppia.modulo.shared.entity.storage;

import java.time.LocalDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.defaults.custom.converters.enums.nomebucket.ConversorEnumNomeBucket;
import br.app.harppia.modulo.shared.entity.storage.enums.NomeBucket;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe auxiliar para gestão de arquivos na aplicação. Representa a entidade
 * do banco de dados e é gerida pelo Hibernate/JPA para manipular dados do banco.
 * Os valores declarados representam os padrões declarados pelo banco.
 */
@Entity(name = "tb_bucket")
@Table(name = "tb_bucket", schema = "storage")
@Getter
@Setter
@ToString(of = {"id", "nome"})
@EqualsAndHashCode(of = {"id"})
public class BucketArquivo {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private LocalDateTime updatedAt;

	@Column
	private LocalDateTime deletedAt = null;
	
	//-----------------//
	// DADOS DO BUCKET //
	//-----------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;

	@Convert(converter = ConversorEnumNomeBucket.class)
	@Column(nullable = false, unique = true)
	private NomeBucket nome;

	@Column(name = "tempo_expiracao_upload_em_segundos", nullable = false)
	private Integer tempoLimiteUpload = 30;

	@Column(nullable = false)
	private Long tamanhoMinimo = 1L;

	@Column(nullable = false)
	private Long tamanhoMaximo = 1073741824L;
}