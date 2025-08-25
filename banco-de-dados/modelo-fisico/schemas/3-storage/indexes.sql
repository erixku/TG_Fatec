CREATE UNIQUE INDEX uq_s_storage_t_tb_arquivo_c_buc_id_c_nome_c_extensao
ON storage.tb_arquivo (buc_id, nome, extensao)
WHERE is_deletado = FALSE;