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
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@ToString(of = {"idDonoConfig", "updatedAt", "tema", "modoDaltonismo"})
@EqualsAndHashCode(of = "idDonoConfig")
public class AcessibilidadeVisualEntity {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 5L;
	
	@Id
	@Generated(event = EventType.INSERT)
	@Column(name = "id", nullable = false)
	private UUID idDonoConfig;
	
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "cor_tema", nullable = false, insertable = false)
	private ECorTema tema;
	
	@Column(name = "tamanho_texto", nullable = false, insertable = false)
	private Character tamanhoTexto;

	@Column(name = "negrito", nullable = false, insertable = false)
	private Boolean negrito;
	
	@Column(name = "alto_contraste", nullable = false, insertable = false)
	private Boolean altoContraste;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "modo_daltonismo", nullable = false, insertable = false)
	private ETipoDaltonismo modoDaltonismo;
	
	@Column(name = "intensidade_daltonismo", nullable = false, insertable = false)
	private Character intensidadeDaltonismo;

	@Column(name = "remover_animacoes", nullable = false, insertable = false)
	private Boolean removerAnimacoes;
	
	@Column(name = "vibrar_ao_tocar", nullable = false, insertable = false)
	private Boolean vibrarAoTocar;
}
