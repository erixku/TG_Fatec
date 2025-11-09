GRANT USAGE ON SCHEMA notification TO r_manutencao;



-- tb_tipo
GRANT
  INSERT (
    nome,
    s_storage_t_tb_arquivo_c_icone
  )
  ON TABLE notification.tb_tipo
  TO r_manutencao;

GRANT
  SELECT (
    id,
    created_at,
    updated_at,
    deleted_at,
    is_deleted,
    nome,
    s_storage_t_tb_arquivo_c_icone
  )
  ON TABLE notification.tb_tipo
  TO r_manutencao;

GRANT
  UPDATE (
    updated_at,
    deleted_at,
    is_deleted,
    nome,
    s_storage_t_tb_arquivo_c_icone
  )
  ON TABLE notification.tb_tipo
  TO r_manutencao;



-- tb_cor
GRANT
  INSERT (
    nome,
    s_storage_t_tb_arquivo_c_icone
  )
  ON TABLE notification.tb_cor
  TO r_manutencao;

GRANT
  SELECT (
    id,
    created_at,
    updated_at,
    deleted_at,
    is_deleted,
    nome,
    s_storage_t_tb_arquivo_c_icone
  )
  ON TABLE notification.tb_cor
  TO r_manutencao;

GRANT
  UPDATE (
    updated_at,
    deleted_at,
    is_deleted,
    nome,
    s_storage_t_tb_arquivo_c_icone
  )
  ON TABLE notification.tb_cor
  TO r_manutencao;