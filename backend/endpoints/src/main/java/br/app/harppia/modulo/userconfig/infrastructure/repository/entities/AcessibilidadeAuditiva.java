package br.app.harppia.modulo.userconfig.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class AcessibilidadeAuditiva {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 3L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updateAt;
	
	@Column(name = "modo_flash", nullable = false)
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
