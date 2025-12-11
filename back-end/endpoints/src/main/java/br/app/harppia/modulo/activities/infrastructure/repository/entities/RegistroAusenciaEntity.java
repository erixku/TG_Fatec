package br.app.harppia.modulo.activities.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.activities.infrastructure.converter.ConversorEnumMotivoAusencia;
import br.app.harppia.modulo.activities.infrastructure.repository.enums.EMotivoAusencia;
import io.hypersistence.utils.hibernate.type.range.Range;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_registro_ausencia")
@Table(name = "tb_registro_ausencia", schema = "schedule")
@Getter
@Setter
@ToString(of = {"id", "motivo", "justificativa", "periodo"})
public class RegistroAusenciaEntity {

	@SuppressWarnings("unused")
	private static long serialVersion = 2L;

	@Id
	@Generated(event = EventType.INSERT)
	@Column(name = "id", insertable = false, updatable = false)
	private Integer id;

	// --------------//
	// DADOS DE LOG //
	// --------------//
	@Column(name = "created_at", insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Column(name = "updated_at", insertable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "deleted_at", insertable = false)
	private OffsetDateTime deletedAt;

	@Column(name = "created_by", nullable = false, updatable = false)
	private UUID createdBy;

	// ------------------//
	// DADOS DA AUSÊNCIA //
	// ------------------//
	@Column(name = "is_deleted", insertable = false)
	private Boolean isDeleted;

	@Convert(converter = ConversorEnumMotivoAusencia.class)
	@Column(name = "motivo", nullable = false)
	@ColumnTransformer(write = "CAST(? AS utils.s_schedule_t_tb_registro_ausencia_e_motivo)")
	private EMotivoAusencia motivo;

	@Column(name = "outro_motivo")
	private String outroMotivo;

	@Column(name = "justificativa", nullable = false)
	private String justificativa;

	// Este campo representa um TSTZRANGE no PostgreSQL, portanto, deve ser algo
	// como: "[\"2025-10-06 00:00:00+00\",\"2025-10-07 23:59:59+00\")"
	@Column(name = "periodo", nullable = false)
	private Range<OffsetDateTime> periodo;

	// ------------ //
	// Foreign Keys //
	// ------------ //
	@Column(name = "s_church_t_tb_igreja_c_igreja", nullable = false, updatable = false)
	private UUID idIgreja;
}
