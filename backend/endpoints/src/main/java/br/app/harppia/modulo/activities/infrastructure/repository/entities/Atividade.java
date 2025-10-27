package br.app.harppia.modulo.activities.infrastructure.repository.entities;

import java.util.UUID;

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

@Entity(name = "tb_atividade")
@Table(name = "tb_atividade", schema = "schedule")
@Getter
@Setter
@ToString(of = {"id", "publicacao", "periodo"})
@EqualsAndHashCode(of = "id")
public class Atividade {

	@SuppressWarnings("unused")
	private static long serialVersion = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//--------------------//
	// DADOS DA ATIVIDADE //
	//--------------------//
	@Column(name = "periodo", nullable = false)
	private String periodo;
	
	//--------------//
	// FOREIGN KEYS //
	//--------------//
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pub_id", nullable = false)
	private Publicacao publicacao;
	
	@Column(name = "s_church_t_tb_categoria_c_categoria", nullable = false)
	private UUID categoriaAtividade;
}
