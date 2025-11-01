GRANT USAGE ON SCHEMA storage TO r_ministro;


-- tb_bucket
GRANT
  SELECT (
    id,
    is_deleted,
    nome,
    tempo_expiracao_upload_em_segundos,
    tamanho_minimo,
    tamanho_maximo
  )
  ON TABLE storage.tb_bucket
  TO r_ministro;



-- tb_arquivo
GRANT
  INSERT (
    created_by,
    nome,
    mime_type,
    extensao,
    tamanho_em_bytes,
    buc_id
  )
  ON TABLE storage.tb_arquivo
  TO r_ministro;

GRANT
  SELECT (
    id,
    created_at,
    deleted_at,
    created_by,
    deleted_by,
    is_deleted,
    nome,
    extensao,
    tamanho_em_bytes,
    buc_id
  )
  ON TABLE storage.tb_arquivo
  TO r_ministro;

GRANT
  UPDATE (
    deleted_at,
    deleted_by,
    is_deleted
  )
  ON TABLE storage.tb_arquivo
  TO r_ministro;