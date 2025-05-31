DROP SCHEMA IF EXISTS auth CASCADE;
CREATE SCHEMA auth;



CREATE TABLE auth.tb_endereco (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- dados de endereço
  cep    CHAR(8)      NOT NULL,
  uf     domain_uf    NOT NULL,
  cidade VARCHAR(100) NOT NULL,
  bairro VARCHAR(100) NOT NULL,
  rua    VARCHAR(100) NOT NULL,
  numero VARCHAR(5)   NOT NULL
);



CREATE TABLE auth.tb_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,

  -- dados de logs
  created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at    TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,
  ultimo_acesso TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados do usuário
  deletado         BOOLEAN      NOT NULL DEFAULT false,
  cpf              CHAR(11)     NOT NULL UNIQUE,
  nome             VARCHAR(20)  NOT NULL,
  sobrenome        VARCHAR(50)  NOT NULL,
  nome_social      VARCHAR(20)      NULL,
  sobrenome_social VARCHAR(50)      NULL,
  sexo             CHAR(1)      NOT NULL,
  data_aniversario DATE         NOT NULL,
  email            VARCHAR(50)  NOT NULL UNIQUE,
  telefone         VARCHAR(25)  NOT NULL UNIQUE,
  senha            CHAR(128)    NOT NULL,

  -- chaves estrangeiras
  end_id                      INTEGER NOT NULL,
  schema_storage_arquivo_foto UUID    NOT NULL,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_endereco_id
      FOREIGN KEY (end_id)
      REFERENCES auth.tb_endereco (id)
      ON UPDATE RESTRICT
      ON DELETE RESTRICT,

  CONSTRAINT fk_usuario_foto_id
      FOREIGN KEY (schema_storage_arquivo_foto)
      REFERENCES storage.tb_arquivo (uuid)
      ON UPDATE RESTRICT
      ON DELETE RESTRICT
);



CREATE TABLE auth.tb_registro_ausencia (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  
  -- dados do registro de ausência
  motivo         VARCHAR(50)                     NOT NULL,
  justificativa  VARCHAR(2000)                   NOT NULL,
  data           DATE                            NOT NULL,
  horario_inicio TIME          WITHOUT TIME ZONE NOT NULL,
  horario_fim    TIME          WITHOUT TIME ZONE NOT NULL,

  -- chaves estrangeiras
  schema_auth_usuario_lev UUID NOT NULL,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_usuario_lev
    FOREIGN KEY (schema_auth_usuario_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);