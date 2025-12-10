package br.app.harppia.modulo.activities.infrastructure.repository.entities;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_atividade")
@Table(name = "tb_atividade", schema = "schedule")
@Getter
@Setter
@ToString(of = {"id", "idPublicacao", "periodo"})
@EqualsAndHashCode(of = "id")
public class AtividadeEntity {

	@SuppressWarnings("unused")
	private static long serialVersion = 3L;
	
	@Id
	@Generated(event = EventType.INSERT)
    @Column(name = "id", insertable = false)
	private Long id;
	
	//--------------------//
	// DADOS DA ATIVIDADE //
	//--------------------//
	@Column(name = "periodo", nullable = false, columnDefinition = "TSTZRANGE")
	@ColumnTransformer(write = "?::tstzrange")
	private String periodo;
	
	//--------------//
	// FOREIGN KEYS //
	//--------------//
	@Column(name = "pub_id", nullable = false)
	private Integer idPublicacao;
	
	@Column(name = "s_church_t_tb_categoria_c_categoria", nullable = false)
	private Integer idCategoria;
}
