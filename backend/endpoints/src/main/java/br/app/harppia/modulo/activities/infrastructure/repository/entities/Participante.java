package br.app.harppia.modulo.activities.infrastructure.repository.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.church.InstrumentoAssUsuario;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_participante")
@Table(name = "tb_participante", schema = "schedule")
@Getter
@Setter
@ToString(of = {"id", "updatedByLev", "participacaoConfirmada", "atividade"})
@EqualsAndHashCode(of = "id")
public class Participante {

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
	@JoinColumn(name = "created_by_lid", nullable = false)
	private Usuario createdByLid;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "updated_by_lev", nullable = false)
	private Usuario updatedByLev;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "deleted_by_lid")
	private Usuario deletedByLid;
	
	//---------------------//
	// DADOS DA PUBLICAÇÃO //
	//---------------------//
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted = false;

	@Column(name = "participacao_confirmada", nullable = false)
	private Boolean participacaoConfirmada = true;
	
	//-----//
	// FKs //
	//-----//
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ati_id", nullable = false)
	private Atividade atividade;
	
	// Papel a ser desempenhado pelo usuário. Ex: baterista, violinista, baixista, etc.
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "s_church_t_tb_instrumento_ass_usuario_c_funcao", nullable = false)
	private InstrumentoAssUsuario funcao;
}
