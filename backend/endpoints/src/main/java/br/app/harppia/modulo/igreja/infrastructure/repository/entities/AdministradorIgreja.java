package br.app.harppia.modulo.igreja.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class AdministradorIgreja {

	@SuppressWarnings("unused")
	private static long serialVersion = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column
	private OffsetDateTime deletedAt;
	
	@Column(nullable = false)
	private UUID createdByAdm;
	
	@Column
	private UUID deletedByAdm;
	
	//----------------//
	// DADOS DO ADMIN //
	//----------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	//-----//
	// FKs //
	//-----//
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "igr_uuid", nullable = false)
	private Igreja igreja;
	
	@Column(name = "s_auth_t_tb_usuario_c_adm", nullable = false)
	private UUID idAdmin;
}
