CREATE TYPE
  utils.enum_s_schedule_t_tb_registro_ausencia_c_motivo
AS ENUM (
  'trabalho',
  'licença médica',
  'exame laboratorial',
  'outros'
);

CREATE TYPE
  utils.enum_s_schedule_t_tb_publicacao_c_tipo
AS ENUM (
  'aviso',
  'agendamento',
  'compromisso'
);