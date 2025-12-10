package br.app.harppia.modulo.usuario.infrasctructure.repository.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.usuario.infrasctructure.annotation.ValidUF;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@ToString(of = {"usuarioDono", "uf", "cidade", "bairro"})
@EqualsAndHashCode(of = {"usuarioDono"})
public class EnderecoUsuarioEntity {
	
	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@OneToOne(optional = false)
	@JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UsuarioEntity usuarioDono;

	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", insertable = false)
	private OffsetDateTime updatedAt;
	
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
