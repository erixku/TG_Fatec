CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_nome (
    IN valor TEXT,
    IN tipo  TEXT
  )
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF tipo IS NULL THEN
    RAISE EXCEPTION 'Tipo inválido: não pode ser nulo';
  END IF;

  tipo := trim(tipo);
  IF tipo NOT IN ('Nome', 'Sobrenome', 'Nome social', 'Sobrenome social') THEN
    RAISE EXCEPTION 'Tipo inválido: só são aceitos os tipos "Nome", "Sobrenome", "Nome social" ou "Sobrenome social"';
  END IF;

  IF tipo IN ('Nome', 'Sobrenome') AND valor IS NULL THEN
    RAISE EXCEPTION 'Valor inválido: não pode ser nulo';
  END IF;

  IF tipo IN ('Nome social', 'Sobrenome social') AND valor IS NULL THEN
    RETURN TRUE;
  END IF;

  valor := trim(valor);
  IF valor = '' THEN
    RAISE EXCEPTION '% inválido: não pode ser vazio', tipo;
  END IF;

  IF length(valor) < 2 THEN
    RAISE EXCEPTION '% inválido: deve conter, pelo menos, dois caracteres', tipo;
  END IF;

  IF valor ~ '^['']+$' THEN
    RAISE EXCEPTION '% inválido: não pode conter apenas apóstrofos', tipo;
  END IF;

  IF valor ~ '[\t]' THEN
    RAISE EXCEPTION '% inválido: não deve conter tabulações', tipo;
  END IF;

  IF valor ~ '[\n]' THEN
    RAISE EXCEPTION '% inválido: não deve conter quebras de linha (ENTER)', tipo;
  END IF;

  IF valor ~ '[\r]' THEN
    RAISE EXCEPTION '% inválido: não deve conter Carriage Return', tipo;
  END IF;

  IF valor ~ '[\f]' THEN
    RAISE EXCEPTION '% inválido: não deve conter Form Feed', tipo;
  END IF;

  -- regras específicas de nome e nome social
  IF tipo = 'Nome' OR tipo = 'Nome social' THEN
    IF length(valor) > 20 THEN
      RAISE EXCEPTION '% inválido: não pode ter mais de 20 caracteres', tipo;
    END IF;

    IF valor ~* '[^a-záéíóúâêôãõçàèìòù'' ]' THEN
      RAISE EXCEPTION '% inválido: deve conter apenas letras e apóstrofos', tipo;
    END IF;

    IF valor ~ '[ ]' THEN
      RAISE EXCEPTION '% inválido: não pode conter espaços', tipo;
    END IF;

  -- regras específicas de sobrenome e sobrenome social
  ELSE
    IF length(valor) > 50 THEN
      RAISE EXCEPTION '% inválido: não pode ter mais de 50 caracteres', tipo;
    END IF;

    IF valor ~* '[^a-záéíóúâêôãõçàèìòù'' ]' THEN
      RAISE EXCEPTION '% inválido: deve conter apenas letras, espaços e apóstrofos', tipo;
    END IF;

    IF valor ~ ' {2,}' THEN
      RAISE EXCEPTION '% inválido: não pode conter espaços consecutivos', tipo;
    END IF;
  END IF;

  RETURN TRUE;
END;
$$;