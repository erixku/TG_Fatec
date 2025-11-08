DROP SCHEMA IF EXISTS storage CASCADE;
CREATE SCHEMA storage;



CREATE TABLE storage.tb_bucket (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados do bucket
  is_deleted                         BOOLEAN                            NOT NULL DEFAULT FALSE,
  nome                               utils.s_storage_t_tb_bucket_e_nome NOT NULL,
  tempo_expiracao_upload_em_segundos SMALLINT                           NOT NULL DEFAULT 30,
  tamanho_minimo                     INTEGER                            NOT NULL DEFAULT 1,
  tamanho_maximo                     INTEGER                            NOT NULL DEFAULT utils.s_utils_f_conversor_mb_para_byte(1000),

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_bucket_c_id PRIMARY KEY (id)
);



CREATE TABLE storage.tb_arquivo (
  -- chaves primárias
  id UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados do arquivo
  is_deleted       BOOLEAN                                  NOT NULL DEFAULT FALSE,
  nome             VARCHAR(300)                             NOT NULL,
  link             TEXT                                     NOT NULL,
  mime_type        utils.s_storage_t_tb_arquivo_e_mime_type NOT NULL,
  extensao         utils.s_storage_t_tb_arquivo_e_extensao  NOT NULL,
  tamanho_em_bytes INTEGER                                  NOT NULL,

  -- chaves estrangeiras
  buc_id SMALLINT NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_arquivo_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_buc_id
    FOREIGN KEY (buc_id)
    REFERENCES storage.tb_bucket (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



-- declaração de chaves estrangeiras de logs (STORAGE.TB_ARQUIVO)
ALTER TABLE storage.tb_arquivo
ADD CONSTRAINT fk_s_storage_t_tb_arquivo_c_created_by
  FOREIGN KEY (created_by)
  REFERENCES auth.tb_usuario (id)
  ON UPDATE RESTRICT
  ON DELETE RESTRICT
  DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE storage.tb_arquivo
ADD CONSTRAINT fk_s_storage_t_tb_arquivo_c_deleted_by
  FOREIGN KEY (deleted_by)
  REFERENCES auth.tb_usuario (id)
  ON UPDATE RESTRICT
  ON DELETE RESTRICT
  NOT DEFERRABLE INITIALLY IMMEDIATE;