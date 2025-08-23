CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_cpf(cpf VARCHAR(11))
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