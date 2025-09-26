DROP SCHEMA IF EXISTS notification CASCADE;
CREATE SCHEMA notification;



CREATE TABLE notification.tb_tipo (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados do tipo de notificação
  nome utils.enum_s_notification_t_tb_tipo_c_nome NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_icone UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_tipo PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_tipo_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (uuid)
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
  descricao VARCHAR(50)  NOT NULL,
  link      VARCHAR(200) NOT NULL,
  is_lida   BOOLEAN      NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  tip_id                            SMALLINT NOT NULL,
  s_auth_t_tb_usuario_c_notificado  UUID     NOT NULL,
  s_auth_t_tb_usuario_c_notificador UUID     NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_notificacao PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_notificacao_c_tip_id
    FOREIGN KEY (tip_id)
    REFERENCES notification.tb_tipo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_notification_t_tb_notificacao_c_notificado
    FOREIGN KEY (s_auth_t_tb_usuario_c_notificado)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_notification_t_tb_notificacao_c_notificador
    FOREIGN KEY (s_auth_t_tb_usuario_c_notificador)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_tipo_por_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados do tipo de notificação por usuário
  is_active BOOLEAN NOT NULL DEFAULT TRUE,

  -- chaves estrangeiras
  tip_id                         SMALLINT NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_tipo_por_usuario PRIMARY KEY (id),

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
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_cor (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados da cor da notificação
  nome utils.enum_s_notification_t_tb_cor_c_nome NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_icone UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_cor PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_cor_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_configuracao_por_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados da configuração de notificação por usuário
  ativar_notificacoes      BOOLEAN   NOT NULL DEFAULT TRUE,
  mostrar_em_tela_bloqueio BOOLEAN   NOT NULL DEFAULT TRUE,
  nao_perturbar_horario    BOOLEAN   NOT NULL DEFAULT FALSE,
  periodo                  TSTZRANGE     NULL DEFAULT NULL,
  nao_perturbar_dia        BOOLEAN   NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  cor_pop_up                SMALLINT NOT NULL DEFAULT 1,
  cor_led                   SMALLINT NOT NULL DEFAULT 1,
  s_auth_t_tb_usuario_c_lev UUID     NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_configuracao_por_usuario PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_notification_t_tb_configuracao_por_usuario_c_lev
  UNIQUE (s_auth_t_tb_usuario_c_lev),

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

  CONSTRAINT fk_s_notification_t_tb_configuracao_por_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_nao_perturbar (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados dos horários de não perturbe do usuário
  tipo CHAR(1) NOT NULL,
  dia  CHAR(1) NOT NULL,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_nao_perturbar PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT pk_s_notification_t_tb_nao_perturbar_c_tipo_c_dia_c_lev
  UNIQUE (tipo, dia, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_nao_perturbar_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);