package br.app.harppia.modulo.shared.entity.church;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.hibernate.validator.constraints.UUID;

import br.app.harppia.modulo.shared.entity.church.enums.EDenominacaoIgreja;
import br.app.harppia.modulo.shared.entity.storage.Arquivo;
import br.app.harppia.modulo.usuario.domain.entities.Usuario;
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

@Entity(name = "tb_igreja")
@Table(name = "tb_igreja", schema = "church")
@Getter
@Setter
@ToString(of = {"uuid", "cnpj", "nome", "proprietario"})
@EqualsAndHashCode(of = "uuid")
public class Igreja {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid; 
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false)
	private OffsetDateTime updatedAt;

	@Column()
	private OffsetDateTime deletedAt = null;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Usuario createdByAdm;

	@OneToOne(fetch = FetchType.LAZY)	
	@JoinColumn(nullable = false)
	private Usuario updatedByAdm;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Usuario deletedByAdm = null;

	//-----------------//
	// DADOS DA IGREJA //
	//-----------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;

	@Column(nullable = false)
	private String cnpj;
	
	@Column(nullable = false)	
	private String nome;
	
	@Column(nullable = false)
	private EDenominacaoIgreja denominacao;
	
	@Column
	private String outraDenominacao;
	
	//-----//
	// FKs //
	//-----//
	@JoinColumn(name = "s_storage_t_tb_arquivo_c_foto", nullable = false)
	private Arquivo foto;

	@JoinColumn(name = "s_auth_t_tb_usuario_c_adm_proprietario", nullable = false)
	private Usuario proprietario;
}
