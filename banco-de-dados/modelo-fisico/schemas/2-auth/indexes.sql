-- tb_usuario
CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_cpf
ON auth.tb_usuario (cpf)
WHERE status = 'ativo';

CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_email
ON auth.tb_usuario (email)
WHERE
  status = 'ativo' AND
  is_email_verificado = TRUE;

CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_telefone
ON auth.tb_usuario (telefone)
WHERE
  status = 'ativo' AND
  is_telefone_verificado = TRUE;

CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_foto
ON auth.tb_usuario (s_storage_t_tb_arquivo_c_foto)
WHERE status = 'ativo';