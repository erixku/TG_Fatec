DROP SCHEMA IF EXISTS auth CASCADE;
CREATE SCHEMA auth;



CREATE TABLE auth.tb_usuario (
  -- chaves primárias
  id UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at  TIMESTAMPTZ     NULL DEFAULT NULL,
  last_access TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados do usuário
  is_deleted       BOOLEAN      NOT NULL DEFAULT FALSE,
  cpf              VARCHAR(11)  NOT NULL,
  nome             VARCHAR(20)  NOT NULL,
  sobrenome        VARCHAR(50)  NOT NULL,
  nome_social      VARCHAR(20)      NULL,
  sobrenome_social VARCHAR(50)      NULL,
  sexo             CHAR(1)      NOT NULL,
  data_nascimento  DATE         NOT NULL,
  email            VARCHAR(79)  NOT NULL,
  telefone         VARCHAR(11)  NOT NULL,
  senha            VARCHAR(128) NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_foto UUID NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_usuario_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE auth.tb_endereco (
  -- chaves primárias/estrangeiras
  id UUID NOT NULL,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados de endereço
  cep         utils.domain_cep         NOT NULL,
  uf          utils.domain_uf          NOT NULL,
  cidade      utils.domain_cidade      NOT NULL,
  bairro      utils.domain_bairro      NOT NULL,
  logradouro  utils.domain_logradouro  NOT NULL,
  numero      utils.domain_numero      NOT NULL,
  complemento utils.domain_complemento     NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_endereco_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_endereco_c_id
    FOREIGN KEY (id)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);