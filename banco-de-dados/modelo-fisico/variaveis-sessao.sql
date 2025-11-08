CREATE OR REPLACE PROCEDURE utils.set_variaveis_sessao (
  IN usuario_id   UUID,
  IN usuario_role utils.s_auth_t_tb_role_e_nome
)
LANGUAGE plpgsql
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF usuario_id IS NULL THEN
    EXECUTE format('SET LOCAL app.usuario_id = %L', 'NULL');
  ELSE
    EXECUTE format('SET LOCAL app.usuario_id = %L', usuario_id);
  END IF;

  IF usuario_role IS NULL THEN
    EXECUTE format('SET LOCAL app.usuario_role = %L', 'NULL');
  ELSE
    EXECUTE format('SET LOCAL app.usuario_role = %L', usuario_role);
  END IF;
END;
$$;

CREATE OR REPLACE FUNCTION utils.get_usuario_id ()
RETURNS TEXT
LANGUAGE SQL
STABLE
SECURITY INVOKER
PARALLEL UNSAFE
SET search_path = pg_catalog
AS $$
  SELECT CURRENT_SETTING('app.usuario_id', TRUE);
$$;

CREATE OR REPLACE FUNCTION utils.get_usuario_role ()
RETURNS TEXT
LANGUAGE SQL
STABLE
SECURITY INVOKER
PARALLEL UNSAFE
SET search_path = pg_catalog
AS $$
  SELECT CURRENT_SETTING('app.usuario_role', TRUE);
$$;