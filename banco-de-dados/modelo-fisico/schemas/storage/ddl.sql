DROP SCHEMA IF EXISTS storage CASCADE;
CREATE SCHEMA storage;



CREATE TABLE storage.tb_bucket (
  id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  nome VARCHAR(30) NOT NULL
);



CREATE TABLE storage.tb_arquivo (
  -- chaves primárias
  id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados do arquivo
  deletado     BOOLEAN                                 NOT NULL DEFAULT false,
  caminho      TEXT                                    NOT NULL,
  extensao     schema_storage_tb_arquivo_enum_extensao NOT NULL,
  tamanho_byte INTEGER                                 NOT NULL,

  -- chaves estrangeiras
  buc_id INTEGER NOT NULL,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_buc_id
    FOREIGN KEY (buc_id)
    REFERENCES storage.tb_bucket (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE storage.tb_arquivo_ass_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- chaves estrangeiras
  arq_id                  UUID NOT NULL,
  schema_auth_usuario_lev UUID NOT NULL,

  -- chaves únicas compostas
  UNIQUE (arq_id, schema_auth_usuario_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_arq_uuid
    FOREIGN KEY (arq_id)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_lev_uuid
    FOREIGN KEY (schema_auth_usuario_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);