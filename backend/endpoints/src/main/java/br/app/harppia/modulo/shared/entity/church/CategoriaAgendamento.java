package br.app.harppia.modulo.shared.entity.church;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.church.enums.ETipoAtividade;
import br.app.harppia.modulo.usuario.domain.entities.Usuario;
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
public class CategoriaAgendamento {

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

    @Generated(event = EventType.INSERT)
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "disabled_at")
    private OffsetDateTime disabledAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_adm", nullable = false)
    private Usuario createdByAdm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_adm", nullable = false)
    private Usuario updatedByAdm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by_adm")
    private Usuario deletedByAdm;

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
    @JoinColumn(name = "igr_uuid", nullable = false)
    private Igreja igreja;
}
