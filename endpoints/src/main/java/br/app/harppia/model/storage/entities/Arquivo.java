package br.app.harppia.endpoints.model.storage.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import br.app.harppia.endpoints.model.enums.ExtensaoArquivo;
import br.app.harppia.endpoints.model.enums.MimeTypeArquivo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "uuid", unique = true, insertable = false, updatable = false)
	private UUID uuid;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime criadoEm;

	@Column(name = "deleted_at", insertable = false)
	private LocalDateTime deletadoEm;

	@Column(insertable = false, nullable = false)
	private LocalDateTime deletado;
	
	@Column(nullable = false)
	private String caminho;
	
	@Column(nullable = false)
	private MimeTypeArquivo mimeType;
	
	@Column(nullable = false)
	private ExtensaoArquivo extensao;
	
	@Column(nullable = false)
	private Long tamanhoEmBytes;
	
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "bucket")
	private Bucket bucId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the criadoEm
	 */
	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	/**
	 * @param criadoEm the criadoEm to set
	 */
	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}

	/**
	 * @return the deletadoEm
	 */
	public LocalDateTime getDeletadoEm() {
		return deletadoEm;
	}

	/**
	 * @param deletadoEm the deletadoEm to set
	 */
	public void setDeletadoEm(LocalDateTime deletadoEm) {
		this.deletadoEm = deletadoEm;
	}

	/**
	 * @return the deletado
	 */
	public LocalDateTime getDeletado() {
		return deletado;
	}

	/**
	 * @param deletado the deletado to set
	 */
	public void setDeletado(LocalDateTime deletado) {
		this.deletado = deletado;
	}

	/**
	 * @return the caminho
	 */
	public String getCaminho() {
		return caminho;
	}

	/**
	 * @param caminho the caminho to set
	 */
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	/**
	 * @return the mimeType
	 */
	public MimeTypeArquivo getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(MimeTypeArquivo mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return the extensao
	 */
	public ExtensaoArquivo getExtensao() {
		return extensao;
	}

	/**
	 * @param extensao the extensao to set
	 */
	public void setExtensao(ExtensaoArquivo extensao) {
		this.extensao = extensao;
	}

	/**
	 * @return the tamanhoEmBytes
	 */
	public Long getTamanhoEmBytes() {
		return tamanhoEmBytes;
	}

	/**
	 * @param tamanhoEmBytes the tamanhoEmBytes to set
	 */
	public void setTamanhoEmBytes(Long tamanhoEmBytes) {
		this.tamanhoEmBytes = tamanhoEmBytes;
	}

	/**
	 * @return the bucId
	 */
	public Bucket getBucId() {
		return bucId;
	}

	/**
	 * @param bucId the bucId to set
	 */
	public void setBucId(Bucket bucId) {
		this.bucId = bucId;
	}
	
	
}
