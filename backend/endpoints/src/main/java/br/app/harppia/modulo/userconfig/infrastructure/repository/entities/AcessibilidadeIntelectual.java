package br.app.harppia.modulo.userconfig.infrastructure.repository.entities;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

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
@Table(name = "tb_intelectual", schema = "acessibility")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "updatedAt"})
@EqualsAndHashCode(of = "id")
public class AcessibilidadeIntelectual {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 4L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Generated(event = EventType.INSERT)
	@Column(insertable = false)
	private OffsetDateTime updatedAt;
	
	@Column(nullable = false)
	private Character tamanhoIcone = '3';
	
	@Column(nullable = false)
	private Boolean modoFoco = false;

	@Column(nullable = false)
	private Boolean feedbackImediato = false;
}
