DROP SCHEMA IF EXISTS storage CASCADE;
CREATE SCHEMA storage;



CREATE TABLE storage.tb_bucket (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,

  -- dados do bucket
  deletado                           BOOLEAN                            NOT NULL DEFAULT false,
  nome                               enum_schema_storage_tb_bucket_nome NOT NULL UNIQUE,
  tempo_expiracao_upload_em_segundos SMALLINT                           NOT NULL DEFAULT 30,
  tamanho_minimo                     INTEGER                            NOT NULL DEFAULT 1,
  tamanho_maximo                     INTEGER                            NOT NULL DEFAULT get_tamanho_em_mb(1000)
);



CREATE TABLE storage.tb_arquivo (
  -- chaves primárias
  id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,

  -- dados do arquivo
  deletado         BOOLEAN                                  NOT NULL DEFAULT false,
  caminho          TEXT                                     NOT NULL,
  mime_type        enum_schema_storage_tb_arquivo_mime_type NOT NULL,
  extensao         enum_schema_storage_tb_arquivo_extensao  NOT NULL,
  tamanho_em_bytes INTEGER                                  NOT NULL,

  -- chaves estrangeiras
  buc_id SMALLINT NOT NULL,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_bucket_id
    FOREIGN KEY (buc_id)
    REFERENCES storage.tb_bucket (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE storage.tb_arquivo_ass_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- chaves estrangeiras
  arq_uuid                UUID NOT NULL,
  schema_auth_usuario_lev UUID NOT NULL,

  -- chaves únicas compostas
  UNIQUE (arq_uuid, schema_auth_usuario_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_arq_uuid
    FOREIGN KEY (arq_uuid)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_lev_uuid
    FOREIGN KEY (schema_auth_usuario_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);