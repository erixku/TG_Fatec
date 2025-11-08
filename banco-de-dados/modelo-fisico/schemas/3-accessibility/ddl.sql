DROP SCHEMA IF EXISTS accessibility CASCADE;
CREATE SCHEMA accessibility;



CREATE TABLE accessibility.tb_intelectual (
  -- chaves primárias/estrangeiras
  id UUID NOT NULL,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  -- dados de acessibilidade intelectual
  tamanho_icone     CHAR(1) NOT NULL DEFAULT '3',
  modo_foco         BOOLEAN NOT NULL DEFAULT FALSE,
  feedback_imediato BOOLEAN NOT NULL DEFAULT FALSE,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_accessibility_t_tb_intelectual_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_accessibility_t_tb_intelectual_c_id
    FOREIGN KEY (id)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE accessibility.tb_auditiva (
  -- chaves primárias/estrangeiras
  id UUID NOT NULL,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados de acessibilidade auditiva
  modo_flash          BOOLEAN NOT NULL DEFAULT FALSE,
  intensidade_flash   CHAR(1) NOT NULL DEFAULT '3',
  transcricao_audio   BOOLEAN NOT NULL DEFAULT FALSE,
  vibracao_aprimorada BOOLEAN NOT NULL DEFAULT FALSE,
  alertas_visuais     BOOLEAN NOT NULL DEFAULT FALSE,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_accessibility_t_tb_auditiva_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_accessibility_t_tb_auditiva_c_id
    FOREIGN KEY (id)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE accessibility.tb_visual (
  -- chaves primárias/estrangeiras
  id UUID NOT NULL,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados de acessibilidade visual
  cor_tema               utils.s_accessibility_t_tb_visual_e_cor_tema   NOT NULL DEFAULT 'escuro',
  tamanho_texto          CHAR(1)                                        NOT NULL DEFAULT '3',
  negrito                BOOLEAN                                        NOT NULL DEFAULT FALSE,
  alto_contraste         BOOLEAN                                        NOT NULL DEFAULT FALSE,
  modo_daltonismo        utils.s_accessibility_t_tb_visual_e_daltonismo NOT NULL DEFAULT 'tricromata',
  intensidade_daltonismo CHAR(1)                                        NOT NULL DEFAULT '3',
  remover_animacoes      BOOLEAN                                        NOT NULL DEFAULT FALSE,
  vibrar_ao_tocar        BOOLEAN                                        NOT NULL DEFAULT FALSE,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_accessibility_t_tb_visual_c_id PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_accessibility_t_tb_visual_c_id
    FOREIGN KEY (id)
    REFERENCES auth.tb_usuario (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);