CREATE OR REPLACE FUNCTION
  utils.s_church_f_validador_descricao(descricao TEXT, tamanho_maximo SMALLINT)
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

  IF tamanho_maximo IS NULL THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser nulo'
  END IF;

  descricao := trim(descricao);
  IF descricao = '' THEN
    RAISE EXCEPTION 'Descrição inválida: não pode ser vazia';
  END IF;

  IF tamanho_maximo < 1 THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser menor que 1';
  END IF;

  IF length(descricao) < 5

  IF length(descricao) > tamanho_maximo THEN
    RAISE EXCEPTION 'Descrição inválida: não pode exceder % caracteres', tamanho_maximo;
  END IF;
END;
$$;