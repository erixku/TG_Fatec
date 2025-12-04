GRANT USAGE ON SCHEMA auth TO r_anonimo;



-- tb_usuario
GRANT
  INSERT (
    cpf,
    nome,
    sobrenome,
    nome_social,
    sobrenome_social,
    sexo,
    data_nascimento,
    email,
    telefone,
    senha,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE auth.tb_usuario
  TO r_anonimo;

GRANT
  SELECT (
    id,
    cpf,
    email,
    is_email_verificado,
    telefone,
    is_telefone_verificado,
    senha
  )
  ON TABLE auth.tb_usuario
  TO r_anonimo;

GRANT
  UPDATE (
    updated_at,
    status,
    is_email_verificado,
    is_telefone_verificado,
    senha,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE auth.tb_usuario
  TO r_anonimo;



-- tb_endereco
GRANT
  INSERT (
    id,
    cep,
    uf,
    cidade,
    bairro,
    logradouro,
    numero,
    complemento
  )
  ON TABLE auth.tb_endereco
  TO r_anonimo;