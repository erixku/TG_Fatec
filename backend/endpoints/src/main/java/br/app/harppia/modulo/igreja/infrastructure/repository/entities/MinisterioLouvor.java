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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_ministerio_louvor")
@Table(name = "tb_ministerio_louvor", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "nome", "codigo"})
@EqualsAndHashCode(of = "id")
public class MinisterioLouvor {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2L;

    //----------------//
    // CHAVE PRIMÁRIA //
    //----------------//
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
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    //-------------------------------//
    // DADOS DO MINISTÉRIO DE LOUVOR //
    //-------------------------------//
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "codigo", nullable = false, insertable = false, updatable = false)
    private String codigo;

    //-----//
    // FKs //
    //-----//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igr_id", nullable = false)
    private Igreja igreja;

    @Column(name = "s_storage_t_tb_arquivo_c_foto")
    private UUID idFoto;
}