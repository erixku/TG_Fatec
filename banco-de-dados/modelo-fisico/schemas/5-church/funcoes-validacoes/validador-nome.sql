CREATE OR REPLACE FUNCTION
  utils.s_church_f_validador_nome (
    IN nome TEXT,
    IN tipo TEXT,
    IN tamanho_minimo SMALLINT,
    IN tamanho_maximo SMALLINT
  )
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
DECLARE
  tipos CONSTANT TEXT[] := ARRAY [
    'igreja',
    'outra denominação de igreja',
    'atividade',
    'ministério de louvor',
    'marca de instrumento',
    'modelo de instrumento',
    'outro instrumento',
    'outra marca de instrumento',
    'outro modelo de instrumento'
  ];
BEGIN
  IF tipo IS NULL THEN
    RAISE EXCEPTION 'Tipo inválido: não pode ser nulo';
  END IF;

  IF tipo != ALL(tipos) THEN
    RAISE EXCEPTION 'Tipo inválido: só são aceitos os tipos
      "igreja",
      "outra denominação de igreja",
      "atividade",
      "ministério de louvor",
      "marca de instrumento",
      "modelo de instrumento",
      "outro instrumento",
      "outra marca de instrumento" ou
      "outro modelo de instrumento"
    ';
  END IF;

  IF tipo IN (tipos[6], tipos[7], tipos[8]) AND nome IS NULL THEN
    RETURN TRUE;
  END IF;

  IF tipo NOT IN (tipos[6], tipos[7], tipos[8]) AND nome IS NULL THEN
    RAISE EXCEPTION 'Nome de % inválido: não pode ser nulo', tipo;
  END IF;

  IF tamanho_minimo IS NULL THEN
    RAISE EXCEPTION 'Tamanho mínimo inválido: não pode ser nulo';
  END IF;

  IF tamanho_maximo IS NULL THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser nulo';
  END IF;

  nome := trim(nome);
  IF nome = '' THEN
    RAISE EXCEPTION 'Nome de % inválido: não pode ser vazio', tipo;
  END IF;

  IF tamanho_minimo < 0 THEN
    RAISE EXCEPTION 'Tamanho mínimo inválido: não pode ser menor que 0';
  END IF;

  IF tamanho_maximo < 1 THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser menor que 1';
  END IF;

  IF length(nome) < tamanho_minimo THEN
    RAISE EXCEPTION 'Nome de % inválido: deve ter, ao menos, % caractere(s)', tipo, tamanho_minimo;
  END IF;

  IF length(nome) > tamanho_maximo THEN
    RAISE EXCEPTION 'Nome de % inválido: não pode exceder % caractere(s)', tipo, tamanho_maximo;
  END IF;

  IF nome ~ '[\r]' THEN
    RAISE EXCEPTION 'Nome de % inválido: não deve conter Carriage Return', tipo;
  END IF;

  IF nome ~ '[\f]' THEN
    RAISE EXCEPTION 'Nome de % inválido: não deve conter Form Feed', tipo;
  END IF;

  IF nome ~* '[^a-záéíóúâêôãõçàèìòù0-9\-\(\):/+.&'' ]' THEN
    RAISE EXCEPTION 'Nome de % inválido: deve conter apenas letras, números, espaços e algumas pontuações (-, (, ), :, ., +, '')', tipo;
  END IF;

  RETURN TRUE;
END;
$$;