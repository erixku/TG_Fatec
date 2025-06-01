DROP SCHEMA IF EXISTS church CASCADE;
CREATE SCHEMA church;



CREATE TABLE church.tb_endereco (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- dados do endereço
  cep    CHAR(8)      NOT NULL,
  uf     domain_uf    NOT NULL,
  cidade VARCHAR(100) NOT NULL,
  bairro VARCHAR(100) NOT NULL,
  rua    VARCHAR(100) NOT NULL,
  numero VARCHAR(5)   NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL
);



CREATE TABLE church.tb_igreja (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,

  -- dados da igreja
  deletado          BOOLEAN                                  NOT NULL DEFAULT false,
  cnpj              CHAR(14)                                 NOT NULL UNIQUE,
  nome              VARCHAR(100)                             NOT NULL,
  denominacao       enum_schema_church_tb_igreja_denominacao     NULL,
  outra_denominacao VARCHAR(100)                                 NULL,

  -- chaves estrangeiras
  end_endereco_principal_id   INTEGER NOT NULL,
  schema_storage_arquivo_foto UUID    NOT NULL,

  -- declaração das chaves estrangeiras
  CONSTRAINT fk_foto_uuid
    FOREIGN KEY (schema_storage_arquivo_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



-- declaração de relacionamento cíclico entre
-- church.tb_endereco e church.tb_igreja
ALTER TABLE church.tb_endereco
  ADD CONSTRAINT fk_igreja_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE church.tb_igreja
  ADD CONSTRAINT fK_endereco_principal_id
    FOREIGN KEY (end_endereco_principal_id)
    REFERENCES church.tb_endereco (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    DEFERRABLE INITIALLY DEFERRED;



CREATE TABLE church.tb_administrador (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- chaves estrangeiras
  igr_id                  UUID NOT NULL,
  schema_auth_usuario_adm UUID NOT NULL,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_igreja_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,
  
  CONSTRAINT fk_usuario_adm
    FOREIGN KEY (schema_auth_usuario_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_compromisso_tipo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- dados do tipo de compromisso
  ativo BOOLEAN     NOT NULL DEFAULT true,
  nome  VARCHAR(30) NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- chaves únicas compostas
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_igreja_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_agendamento_tipo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- dados do tipo de agendamento
  ativo BOOLEAN     NOT NULL DEFAULT true,
  nome  VARCHAR(30) NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- chaves únicas compostas
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_igreja_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_ministerio_louvor (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,
  
  -- dados do ministério de louvor
  deletado BOOLEAN NOT NULL DEFAULT false,
  nome     VARCHAR(100) NOT NULL,

  -- chaves estrangeiras
  igr_uuid                    UUID NOT NULL,
  schema_storage_arquivo_foto UUID NOT NULL,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_igreja_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_foto_uuid
    FOREIGN KEY (schema_storage_arquivo_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_usuario_funcao (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- dados de funções dos usuários
  funcao enum_schema_church_tb_usuario_funcao_funcao NOT NULL,

  -- chaves estrangeiras
  min_lou_uuid            UUID NOT NULL,
  schema_auth_usuario_lev UUID NOT NULL,

  -- chaves únicas compostas
  UNIQUE (funcao, min_lou_uuid, schema_auth_usuario_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_ministerio_louvor_uuid
    FOREIGN KEY (min_lou_uuid)
    REFERENCES church.tb_ministerio_louvor (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_usuario_lev
    FOREIGN KEY (schema_auth_usuario_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_instrumento (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- dados de instrumentos
  ativo BOOLEAN     NOT NULL DEFAULT true,
  nome  VARCHAR(25) NOT NULL,

  -- chaves estrangeiras
  schema_storage_arquivo_icone UUID NOT NULL,
  igr_uuid                     UUID NOT NULL,

  -- chaves únicas compostas
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_icone_uuid
    FOREIGN KEY (schema_storage_arquivo_icone)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_igreja_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_instrumento_ass_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  -- chaves estrangeiras
  ins_id                  INTEGER NOT NULL,
  schema_auth_usuario_lev UUID    NOT NULL,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_instrumento_id
    FOREIGN KEY (ins_id)
    REFERENCES church.tb_instrumento (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_usuario_lev
    FOREIGN KEY (schema_auth_usuario_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);