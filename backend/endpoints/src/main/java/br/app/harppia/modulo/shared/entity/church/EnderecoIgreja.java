package br.app.harppia.modulo.shared.entity.church;

import java.io.Serializable;
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
public class EnderecoIgreja implements Serializable {

    /**
     * Identificador único para a serialização da classe.
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Generated(event = EventType.INSERT)
    @Column(nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Generated(event = EventType.INSERT)
    @Column(nullable = false, insertable = false)
    private OffsetDateTime updatedAt;

    @Column
    private OffsetDateTime deletedAt = null;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private UsuarioEntity createdByAdm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UsuarioEntity updatedByAdm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private UsuarioEntity deletedByAdm;

    //-------------------//
    // DADOS DO ENDERECO //
    //-------------------//
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String uf;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    @Column
    private String complemento;

    @Column(name = "is_endereco_principal", nullable = false)
    private Boolean isPrincipal;
    
    //-----//
    // FKs //
    //-----//
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igr_uuid", nullable = false)
    private Igreja igreja;
}