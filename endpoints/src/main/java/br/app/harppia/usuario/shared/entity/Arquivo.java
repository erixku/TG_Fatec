package br.app.harppia.usuario.shared.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnTransformer;

import br.app.harppia.defaults.custom.converters.enums.extensaoarquivo.ConversorEnumExtensaoArquivo;
import br.app.harppia.defaults.custom.converters.enums.mimetypearquivo.ConversorEnumMimeTypeArquivo;
import br.app.harppia.defaults.shared.enums.ExtensaoArquivo;
import br.app.harppia.defaults.shared.enums.MimeTypeArquivo;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "tb_arquivo")
@Table(name = "tb_arquivo", schema = "storage")
public class Arquivo {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "uuid", unique = true, insertable = false, updatable = false)
	private UUID uuid;
	
	@Column(nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false, insertable = false)
	private LocalDateTime deletedAt;

	@Column(nullable = false, insertable = false)
	private LocalDateTime isDeleted;
	
	@Column(nullable = false, updatable = false)
	private String nome;
	
	@Convert(converter = ConversorEnumMimeTypeArquivo.class)
	@Column(nullable = false, updatable = false)
    @ColumnTransformer(write = "CAST(? AS utils.enum_s_storage_t_tb_arquivo_c_mime_type)")
	private MimeTypeArquivo mimeType;
	
	@Convert(converter = ConversorEnumExtensaoArquivo.class)
	@Column(nullable = false, updatable = false)
    @ColumnTransformer(write = "CAST(? AS utils.enum_s_storage_t_tb_arquivo_c_extensao)")
	private ExtensaoArquivo extensao;
	
	@Column(nullable = false, updatable = false)
	private Long tamanhoEmBytes;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "buc_id", nullable = false, updatable = false)
	private Bucket bucket;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	public LocalDateTime getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(LocalDateTime isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public MimeTypeArquivo getMimeType() {
		return mimeType;
	}

	public void setMimeType(MimeTypeArquivo mimeType) {
		this.mimeType = mimeType;
	}

	public ExtensaoArquivo getExtensao() {
		return extensao;
	}

	public void setExtensao(ExtensaoArquivo extensao) {
		this.extensao = extensao;
	}

	public Long getTamanhoEmBytes() {
		return tamanhoEmBytes;
	}

	public void setTamanhoEmBytes(Long tamanhoEmBytes) {
		this.tamanhoEmBytes = tamanhoEmBytes;
	}

	public Bucket getBucket() {
		return bucket;
	}

	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
	}
}
