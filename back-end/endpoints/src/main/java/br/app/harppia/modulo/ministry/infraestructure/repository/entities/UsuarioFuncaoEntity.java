package br.app.harppia.modulo.ministry.infraestructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.ministry.infraestructure.converter.ConversorEnumFuncaoMembro;
import br.app.harppia.modulo.ministry.infraestructure.repository.enums.EFuncaoMembro;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class UsuarioFuncaoEntity {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 3L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //---------------//
    // DADOS DE LOGS //
    //---------------//
    @Generated(event = EventType.INSERT)
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    //----------------------------//
    // DADOS DE FUNÇÃO DO USUÁRIO //
    //----------------------------//
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Convert(converter = ConversorEnumFuncaoMembro.class)
    @ColumnTransformer(write = "CAST(? AS utils.s_church_t_tb_usuario_funcao_e_funcao)")
    @Column(name = "funcao", nullable = false)
    private EFuncaoMembro funcao;

    //-----------//
    // CHAVES FK //
    //-----------//
    @Column(name = "min_lou_id", nullable = false)
    private UUID idMinisterio;

    @Column(name = "s_auth_t_tb_usuario_c_lev", nullable = false)
    private UUID idLevita;
}
