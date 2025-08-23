CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_data_nascimento(data_nascimento DATE)
RETURNS BOOLEAN
LANGUAGE plpgsql
STABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF data_nascimento IS NULL THEN
    RAISE EXCEPTION 'Data de nascimento inválida: não pode ser nulo';
  END IF;

  IF data_nascimento <= (CURRENT_DATE - INTERVAL '18 years') THEN
    RAISE EXCEPTION 'Data de nascimento inválida: deve ser maior ou igual a 18 anos';
  END IF;

  IF data_nascimento >= (CURRENT_DATE - INTERVAL '120 years') THEN
    RAISE EXCEPTION 'Data de nascimento inválida: deve ser menor ou igual a 120 anos';
  END IF;

  RETURN TRUE;
END;
$$;