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

CREATE DOMAIN utils.domain_cidade AS VARCHAR(40);
ALTER DOMAIN utils.domain_cidade
ADD CONSTRAINT ck_s_utils_d_domain_cidade
CHECK (
  char_length(VALUE) >= 3 AND
  char_length(VALUE) <= 40 AND
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù\-''\. ]+$'
);

CREATE DOMAIN utils.domain_bairro AS VARCHAR(70);
ALTER DOMAIN utils.domain_bairro
ADD CONSTRAINT ck_s_utils_d_domain_bairro
CHECK (
  char_length(VALUE) >= 2 AND
  char_length(VALUE) <= 70 AND
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù0-9\-''\., ]+$'
);

CREATE DOMAIN utils.domain_logradouro AS VARCHAR(120);
ALTER DOMAIN utils.domain_logradouro
ADD CONSTRAINT ck_s_utils_d_domain_logradouro
CHECK (
  char_length(VALUE) <= 120 AND
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù0-9\-''\., ]+$'
);

CREATE DOMAIN utils.domain_numero AS VARCHAR(5);
ALTER DOMAIN utils.domain_numero
ADD CONSTRAINT ck_s_utils_d_domain_numero
CHECK (
  (char_length(VALUE) <= 5 AND VALUE ~ '^[0-9]+$') OR
  (VALUE = 'S/N')
);

CREATE DOMAIN utils.domain_complemento AS VARCHAR(50);
ALTER DOMAIN utils.domain_complemento
ADD CONSTRAINT ck_s_utils_d_domain_complemento
CHECK (
  char_length(VALUE) <= 50 AND
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù0-9,\-\.\(\)'' ]+$'
);