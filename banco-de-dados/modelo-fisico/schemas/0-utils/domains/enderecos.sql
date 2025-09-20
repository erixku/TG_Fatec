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
  VALUE ~ '^[0-9]{8}$'
);

CREATE DOMAIN utils.domain_cidade AS VARCHAR(40);
ALTER DOMAIN utils.domain_cidade
ADD CONSTRAINT ck_s_utils_d_domain_cidade
CHECK (
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù\-''\. ]{3,40}$'
);

CREATE DOMAIN utils.domain_bairro AS VARCHAR(70);
ALTER DOMAIN utils.domain_bairro
ADD CONSTRAINT ck_s_utils_d_domain_bairro
CHECK (
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù0-9\-''\., ]{2,70}$'
);

CREATE DOMAIN utils.domain_logradouro AS VARCHAR(120);
ALTER DOMAIN utils.domain_logradouro
ADD CONSTRAINT ck_s_utils_d_domain_logradouro
CHECK (
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù0-9\-''\., ]{1,120}$'
);

CREATE DOMAIN utils.domain_numero AS VARCHAR(5);
ALTER DOMAIN utils.domain_numero
ADD CONSTRAINT ck_s_utils_d_domain_numero
CHECK (
  VALUE ~ '^[0-9]{1,5}$' OR
  VALUE = 'S/N'
);

CREATE DOMAIN utils.domain_complemento AS VARCHAR(50);
ALTER DOMAIN utils.domain_complemento
ADD CONSTRAINT ck_s_utils_d_domain_complemento
CHECK (
  VALUE ~* '^[a-záéíóúâêôãõçàèìòù0-9,\-\.\(\)'' ]{0,50}$'
);