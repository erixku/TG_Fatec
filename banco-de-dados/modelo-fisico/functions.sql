-- retorna um número passado com valor convertido para MB
CREATE OR REPLACE FUNCTION
get_tamanho_em_mb(tamanho INT)
RETURNS INTEGER
LANGUAGE sql
SECURITY DEFINER
AS $$
  SELECT tamanho * 1024 * 1024
$$;