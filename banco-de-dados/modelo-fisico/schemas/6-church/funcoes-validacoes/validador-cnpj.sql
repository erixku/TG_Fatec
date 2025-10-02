CREATE OR REPLACE FUNCTION
  utils.s_church_f_validador_cnpj(IN cnpj TEXT)
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
DECLARE
  cnpj_vetor TEXT[];
  soma INT;
  resto INT;
  i INT;
  pesos1 INTEGER[] := ARRAY[5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2];
  pesos2 INTEGER[] := ARRAY[6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2];
BEGIN
  IF cnpj IS NULL THEN
    RAISE EXCEPTION 'CNPJ inválido: não pode ser nulo';
  END IF;

  cnpj := trim(cnpj);
  IF cnpj = '' THEN
    RAISE EXCEPTION 'CNPJ inválido: não pode ser vazio';
  END IF;

  IF length(cnpj) != 14 THEN
    RAISE EXCEPTION 'CNPJ inválido: deve ter exatamente 14 caracteres';
  END IF;

  IF cnpj ~ '[ ]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter espaços';
  END IF;

  IF cnpj ~ '[\t]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter tabulações';
  END IF;

  IF cnpj ~ '[\n]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter quebras de linha (ENTER)';
  END IF;

  IF cnpj ~ '[\r]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter Carriage Return';
  END IF;

  IF cnpj ~ '[\f]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter Form Feed';
  END IF;

  IF cnpj ~ '[^0-9]' THEN
    RAISE EXCEPTION 'CNPJ inválido: deve conter apenas números (sem pontos ou traços)';
  END IF;

  IF cnpj ~ '^(\d)\1{13}$' THEN
    RAISE EXCEPTION 'CNPJ inválido: não pode conter todos os dígitos iguais';
  END IF;

  -- converte o CNPJ em um vetor de caracteres
  cnpj_vetor := regexp_split_to_array(cnpj, '');
  
  -- Valida o primeiro dígito verificador
  soma := 0;
  FOR i IN 1..12 LOOP
    soma := soma + (cast(cnpj_vetor[i] AS INTEGER) * pesos1[i]);
  END LOOP;
  resto := soma % 11;
  IF resto < 2 THEN
    resto := 0;
  ELSE
    resto := 11 - resto;
  END IF;
  IF resto != cast(cnpj_vetor[13] AS INTEGER) THEN
    RAISE EXCEPTION 'CNPJ inválido: primeiro dígito verificador incorreto';
  END IF;

  -- Valida o segundo dígito verificador
  soma := 0;
  FOR i IN 1..13 LOOP
    soma := soma + (cast(cnpj_vetor[i] AS INTEGER) * pesos2[i]);
  END LOOP;
  resto := soma % 11;
  IF resto < 2 THEN
    resto := 0;
  ELSE
    resto := 11 - resto;
  END IF;
  IF resto != cast(cnpj_vetor[14] AS INTEGER) THEN
    RAISE EXCEPTION 'CNPJ inválido: segundo dígito verificador incorreto';
  END IF;

  RETURN TRUE;
END;
$$;