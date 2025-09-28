CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_email(IN email TEXT)
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

  IF length(email) > 79 THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter, no máximo, 79 caracteres';
  END IF;

  IF email ~ '[ ]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter espaços';
  END IF;

  IF email ~ '[\t]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter tabulações';
  END IF;

  IF email ~ '[\n]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter quebras de linha (ENTER)';
  END IF;

  IF email ~ '[\r]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter Carriage Return';
  END IF;

  IF email ~ '[\f]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter Form Feed';
  END IF;

  IF email ~ '[^a-z0-9!@#%_+-.]' THEN
    RAISE EXCEPTION 'E-mail inválido: só pode conter letras minúsculas, números e símbolos (!, @, #, %%, _, +, - e .)';
  END IF;

  IF email !~ '[@]' THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter arroba (@)';
  END IF;

  IF email !~ '^[^@]+@[^@]+$' THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter apenas um único arroba (@)';
  END IF;

  IF split_part(email, '@', 1) ~ '[.]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter pontos (.) antes do arroba (@)';
  END IF;

  IF split_part(email, '@', 2) NOT IN (
    'gmail.com',
    'outlook.com',
    'outlook.com.br',
    'hotmail.com',
    'yahoo.com',
    'myyahoo.com'
  ) THEN
    RAISE EXCEPTION '
      E-mail inválido: só são permitidos domínios de e-mail válidos
      (gmail.com, outlook.com, outlook.com.br, hotmail.com, yahoo.com e myyahoo.com)
    ';
  END IF;

  IF
    length(split_part(email, '@', 1)) > 30 AND
    split_part(email, '@', 2) = 'gmail.com'
  THEN
    RAISE EXCEPTION 'E-mail inválido: e-mails com endereço de domínio "gmail.com" só podem ter, antes do arroba, até 30 caracteres';
  END IF;

  IF
    length(split_part(email, '@', 1)) > 64 AND
    split_part(email, '@', 2) IN ('outlook.com', 'outlook.com.br', 'hotmail.com')
  THEN
    RAISE EXCEPTION '
      E-mail inválido: e-mails com endereço de domínio "outlook.com", "outlook.com.br"
      ou "hotmail.com" só podem ter, antes do arroba, até 64 caracteres
    ';
  END IF;

  IF
    length(split_part(email, '@', 1)) > 32 AND
    split_part(email, '@', 2) IN ('yahoo.com', 'myyahoo.com')
  THEN
    RAISE EXCEPTION '
      E-mail inválido: e-mails com endereço de domínio "yahoo.com" ou "myyahoo.com"
      só podem ter, antes do arroba, até 32 caracteres
    ';
  END IF;

  RETURN TRUE;
END;
$$;