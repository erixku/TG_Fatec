package br.app.harppia.modulo.file.infrastructure.repository.entities;

import java.time.OffsetDateTime;

import br.app.harppia.modulo.file.infrastructure.converter.ConversorEnumNomeBucket;
import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "nome"})
@EqualsAndHashCode(of = {"id"})
public class BucketEntity {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Short id;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt = null;
	
	//-----------------//
	// DADOS DO BUCKET //
	//-----------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;

	@Convert(converter = ConversorEnumNomeBucket.class)
	@Column(name = "nome", nullable = false, unique = true)
	private ENomeBucket nome;

	@Column(name = "tempo_expiracao_upload_em_segundos", nullable = false)
	private Short tempoLimiteUpload = 30;

	@Column(name = "tamanho_minimo", nullable = false)
	private Integer tamanhoMinimo = 1;

	@Column(name = "tamanho_maximo", nullable = false)
	private Integer tamanhoMaximo = 1073741824;
}