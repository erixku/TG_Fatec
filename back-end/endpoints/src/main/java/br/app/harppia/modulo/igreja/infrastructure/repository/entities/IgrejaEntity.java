package br.app.harppia.modulo.igreja.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.igreja.infrastructure.repository.enums.EDenominacaoIgreja;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_igreja")
@Table(name = "tb_igreja", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "cnpj", "nome", "idProprietario"})
@EqualsAndHashCode(of = "id")
public class IgrejaEntity {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id; 
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;

	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt = null;

	@Column(name = "created_by", nullable = false, updatable = false)
	private UUID createdBy;

	@Column(name = "updated_by", nullable = false)
	private UUID updatedBy;
	
	@Column(name = "deleted_by")
	private UUID deletedBy = null;

	//-----------------//
	// DADOS DA IGREJA //
	//-----------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;

	@Column(name = "cnpj", nullable = false)
	private String cnpj;
	
	@Column(name = "nome", nullable = false)	
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "denominacao", nullable = false)
	private EDenominacaoIgreja denominacao;
	
	@Column(name = "outra_denominacao")
	private String outraDenominacao;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "s_storage_t_tb_arquivo_c_foto", nullable = false)
	private UUID idFoto;

	@Column(name = "s_auth_t_tb_usuario_c_adm_proprietario", nullable = false)
	private UUID idProprietario;
}
