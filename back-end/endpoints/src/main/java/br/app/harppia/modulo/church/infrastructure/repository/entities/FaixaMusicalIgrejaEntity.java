package br.app.harppia.modulo.church.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.generator.EventType;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_faixa")
@Table(name = "tb_faixa", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "isDeleted", "isDisabled"})
@EqualsAndHashCode(of = "id")
public class FaixaMusicalIgrejaEntity {
	
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    //---------------//
    // DADOS DE LOGS //
    //---------------//
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Generated(event = EventType.INSERT)
    @Column(name = "updated_at", insertable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", insertable = false)
    private OffsetDateTime deletedAt;

    @Column(name = "disabled_at", insertable = false)
    private OffsetDateTime disabledAt;

    //--------------//
    // DADOS GERAIS //
    //--------------//
    @Column(name = "is_deleted", insertable = false)
    private Boolean isDeleted;

    @Column(name = "is_disabled", insertable = false)
    private Boolean isDisabled;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "snapshot_inicial", nullable = false)
    private String snapshot;

    //-----//
    // FKs //
    //-----//
    @Column(name = "igr_id", nullable = false)
    private UUID idIgreja;

    @Column(name = "s_song_t_tb_musica_c_mus")
    private Integer idMusica;

    @Column(name = "s_song_t_tb_medley_c_med")
    private Integer idMedley;
}
