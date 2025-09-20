DROP SCHEMA IF EXISTS accessibility CASCADE;
CREATE SCHEMA accessibility;



CREATE TABLE accessibility.tb_intelectual (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  
  -- dados de acessibilidade intelectual
  tamanho_icone     CHAR(1) NOT NULL DEFAULT '3',
  modo_foco         BOOLEAN NOT NULL DEFAULT FALSE,
  feedback_imediato BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_usu UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_accessibility_t_tb_intelectual PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_accessibility_t_tb_intelectual_c_usu UNIQUE (s_auth_t_tb_usuario_c_usu),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_accessibility_t_tb_intelectual_c_usu
    FOREIGN KEY (s_auth_t_tb_usuario_c_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE accessibility.tb_auditiva (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de acessibilidade auditiva
  modo_flash          BOOLEAN NOT NULL DEFAULT FALSE,
  intensidade_flash   CHAR(1) NOT NULL DEFAULT '3',
  transcricao_audio   BOOLEAN NOT NULL DEFAULT FALSE,
  vibracao_aprimorada BOOLEAN NOT NULL DEFAULT FALSE,
  alertas_visuais     BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_usu UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_accessibility_t_tb_auditiva PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_accessibility_t_tb_auditiva_c_usu UNIQUE (s_auth_t_tb_usuario_c_usu),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_accessibility_t_tb_auditiva_c_usu
    FOREIGN KEY (s_auth_t_tb_usuario_c_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE accessibility.tb_visual (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de acessibilidade visual
  cor_tema               utils.enum_s_accessibility_t_tb_visual_c_cor_tema   NOT NULL DEFAULT 'escuro',
  tamanho_texto          CHAR(1)                                            NOT NULL DEFAULT '3',
  negrito                BOOLEAN                                            NOT NULL DEFAULT FALSE,
  alto_contraste         BOOLEAN                                            NOT NULL DEFAULT FALSE,
  modo_daltonismo        utils.enum_s_accessibility_t_tb_visual_c_daltonismo NOT NULL DEFAULT 'tricromata',
  intensidade_daltonismo CHAR(1)                                            NOT NULL DEFAULT '3',
  remover_animacoes      BOOLEAN                                            NOT NULL DEFAULT FALSE,
  vibrar_ao_tocar        BOOLEAN                                            NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_usu UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_accessibility_t_tb_visual PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_accessibility_t_tb_visual_c_usu UNIQUE (s_auth_t_tb_usuario_c_usu),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_accessibility_t_tb_visual_c_usu
    FOREIGN KEY (s_auth_t_tb_usuario_c_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);