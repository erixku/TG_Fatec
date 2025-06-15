-- garante que o upload de arquivos
-- dure de mais que zero a no máximo 5 minutos
ALTER TABLE storage.tb_bucket
ADD CONSTRAINT
check_s_storage_t_tb_bucket_c_tempo_expiracao
CHECK (
  tempo_expiracao_upload_em_segundos > 0 AND
  tempo_expiracao_upload_em_segundos <= 300
);

-- garante que o tamanho mínimo e máximo
-- dos arquivos esteja no intervalo de
-- 1 byte a 1 gigabyte
ALTER TABLE storage.tb_bucket
ADD CONSTRAINT
check_s_storage_t_tb_bucket_c_tamanho_minimo_e_maximo
CHECK (
  tamanho_minimo > 0 AND
  tamanho_minimo <= get_tamanho_em_mb(1000) AND
  tamanho_maximo > 0 AND
  tamanho_maximo <= get_tamanho_em_mb(1000) AND
  tamanho_maximo >= tamanho_minimo
);