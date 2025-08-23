CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_sexo(sexo TEXT)
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF sexo IS NULL THEN
    RAISE EXCEPTION 'Sexo inválido: não pode ser nulo';
  END IF;

  IF sexo NOT IN ('M', 'F') THEN
    RAISE EXCEPTION 'Sexo inválido: deve ser "M" (masculino) ou "F" (feminino)';
  END IF;

  RETURN TRUE;
END;
$$;