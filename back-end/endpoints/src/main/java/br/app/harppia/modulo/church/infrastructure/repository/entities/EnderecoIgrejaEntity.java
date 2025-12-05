package br.app.harppia.modulo.church.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidade que representa um endereço físico associado a uma Igreja.
 * Esta classe implementa as melhores práticas de mapeamento JPA/Hibernate,
 * incluindo o tratamento de colunas gerenciadas pelo banco de dados,
 * relacionamentos, e a implementação correta de equals(), hashCode() e toString().
 */
@Entity
@Table(name = "tb_endereco", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "logradouro", "cidade", "uf"})
@EqualsAndHashCode(of = "id")
public class EnderecoIgrejaEntity  {

	@SuppressWarnings("unused")
    private static final long serialVersionUID = 3L;

    @Id
    @Generated(event = EventType.INSERT)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Generated(event = EventType.INSERT)
    @Column(name = "updated_at", insertable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", insertable = false)
    private OffsetDateTime deletedAt = null;

    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;

    @Column(name = "deleted_by", insertable = false)
    private UUID deletedBy;

    //-------------------//
    // DADOS DO ENDERECO //
    //-------------------//
    @Column(name = "is_deleted", insertable = false)
    private Boolean isDeleted;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "uf", nullable = false)
    private String uf;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "is_endereco_principal", nullable = false)
    private Boolean isPrincipal;
    
    //-----//
    // FKs //
    //-----//
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igr_id", nullable = false)
    private IgrejaEntity igreja;
}