CREATE OR REPLACE FUNCTION
  utils.conversor_mb_para_byte(tamanho INTEGER)
RETURNS INTEGER
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF tamanho IS NULL THEN
    RAISE EXCEPTION 'O tamanho em megabytes não pode ser nulo';
  END IF;

  IF tamanho < 0 THEN
    RAISE EXCEPTION 'O tamanho em megabytes não pode ser negativo';
  END IF;

  RETURN tamanho * 1024 * 1024;
END;
$$;



CREATE OR REPLACE FUNCTION
  utils.get_codigo_ministerio()
RETURNS VARCHAR(6)
LANGUAGE plpgsql
VOLATILE
SECURITY DEFINER
SET search_path = church, pg_catalog
AS $$
DECLARE
  caracteres CONSTANT VARCHAR(62) := 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  codigo_gerado VARCHAR(6);
  is_codigo     BOOLEAN;
  tentativas    SMALLINT := 0;
BEGIN
  LOOP
    codigo_gerado := '';
    tentativas := tentativas + 1;

    -- gera o código do ministério
    FOR i IN 1..6 LOOP
      codigo_gerado := codigo_gerado || substr(caracteres, ceil(random() * length(caracteres))::int, 1);
    END LOOP;

    -- verifica se o código gerado já está em uso por algum ministério
    SELECT EXISTS (
      SELECT 1
      FROM church.tb_ministerio_louvor t
      WHERE codigo = codigo_gerado
    ) INTO is_codigo;

    -- caso não esteja em uso por outro ministério, retorna o código. Senão, tenta novamente
    IF NOT is_codigo THEN
      RETURN codigo_gerado;
    END IF;

    IF tentativas >= 10000 THEN
      RAISE EXCEPTION 'Falha ao gerar código único de ministério por excesso de tentativas';
    END IF;
  END LOOP;
END;
$$;



CREATE OR REPLACE FUNCTION
  utils.validador_cpf(cpf VARCHAR(11))
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
DECLARE
  cpf_vetor TEXT[];
  soma INT;
  resto INT;
  i INT;
BEGIN
  IF cpf IS NULL THEN
    RAISE EXCEPTION 'CPF inválido: não pode ser nulo';
  END IF;

  cpf := trim(cpf);

  IF cpf = '' THEN
    RAISE EXCEPTION 'CPF inválido: não pode ser vazio';
  END IF;

  IF length(cpf) != 11 THEN
    RAISE EXCEPTION 'CPF inválido: deve conter exatamente 11 dígitos';
  END IF;

  IF cpf ~ '[^0-9]' THEN
    RAISE EXCEPTION 'CPF inválido: deve conter apenas números (sem pontos ou traços)';
  END IF;

  IF cpf ~ '^(\d)\1{10}$' THEN
    RAISE EXCEPTION 'CPF inválido: não pode conter todos os dígitos iguais';
  END IF;

  -- converte o CPF em um vetor de caracteres
  cpf_vetor := regexp_split_to_array(cpf, '');

  -- Valida primeiro dígito verificador
  soma := 0;
  FOR i IN 1..9 LOOP
    soma := soma + (cast(cpf_vetor[i] AS INTEGER) * (11 - i));
  END LOOP;
  resto := (soma * 10) % 11;
  IF resto = 10 THEN
    resto := 0;
  END IF;
  IF resto != cast(cpf_vetor[10] AS INTEGER) THEN
    RAISE EXCEPTION 'CPF inválido: primeiro dígito verificador incorreto';
  END IF;

  -- Valida segundo dígito verificador
  soma := 0;
  FOR i IN 1..10 LOOP
    soma := soma + (cast(cpf_vetor[i] AS INTEGER) * (12 - i));
  END LOOP;
  resto := (soma * 10) % 11;
  IF resto = 10 THEN
    resto := 0;
  END IF;
  IF resto != cast(cpf_vetor[11] AS INTEGER) THEN
    RAISE EXCEPTION 'CPF inválido: segundo dígito verificador incorreto';
  END IF;

  RETURN TRUE;
END;
$$;





-- CREATE OR REPLACE VIEW utils.view_teste AS
--   SELECT id, cep, uf, cidade, bairro, rua, numero, complemento
--   FROM auth.tb_endereco;

-- SELECT * FROM utils.view_teste;