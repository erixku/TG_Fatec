ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_cpf
CHECK (
  
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sexo
CHECK (
  sexo IN (
    'M', 'F'
  )
);