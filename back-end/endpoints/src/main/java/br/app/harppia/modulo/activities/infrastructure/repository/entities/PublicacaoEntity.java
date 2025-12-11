package br.app.harppia.modulo.activities.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.activities.infrastructure.converter.ConversorEnumTipoPublicacao;
import br.app.harppia.modulo.activities.infrastructure.repository.enums.ETipoPublicacao;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_publicacao")
@Table(name = "tb_publicacao", schema = "schedule")
@Getter
@Setter
@ToString(of = {"id", "updatedAt", "createdBy", "titulo", "descricao"})
@EqualsAndHashCode(of = "id")
public class PublicacaoEntity {
	
	@SuppressWarnings("unused")
	private static long serialVersion = 2L;
	
	@Id
	@Generated(event = EventType.INSERT)
	@Column(name = "id", insertable = false, updatable = false)
	private Long id;

	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Column(name = "created_at", insertable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	@Column(name = "updated_at", insertable = false)
	private OffsetDateTime updatedAt;
	
	@Column(name = "deleted_at", insertable = false)
	private OffsetDateTime deletedAt;
	
	@Column(name = "created_by", nullable = false, updatable = false)
	private UUID createdBy;
	
	@Column(name = "updated_by", nullable = false)
	private UUID updatedBy;
	
	@Column(name = "deleted_by", insertable = false)
	private UUID deletedBy;
	
	//---------------------//
	// DADOS DA PUBLICAÇÃO //
	//---------------------//
	@Column(name = "is_deleted", insertable = false)
	private Boolean isDeleted;
	
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Convert(converter = ConversorEnumTipoPublicacao.class)
	@Column(name = "tipo", nullable = false)
	@ColumnTransformer(write = "CAST(? AS utils.s_schedule_t_tb_publicacao_e_tipo)")
	private ETipoPublicacao tipo;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	//-----//
	// FKs //
	//-----//
	@Column(name = "s_church_t_tb_igreja_c_igreja", nullable = false, updatable = false)
	private UUID idIgreja;

	@Column(name = "s_church_t_tb_ministerio_louvor_c_min_lou", nullable = false, updatable = false)
	private UUID idMinisterio;
}
