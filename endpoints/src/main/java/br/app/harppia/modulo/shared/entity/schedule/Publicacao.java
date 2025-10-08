package br.app.harppia.modulo.shared.entity.schedule;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.auth.Usuario;
import br.app.harppia.modulo.shared.entity.church.Igreja;
import br.app.harppia.modulo.shared.entity.schedule.enums.TipoPublicacao;
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

@Entity(name = "tb_publicacao")
@Table(name = "tb_publicacao", schema = "schedule")
@Getter
@Setter
@ToString(of = {"id", "updatedAt", "createdByLev", "titulo", "descricao"})
@EqualsAndHashCode(of = "id")
public class Publicacao {
	
	@SuppressWarnings("unused")
	private static long serialVersion = 1L;
	
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
	@Column(name = "updated_at", nullable = false, insertable = false)
	private OffsetDateTime updatedAt;
	
	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by_lev", nullable = false, updatable = false)
	private Usuario createdByLev;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "updated_by_lev", nullable = false)
	private Usuario updatedByLev;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "deleted_by_lev")
	private Usuario deletedByLev;
	
	//---------------------//
	// DADOS DA PUBLICAÇÃO //
	//---------------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;
	
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Column(name = "tipo", nullable = false)
	private TipoPublicacao tipo;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	//-----//
	// FKs //
	//-----//
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "s_church_t_tb_igreja_c_igreja", nullable = false)
	private Igreja igreja;
}
