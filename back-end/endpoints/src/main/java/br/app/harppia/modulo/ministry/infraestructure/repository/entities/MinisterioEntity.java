package br.app.harppia.modulo.ministry.infraestructure.repository.entities;

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

@Entity(name = "tb_ministerio_louvor")
@Table(name = "tb_ministerio_louvor", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "nome", "codigo"})
@EqualsAndHashCode(of = "id")
public class MinisterioEntity {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 3L;

    //----------------//
    // CHAVE PRIMÁRIA //
    //----------------//
    @Id
    @Generated(event = EventType.INSERT)
    @Column(name = "id", insertable = false, updatable = false)
    private UUID id;

    //---------------//
    // DADOS DE LOGS //
    //---------------//
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", insertable = false)
    private OffsetDateTime deletedAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;

    @Column(name = "deleted_by", insertable = false)
    private UUID deletedBy;

    //-------------------------------//
    // DADOS DO MINISTÉRIO DE LOUVOR //
    //-------------------------------//
    @Column(name = "is_deleted", insertable = false)
    private Boolean isDeleted;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Generated(event = EventType.INSERT)
    @Column(name = "codigo", insertable = false, updatable = false)
    private String codigo;

    //-----//
    // FKs //
    //-----//
    @Column(name = "igr_id", nullable = false)
    private UUID idIgreja;

    @Column(name = "s_storage_t_tb_arquivo_c_foto")
    private UUID idFoto;
}