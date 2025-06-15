DROP SCHEMA IF EXISTS church CASCADE;
CREATE SCHEMA church;



CREATE TABLE church.tb_endereco (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados do endereço
  cep    app_utils.domain_cep   NOT NULL,
  uf     app_utils.domain_uf    NOT NULL,
  cidade VARCHAR(100)           NOT NULL,
  bairro VARCHAR(100)           NOT NULL,
  rua    VARCHAR(100)           NOT NULL,
  numero VARCHAR(5)             NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_endereco PRIMARY KEY (id)
);



CREATE TABLE church.tb_igreja (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,

  -- dados da igreja
  deletado          BOOLEAN                                           NOT NULL DEFAULT false,
  cnpj              VARCHAR(14)                                       NOT NULL,
  nome              VARCHAR(100)                                      NOT NULL,
  denominacao       app_utils.enum_s_church_t_tb_igreja_c_denominacao     NULL,
  outra_denominacao VARCHAR(100)                                          NULL,

  -- chaves estrangeiras
  end_endereco_principal_id     INTEGER NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID    NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_igreja PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_church_t_tb_igreja_c_id UNIQUE (id),
  CONSTRAINT uq_s_church_t_tb_igreja_c_cnpj UNIQUE (cnpj),

  -- declaração das chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



-- declaração de relacionamento cíclico entre
-- church.tb_endereco e church.tb_igreja
ALTER TABLE church.tb_endereco
  ADD CONSTRAINT fk_s_church_t_tb_endereco_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE church.tb_igreja
  ADD CONSTRAINT fk_s_church_t_tb_igreja_c_end_endereco_principal_id
    FOREIGN KEY (end_endereco_principal_id)
    REFERENCES church.tb_endereco (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    DEFERRABLE INITIALLY DEFERRED;



CREATE TABLE church.tb_administrador (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- chaves estrangeiras
  igr_uuid                  UUID NOT NULL,
  s_auth_t_tb_usuario_c_adm UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_administrador PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_administrador_c_igr_uuid_c_adm
  UNIQUE (igr_uuid, s_auth_t_tb_usuario_c_adm),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_igreja_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,
  
  CONSTRAINT fk_s_auth_t_tb_usuario_c_adm
    FOREIGN KEY (s_auth_t_tb_usuario_c_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_compromisso_tipo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados do tipo de compromisso
  ativo BOOLEAN     NOT NULL DEFAULT true,
  nome  VARCHAR(30) NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_compromisso_tipo PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_compromisso_tipo_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_compromisso_tipo_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_agendamento_tipo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados do tipo de agendamento
  ativo BOOLEAN     NOT NULL DEFAULT true,
  nome  VARCHAR(30) NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_agendamento_tipo PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_agendamento_tipo_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_agendamento_tipo_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_ministerio_louvor (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,
  
  -- dados do ministério de louvor
  deletado BOOLEAN      NOT NULL DEFAULT false,
  nome     VARCHAR(100) NOT NULL,

  -- chaves estrangeiras
  igr_uuid                      UUID NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_ministerio_louvor PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_church_t_tb_ministerio_louvor_c_id UNIQUE (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_ministerio_louvor_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_igreja_c_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_storage_t_tb_arquivo_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_usuario_funcao (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de funções dos usuários
  funcao app_utils.enum_s_church_t_tb_usuario_funcao_c_funcao NOT NULL,

  -- chaves estrangeiras
  min_lou_uuid              UUID NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_usuario_funcao PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_usuario_funcao_c_funcao_c_min_lou_uuid_c_lev
  UNIQUE (funcao, min_lou_uuid, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_min_lou_uuid
    FOREIGN KEY (min_lou_uuid)
    REFERENCES church.tb_ministerio_louvor (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_instrumento (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de instrumentos
  ativo BOOLEAN     NOT NULL DEFAULT true,
  nome  VARCHAR(25) NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_icone UUID NOT NULL,
  igr_uuid                       UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_instrumento_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_church_t_tb_igreja_c_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_instrumento_ass_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- chaves estrangeiras
  ins_id                  INTEGER NOT NULL,
  s_auth_t_tb_usuario_lev UUID    NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_ass_usuario PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_instrumento_ass_usuario_c_ins_id_c_lev
  UNIQUE (ins_id, s_auth_t_tb_usuario_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_ins_id
    FOREIGN KEY (ins_id)
    REFERENCES church.tb_instrumento (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_auth_t_tb_usuario_lev
    FOREIGN KEY (s_auth_t_tb_usuario_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);