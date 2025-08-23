DROP SCHEMA IF EXISTS schedule CASCADE;
CREATE SCHEMA schedule;



CREATE TABLE schedule.tb_registro_ausencia (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  
  -- dados do registro de ausência
  motivo        utils.enum_s_schedule_t_tb_registro_ausencia_c_motivo NOT NULL,
  outro_motivo  VARCHAR(20)                                               NULL DEFAULT NULL,
  justificativa VARCHAR(2000)                                         NOT NULL,
  periodo       TSTZRANGE                                             NOT NULL,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_registro_ausencia PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);