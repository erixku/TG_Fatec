-- tb_usuario
CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_cpf
ON auth.tb_usuario (cpf)
WHERE is_deletado = FALSE;

CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_email
ON auth.tb_usuario (email)
WHERE is_deletado = FALSE;

CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_telefone
ON auth.tb_usuario (telefone)
WHERE is_deletado = FALSE;