package br.app.harppia.modulo.userconfig.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.userconfig.infrastructure.repository.enums.ECorTema;
import br.app.harppia.modulo.userconfig.infrastructure.repository.enums.ETipoDaltonismo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_visual")
@Table(name = "tb_visual", schema = "accessibility")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "updatedAt", "tema", "modoDaltonismo"})
@EqualsAndHashCode(of = "id")
public class AcessibilidadeVisual {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 3L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "cor_tema", nullable = false)
	private ECorTema tema = ECorTema.ESCURO;
	
	@Column(name = "tamanho_texto", nullable = false)
	private Character tamanhoTexto = '3';

	@Column(name = "negrito", nullable = false)
	private Boolean negrito = false;
	
	@Column(name = "alto_contraste", nullable = false)
	private Boolean altoContraste = false;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "modo_daltonismo", nullable = false)
	private ETipoDaltonismo modoDaltonismo = ETipoDaltonismo.TRICOMATA;
	
	@Column(name = "intensidade_daltonismo", nullable = false)
	private Character intensidadeDaltonismo = '3';

	@Column(name = "remover_animacoes", nullable = false)
	private Boolean removerAnimacoes = false;
	
	@Column(name = "vibrar_ao_tocar", nullable = false)
	private Boolean vibrarAoTocar = false;
}
