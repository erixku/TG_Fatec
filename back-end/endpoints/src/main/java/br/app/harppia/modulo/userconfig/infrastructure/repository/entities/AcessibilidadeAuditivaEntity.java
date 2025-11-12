package br.app.harppia.modulo.userconfig.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_auditiva")
@Table(name = "tb_auditiva", schema = "accessibility")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"idDonoConfig"})
@EqualsAndHashCode(of = {"idDonoConfig"})
public class AcessibilidadeAuditivaEntity {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 5L;
	
	@Id
	@Column(name = "id", nullable = false)
	@Generated(event = EventType.INSERT)
	private UUID idDonoConfig;
	
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;
	
	@Column(name = "modo_flash", nullable = false, insertable = false)
	private Boolean modoFlash;

	@Column(name = "intensidade_flash", nullable = false, insertable = false)
	private Character intensidadeFlash;
	
	@Column(name = "transcricao_audio", nullable = false, insertable = false)
	private Boolean transcricaoAudio;
	
	@Column(name = "vibracao_aprimorada", nullable = false, insertable = false)
	private Boolean vibracaoAprimorada;

	@Column(name = "alertas_visuais", nullable = false, insertable = false)
	private Boolean alertasVisuais;
}
