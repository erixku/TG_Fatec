ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_cpf
CHECK (
  utils.validador_cpf(cpf)
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_nome
CHECK (
  utils.validador_nome(nome, 'Nome')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sobrenome
CHECK (
  utils.validador_nome(sobrenome, 'Sobrenome')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_nome_social
CHECK (
  utils.validador_nome(nome_social, 'Nome social')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sobrenome_social
CHECK (
  utils.validador_nome(sobrenome_social, 'Sobrenome social')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sexo
CHECK (
  sexo IN (
    'M', 'F'
  )
);