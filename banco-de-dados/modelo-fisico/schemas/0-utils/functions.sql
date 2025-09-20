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