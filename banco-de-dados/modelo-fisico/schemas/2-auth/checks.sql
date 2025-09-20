ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_cpf
CHECK (
  utils.s_auth_f_validador_cpf(cpf)
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_nome
CHECK (
  utils.s_auth_f_validador_nome(nome, 'Nome')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sobrenome
CHECK (
  utils.s_auth_f_validador_nome(sobrenome, 'Sobrenome')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_nome_social
CHECK (
  utils.s_auth_f_validador_nome(nome_social, 'Nome social')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sobrenome_social
CHECK (
  utils.s_auth_f_validador_nome(sobrenome_social, 'Sobrenome social')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sexo
CHECK (
  sexo IN (
    'm', 'f', 'o'
  )
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_data_nascimento
CHECK (
  data_nascimento BETWEEN
  (CURRENT_DATE - INTERVAL '120 years') AND
  (CURRENT_DATE - INTERVAL '18 years')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_email
CHECK (
  utils.s_auth_f_validador_email(email)
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_telefone
CHECK (
  utils.s_auth_f_validador_telefone(telefone)
);