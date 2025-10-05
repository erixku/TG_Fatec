DROP SCHEMA IF EXISTS schedule CASCADE;
CREATE SCHEMA schedule;



CREATE TABLE schedule.tb_registro_ausencia (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_lev UUID        NOT NULL,
  
  -- dados do registro de ausência
  is_deleted    BOOLEAN                                               NOT NULL DEFAULT FALSE,
  motivo        utils.enum_s_schedule_t_tb_registro_ausencia_c_motivo NOT NULL,
  outro_motivo  VARCHAR(20)                                               NULL DEFAULT NULL,
  justificativa VARCHAR(2000)                                         NOT NULL,
  periodo       TSTZRANGE                                             NOT NULL,

  -- chaves estrangeiras
  s_church_t_tb_igreja_c_igreja UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_schedule_t_tb_registro_ausencia PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_schedule_t_tb_registro_ausencia_c_created_by_lev
    FOREIGN KEY (created_by_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_schedule_t_tb_registro_ausencia_c_igreja
    FOREIGN KEY (s_church_t_tb_igreja_c_igreja)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE schedule.tb_publicacao (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_lev UUID        NOT NULL,
  updated_by_lev UUID        NOT NULL,
  deleted_by_lev UUID            NULL DEFAULT NULL,
  
  -- dados da publicação
  is_deleted BOOLEAN                                      NOT NULL DEFAULT FALSE,
  titulo     VARCHAR(50)                                  NOT NULL,
  tipo       utils.enum_s_schedule_t_tb_publicacao_c_tipo NOT NULL,
  descricao  VARCHAR(2000)                                NOT NULL,

  -- chaves estrangeiras
  s_church_t_tb_igreja_c_igreja UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_schedule_t_tb_publicacao PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_schedule_t_tb_publicacao_c_created_by_lev
    FOREIGN KEY (created_by_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_publicacao_c_updated_by_lev
    FOREIGN KEY (updated_by_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_publicacao_c_deleted_by_lev
    FOREIGN KEY (deleted_by_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_schedule_t_tb_publicacao_c_igreja
    FOREIGN KEY (s_church_t_tb_igreja_c_igreja)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE schedule.tb_atividade (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  
  -- dados da atividade
  periodo TSTZRANGE NOT NULL,

  -- chaves estrangeiras
  pub_id                              INTEGER NOT NULL,
  s_church_t_tb_categoria_c_categoria INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_schedule_t_tb_atividade PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_schedule_t_tb_atividade_c_pub_id
    FOREIGN KEY (pub_id)
    REFERENCES schedule.tb_publicacao (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_atividade_c_categoria
    FOREIGN KEY (s_church_t_tb_categoria_c_categoria)
    REFERENCES church.tb_categoria (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE schedule.tb_participante (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_lid UUID        NOT NULL,
  updated_by_lev UUID        NOT NULL,
  deleted_by_lid UUID            NULL DEFAULT NULL,
  
  -- dados da publicação
  is_deleted              BOOLEAN NOT NULL DEFAULT FALSE,
  participacao_confirmada BOOLEAN NOT NULL DEFAULT TRUE,

  -- chaves estrangeiras
  ati_id                                         INTEGER NOT NULL,
  s_church_t_tb_instrumento_ass_usuario_c_funcao INTEGER     NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_schedule_t_tb_participante PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_schedule_t_tb_participante_c_created_by_lid
    FOREIGN KEY (created_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_participante_c_updated_by_lev
    FOREIGN KEY (updated_by_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_participante_c_deleted_by_lid
    FOREIGN KEY (deleted_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_participante_c_funcao
    FOREIGN KEY (s_church_t_tb_instrumento_ass_usuario_c_funcao)
    REFERENCES church.tb_instrumento_ass_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE schedule.tb_item_levado (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_lev UUID        NOT NULL,
  deleted_by_lev UUID            NULL DEFAULT NULL,
  
  -- dados do item levado
  is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
  nome       VARCHAR(30) NOT NULL,
  descricao  VARCHAR(50) NOT NULL,

  -- chaves estrangeiras
  ati_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_schedule_t_tb_item_levado PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_schedule_t_tb_item_levado_c_created_by_lev
    FOREIGN KEY (created_by_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_item_levado_c_deleted_by_lev
    FOREIGN KEY (deleted_by_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_schedule_t_tb_item_levado_c_ati_id
    FOREIGN KEY (ati_id)
    REFERENCES schedule.tb_atividade (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE schedule.tb_faixa_elencada (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_min UUID        NOT NULL,
  updated_by_min UUID        NOT NULL,
  deleted_by_min UUID            NULL DEFAULT NULL,
  
  -- dados da faixa elencada
  is_deleted BOOLEAN       NOT NULL DEFAULT FALSE,
  posicao    SMALLINT      NOT NULL,
  observacao VARCHAR(2000)     NULL,

  -- chaves estrangeiras
  ati_id                      INTEGER NOT NULL,
  s_church_t_tb_faixa_c_faixa INTEGER     NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_schedule_t_tb_faixa_elencada PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_schedule_t_tb_faixa_elencada_c_created_by_min
    FOREIGN KEY (created_by_min)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_faixa_elencada_c_updated_by_min
    FOREIGN KEY (updated_by_min)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_faixa_elencada_c_deleted_by_min
    FOREIGN KEY (deleted_by_min)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_schedule_t_tb_faixa_elencada_c_ati_id
    FOREIGN KEY (ati_id)
    REFERENCES schedule.tb_atividade (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_faixa_elencada_c_faixa
    FOREIGN KEY (s_church_t_tb_faixa_c_faixa)
    REFERENCES church.tb_faixa (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE schedule.tb_escala (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_lid UUID        NOT NULL,
  updated_by_lid UUID        NOT NULL,
  deleted_by_lid UUID            NULL DEFAULT NULL,
  
  -- dados da escala
  is_deleted            BOOLEAN       NOT NULL DEFAULT FALSE,
  nome                  VARCHAR(50)   NOT NULL,
  descricao             VARCHAR(2000) NOT NULL,
  quantidade_atividades SMALLINT      NOT NULL,

  -- chaves estrangeiras
  s_church_t_tb_igreja_c_igreja UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_schedule_t_tb_escala PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_schedule_t_tb_escala_c_created_by_lid
    FOREIGN KEY (created_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_escala_c_updated_by_lid
    FOREIGN KEY (updated_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_escala_c_deleted_by_lid
    FOREIGN KEY (deleted_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_schedule_t_tb_escala_c_igreja
    FOREIGN KEY (s_church_t_tb_igreja_c_igreja)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE schedule.tb_escala_ass_atividade (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_lid UUID        NOT NULL,
  updated_by_lid UUID        NOT NULL,
  deleted_by_lid UUID            NULL DEFAULT NULL,
  
  -- dados da associação entre escala e atividade
  is_deleted BOOLEAN  NOT NULL DEFAULT FALSE,
  posicao    SMALLINT NOT NULL,

  -- chaves estrangeiras
  esc_id INTEGER NOT NULL,
  ati_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_schedule_t_tb_escala_ass_atividade PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_schedule_t_tb_escala_ass_atividade_c_created_by_lid
    FOREIGN KEY (created_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_escala_ass_atividade_c_updated_by_lid
    FOREIGN KEY (updated_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_escala_ass_atividade_c_deleted_by_lid
    FOREIGN KEY (deleted_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_schedule_t_tb_escala_ass_atividade_c_esc_id
    FOREIGN KEY (esc_id)
    REFERENCES schedule.tb_escala (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_schedule_t_tb_escala_ass_atividade_c_ati_id
    FOREIGN KEY (ati_id)
    REFERENCES schedule.tb_atividade (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);