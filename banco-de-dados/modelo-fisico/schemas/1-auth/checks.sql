ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_cpf
CHECK (
  utils.validador_cpf(cpf)
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sexo
CHECK (
  sexo IN (
    'M', 'F'
  )
);