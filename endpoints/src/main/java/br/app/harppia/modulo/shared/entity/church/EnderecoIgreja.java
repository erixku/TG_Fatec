package br.app.harppia.modulo.shared.entity.church;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.auth.Usuario;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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

    /**
     * Chave primária da tabela, gerenciada pelo banco de dados (IDENTITY).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Timestamp de criação, gerenciado pelo banco (DEFAULT CURRENT_TIMESTAMP).
     */
    @Generated(event = EventType.INSERT)
    @Column(nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    /**
     * Timestamp de atualização, gerenciado por triggers no banco.
     */
    @Generated(event = EventType.INSERT)
    @Column(nullable = false, insertable = false)
    private OffsetDateTime updatedAt;

    /**
     * Timestamp de exclusão lógica (soft delete).
     * Este campo é nulo por padrão e gerenciado pela lógica da aplicação.
     */
    @Column
    private LocalDateTime deletedAt;

    /**
     * Relacionamento com o usuário que criou o registro. Apenas inserível.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Usuario createdByAdm;

    /**
     * Relacionamento com o usuário que atualizou o registro.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Usuario updatedByAdm;

    /**
     * Relacionamento com o usuário que realizou a exclusão lógica.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Usuario deletedByAdm;


    // --- Dados do Endereço ---

    /**
     * Flag de exclusão lógica (soft delete).
     * O valor padrão 'false' é definido no DDL e na inicialização do objeto.
     */
    @Column(nullable = false)
    @ColumnDefault("false")
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

    @Column()
    private String complemento;

    @Column(name = "is_endereco_principal", nullable = false)
    private boolean principal;

    // --- Chaves Estrangeiras ---

    /**
     * Relacionamento com a Igreja à qual este endereço pertence
     * Este é o lado "dono" da relação Many-to-One
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igr_uuid", nullable = false)
    private Igreja igreja;
}