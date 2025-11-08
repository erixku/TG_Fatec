CREATE OR REPLACE FUNCTION
  utils.s_notification_f_validador_fuso_horario(IN fuso_horario TEXT)
RETURNS BOOLEAN
LANGUAGE sql
STABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
  SELECT EXISTS (
    SELECT 1
    FROM pg_catalog.pg_timezone_names
    WHERE name = fuso_horario
  );
$$;