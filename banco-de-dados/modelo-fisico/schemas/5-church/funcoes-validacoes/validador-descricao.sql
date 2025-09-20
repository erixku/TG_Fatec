CREATE OR REPLACE FUNCTION
  utils.s_church_f_validador_descricao (
    IN descricao TEXT,
    IN tamanho_minimo SMALLINT,
    IN tamanho_maximo SMALLINT
  )
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF descricao IS NULL THEN
    RAISE EXCEPTION 'Descrição inválida: não pode ser nulo';
  END IF;

  IF tamanho_minimo IS NULL THEN
    RAISE EXCEPTION 'Tamanho mínimo inválido: não pode ser nulo';
  END IF;

  IF tamanho_maximo IS NULL THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser nulo';
  END IF;

  descricao := trim(descricao);
  IF descricao = '' THEN
    RAISE EXCEPTION 'Descrição inválida: não pode ser vazia';
  END IF;

  IF tamanho_minimo < 0 THEN
    RAISE EXCEPTION 'Tamanho mínimo inválido: não pode ser menor que 0';
  END IF;

  IF tamanho_maximo < 1 THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser menor que 1';
  END IF;

  IF length(descricao) < tamanho_minimo THEN
    RAISE EXCEPTION 'Descrição inválida: deve ter, ao menos, % caractere(s)', tamanho_minimo;
  END IF;

  IF length(descricao) > tamanho_maximo THEN
    RAISE EXCEPTION 'Descrição inválida: não pode exceder % caractere(s)', tamanho_maximo;
  END IF;

  IF descricao ~ '[\r]' THEN
    RAISE EXCEPTION 'Descrição inválida: não deve conter Carriage Return';
  END IF;

  IF descricao ~ '[\f]' THEN
    RAISE EXCEPTION 'Descrição inválida: não deve conter Form Feed';
  END IF;

  IF descricao ~* '[^a-záéíóúâêôãõçàèìòù0-9\-\n\(\):/+.&'' ]' THEN
    RAISE EXCEPTION 'Descrição inválida: deve conter apenas letras, números, espaços, quebras de linha e algumas pontuações (-, (, ), :, ., +, '')';
  END IF;

  RETURN TRUE;
END;
$$;