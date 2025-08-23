CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_email(email TEXT)
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF email IS NULL THEN
    RAISE EXCEPTION 'E-mail inválido: não pode ser nulo';
  END IF;

  email := trim(email);
  IF email = '' THEN
    RAISE EXCEPTION 'E-mail inválido: não pode ser vazio';
  END IF;

  IF length(email) < 11 THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter, pelo menos, 11 caracteres';
  END IF;

  IF length(email) > 50 THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter, no máximo, 50 caracteres';
  END IF;

  IF email ~ '[\s]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter espaços';
  END IF;

  IF email ~ '[\t]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter tabulações';
  END IF;

  IF email ~ '[.]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter pontos (.)';
  END IF;

  IF email ~ '[^a-z0-9!@#%_+-.]' THEN
    RAISE EXCEPTION 'E-mail inválido: só pode conter letras minúsculas, números e símbolos (!, @, #, %%, _, + e -)';
  END IF;

  IF email !~ '[@]' THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter arroba (@)';
  END IF;

  IF email !~ '^[^@]+@[^@]+$' THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter apenas um único arroba (@)';
  END IF;

  IF split_part(email, '@', 2) NOT IN ('gmail.com', 'outlook.com', 'hotmail.com', 'yahoo.com') THEN
    RAISE EXCEPTION 'E-mail inválido: só são permitidos domínios de e-mail válidos (gmail.com, outlook.com, hotmail.com e yahoo.com)';
  END IF;

  RETURN TRUE;
END;
$$;