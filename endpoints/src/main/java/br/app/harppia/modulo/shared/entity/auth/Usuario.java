package br.app.harppia.modulo.shared.entity.auth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.app.harppia.modulo.shared.entity.storage.Arquivo;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * Essa classe representa a estrutura da tabela de usuários do banco de dados.
 * 
 * @since 31/08/2025
 * @author Lucas Souza
 */
@Entity(name = "tb_usuario")
@Table(name = "tb_usuario", schema = "auth")
@Getter
@Setter
@ToString(of = { "uuid", "cpf", "nome", "sobrenome", "email" })
@EqualsAndHashCode(of = "uuid")
public class Usuario implements UserDetails {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "uuid", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID uuid;

	// DADOS DE LOG //
	@Generated(event = EventType.INSERT)
	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private LocalDateTime updatedAt;

	@Column
	private LocalDateTime deletedAt = null;

	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private LocalDateTime lastAccess;

	// DADOS DO USUÁRIO //
	@Column(nullable = false)
	private Boolean isDeleted = false;

	@Column(nullable = false, unique = true, updatable = false)
	private String cpf;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String sobrenome;

	@Column
	private String nomeSocial;

	@Column
	private String sobrenomeSocial;

	@Column(nullable = false)
	private Character sexo;

	@Column(nullable = false)
	private LocalDate dataNascimento;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true)
	private String telefone;

	@Column(nullable = false)
	@JsonIgnore
	private String senha;

	@OneToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "end_id", nullable = false)
	private EnderecoUsuario endereco;

	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "s_storage_t_tb_arquivo_c_foto", referencedColumnName = "uuid")
	private Arquivo fotoPerfil;

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
}