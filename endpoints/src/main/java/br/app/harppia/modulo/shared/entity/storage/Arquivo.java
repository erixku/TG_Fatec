package br.app.harppia.modulo.shared.entity.storage;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.defaults.custom.converters.enums.extensaoarquivo.ConversorEnumExtensaoArquivo;
import br.app.harppia.defaults.custom.converters.enums.mimetypearquivo.ConversorEnumMimeTypeArquivo;
import br.app.harppia.modulo.shared.entity.storage.enums.ExtensaoArquivo;
import br.app.harppia.modulo.shared.entity.storage.enums.MimeTypeArquivo;
import br.app.harppia.modulo.shared.entity.auth.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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

/**
 * Representa um arquivo gerido pela aplicação. Pode ser audio, pdf, imagem ou video.
 */
@Entity(name = "tb_arquivo")
@Table(name = "tb_arquivo", schema = "storage")
@Getter
@Setter
@ToString(of = {"uuid", "createdAt", "createdBy", "nome", "tamanhoEmBytes"})
@EqualsAndHashCode(of = {"uuid"})
public class Arquivo {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime deletedAt = null;
	
	@JoinColumn(name = "created_by_usu", nullable = false)
	private Usuario createdBy;
	
	@JoinColumn(name = "deleted_by_usu")
	private Usuario deletedBy = null;

	//------------------//
	// DADOS DO ARQUIVO //
	//------------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	private String nome;
	
	@Convert(converter = ConversorEnumMimeTypeArquivo.class)
	@Column(nullable = false)
	private MimeTypeArquivo mimeType;
	
	@Convert(converter = ConversorEnumExtensaoArquivo.class)
	@Column(nullable = false)
	private ExtensaoArquivo extensao;
	
	@Column(nullable = false)
	private Long tamanhoEmBytes;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "buc_id", nullable = false)
	private BucketArquivo bucket;
}
