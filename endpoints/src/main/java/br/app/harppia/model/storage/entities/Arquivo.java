package br.app.harppia.model.storage.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import br.app.harppia.model.enums.ExtensaoArquivo;
import br.app.harppia.model.enums.MimeTypeArquivo;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "uuid", unique = true, insertable = false, updatable = false)
	private UUID uuid;
	
	@Column(unique = true, insertable = false, updatable = false)
	private Long id;
	
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime criadoEm;

	@Column(name = "deleted_at", nullable = false, insertable = false)
	private LocalDateTime deletadoEm;

	@Column(nullable = false, insertable = false)
	private LocalDateTime deletado;
	
	@Column(nullable = false, updatable = false)
	private String caminho;
	
	@Column(nullable = false, updatable = false)
	private MimeTypeArquivo mimeType;
	
	@Column(nullable = false, updatable = false)
	private ExtensaoArquivo extensao;
	
	@Column(nullable = false, updatable = false)
	private Long tamanhoEmBytes;
	
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "buc_id", nullable = false)
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
