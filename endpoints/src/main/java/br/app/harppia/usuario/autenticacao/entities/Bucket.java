package br.app.harppia.usuario.autenticacao.entities;

import java.sql.Timestamp;

import br.app.harppia.app.utils.converters.ConversorEnumPadrao;
import br.app.harppia.app.utils.converters.enums.nomebucket.ConversorEnumNomeBucket;
import br.app.harppia.usuario.cadastro.enums.NomeBucket;
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

	@Column(name = "created_at", insertable = false, nullable = false)
	private Timestamp criadoEm;
	
	@Column(name = "updated_at", insertable = false, nullable = false)
	private Timestamp atualizadoEm;
	
	@Column(name = "deleted_at", insertable = false)
	private Timestamp deletadoEm;
	
	@Column(insertable = false, nullable = false)
	private Boolean deletado;
	
	@Convert(converter = ConversorEnumNomeBucket.class)
	@Column(nullable = false, unique = true)
	private NomeBucket nome;
	
	@Column(name = "tempo_expiracao_upload_em_segundos", nullable = false, columnDefinition = "DEFAULT '30'")
	private Integer tempoLimiteUpload;
	
	@Column(nullable = false, columnDefinition = "DEFAULT '1'")
	private Long tamanhoMinimo;

	@Column(nullable = false, columnDefinition = "DEFAULT '1073741824'")
	private Long tamanhoMaximo;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the criadoEm
	 */
	public Timestamp getCriadoEm() {
		return criadoEm;
	}

	/**
	 * @param criadoEm the criadoEm to set
	 */
	public void setCriadoEm(Timestamp criadoEm) {
		this.criadoEm = criadoEm;
	}

	/**
	 * @return the atualizadoEm
	 */
	public Timestamp getAtualizadoEm() {
		return atualizadoEm;
	}

	/**
	 * @param atualizadoEm the atualizadoEm to set
	 */
	public void setAtualizadoEm(Timestamp atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	/**
	 * @return the deletadoEm
	 */
	public Timestamp getDeletadoEm() {
		return deletadoEm;
	}

	/**
	 * @param deletadoEm the deletadoEm to set
	 */
	public void setDeletadoEm(Timestamp deletadoEm) {
		this.deletadoEm = deletadoEm;
	}

	/**
	 * @return the deletado
	 */
	public Boolean getDeletado() {
		return deletado;
	}

	/**
	 * @param deletado the deletado to set
	 */
	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}

	/**
	 * @return the nome
	 */
	public NomeBucket getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(NomeBucket nome) {
		this.nome = nome;
	}

	/**
	 * @return the tempoLimiteUpload
	 */
	public Integer getTempoLimiteUpload() {
		return tempoLimiteUpload;
	}

	/**
	 * @param tempoLimiteUpload the tempoLimiteUpload to set
	 */
	public void setTempoLimiteUpload(Integer tempoLimiteUpload) {
		this.tempoLimiteUpload = tempoLimiteUpload;
	}

	/**
	 * @return the tamanhoMinimo
	 */
	public Long getTamanhoMinimo() {
		return tamanhoMinimo;
	}

	/**
	 * @param tamanhoMinimo the tamanhoMinimo to set
	 */
	public void setTamanhoMinimo(Long tamanhoMinimo) {
		this.tamanhoMinimo = tamanhoMinimo;
	}

	/**
	 * @return the tamanhoMaximo
	 */
	public Long getTamanhoMaximo() {
		return tamanhoMaximo;
	}

	/**
	 * @param tamanhoMaximo the tamanhoMaximo to set
	 */
	public void setTamanhoMaximo(Long tamanhoMaximo) {
		this.tamanhoMaximo = tamanhoMaximo;
	}
	
}