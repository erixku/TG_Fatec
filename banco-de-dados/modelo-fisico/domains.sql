CREATE DOMAIN domain_uf
AS CHAR(2)
CHECK (
  VALUE IN (
    'AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO',
    'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR',
    'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO'
  )
);

CREATE DOMAIN domain_cep
AS VARCHAR(8)
CHECK (
  char_length(VALUE) = 8 AND
  VALUE ~ '^[0-9]{8}$'
);