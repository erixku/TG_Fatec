package br.app.harppia.modulo.church.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.church.infrastructure.converter.ConversorEnumFamiliaInstrumento;
import br.app.harppia.modulo.church.infrastructure.converter.ConversorEnumNomeInstrumento;
import br.app.harppia.modulo.church.infrastructure.repository.enums.EFamiliaInstrumento;
import br.app.harppia.modulo.church.infrastructure.repository.enums.ENomeInstrumento;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Generated(event = EventType.INSERT)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

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

    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;

    @Column(name = "deleted_by", insertable = false)
    private UUID deletedBy;

    //----------------------//
    // DADOS DO INSTRUMENTO //
    //----------------------//
    @Column(name = "is_deleted", insertable = false, updatable = false)
    private Boolean isDeleted;

    @Column(name = "is_disabled", insertable = false, updatable = false)
    private Boolean isDisabled;

    @Convert(converter = ConversorEnumNomeInstrumento.class)
    @ColumnTransformer(write = "CAST(? AS utils.s_church_t_tb_instrumento_e_nome)")
    @Column(name = "nome", nullable = false)
    private ENomeInstrumento nome;

    @Column(name = "outro_nome")
    private String outroNome;

    @Convert(converter = ConversorEnumFamiliaInstrumento.class)
    @ColumnTransformer(write = "CAST(? AS utils.s_church_t_tb_instrumento_e_nome)")
    @Column(name = "familia", nullable = false)
    private EFamiliaInstrumento familia;

    @Column(name = "outra_marca")
    private String outraMarca;

    @Column(name = "outro_modelo")
    private String outroModelo;

    //-----//
    // FKs //
    //-----//
    @Column(name = "ins_mod_id", nullable = false)
    private Integer idModelo;

    @Column(name = "igr_id", nullable = false)
    private UUID idIgreja;

    @Column(name = "s_storage_t_tb_arquivo_c_foto", nullable = false)
    private UUID idFoto;

    @Column(name = "s_storage_t_tb_arquivo_c_icone", nullable = false)
    private UUID idIcone;
}
