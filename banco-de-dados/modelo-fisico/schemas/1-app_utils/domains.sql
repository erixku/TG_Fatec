CREATE DOMAIN app_utils.domain_uf AS CHAR(2);
ALTER DOMAIN app_utils.domain_uf
ADD CONSTRAINT check_s_app_utils_d_domain_uf
CHECK (
  VALUE IN (
    'AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO',
    'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR',
    'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO'
  )
);

CREATE DOMAIN app_utils.domain_cep AS VARCHAR(8);
ALTER DOMAIN app_utils.domain_cep
ADD CONSTRAINT check_s_app_utils_d_domain_cpf
CHECK (
  char_length(VALUE) = 8 AND
  VALUE ~ '^[0-9]{8}$'
);