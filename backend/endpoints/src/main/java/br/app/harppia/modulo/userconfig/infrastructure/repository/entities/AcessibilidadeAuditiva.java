package br.app.harppia.modulo.userconfig.infrastructure.repository.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
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

@Entity(name = "tb_auditiva")
@Table(name = "tb_auditiva", schema = "acessibility")
@Getter
@Setter
@ToString(of = {"usuario"})
@EqualsAndHashCode(of = {"usuario"})
public class AcessibilidadeAuditiva {
	
	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@OneToOne(optional = false)
	@JoinColumn(name = "s_auth_t_tb_usuario_c_usu", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UsuarioEntity usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private OffsetDateTime updateAt;
	
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
}
