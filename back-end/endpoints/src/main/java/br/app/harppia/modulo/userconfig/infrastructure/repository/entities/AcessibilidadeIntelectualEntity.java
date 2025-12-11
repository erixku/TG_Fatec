package br.app.harppia.modulo.userconfig.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Nesta entidade são armazenadas as configurações pessoais acerca da acessibilidade visual.
 * Esta classe é uma representação da tabela, por isso é anêmica.
 */
@Entity(name = "tb_intelectual")
@Table(name = "tb_intelectual", schema = "accessibility")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"idDonoConfig", "updatedAt"})
@EqualsAndHashCode(of = "idDonoConfig")
public class AcessibilidadeIntelectualEntity {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 5L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false)
	private UUID idDonoConfig;
	
	@Column(name = "updated_at", insertable = false)
	private OffsetDateTime updatedAt;
	
	@Column(name = "tamanho_icone", nullable = false, insertable = false)
	private Character tamanhoIcone;
	
	@Column(name = "modo_foco", nullable = false, insertable = false)
	private Boolean modoFoco;

	@Column(name = "feedback_imediato", nullable = false, insertable = false)
	private Boolean feedbackImediato;
}
