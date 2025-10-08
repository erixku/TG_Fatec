package br.app.harppia.modulo.shared.entity.church;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.auth.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_instrumento_ass_usuario")
@Table(name = "tb_instrumento_ass_usuario", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "instrumento", "donoInstrumento"})
@EqualsAndHashCode(of = "id")
public class InstrumentoAssUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//--------------//
	// DADOS DE LOG //
	//--------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;
	
	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt;

	//---------------------//
	// DADOS DA ASSOCIAÇÃO //
	//---------------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	//-----//
	// FKs //
	//-----//
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ins_id", nullable = false)
	private Instrumento instrumento;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "s_auth_t_tb_usuario_c_lev", nullable = false)
	private Usuario donoInstrumento;
}
