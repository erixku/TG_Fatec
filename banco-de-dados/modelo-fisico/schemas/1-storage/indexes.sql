-- tb_bucket
CREATE UNIQUE INDEX uq_s_storage_t_tb_bucket_c_nome
ON storage.tb_bucket (nome)
WHERE is_deleted = FALSE;


-- tb_arquivo
CREATE UNIQUE INDEX uq_s_storage_t_tb_arquivo_c_link
ON storage.tb_arquivo (link)
WHERE is_deleted = FALSE;