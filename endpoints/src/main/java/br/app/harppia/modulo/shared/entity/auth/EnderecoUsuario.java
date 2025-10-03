package br.app.harppia.modulo.shared.entity.auth;

import java.time.LocalDateTime;

import org.hibernate.annotations.Generated;

import br.app.harppia.defaults.custom.annotations.ValidUF;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Essa classe representa a arquitetura da tabela 'Endereco' do banco de dados.
 * 
 * @author asher_ren
 * @since 15/08/2025
 */
@Entity(name = "tb_endereco")
@Table(name = "tb_endereco", schema = "auth")
@Getter
@Setter
@ToString(of = {"id", "cep", "uf", "cidade"})
@EqualsAndHashCode(of = {"id"})
public class EnderecoUsuario {
	
	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Generated
	@Column(insertable = false)
	private LocalDateTime updatedAt;
	
	@Column(nullable = false)
	private String cep;
	
	@Column(nullable = false)
	@ValidUF
	private String uf;

	@Column(nullable = false)
	private String cidade;

	@Column(nullable = false)
	private String bairro;
	
	@Column(nullable = false)
	private String logradouro;
	
	@Column(nullable = false)
	private String numero;

	@Column(nullable = false)
	private String complemento;	
}
