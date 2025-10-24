package br.app.harppia.modulo.shared.entity.church;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.file.infrastructure.repository.entities.ArquivoEntity;
import br.app.harppia.modulo.shared.entity.church.enums.EFamiliaInstrumento;
import br.app.harppia.modulo.shared.entity.church.enums.ENomeInstrumento;
import br.app.harppia.modulo.usuario.infrasctructure.repository.entities.UsuarioEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity(name = "tb_instrumento")
@Table(name = "tb_instrumento", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "nome", "familia"})
@EqualsAndHashCode(of = "id")
public class Instrumento {

    //----------------//
    // CHAVE PRIMÁRIA //
    //----------------//
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_adm", nullable = false)
    private UsuarioEntity createdByAdm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_adm", nullable = false)
    private UsuarioEntity updatedByAdm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by_adm")
    private UsuarioEntity deletedByAdm;

    //----------------------//
    // DADOS DO INSTRUMENTO //
    //----------------------//
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "is_disabled", nullable = false)
    private Boolean isDisabled = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome", nullable = false)
    private ENomeInstrumento nome;

    @Column(name = "outro_nome")
    private String outroNome;

    @Enumerated(EnumType.STRING)
    @Column(name = "familia", nullable = false)
    private EFamiliaInstrumento familia;

    @Column(name = "outra_marca")
    private String outraMarca;

    @Column(name = "outro_modelo")
    private String outroModelo;

    //-----//
    // FKs //
    //-----//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ins_mod_id")
    private ModeloInstrumento modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igr_uuid", nullable = false)
    private Igreja igreja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_storage_t_tb_arquivo_c_foto")
    private ArquivoEntity foto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_storage_t_tb_arquivo_c_icone", nullable = false)
    private ArquivoEntity icone;
}
