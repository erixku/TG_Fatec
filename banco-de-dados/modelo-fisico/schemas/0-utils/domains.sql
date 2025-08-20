CREATE DOMAIN utils.domain_uf AS CHAR(2);
ALTER DOMAIN utils.domain_uf
ADD CONSTRAINT ck_s_utils_d_domain_uf
CHECK (
  VALUE IN (
    'AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO',
    'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR',
    'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO'
  )
);

CREATE DOMAIN utils.domain_cep AS VARCHAR(8);
ALTER DOMAIN utils.domain_cep
ADD CONSTRAINT ck_s_utils_d_domain_cep
CHECK (
  char_length(VALUE) = 8 AND
  VALUE ~ '^[0-9]{8}$'
);

CREATE DOMAIN utils.domain_cidade AS VARCHAR(100);
ALTER DOMAIN utils.domain_cidade
ADD CONSTRAINT ck_s_utils_d_domain_cidade
CHECK (
  char_length(VALUE) <= 100 AND
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù\s\-''\.]+$'
);

CREATE DOMAIN utils.domain_local AS VARCHAR(100);
ALTER DOMAIN utils.domain_local
ADD CONSTRAINT ck_s_utils_d_domain_local
CHECK (
  char_length(VALUE) <= 100 AND
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù0-9\s\-''\.]+$'
);

CREATE DOMAIN utils.domain_numero AS VARCHAR(5);
ALTER DOMAIN utils.domain_numero
ADD CONSTRAINT ck_s_utils_d_domain_numero
CHECK (
  (char_length(VALUE) <= 5 AND VALUE ~ '^[0-9]+$') OR
  (VALUE = 'S/N')
);

CREATE DOMAIN utils.domain_complemento AS VARCHAR(30);
ALTER DOMAIN utils.domain_complemento
ADD CONSTRAINT ck_s_utils_d_domain_complemento
CHECK (
  char_length(VALUE) <= 30 AND
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù0-9\s\-''\.]+$'
);