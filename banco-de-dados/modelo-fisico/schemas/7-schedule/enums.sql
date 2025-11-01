CREATE TYPE
  utils.s_schedule_t_tb_registro_ausencia_e_motivo
AS ENUM (
  'trabalho',
  'licença médica',
  'exame laboratorial',
  'outros'
);

CREATE TYPE
  utils.s_schedule_t_tb_publicacao_e_tipo
AS ENUM (
  'aviso',
  'agendamento',
  'compromisso'
);