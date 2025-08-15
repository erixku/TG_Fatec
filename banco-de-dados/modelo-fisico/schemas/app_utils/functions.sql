-- retorna um número passado com valor convertido para MB
CREATE OR REPLACE FUNCTION
app_utils.get_tamanho_em_mb(tamanho INT)
RETURNS INTEGER
LANGUAGE sql
IMMUTABLE
SECURITY DEFINER
SET search_path = storage, pg_temp
AS $$
  SELECT tamanho * 1024 * 1024
$$;





-- gera um código único e aleatório de 6 dígitos para
-- um ministério de louvor. Esse código é utilizado
-- por usuários para enviar solicitações de entrada
-- aos administradores/líderes no ministério de louvor
-- desejado
CREATE OR REPLACE FUNCTION
app_utils.get_codigo_ministerio()
RETURNS VARCHAR(6)
LANGUAGE plpgsql
VOLATILE
SECURITY DEFINER
SET search_path = church, pg_temp
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

    FOR i IN 1..6 LOOP
      codigo_gerado := codigo_gerado || substr(caracteres, ceil(random() * length(caracteres))::int, 1);
    END LOOP;

    -- verifica se o código gerado já existe
    SELECT EXISTS (
      SELECT 1
      FROM church.tb_ministerio_louvor t
      WHERE codigo = codigo_gerado
    ) INTO is_codigo;

    IF NOT is_codigo THEN
      RETURN codigo_gerado;
    END IF;

    IF tentativas >= 10000 THEN
      RAISE EXCEPTION 'Falha ao gerar código único de ministério por excesso de tentativas';
    END IF;
  END LOOP;
END;
$$;