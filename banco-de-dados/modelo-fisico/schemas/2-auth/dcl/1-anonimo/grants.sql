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
    telefone,
    senha
  )
  ON TABLE auth.tb_usuario
  TO r_anonimo;

GRANT
  UPDATE (
    senha
  )
  ON TABLE auth.tb_usuario
  TO r_anonimo;



-- tb_endereco
GRANT
  INSERT (
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