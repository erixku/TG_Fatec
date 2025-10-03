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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_auditiva")
@Table(name = "tb_auditiva", schema = "acessibility")
@Getter
@Setter
@ToString(of = {"id", "usuario"})
@EqualsAndHashCode(of = {"id"})
public class AcessibilidadeAuditiva {
	
	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private LocalDateTime updateAt;
	
	@Column(nullable = false)
	private Boolean modoFlash = false;

	@Column(nullable = false)
	private Character intensidadeFlash = '3';
	
	@Column(nullable = false)
	private Boolean transcricaoAudio = false;
	
	@Column(nullable = false)
	private Boolean vibracaoAprimorada = false;

	@Column(nullable = false)
	private Boolean alertasVisuais = false;

	@Column(name = "s_auth_t_tb_usuario_c_usu", nullable = false)
	private Usuario usuario;
}
