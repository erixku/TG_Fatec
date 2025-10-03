package br.app.harppia.modulo.shared.entity.accessibility;

import java.time.LocalDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.accessibility.enums.CorTema;
import br.app.harppia.modulo.shared.entity.accessibility.enums.TipoDaltonismo;
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

@Entity(name = "tb_visual")
@Table(name = "tb_visual", schema = "acessibility")
@Getter
@Setter
@ToString(of = {"id"})
@EqualsAndHashCode(of = "id")
public class AcessibilidadeVisual {

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
	private LocalDateTime updatedAt;
	
	@Column(nullable = false)
	private CorTema tema = CorTema.ESCURO;
	
	@Column(nullable = false)
	private Character tamanhoTexto = '3';

	@Column(nullable = false)
	private Boolean negrito = false;
	
	@Column(nullable = false)
	private Boolean altoContraste = false;
	
	@Column(nullable = false)
	private TipoDaltonismo modoDaltonismo = TipoDaltonismo.TRICOMATA;
	
	@Column(nullable = false)
	private Character intensidadeDaltonismo = '3';

	@Column(nullable = false)
	private Boolean removerAnimacoes = false;
	
	@Column(nullable = false)
	private Boolean vibrarAoTocar = false;

	@Column(name = "s_auth_t_tb_usuario_c_usu", nullable = false)
	private Usuario usuario;
}
