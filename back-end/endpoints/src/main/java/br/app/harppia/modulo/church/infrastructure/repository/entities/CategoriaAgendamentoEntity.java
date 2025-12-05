package br.app.harppia.modulo.church.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.church.infrastructure.repository.enums.ETipoAtividade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity(name = "tb_categoria")
@Table(name = "tb_categoria", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "tipo", "nome"})
@EqualsAndHashCode(of = "id")
public class CategoriaAgendamentoEntity {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //---------------//
    // DADOS DE LOGS //
    //---------------//
    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Generated(event = EventType.INSERT)
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "disabled_at")
    private OffsetDateTime disabledAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    //---------------------------//
    // DADOS DA CATEGORIA IGREJA //
    //---------------------------//
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "is_disabled", nullable = false)
    private Boolean isDisabled = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private ETipoAtividade tipo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    //-----//
    // FKs //
    //-----//
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igr_id", nullable = false)
    private IgrejaEntity igreja;
}
