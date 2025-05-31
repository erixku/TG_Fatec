-- valida o "uf"
ALTER TABLE auth.tb_endereco
ADD CONSTRAINT
schema_auth_tb_endereco_constraint_validar_uf
CHECK (uf IN (
  'AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO',
  'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR',
  'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO'
));

-- valida o "sexo"
ALTER TABLE auth.tb_usuario
ADD CONSTRAINT
schema_auth_tb_endereco_constraint_validar_sexo
CHECK (sexo IN ('M', 'F'));