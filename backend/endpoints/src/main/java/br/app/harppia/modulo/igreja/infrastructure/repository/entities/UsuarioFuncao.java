package br.app.harppia.modulo.igreja.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.igreja.infrastructure.repository.enums.EFuncaoUsuario;
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

@Entity(name = "tb_usuario_funcao")
@Table(name = "tb_usuario_funcao", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "funcao", "isDeleted"})
@EqualsAndHashCode(of = "id")
public class UsuarioFuncao {

    //----------------//
    // CHAVE PRIMÁRIA //
    //----------------//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //---------------//
    // DADOS DE LOGS //
    //---------------//
    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "created_by_lid", nullable = false)
    private UUID createdBy;

    @Column(name = "deleted_by_lid")
    private UUID deletedBy;

    //----------------------------//
    // DADOS DE FUNÇÃO DO USUÁRIO //
    //----------------------------//
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "funcao", nullable = false)
    private EFuncaoUsuario funcao;

    //-----------//
    // CHAVES FK //
    //-----------//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "min_lou_uuid", nullable = false)
    private MinisterioLouvor ministerioLouvor;

    @Column(name = "s_auth_t_tb_usuario_c_lev", nullable = false)
    private UUID idLevita;
}
