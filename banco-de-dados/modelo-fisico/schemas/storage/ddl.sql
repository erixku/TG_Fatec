DROP SCHEMA IF EXISTS storage CASCADE;
CREATE SCHEMA storage;



CREATE TABLE storage.tb_bucket (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,

  -- dados do bucket
  deletado                           BOOLEAN                                      NOT NULL DEFAULT false,
  nome                               app_utils.enum_s_storage_t_tb_bucket_c_nome  NOT NULL,
  tempo_expiracao_upload_em_segundos SMALLINT                                     NOT NULL DEFAULT 30,
  tamanho_minimo                     INTEGER                                      NOT NULL DEFAULT 1,
  tamanho_maximo                     INTEGER                                      NOT NULL DEFAULT app_utils.get_tamanho_em_mb(1000),

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_bucket PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_storage_t_tb_bucket_c_nome UNIQUE (nome)
);



CREATE TABLE storage.tb_arquivo (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),
  id   INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,

  -- dados do arquivo
  deletado         BOOLEAN                                            NOT NULL DEFAULT false,
  caminho          TEXT                                               NOT NULL,
  mime_type        app_utils.enum_s_storage_t_tb_arquivo_c_mime_type  NOT NULL,
  extensao         app_utils.enum_s_storage_t_tb_arquivo_c_extensao   NOT NULL,
  tamanho_em_bytes INTEGER                                            NOT NULL,

  -- chaves estrangeiras
  buc_id SMALLINT NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_arquivo PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_storage_t_tb_arquivo_c_id UNIQUE (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_bucket_c_id
    FOREIGN KEY (buc_id)
    REFERENCES storage.tb_bucket (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE storage.tb_arquivo_ass_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- chaves estrangeiras
  arq_uuid                  UUID NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_arquivo_ass_usuario PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_storage_t_tb_arquivo_ass_usuario_c_uuid_c_lev
  UNIQUE (arq_uuid, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_arq_uuid
    FOREIGN KEY (arq_uuid)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);