DROP SCHEMA IF EXISTS acessibility CASCADE;
CREATE SCHEMA acessibility;



CREATE TABLE acessibility.tb_acessibilidade_intelectual (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  
  -- dados de acessibilidade intelectual
  tamanho_icone     CHAR(1) NOT NULL DEFAULT '3',
  modo_foco         BOOLEAN NOT NULL DEFAULT false,
  feedback_imediato BOOLEAN NOT NULL DEFAULT false,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_usu UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_acessibility_t_tb_acessibilidade_intelectual PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_usu
    FOREIGN KEY (s_auth_t_tb_usuario_c_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE acessibility.tb_acessibilidade_auditiva (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de acessibilidade auditiva
  flash               BOOLEAN NOT NULL DEFAULT false,
  intensidade_flash   CHAR(1) NOT NULL DEFAULT '3',
  transcricao_audio   BOOLEAN NOT NULL DEFAULT false,
  vibracao_aprimorada BOOLEAN NOT NULL DEFAULT false,
  alertas_visuais     BOOLEAN NOT NULL DEFAULT false,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_usu UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_acessibility_t_tb_acessibilidade_auditiva PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_usu
    FOREIGN KEY (s_auth_t_tb_usuario_c_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE acessibility.tb_acessibilidade_visual (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de acessibilidade visual
  cor_tema               app_utils.enum_s_acessibility_t_tb_acessibilidade_visual_c_cor_tema   NOT NULL DEFAULT 'Claro',
  tamanho_fonte          CHAR(1)                                                               NOT NULL DEFAULT '3',
  negrito                BOOLEAN                                                               NOT NULL DEFAULT false,
  alto_contraste         BOOLEAN                                                               NOT NULL DEFAULT false,
  modo_daltonismo        app_utils.enum_s_acessibility_t_tb_acessibilidade_visual_c_daltonismo NOT NULL DEFAULT 'Tricromata',
  intensidade_daltonismo CHAR(1)                                                               NOT NULL DEFAULT '3',
  reduzir_animacoes      BOOLEAN                                                               NOT NULL DEFAULT false,
  vibrar_ao_tocar        BOOLEAN                                                               NOT NULL DEFAULT false,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_usu UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_acessibility_t_tb_acessibilidade_visual PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_usu
    FOREIGN KEY (s_auth_t_tb_usuario_c_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);