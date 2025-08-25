DROP SCHEMA IF EXISTS church CASCADE;
CREATE SCHEMA church;



CREATE TABLE church.tb_igreja (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  updated_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados da igreja
  is_deletado       BOOLEAN                                       NOT NULL DEFAULT FALSE,
  cnpj              VARCHAR(14)                                   NOT NULL,
  nome              VARCHAR(100)                                  NOT NULL,
  denominacao       utils.enum_s_church_t_tb_igreja_c_denominacao NOT NULL,
  outra_denominacao VARCHAR(100)                                      NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_foto          UUID NOT NULL,
  s_auth_t_tb_usuario_c_adm_proprietario UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_igreja PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_church_t_tb_igreja_c_cnpj UNIQUE (cnpj),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_adm_proprietario
    FOREIGN KEY (s_auth_t_tb_usuario_c_adm_proprietario)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_endereco (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  updated_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados do endereço
  is_deletado           BOOLEAN                  NOT NULL DEFAULT FALSE,
  cep                   utils.domain_cep         NOT NULL,
  uf                    utils.domain_uf          NOT NULL,
  cidade                utils.domain_cidade      NOT NULL,
  bairro                utils.domain_local       NOT NULL,
  logradouro            utils.domain_local       NOT NULL,
  numero                utils.domain_numero      NOT NULL,
  complemento           utils.domain_complemento     NULL,
  is_endereco_principal BOOLEAN                  NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_endereco PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_igreja_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_administrador (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados do administrador
  is_deletado BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  igr_uuid                  UUID NOT NULL,
  s_auth_t_tb_usuario_c_adm UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_administrador PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_administrador_c_igr_uuid_c_adm
  UNIQUE (igr_uuid, s_auth_t_tb_usuario_c_adm),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_igreja_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
  
  CONSTRAINT fk_s_auth_t_tb_usuario_c_adm
    FOREIGN KEY (s_auth_t_tb_usuario_c_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_compromisso_tipo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  updated_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados do tipo de compromisso
  is_deletado BOOLEAN     NOT NULL DEFAULT FALSE,
  is_ativo    BOOLEAN     NOT NULL DEFAULT TRUE,
  nome        VARCHAR(30) NOT NULL,
  descricao   VARCHAR(50) NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_compromisso_tipo PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_compromisso_tipo_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_compromisso_tipo_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_agendamento_tipo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  updated_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados do tipo de agendamento
  is_deletado BOOLEAN     NOT NULL DEFAULT FALSE,
  is_ativo    BOOLEAN     NOT NULL DEFAULT TRUE,
  nome        VARCHAR(30) NOT NULL,
  descricao   VARCHAR(50) NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_agendamento_tipo PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_agendamento_tipo_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_agendamento_tipo_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_ministerio_louvor (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  updated_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,
  
  -- dados do ministério de louvor
  is_deletado BOOLEAN      NOT NULL DEFAULT FALSE,
  nome        VARCHAR(100) NOT NULL,
  descricao   VARCHAR(50)  NOT NULL,
  codigo      VARCHAR(6)   NOT NULL DEFAULT utils.s_church_f_get_codigo_ministerio(),

  -- chaves estrangeiras
  igr_uuid                      UUID NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_ministerio_louvor PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_church_t_tb_ministerio_louvor_c_codigo UNIQUE (codigo),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_ministerio_louvor_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_igreja_c_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_storage_t_tb_arquivo_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_usuario_funcao (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados de funções dos usuários
  is_deletado BOOLEAN                                          NOT NULL DEFAULT FALSE,
  funcao      utils.enum_s_church_t_tb_usuario_funcao_c_funcao NOT NULL DEFAULT 'Levita',

  -- chaves estrangeiras
  min_lou_uuid              UUID NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_usuario_funcao PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_usuario_funcao_c_funcao_c_min_lou_uuid_c_lev
  UNIQUE (funcao, min_lou_uuid, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_min_lou_uuid
    FOREIGN KEY (min_lou_uuid)
    REFERENCES church.tb_ministerio_louvor (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_instrumento (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  updated_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados de instrumentos
  is_deletado BOOLEAN                                        NOT NULL DEFAULT FALSE,
  is_ativo    BOOLEAN                                        NOT NULL DEFAULT TRUE,
  nome        utils.enum_s_church_t_tb_instrumento_c_nome    NOT NULL,
  outro_nome  VARCHAR(25)                                        NULL,
  familia     utils.enum_s_church_t_tb_instrumento_c_familia NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_foto  UUID     NULL,
  s_storage_t_tb_arquivo_c_icone UUID NOT NULL,
  igr_uuid                       UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_instrumento_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
  
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_igreja_c_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_instrumento_ass_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados da associação de instrumento e usuário
  is_deletado BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  ins_id                    INTEGER NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID    NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_ass_usuario PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_instrumento_ass_usuario_c_ins_id_c_lev
  UNIQUE (ins_id, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_ins_id
    FOREIGN KEY (ins_id)
    REFERENCES church.tb_instrumento (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);