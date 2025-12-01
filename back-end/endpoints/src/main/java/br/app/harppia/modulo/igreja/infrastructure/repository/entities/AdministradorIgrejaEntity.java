package br.app.harppia.modulo.igreja.infrastructure.repository.entities;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_administrador")
@Table(name = "tb_administrador", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "isDeleted", "idAdmin"})
@EqualsAndHashCode(of = "id")
public class AdministradorIgrejaEntity {

	@SuppressWarnings("unused")
	private static long serialVersion = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt;
	
	@Column(name = "created_by", nullable = false)
	private UUID createdBy;
	
	@Column(name = "deleted_by")
	private UUID deletedBy;
	
	//----------------//
	// DADOS DO ADMIN //
	//----------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "igr_id", nullable = false)
	private UUID idIgreja;
	
	@Column(name = "s_auth_t_tb_usuario_c_adm", nullable = false)
	private UUID idAdmin;
}
