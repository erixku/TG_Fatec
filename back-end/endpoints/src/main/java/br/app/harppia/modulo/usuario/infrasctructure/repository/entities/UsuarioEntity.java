package br.app.harppia.modulo.usuario.infrasctructure.repository.entities;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@ToString(of = { "id", "cpf", "nome", "sobrenome", "email" })
@EqualsAndHashCode(of = "id")
public class UsuarioEntity implements UserDetails {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 3L;

	@Id
	@Generated(event = EventType.INSERT)
	@Column(columnDefinition = "uuid", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;

	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Column(name = "created_at", insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "deleted_at", insertable = false)
	private OffsetDateTime deletedAt;

	@Column(name = "last_access", insertable = false)
	private OffsetDateTime lastAccess;

	//------------------//
	// DADOS DO USUÁRIO //
	//------------------//
	@Column(name = "is_deleted", nullable = false, insertable = false)
	private Boolean isDeleted;

	@Column(name = "cpf", nullable = false, unique = true, updatable = false)
	private String cpf;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "sobrenome", nullable = false)
	private String sobrenome;

	@Column(name = "nome_social")
	private String nomeSocial;

	@Column(name = "sobrenome_social")
	private String sobrenomeSocial;

	@Column(name = "sexo", nullable = false)
	private Character sexo;

	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "telefone", nullable = false, unique = true)
	private String telefone;

	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "s_storage_t_tb_arquivo_c_foto")
	private UUID idFotoPerfil;

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