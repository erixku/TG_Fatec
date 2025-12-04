GRANT USAGE ON SCHEMA auth TO r_usuario;



-- tb_usuario
GRANT
  SELECT (
    id,
    status,
    cpf,
    nome,
    sobrenome,
    nome_social,
    sobrenome_social,
    sexo,
    data_nascimento,
    email,
    is_email_verificado,
    telefone,
    is_telefone_verificado,
    senha,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE auth.tb_usuario
  TO r_usuario;

GRANT
  UPDATE (
    updated_at,
    deleted_at,
    last_access,
    status,
    nome,
    sobrenome,
    nome_social,
    sobrenome_social,
    data_nascimento,
    is_email_verificado,
    telefone,
    is_telefone_verificado,
    senha,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE auth.tb_usuario
  TO r_usuario;



-- tb_endereco
GRANT
  SELECT (
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
  TO r_usuario;

GRANT
  UPDATE (
    updated_at,
    cep,
    uf,
    cidade,
    bairro,
    logradouro,
    numero,
    complemento
  )
  ON TABLE auth.tb_endereco
  TO r_usuario;