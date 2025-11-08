CREATE OR REPLACE FUNCTION
  utils.s_utils_f_conversor_mb_para_byte(IN valor_em_mb INTEGER)
RETURNS INTEGER
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF valor_em_mb IS NULL THEN
    RAISE EXCEPTION 'O tamanho em megabytes não pode ser nulo';
  END IF;

  IF valor_em_mb < 0 THEN
    RAISE EXCEPTION 'O tamanho em megabytes não pode ser negativo';
  END IF;

  RETURN valor_em_mb * 1024 * 1024;
END;
$$;