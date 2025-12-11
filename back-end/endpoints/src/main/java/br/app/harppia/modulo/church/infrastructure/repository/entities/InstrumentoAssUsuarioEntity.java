package br.app.harppia.modulo.church.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_instrumento_ass_usuario")
@Table(name = "tb_instrumento_ass_usuario", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "idInstrumento", "idDonoInstrumento"})
@EqualsAndHashCode(of = "id")
public class InstrumentoAssUsuarioEntity {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@Generated(event = EventType.INSERT)
	@Column(name = "id", insertable = false, updatable = false)
	private Integer id;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Column(name = "created_at", insertable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	@Column(name = "updated_at", insertable = false)
	private OffsetDateTime updatedAt;
	
	@Column(name = "deleted_at", insertable = false)
	private OffsetDateTime deletedAt;

	//---------------------//
	// DADOS DA ASSOCIAÇÃO //
	//---------------------//
	@Column(name = "is_deleted", insertable = false)
	private Boolean isDeleted;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "ins_id", nullable = false)
	private Long idInstrumento;

	@Column(name = "s_auth_t_tb_usuario_c_lev", nullable = false)
	private UUID idDonoInstrumento;
}
