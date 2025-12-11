DROP SCHEMA IF EXISTS church CASCADE;
CREATE SCHEMA church;



CREATE TABLE church.tb_igreja (
  -- chaves primárias
  id UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  updated_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados da igreja
  is_deleted        BOOLEAN                                  NOT NULL DEFAULT FALSE,
  cnpj              VARCHAR(14)                              NOT NULL,
  nome              VARCHAR(100)                             NOT NULL,
  denominacao       utils.s_church_t_tb_igreja_e_denominacao NOT NULL,
  outra_denominacao VARCHAR(100)                                 NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_foto          UUID NOT NULL,
  s_auth_t_tb_usuario_c_adm_proprietario UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_igreja_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_igreja_c_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_igreja_c_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_igreja_c_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_igreja_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_igreja_c_adm_proprietario
    FOREIGN KEY (s_auth_t_tb_usuario_c_adm_proprietario)
    REFERENCES auth.tb_usuario (id)
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
  is_deleted            BOOLEAN                  NOT NULL DEFAULT FALSE,
  cep                   utils.domain_cep         NOT NULL,
  uf                    utils.domain_uf          NOT NULL,
  cidade                utils.domain_cidade      NOT NULL,
  bairro                utils.domain_bairro      NOT NULL,
  logradouro            utils.domain_logradouro  NOT NULL,
  numero                utils.domain_numero      NOT NULL,
  complemento           utils.domain_complemento     NULL,
  is_endereco_principal BOOLEAN                  NOT NULL,

  -- chaves estrangeiras
  igr_id UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_endereco_c_id PRIMARY KEY (igr_id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_endereco_c_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_endereco_c_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_endereco_c_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_endereco_c_igr_id
    FOREIGN KEY (igr_id)
    REFERENCES church.tb_igreja (id)
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
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  igr_id                    UUID NOT NULL,
  s_auth_t_tb_usuario_c_adm UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_administrador_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_administrador_c_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_administrador_c_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_administrador_c_igr_id
    FOREIGN KEY (igr_id)
    REFERENCES church.tb_igreja (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
  
  CONSTRAINT fk_s_church_t_tb_administrador_c_adm
    FOREIGN KEY (s_auth_t_tb_usuario_c_adm)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_categoria (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at  TIMESTAMPTZ     NULL DEFAULT NULL,
  disabled_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by  UUID        NOT NULL,
  updated_by  UUID        NOT NULL,
  deleted_by  UUID            NULL DEFAULT NULL,

  -- dados do tipo de agendamento
  is_deleted  BOOLEAN                              NOT NULL DEFAULT FALSE,
  is_disabled BOOLEAN                              NOT NULL DEFAULT FALSE,
  tipo        utils.s_church_t_tb_categoria_e_tipo NOT NULL,
  nome        VARCHAR(30)                          NOT NULL,
  descricao   VARCHAR(50)                          NOT NULL,

  -- chaves estrangeiras
  igr_id UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_categoria_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_categoria_c_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_categoria_c_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_categoria_c_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_categoria_c_igr_id
    FOREIGN KEY (igr_id)
    REFERENCES church.tb_igreja (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_faixa (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at  TIMESTAMPTZ     NULL DEFAULT NULL,
  disabled_at TIMESTAMPTZ     NULL DEFAULT NULL,
  
  -- dados da música da igreja
  is_deleted       BOOLEAN NOT NULL DEFAULT FALSE,
  is_disabled      BOOLEAN NOT NULL DEFAULT FALSE,
  snapshot_inicial JSONB   NOT NULL,
  dados            JSONB   NOT NULL,

  -- chaves estrangeiras
  igr_id                   UUID    NOT NULL,
  s_song_t_tb_musica_c_mus INTEGER     NULL,
  s_song_t_tb_medley_c_med INTEGER     NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_faixa_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_faixa_c_igr_id
    FOREIGN KEY (igr_id)
    REFERENCES church.tb_igreja (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_faixa_c_mus
    FOREIGN KEY (s_song_t_tb_musica_c_mus)
    REFERENCES song.tb_musica (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_faixa_c_med
    FOREIGN KEY (s_song_t_tb_medley_c_med)
    REFERENCES song.tb_medley (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_faixa_ass_categoria (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,

  -- dados da associação de faixas e categorias
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  fai_id INTEGER NOT NULL,
  cat_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_faixa_ass_categoria_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_faixa_ass_categoria_c_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_faixa_ass_categoria_c_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_faixa_ass_categoria_c_fai_id
    FOREIGN KEY (fai_id)
    REFERENCES church.tb_faixa (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_faixa_ass_categoria_c_cat_id
    FOREIGN KEY (cat_id)
    REFERENCES church.tb_categoria (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_ministerio_louvor (
  -- chaves primárias
  id UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by UUID        NOT NULL,
  updated_by UUID        NOT NULL,
  deleted_by UUID            NULL DEFAULT NULL,
  
  -- dados do ministério de louvor
  is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE,
  nome        VARCHAR(100) NOT NULL,
  descricao   VARCHAR(50)  NOT NULL,
  codigo      VARCHAR(6)   NOT NULL DEFAULT utils.s_church_f_get_codigo_ministerio(),

  -- chaves estrangeiras
  igr_id                        UUID NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID     NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_ministerio_louvor_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_igr_id
    FOREIGN KEY (igr_id)
    REFERENCES church.tb_igreja (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (id)
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
  is_deleted BOOLEAN                                     NOT NULL DEFAULT FALSE,
  funcao     utils.s_church_t_tb_usuario_funcao_e_funcao NOT NULL DEFAULT 'levita',

  -- chaves estrangeiras
  min_lou_id                UUID NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_usuario_funcao_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_usuario_funcao_c_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_usuario_funcao_c_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_usuario_funcao_c_min_lou_id
    FOREIGN KEY (min_lou_id)
    REFERENCES church.tb_ministerio_louvor (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_usuario_funcao_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_instrumento_marca (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados da marca do instrumento
  is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
  nome       VARCHAR(30) NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_marca_c_id PRIMARY KEY (id)
);



CREATE TABLE church.tb_instrumento_modelo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados da marca do instrumento
  is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
  nome       VARCHAR(30) NOT NULL,

  -- chaves estrangeiras
  ins_mar_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_modelo_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_instrumento_modelo_c_ins_mar_id
    FOREIGN KEY (ins_mar_id)
    REFERENCES church.tb_instrumento_marca (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_instrumento (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at  TIMESTAMPTZ     NULL DEFAULT NULL,
  disabled_at TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by  UUID        NOT NULL,
  updated_by  UUID        NOT NULL,
  deleted_by  UUID            NULL DEFAULT NULL,

  -- dados de instrumentos
  is_deleted   BOOLEAN                                   NOT NULL DEFAULT FALSE,
  is_disabled  BOOLEAN                                   NOT NULL DEFAULT FALSE,
  nome         utils.s_church_t_tb_instrumento_e_nome    NOT NULL,
  outro_nome   VARCHAR(30)                                   NULL,
  familia      utils.s_church_t_tb_instrumento_e_familia NOT NULL,
  outra_marca  VARCHAR(30)                                   NULL,
  outro_modelo VARCHAR(30)                                   NULL,

  -- chaves estrangeiras
  ins_mod_id                     INTEGER     NULL,
  igr_id                         UUID    NOT NULL,
  s_storage_t_tb_arquivo_c_foto  UUID        NULL,
  s_storage_t_tb_arquivo_c_icone UUID    NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_instrumento_c_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_instrumento_c_updated_by
    FOREIGN KEY (updated_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_instrumento_c_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_instrumento_c_ins_mod_id
    FOREIGN KEY (ins_mod_id)
    REFERENCES church.tb_instrumento_modelo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_instrumento_c_igr_id
    FOREIGN KEY (igr_id)
    REFERENCES church.tb_igreja (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
    
  CONSTRAINT fk_s_church_t_tb_instrumento_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
  
  CONSTRAINT fk_s_church_t_tb_instrumento_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (id)
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
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  ins_id                    INTEGER NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID    NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_ass_usuario_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_created_by
    FOREIGN KEY (created_by)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_deleted_by
    FOREIGN KEY (deleted_by)
    REFERENCES auth.tb_usuario (id)
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

  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);