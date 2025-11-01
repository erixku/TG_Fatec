SET ROLE neondb_owner;

DO $$
DECLARE
  nomes_schemas TEXT[];
  nome_schema TEXT;
  nomes_roles TEXT[] := ARRAY [
    'public',
    'r_anonimo',
    'r_usuario',
    'r_levita',
    'r_ministro',
    'r_lider',
    'r_administrador',
    'r_api',
    'r_sistema'
  ];
  nome_role TEXT;
  query_revogadora TEXT;
BEGIN
  SELECT ARRAY_AGG(schema_name)
  INTO nomes_schemas
  FROM information_schema.schemata
  WHERE schema_name NOT LIKE 'pg_%'
    AND schema_name <> 'information_schema';

  FOREACH nome_role IN ARRAY nomes_roles LOOP
    -- revoga permissões globais
    EXECUTE format('REVOKE ALL PRIVILEGES ON DATABASE %I FROM %I;', current_database(), nome_role);
    EXECUTE format('REVOKE ALL PRIVILEGES ON LANGUAGE plpgsql FROM %I;', nome_role);
    -- EXECUTE format('REVOKE ALL PRIVILEGES ON ALL TABLESPACES FROM %I;', nome_role);
    -- EXECUTE format('REVOKE ALL PRIVILEGES ON ALL CONFIGURATION PARAMETERS FROM %I;', nome_role);

    -- revoga permissões em LARGE OBJECTs
    FOR query_revogadora IN
      SELECT format(
        'REVOKE ALL PRIVILEGES ON LARGE OBJECT %s FROM %I;',
        oid, nome_role
      )
      FROM pg_largeobject_metadata
    LOOP
      EXECUTE query_revogadora;
    END LOOP;

    -- revoga permissões em objetos por schema
    FOREACH nome_schema IN ARRAY nomes_schemas LOOP
      EXECUTE format('REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA %I FROM %I;', nome_schema, nome_role);
      EXECUTE format('REVOKE ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA %I FROM %I;', nome_schema, nome_role);
      EXECUTE format('REVOKE ALL PRIVILEGES ON ALL ROUTINES IN SCHEMA %I FROM %I;', nome_schema, nome_role);
      EXECUTE format('REVOKE ALL PRIVILEGES ON SCHEMA %I FROM %I;', nome_schema, nome_role);
    END LOOP;

    -- revoga todos os TYPES de um ROLE
    FOR query_revogadora IN
      SELECT format(
        'REVOKE ALL PRIVILEGES ON TYPE %I.%I FROM %I;',
        n.nspname, t.typname, nome_role
      )
      FROM pg_type t
      JOIN pg_namespace n ON n.oid = t.typnamespace
      WHERE n.nspname NOT LIKE 'pg_%'
        AND n.nspname <> 'information_schema'
        AND t.typtype IN ('b', 'c', 'd', 'e', 'r', 'm')
        AND t.typname NOT LIKE '\_%' ESCAPE '\'
    LOOP
      EXECUTE query_revogadora;
    END LOOP;

    -- transfere objetos OWNED para neondb_owner
    IF nome_role <> 'public' THEN
      EXECUTE format('REASSIGN OWNED BY %I TO neondb_owner;', nome_role);
    END IF;
  END LOOP;
END $$
LANGUAGE plpgsql;