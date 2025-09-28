CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_telefone(IN telefone TEXT)
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
DECLARE
  ddds_validos CONSTANT CHAR(2)[] = ARRAY [
    '11','12','13','14','15','16','17','18','19',
    '21','22','24',
    '27','28',
    '31','32','33','34','35','37','38',
    '41','42','43','44','45','46',
    '47','48','49',
    '51','53','54','55',
    '61','62','64','65','66','67',
    '68','69',
    '71','73','74','75','77',
    '79','81','82','83','84','85','86','87','88','89',
    '91','92','93','94','95','96','97','98','99'
  ]::CHAR(2)[];
BEGIN
  IF telefone IS NULL THEN
    RAISE EXCEPTION 'Telefone inválido: não pode ser nulo';
  END IF;

  telefone := trim(telefone);
  IF telefone = '' THEN
    RAISE EXCEPTION 'Telefone inválido: não pode ser vazio';
  END IF;

  IF telefone ~ '[ ]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter espaços';
  END IF;

  IF telefone ~ '[\t]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter tabulações';
  END IF;

  IF telefone ~ '[\n]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter quebras de linha (ENTER)';
  END IF;

  IF telefone ~ '[\r]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter Carriage Return';
  END IF;

  IF telefone ~ '[\f]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter Form Feed';
  END IF;

  IF telefone ~ '[^0-9]' THEN
    RAISE EXCEPTION 'Telefone inválido: deve conter apenas números';
  END IF;

  IF length(telefone) != 11 THEN
    RAISE EXCEPTION 'Telefone inválido: deve ter exatamente 11 caracteres (DDD e número)';
  END IF;

  IF LEFT(telefone, 2) != ALL(ddds_validos) THEN
    RAISE EXCEPTION 'Telefone inválido: DDD inválido';
  END IF;

  IF substring(telefone FROM 3 FOR 1) != '9' THEN
    RAISE EXCEPTION 'Telefone inválido: o primeiro dígito do telefone deve ser "9"';
  END IF;

  RETURN TRUE;
END;
$$;