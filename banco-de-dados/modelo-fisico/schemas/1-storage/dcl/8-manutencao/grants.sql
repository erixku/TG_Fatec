GRANT USAGE ON SCHEMA storage TO r_manutencao;



-- tb_bucket
GRANT
  INSERT (
    nome,
    tempo_expiracao_upload_em_segundos,
    tamanho_minimo,
    tamanho_maximo
  )
  ON TABLE storage.tb_bucket
  TO r_manutencao;

GRANT
  SELECT (
    id,
    created_at,
    updated_at,
    deleted_at,
    is_deleted,
    nome,
    tempo_expiracao_upload_em_segundos,
    tamanho_minimo,
    tamanho_maximo
  )
  ON TABLE storage.tb_bucket
  TO r_manutencao;

GRANT
  UPDATE (
    updated_at,
    deleted_at,
    is_deleted,
    nome,
    tempo_expiracao_upload_em_segundos,
    tamanho_minimo,
    tamanho_maximo
  )
  ON TABLE storage.tb_bucket
  TO r_manutencao;