package br.app.harppia.modulo.igreja.infrastructure.repository.entities;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
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
    private static final long serialVersionUID = 1L;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_min", nullable = false)
    private UsuarioEntity createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by_min")
    private UsuarioEntity deletedBy;

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
    private FaixaMusicalIgreja faixa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    private CategoriaAgendamento categoria;
}
