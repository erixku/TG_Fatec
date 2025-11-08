GRANT USAGE ON SCHEMA storage TO r_anonimo;



-- tb_usuario
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
  TO r_anonimo;