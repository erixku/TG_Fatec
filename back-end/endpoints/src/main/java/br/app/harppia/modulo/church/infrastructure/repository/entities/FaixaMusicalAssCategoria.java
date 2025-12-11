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

@Entity(name = "tb_faixa_ass_categoria")
@Table(name = "tb_faixa_ass_categoria", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "isDeleted", "idFaixa", "idCategoria"})
@EqualsAndHashCode(of = "id")
public class FaixaMusicalAssCategoria {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2L;

    @Id
    @Generated(event = EventType.INSERT)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    //---------------//
    // DADOS DE LOGS //
    //---------------//
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at", insertable = false)
    private OffsetDateTime deletedAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "deleted_by", insertable = false)
    private UUID deletedBy;

    //---------------------//
    // DADOS DA ASSOCIAÇÃO //
    //---------------------//
    @Column(name = "is_deleted", insertable = false)
    private Boolean isDeleted;

    //-----//
    // FKs //
    //-----//
    @Column(name = "fai_id", nullable = false)
    private Long idFaixa;

    @Column(name = "cat_id", nullable = false)
    private Integer idCategoria;
}
