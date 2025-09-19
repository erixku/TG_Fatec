package br.app.harppia.modulo.usuario.shared.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

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


/**
 * Essa classe representa a estrutura da tabela de usuários do banco de dados. 
 * Todas as constraints e regras de negócio (que envolvam a persistência dos dados - das tabelas)
 * devem ser registradas aqui.
 * 
 * @since 31/08/2025
 * @author Lucas Souza
 */
@Entity(name = "tb_usuario")
@Table(name = "tb_usuario", schema = "auth")
public class Usuario implements UserDetails {

	// Versão atual da classe para serialização dos objetos:
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "uuid", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID uuid;

	@Column(nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false, insertable = false)
	private LocalDateTime updatedAt;

	@Column(nullable = true, insertable = false)
	private LocalDateTime deletedAt;

	@Column(nullable = false, insertable = false)
	private LocalDateTime ultimoAcesso;

	@Column(name = "is_deletado", nullable = false, insertable = false)
	private LocalDateTime isDeleted;

	@Column(nullable = false, unique = true, updatable = false)
	private String cpf;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String sobrenome;

	@Column(nullable = true)
	private String nomeSocial;

	@Column(nullable = true)
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
	@Size(min = 8, max = 128)
	private String senha;

	@OneToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "end_id", nullable = false)
	private Endereco endId;

	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "s_storage_t_tb_arquivo_c_foto", referencedColumnName = "uuid")
	private Arquivo arquivoUUID;

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

	public LocalDateTime getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public LocalDateTime getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(LocalDateTime isDeletado) {
		this.isDeleted = isDeletado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	public String getSobrenomeSocial() {
		return sobrenomeSocial;
	}

	public void setSobrenomeSocial(String sobrenomeSocial) {
		this.sobrenomeSocial = sobrenomeSocial;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Endereco getEndId() {
		return endId;
	}

	public void setEndId(Endereco endId) {
		this.endId = endId;
	}

	public Arquivo getArquivoUUID() {
		return arquivoUUID;
	}

	public void setArquivoUUID(Arquivo arquivoUUID) {
		this.arquivoUUID = arquivoUUID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// =================================================================================
	    // --- ATENÇÃO: LÓGICA DE DERIVAÇÃO DE ROLE TEMPORÁRIA (DÍVIDA TÉCNICA) ---
	    // Esta lógica deve ser substituída por um campo 'role' no banco de dados
	    // antes de o sistema ir para produção.
	    // =================================================================================

	    // Defina e-mails específicos para testes que corresponderão a cada role
	    if ("admin@gmail.com".equalsIgnoreCase(this.email)) {
	        return List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));
	    }
	    
	    if ("lider@gmail.com".equalsIgnoreCase(this.email)) {
	        return List.of(new SimpleGrantedAuthority("ROLE_LIDER"));
	    }
	    
	    if ("ministro@.gmail.com".equalsIgnoreCase(this.email)) {
	        return List.of(new SimpleGrantedAuthority("ROLE_MINISTRO"));
	    }
	    
	    // Para todos os outros usuários, a role padrão será LEVITA
	    return List.of(new SimpleGrantedAuthority("ROLE_LEVITA"));
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
	public String toString() {
		System.out.println("==== DADOS DO USUÁRIO CONVERTIDO ====");
		System.out.println("CPF: " + this.getCpf());
		System.out.println("EMail: " + this.getEmail());
		System.out.println("Nome: " + this.getNome());
		System.out.println("Nome social: " + this.getNomeSocial());
		System.out.println("Senha: " + this.getPassword());
		System.out.println("Senha: " + this.getSenha());
		System.out.println("Sobrenome: " + this.getSobrenome());
		System.out.println("Sobrenome: " + this.getSobrenomeSocial());
		System.out.println("Telefone: " + this.getTelefone());
		System.out.println("Username: " + this.getUsername());
		System.out.println("Sexo: " + this.getSexo());
		
		System.out.println("Arquivo: " + this.getArquivoUUID());
		System.out.println("Bucket: " + this.getArquivoUUID().getBucket());
		System.out.println("Bucket id: " + this.getArquivoUUID().getBucket().getId());
		System.out.println("Bucket nome: " + this.getArquivoUUID().getBucket().getNome());
		
		
		System.out.println("Roles: " + this.getAuthorities());
		System.out.println("Created at: " + this.getCreatedAt());
		System.out.println("Data nasc: " + this.getDataNascimento());
		System.out.println("Deleted at: " + this.getDeletedAt());
		
		System.out.println("End id: " + this.getEndId().getId());
		System.out.println("End id: " + this.getEndId().getId());
		
		System.out.println("is deleted: " + this.getIsDeleted());
		System.out.println("Last access: " + this.getUltimoAcesso());
		System.out.println("Updated at: " + this.getUpdatedAt());
		System.out.println("UUID: " + this.getUuid());
		
		return "";
	}
}