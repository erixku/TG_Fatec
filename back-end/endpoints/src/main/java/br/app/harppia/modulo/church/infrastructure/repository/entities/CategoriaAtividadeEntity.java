package br.app.harppia.modulo.church.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.activities.infrastructure.converter.ConversorEnumTipoAtividade;
import br.app.harppia.modulo.church.infrastructure.repository.enums.ETipoAtividade;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_categoria")
@Table(name = "tb_categoria", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "tipo", "nome"})
@EqualsAndHashCode(of = "id")
public class CategoriaAtividadeEntity {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2L;

    @Id
    @Generated(event = EventType.INSERT)
    @Column(name = "id", insertable = false, updatable = false)
    private Integer id;

    //---------------//
    // DADOS DE LOGS //
    //---------------//
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", insertable = false)
    private OffsetDateTime deletedAt;

    @Column(name = "disabled_at", insertable = false)
    private OffsetDateTime disabledAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;

    @Column(name = "deleted_by", insertable = false)
    private UUID deletedBy;

    //---------------------------//
    // DADOS DA CATEGORIA IGREJA //
    //---------------------------//
    @Column(name = "is_deleted", insertable = false)
    private Boolean isDeleted;

    @Column(name = "is_disabled", insertable = false)
    private Boolean isDisabled;

    @Column(name = "tipo", nullable = false)
    @Convert(converter = ConversorEnumTipoAtividade.class)
    @ColumnTransformer(write = "CAST (? AS utils.s_church_t_tb_categoria_e_tipo)")
    private ETipoAtividade tipo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    //-----//
    // FKs //
    //-----//
    @Column(name = "igr_id", nullable = false)
    private UUID idIgreja;
}
