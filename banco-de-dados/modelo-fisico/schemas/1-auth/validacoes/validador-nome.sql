CREATE OR REPLACE FUNCTION
  utils.validador_nome(valor TEXT, tipo VARCHAR(16))
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF valor IS NULL THEN
    RAISE EXCEPTION 'Valor inválido: não pode ser nulo';
  END IF;

  IF tipo IS NULL THEN
    RAISE EXCEPTION 'Tipo inválido: não pode ser nulo';
  END IF;

  valor := trim(valor);
  IF valor = '' THEN
    RAISE EXCEPTION '% inválido: não pode ser vazio', tipo;
  END IF;

  IF tipo NOT IN ('Nome', 'Sobrenome', 'Nome social', 'Sobrenome social') THEN
    RAISE EXCEPTION 'Tipo inválido: só são aceitos os tipos "Nome", "Sobrenome", "Nome social" ou "Sobrenome social"';
  END IF;

  IF length(valor) < 2 THEN
    RAISE EXCEPTION '% inválido: deve conter, pelo menos, dois caracteres', tipo;
  END IF;

  IF valor ~* '^['']+$' THEN
    RAISE EXCEPTION '% inválido: não pode conter apenas apóstrofos', tipo;
  END IF;

  -- regras específicas de nome, sobrenome, nome social e sobrenome social
  IF tipo = 'Nome' OR tipo = 'Nome social' THEN
    -- regras específicas nome e nome social
    IF valor ~ '\s' THEN
      RAISE EXCEPTION '% inválido: não pode conter espaços', tipo;
    END IF;
    
    IF valor ~* '[^a-záéíóúâêôãõçàèìòù'']' THEN
      RAISE EXCEPTION '% inválido: deve conter apenas letras e apóstrofos', tipo;
    END IF;

    IF length(valor) > 20 THEN
      RAISE EXCEPTION '% inválido: não pode ter mais de 20 caracteres', tipo;
    END IF;
  ELSE
    -- regras específicas de sobrenome e sobrenome social
    IF valor ~ '\s{2,}' THEN
      RAISE EXCEPTION '% inválido: não pode conter espaços consecutivos', tipo;
    END IF;
    
    IF valor ~* '[^a-záéíóúâêôãõçàèìòù\s'']' THEN
      RAISE EXCEPTION '% inválido: deve conter apenas letras, espaços e apóstrofos', tipo;
    END IF;

    IF length(valor) > 50 THEN
      RAISE EXCEPTION '% inválido: não pode ter mais de 50 caracteres', tipo;
    END IF;
  END IF;

  RETURN TRUE;
END;
$$;