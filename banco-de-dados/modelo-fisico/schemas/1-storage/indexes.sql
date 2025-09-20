-- tb_bucket
CREATE UNIQUE INDEX uq_s_storage_t_tb_bucket_c_nome
ON storage.tb_bucket (nome)
WHERE is_deleted = FALSE;


-- tb_arquivo
CREATE UNIQUE INDEX uq_s_storage_t_tb_arquivo_c_buc_id_c_nome_c_extensao
ON storage.tb_arquivo (buc_id, nome, extensao)
WHERE is_deleted = FALSE;