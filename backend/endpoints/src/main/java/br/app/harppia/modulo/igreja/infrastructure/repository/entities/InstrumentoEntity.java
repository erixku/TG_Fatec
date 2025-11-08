package br.app.harppia.modulo.igreja.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.igreja.infrastructure.repository.enums.EFamiliaInstrumento;
import br.app.harppia.modulo.igreja.infrastructure.repository.enums.ENomeInstrumento;
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
public class InstrumentoEntity {

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
    private ModeloInstrumentoEntity modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igr_id", nullable = false)
    private IgrejaEntity igreja;

    @Column(name = "s_storage_t_tb_arquivo_c_foto", nullable = false)
    private UUID idFoto;

    @Column(name = "s_storage_t_tb_arquivo_c_icone", nullable = false)
    private UUID idIcone;
}
