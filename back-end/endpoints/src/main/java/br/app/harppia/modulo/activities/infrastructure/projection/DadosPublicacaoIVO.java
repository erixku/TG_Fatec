package br.app.harppia.modulo.activities.infrastructure.projection;

import java.time.OffsetDateTime;
import java.util.UUID;

import br.app.harppia.modulo.activities.infrastructure.repository.enums.ETipoPublicacao;

/**
 * Dados que devem ser retornados:
 * - created_by: uuid
 * - updated_by: uuid
 * - updated_at: timestamp
 * - titulo: string
 * - tipo: ETipoPublicacao
 * - descricao: string
 * - id_igreja: uuid
 * - id_ministerio: uuid
 */
public interface DadosPublicacaoIVO  {

	public UUID getCreatedBy();
	public UUID getUpdatedBy();
	public OffsetDateTime getUpdatedAt();
	public String getTitulo();
	public ETipoPublicacao getTipo();
	public String getDescricao();
	public UUID getIdIgreja();
	public UUID getIdMinisterio();
	
}
