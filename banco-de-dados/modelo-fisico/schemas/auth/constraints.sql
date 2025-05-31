-- valida o "sexo"
ALTER TABLE auth.tb_usuario
ADD CONSTRAINT
check_schema_auth_tb_endereco_sexo
CHECK (sexo IN (
  'M', 'F'
));