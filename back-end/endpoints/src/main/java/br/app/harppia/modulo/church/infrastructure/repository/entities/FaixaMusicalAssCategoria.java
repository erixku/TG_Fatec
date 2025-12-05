package br.app.harppia.modulo.church.infrastructure.repository.entities;

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

@Entity(name = "tb_faixa_ass_categoria")
@Table(name = "tb_faixa_ass_categoria", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "isDeleted", "faixa", "categoria"})
@EqualsAndHashCode(of = "id")
public class FaixaMusicalAssCategoria {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //---------------//
    // DADOS DE LOGS //
    //---------------//
    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    //---------------------//
    // DADOS DA ASSOCIAÇÃO //
    //---------------------//
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    //-----//
    // FKs //
    //-----//
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fai_id", nullable = false)
    private FaixaMusicalIgrejaEntity faixa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    private CategoriaAgendamentoEntity categoria;
}
