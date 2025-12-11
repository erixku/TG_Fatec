package br.app.harppia.modulo.activities.application.port.out;

public interface ConsultarIgrejaActivityToChurchPort {

	/**
	 * Busca somente o ID de uma dada categoria de uma atividade.
	 * @return id da categoria
	 */
	public Integer getIdOf(String strCategoria);
}
