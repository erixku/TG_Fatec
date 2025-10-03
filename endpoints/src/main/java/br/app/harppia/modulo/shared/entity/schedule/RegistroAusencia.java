package br.app.harppia.modulo.shared.entity.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

import br.app.harppia.modulo.shared.entity.auth.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity(name = "tb_registro_ausencia")
@Table(name = "tb_registro_ausencia", schema = "auth")
public class RegistroAusencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@Size(max = 50)
	private String motivo;
	
	@Column(nullable = false)
	@Size(max = 2000)
	private String justificativa;
	
	@Column(nullable = false)	
	private LocalDate data;
	
	@Column(nullable = false)
	private LocalTime horarioInicio;
	 
	@Column(nullable = false)
	private LocalTime horarioFim;
	
	@OneToOne
	@JoinColumn(name = "schema_auth_usuario_lev", referencedColumnName = "uuid")
	private Usuario usuarioUUID;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}
	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}
	/**
	 * @param justificativa the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	/**
	 * @return the data
	 */
	public LocalDate getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(LocalDate data) {
		this.data = data;
	}
	/**
	 * @return the horarioInicio
	 */
	public LocalTime getHorarioInicio() {
		return horarioInicio;
	}
	/**
	 * @param horarioInicio the horarioInicio to set
	 */
	public void setHorarioInicio(LocalTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}
	/**
	 * @return the horarioFim
	 */
	public LocalTime getHorarioFim() {
		return horarioFim;
	}
	/**
	 * @param horarioFim the horarioFim to set
	 */
	public void setHorarioFim(LocalTime horarioFim) {
		this.horarioFim = horarioFim;
	}
	/**
	 * @return the schemaAuthUsuarioLev
	 */
	public Usuario getSchemaAuthUsuarioLev() {
		return usuarioUUID;
	}
	/**
	 * @param schemaAuthUsuarioLev the schemaAuthUsuarioLev to set
	 */
	public void setSchemaAuthUsuarioLev(Usuario schemaAuthUsuarioLev) {
		this.usuarioUUID = schemaAuthUsuarioLev;
	}
	
	

}
