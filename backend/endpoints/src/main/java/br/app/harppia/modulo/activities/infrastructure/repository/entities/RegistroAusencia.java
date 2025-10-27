package br.app.harppia.modulo.activities.infrastructure.repository.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.schedule.enums.MotivoAusencia;
import br.app.harppia.modulo.usuario.domain.entities.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_registro_ausencia")
@Table(name = "tb_registro_ausencia", schema = "auth")
@Getter
@Setter
@ToString(of = {"id", "motivo", "justificativa", "periodo"})
public class RegistroAusencia {

	@SuppressWarnings("unused")
	private static long serialVersion = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// --------------//
	// DADOS DE LOG //
	// --------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt;

	@ManyToOne(optional = false)
	@JoinColumn(name = "created_by_lev", nullable = false)
	private Usuario createdByLev;

	// -------------------//
	// DADOS DA AUSÊNCIA //
	// -------------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;

	@Column(name = "motivo", nullable = false)
	private MotivoAusencia motivo;

	@Column(name = "outro_motivo")
	private String outroMotivo;

	@Column(name = "justificativa", nullable = false)
	private String justificativa;

	// Este campo representa um TSTZRANGE no PostgreSQL, portanto, deve ser algo
	// como: "[\"2025-10-06 00:00:00+00\",\"2025-10-07 23:59:59+00\")"
	@Column(name = "periodo", nullable = false)
	private String periodo;

	// -----//
	// FKs //
	// -----//
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "schema_auth_usuario_lev")
	private Usuario usuario;
}
