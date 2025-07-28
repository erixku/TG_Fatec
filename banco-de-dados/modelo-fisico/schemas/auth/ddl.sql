DROP SCHEMA IF EXISTS auth CASCADE;
CREATE SCHEMA auth;



CREATE TABLE auth.tb_endereco (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de endereço
  cep    app_utils.domain_cep   NOT NULL,
  uf     app_utils.domain_uf    NOT NULL,
  cidade VARCHAR(100)           NOT NULL,
  bairro VARCHAR(100)           NOT NULL,
  rua    VARCHAR(100)           NOT NULL,
  numero VARCHAR(5)             NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_endereco PRIMARY KEY (id)
);



CREATE TABLE auth.tb_usuario (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at    TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,
  ultimo_acesso TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados do usuário
  deletado         BOOLEAN      NOT NULL DEFAULT false,
  cpf              VARCHAR(11)  NOT NULL,
  nome             VARCHAR(20)  NOT NULL,
  sobrenome        VARCHAR(50)  NOT NULL,
  nome_social      VARCHAR(20)      NULL,
  sobrenome_social VARCHAR(50)      NULL,
  sexo             CHAR(1)      NOT NULL,
  data_nascimento  DATE         NOT NULL,
  email            VARCHAR(50)  NOT NULL,
  telefone         VARCHAR(25)  NOT NULL,
  senha            VARCHAR(128) NOT NULL,

  -- chaves estrangeiras
  end_id                        INTEGER NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID        NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_usuario PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_auth_t_tb_usuario_c_id       UNIQUE (id),
  CONSTRAINT uq_s_auth_t_tb_usuario_c_cpf      UNIQUE (cpf),
  CONSTRAINT uq_s_auth_t_tb_usuario_c_email    UNIQUE (email),
  CONSTRAINT uq_s_auth_t_tb_usuario_c_telefone UNIQUE (telefone),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_end_id
    FOREIGN KEY (end_id)
    REFERENCES auth.tb_endereco (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE auth.tb_registro_ausencia (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  
  -- dados do registro de ausência
  motivo         VARCHAR(50)                     NOT NULL,
  justificativa  VARCHAR(2000)                   NOT NULL,
  data           DATE                            NOT NULL,
  horario_inicio TIME          WITHOUT TIME ZONE NOT NULL,
  horario_fim    TIME          WITHOUT TIME ZONE NOT NULL,

  -- chaves estrangeiras
  usu_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_registro_ausencia PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_usu_uuid
    FOREIGN KEY (usu_uuid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);