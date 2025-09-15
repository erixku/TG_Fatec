package br.app.harppia.usuario.shared.entity;

import java.time.LocalDateTime;

import br.app.harppia.defaults.custom.converters.enums.nomebucket.ConversorEnumNomeBucket;
import br.app.harppia.defaults.shared.enums.NomeBucket;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "tb_bucket")
@Table(name = "tb_bucket", schema = "storage")
public class Bucket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(insertable = false, nullable = false)
	private LocalDateTime createdAt;
	
	@Column(insertable = false, nullable = false)
	private LocalDateTime updatedAt;
	
	@Column(insertable = false)
	private LocalDateTime deletedAt;
	
	@Column(name = "is_deletado", insertable = false, nullable = false)
	private Boolean isDeleted;
	
	@Convert(converter = ConversorEnumNomeBucket.class)
	@Column(nullable = false, unique = true)
	private NomeBucket nome;
	
	@Column(name = "tempo_expiracao_upload_em_segundos", nullable = false, columnDefinition = "DEFAULT '30'")
	private Integer tempoLimiteUpload;
	
	@Column(nullable = false, columnDefinition = "DEFAULT '1'")
	private Long tamanhoMinimo;

	@Column(nullable = false, columnDefinition = "DEFAULT '1073741824'")
	private Long tamanhoMaximo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public NomeBucket getNome() {
		return nome;
	}

	public void setNome(NomeBucket nome) {
		this.nome = nome;
	}

	public Integer getTempoLimiteUpload() {
		return tempoLimiteUpload;
	}

	public void setTempoLimiteUpload(Integer tempoLimiteUpload) {
		this.tempoLimiteUpload = tempoLimiteUpload;
	}

	public Long getTamanhoMinimo() {
		return tamanhoMinimo;
	}

	public void setTamanhoMinimo(Long tamanhoMinimo) {
		this.tamanhoMinimo = tamanhoMinimo;
	}

	public Long getTamanhoMaximo() {
		return tamanhoMaximo;
	}

	public void setTamanhoMaximo(Long tamanhoMaximo) {
		this.tamanhoMaximo = tamanhoMaximo;
	}
}