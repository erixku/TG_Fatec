package br.app.harppia.modulo.file.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.storage.enums.EExtensaoArquivo;
import br.app.harppia.modulo.shared.entity.storage.enums.EMimeTypeArquivo;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa um arquivo gerido pela aplicação. Pode ser audio, pdf, imagem ou video.
 */
@Entity(name = "tb_arquivo")
@Table(name = "tb_arquivo", schema = "storage")
@Getter
@Setter
@AllArgsConstructor
@ToString(of = {"uuid", "createdAt", "createdBy", "nome", "tamanhoEmBytes"})
@EqualsAndHashCode(of = {"uuid"})
public class ArquivoEntity {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt = null;
	
	@ManyToOne
	@JoinColumn(name = "created_by_usu", nullable = false)
	private UsuarioEntity createdBy;
	
	@ManyToOne
	@JoinColumn(name = "deleted_by_usu")
	private UsuarioEntity deletedBy = null;

	//------------------//
	// DADOS DO ARQUIVO //
	//------------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "mime_type", nullable = false)
	private EMimeTypeArquivo mimeType;
	
	@Column(name = "extensao", nullable = false)
	private EExtensaoArquivo extensao;
	
	@Column(name = "tamanho_em_bytes", nullable = false)
	private Long tamanhoEmBytes;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "buc_id", nullable = false)
	private BucketEntity bucket;
}
