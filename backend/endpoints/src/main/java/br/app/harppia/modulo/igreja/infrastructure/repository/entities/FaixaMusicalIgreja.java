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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
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
public class FaixaMusicalIgreja {
	
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

    @Column(name = "deleted_at", insertable = false)
    private OffsetDateTime deletedAt;

    @Column(name = "disabled_at", insertable = false)
    private OffsetDateTime disabledAt;

    //--------------//
    // DADOS GERAIS //
    //--------------//
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "is_disabled", nullable = false)
    private Boolean isDisabled = false;

    @Lob
    @Column(name = "snapshot")
    private String snapshot;

    //-----//
    // FKs //
    //-----//
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igr_uuid", nullable = false)
    private Igreja igreja;

    @Column(name = "s_song_t_tb_musica_c_mus")
    private UUID idMusica;

    @Column(name = "s_song_t_tb_medley_c_med")
    private UUID idMedley;
}
