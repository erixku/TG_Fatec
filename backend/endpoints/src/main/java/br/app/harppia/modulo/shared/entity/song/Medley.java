package br.app.harppia.modulo.shared.entity.song;

import java.time.OffsetDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.storage.Arquivo;
import br.app.harppia.modulo.usuario.domain.entities.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_medley")
@Table(name = "tb_medley", schema = "song")
@Getter
@Setter
@ToString(of = {"id", "nome", "quantidadeMusicas"})
@EqualsAndHashCode(of = "id")
public class Medley {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)	
	private OffsetDateTime updatedAt;
	
	@Column
	private OffsetDateTime deletedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by_usu")
	private Usuario createdBy;
	
	//-----------------//
	// DADOS DO MEDLEY //
	//-----------------//
	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Integer quantidadeMusicas;
	
	//-----//
	// FKs //
	//-----//
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "s_storage_t_tb_arquivo_c_foto")
	private Arquivo foto;
}
