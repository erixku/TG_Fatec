package br.app.harppia.modulo.file.infrastructure.repository.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.defaults.custom.converters.enums.nomebucket.ConversorEnumNomeBucket;
import br.app.harppia.modulo.shared.entity.storage.enums.ENomeBucket;
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
public class BucketEntity {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private OffsetDateTime updatedAt;

	@Column
	private OffsetDateTime deletedAt = null;
	
	//-----------------//
	// DADOS DO BUCKET //
	//-----------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;

	@Convert(converter = ConversorEnumNomeBucket.class)
	@Column(nullable = false, unique = true)
	private ENomeBucket nome;

	@Column(name = "tempo_expiracao_upload_em_segundos", nullable = false)
	private Integer tempoLimiteUpload = 30;

	@Column(nullable = false)
	private Long tamanhoMinimo = 1L;

	@Column(nullable = false)
	private Long tamanhoMaximo = 1073741824L;
}