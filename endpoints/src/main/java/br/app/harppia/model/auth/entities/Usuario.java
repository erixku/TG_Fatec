package br.app.harppia.endpoints.model.auth.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.app.harppia.endpoints.model.storage.entities.Arquivo;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "tb_usuario")
@Table(name = "tb_usuario", schema = "auth")
public class Usuario implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "uuid", unique = true, updatable = false, insertable = false, nullable = false)
	private UUID uuid;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime criadoEm;

	@Column(name = "updated_at", insertable = false)
	private LocalDateTime atualizadoEm;

	@Column(name = "deleted_at", insertable = false)
	private LocalDateTime deletadoEm;

	@Column(name = "ultimo_acesso", insertable = false)
	private LocalDateTime ultimoAcesso;

	@Column(insertable = false, nullable = false)
	private LocalDateTime deletado;

	@Column(unique = true, nullable = false)
	private String cpf;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String sobrenome;

	@Column(nullable = false)
	private String nomeSocial;

	@Column(nullable = false)
	private String sobrenomeSocial;

	@Column(nullable = false)
	private Character sexo;

	@Column(nullable = false)
	private LocalDate dataNascimento;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(unique = true, nullable = false)
	private String telefone;

	@Column(nullable = false)
	@JsonIgnore
	@Size(min = 128, max = 128)
	private String senha;

	@OneToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "end_id", nullable = false)
	private Endereco endId;

	@OneToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "schema_storage_arquivo_foto", referencedColumnName = "uuid", nullable = false)
	private Arquivo arquivoUUID;

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
	 * @return the atualizadoEm
	 */
	public LocalDateTime getAtualizadoEm() {
		return atualizadoEm;
	}

	/**
	 * @param atualizadoEm the atualizadoEm to set
	 */
	public void setAtualizadoEm(LocalDateTime atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
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
	 * @return the ultimoAcesso
	 */
	public LocalDateTime getUltimoAcesso() {
		return ultimoAcesso;
	}

	/**
	 * @param ultimoAcesso the ultimoAcesso to set
	 */
	public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
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
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return sobrenome;
	}

	/**
	 * @param sobrenome the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	/**
	 * @return the nomeSocial
	 */
	public String getNomeSocial() {
		return nomeSocial;
	}

	/**
	 * @param nomeSocial the nomeSocial to set
	 */
	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	/**
	 * @return the sobrenomeSocial
	 */
	public String getSobrenomeSocial() {
		return sobrenomeSocial;
	}

	/**
	 * @param sobrenomeSocial the sobrenomeSocial to set
	 */
	public void setSobrenomeSocial(String sobrenomeSocial) {
		this.sobrenomeSocial = sobrenomeSocial;
	}

	/**
	 * @return the sexo
	 */
	public Character getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the dataNascimento
	 */
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the endId
	 */
	public Endereco getEndId() {
		return endId;
	}

	/**
	 * @param endId the endId to set
	 */
	public void setEndId(Endereco endId) {
		this.endId = endId;
	}

	/**
	 * @return the arquivoUUID
	 */
	public Arquivo getArquivoUUID() {
		return arquivoUUID;
	}

	/**
	 * @param arquivoUUID the arquivoUUID to set
	 */
	public void setArquivoUUID(Arquivo arquivoUUID) {
		this.arquivoUUID = arquivoUUID;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
}