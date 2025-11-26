DROP SCHEMA IF EXISTS notification CASCADE;
CREATE SCHEMA notification;



CREATE TABLE notification.tb_tipo (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados do tipo de notificação
  is_deleted BOOLEAN                               NOT NULL DEFAULT FALSE,
  nome       utils.s_notification_t_tb_tipo_e_nome NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_icone UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_tipo_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_tipo_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_notificacao (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados da notificação
  descricao  VARCHAR(50)  NOT NULL,
  link       VARCHAR(200) NOT NULL,
  was_readed BOOLEAN      NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  tip_id                                    SMALLINT NOT NULL,
  s_auth_t_tb_usuario_c_notificado          UUID     NOT NULL,
  s_auth_t_tb_usuario_c_notificador         UUID         NULL,
  s_church_t_tb_ministerio_louvor_c_min_lou UUID         NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_notificacao_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_notificacao_c_tip_id
    FOREIGN KEY (tip_id)
    REFERENCES notification.tb_tipo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_notification_t_tb_notificacao_c_notificado
    FOREIGN KEY (s_auth_t_tb_usuario_c_notificado)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_notification_t_tb_notificacao_c_notificador
    FOREIGN KEY (s_auth_t_tb_usuario_c_notificador)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_notification_t_tb_notificacao_c_min_lou
    FOREIGN KEY (s_church_t_tb_ministerio_louvor_c_min_lou)
    REFERENCES church.tb_ministerio_louvor (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_tipo_por_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

   -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados do tipo de notificação por usuário
  is_disabled BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  tip_id                    SMALLINT NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID     NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_tipo_por_usuario_c_id PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_notification_t_tb_tipo_por_usuario_c_tip_id_c_lev
  UNIQUE (tip_id, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_tipo_por_usuario_c_tip_id
    FOREIGN KEY (tip_id)
    REFERENCES notification.tb_tipo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
    
  CONSTRAINT fk_s_notification_t_tb_tipo_por_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_cor (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados da cor da notificação
  is_deleted BOOLEAN                              NOT NULL DEFAULT FALSE,    
  nome       utils.s_notification_t_tb_cor_e_nome NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_icone UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_cor_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_cor_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_configuracao_por_usuario (
  -- chaves primárias/estrangeiras
  id UUID NOT NULL,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados da configuração de notificação por usuário
  ativar_notificacoes        BOOLEAN     NOT NULL DEFAULT TRUE,
  mostrar_em_tela_bloqueio   BOOLEAN     NOT NULL DEFAULT TRUE,
  nao_perturbar_horario      BOOLEAN     NOT NULL DEFAULT FALSE,
  nao_perturbar_horario_dias BIT(7)      NOT NULL DEFAULT B'0000000',
  horario_inicio             TIME(0)         NULL DEFAULT NULL,
  horario_fim                TIME(0)         NULL DEFAULT NULL,
  fuso_horario               VARCHAR(50)     NULL DEFAULT NULL,
  nao_perturbar_dia          BOOLEAN     NOT NULL DEFAULT FALSE,
  nao_perturbar_dia_dias     BIT(7)      NOT NULL DEFAULT B'0000000',

  -- chaves estrangeiras
  cor_pop_up SMALLINT NOT NULL DEFAULT 1,
  cor_led    SMALLINT NOT NULL DEFAULT 1,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_configuracao_por_usuario_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_configuracao_por_usuario_c_cor_pop_up
    FOREIGN KEY (cor_pop_up)
    REFERENCES notification.tb_cor (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
  
  CONSTRAINT fk_s_notification_t_tb_configuracao_por_usuario_c_cor_led
    FOREIGN KEY (cor_led)
    REFERENCES notification.tb_cor (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_notification_t_tb_configuracao_por_usuario_c_id
    FOREIGN KEY (id)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);