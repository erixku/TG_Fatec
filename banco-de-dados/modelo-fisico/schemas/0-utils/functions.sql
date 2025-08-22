CREATE OR REPLACE FUNCTION
  utils.conversor_mb_para_byte(tamanho INTEGER)
RETURNS INTEGER
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF tamanho IS NULL THEN
    RAISE EXCEPTION 'O tamanho em megabytes não pode ser nulo';
  END IF;

  IF tamanho < 0 THEN
    RAISE EXCEPTION 'O tamanho em megabytes não pode ser negativo';
  END IF;

  RETURN tamanho * 1024 * 1024;
END;
$$;



CREATE OR REPLACE FUNCTION
  utils.get_codigo_ministerio()
RETURNS VARCHAR(6)
LANGUAGE plpgsql
VOLATILE
SECURITY DEFINER
SET search_path = church, pg_catalog
AS $$
DECLARE
  caracteres CONSTANT VARCHAR(62) := 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  codigo_gerado VARCHAR(6);
  is_codigo     BOOLEAN;
  tentativas    SMALLINT := 0;
BEGIN
  LOOP
    codigo_gerado := '';
    tentativas := tentativas + 1;

    -- gera o código do ministério
    FOR i IN 1..6 LOOP
      codigo_gerado := codigo_gerado || substr(caracteres, ceil(random() * length(caracteres))::int, 1);
    END LOOP;

    -- verifica se o código gerado já está em uso por algum ministério
    SELECT EXISTS (
      SELECT 1
      FROM church.tb_ministerio_louvor t
      WHERE codigo = codigo_gerado
    ) INTO is_codigo;

    -- caso não esteja em uso por outro ministério, retorna o código. Senão, tenta novamente
    IF NOT is_codigo THEN
      RETURN codigo_gerado;
    END IF;

    IF tentativas >= 10000 THEN
      RAISE EXCEPTION 'Falha ao gerar código único de ministério por excesso de tentativas';
    END IF;
  END LOOP;
END;
$$;



-- CREATE OR REPLACE VIEW utils.view_teste AS
--   SELECT id, cep, uf, cidade, bairro, rua, numero, complemento
--   FROM auth.tb_endereco;

-- SELECT * FROM utils.view_teste;