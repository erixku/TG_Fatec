ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_endereco_c_sexo
CHECK (sexo IN (
  'M', 'F'
));