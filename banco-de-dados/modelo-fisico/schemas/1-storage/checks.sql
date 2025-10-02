ALTER TABLE storage.tb_bucket
ADD CONSTRAINT ck_s_storage_t_tb_bucket_c_tempo_expiracao
CHECK (
  tempo_expiracao_upload_em_segundos > 0 AND
  tempo_expiracao_upload_em_segundos <= 300
);

-- garante que arquivos de buckets tenham de 1 byte a 1 gigabyte de tamanho
ALTER TABLE storage.tb_bucket
ADD CONSTRAINT ck_s_storage_t_tb_bucket_c_tamanho_minimo_e_maximo
CHECK (
  tamanho_minimo > 0 AND
  tamanho_minimo <= utils.conversor_mb_para_byte(1000) AND
  tamanho_maximo > 0 AND
  tamanho_maximo <= utils.conversor_mb_para_byte(1000) AND
  tamanho_maximo >= tamanho_minimo
);

ALTER TABLE storage.tb_arquivo
ADD CONSTRAINT ck_s_storage_t_tb_arquivo_c_nome
CHECK (
  nome ~ (
    '^'      ||
    '['      ||
      'a-z'  ||
      'A-Z'  ||
      '0-9'  ||
      '\-_ ' ||
    ']'      ||
    '+'      ||
    '$'
  )
);