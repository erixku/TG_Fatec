package br.app.harppia.modulo.shared.entity.church;

import java.time.LocalDateTime;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import br.app.harppia.modulo.shared.entity.auth.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_endereco")
@Table(name = "tb_endereco", schema = "church")
@Getter
@Setter
@ToString(of = {"id", "createdAt", "isDeleted", "admin"})
@EqualsAndHashCode(of = "id")
public class AdministradorIgreja {

//	private static long serialVersion = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//---------------//
	// DADOS DE LOGS //
	//---------------//
	@Generated(event = EventType.INSERT)
	@Column(nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Generated(event = EventType.INSERT)
	@Column
	private LocalDateTime deletedAt;
	
	@JoinColumn(nullable = false)
	private Usuario createdByAdm;
	
	@JoinColumn
	private Usuario deletedByAdm;
	
	//----------------//
	// DADOS DO ADMIN //
	//----------------//
	private Boolean isDeleted = false;
	
	
	
}
