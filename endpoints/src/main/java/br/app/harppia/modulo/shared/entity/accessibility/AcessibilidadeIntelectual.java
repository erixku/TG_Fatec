package br.app.harppia.modulo.shared.entity.accessibility;

import java.time.LocalDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.auth.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Nesta entidade são armazenadas as configurações pessoais acerca da acessibilidade visual.
 * Esta classe é uma representação da tabela, por isso é anêmica.
 */
@Entity(name = "tb_intelectual")
@Table(name = "tb_intelectual", schema = "acessibility")
@Getter
@Setter
@ToString(of = {"id", "updatedAt", "usuario"})
@EqualsAndHashCode(of = "id")
public class AcessibilidadeIntelectual {
	
	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Generated(event = EventType.INSERT)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	private LocalDateTime updatedAt;
	
	@Column(nullable = false)
	private Character tamanhoIcone = '3';
	
	@Column(nullable = false)
	private Boolean modoFoco = false;

	@Column(nullable = false)
	private Boolean feedbackImediato = false;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "s_auth_t_tb_usuario_c_usu", nullable = false)
	private Usuario usuario;
}
