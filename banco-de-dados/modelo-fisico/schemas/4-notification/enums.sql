CREATE TYPE
  utils.enum_s_notification_t_tb_tipo_c_nome
AS ENUM (
  'novo aviso',
  'nova escala',
  'atribuição de escala',
  'novo agendamento',
  'atribuição de agendamento',
  'novo compromisso',
  'lembrete de aniversário',
  'menção em mensagem'
);

CREATE TYPE
  utils.enum_s_notification_t_tb_cor_c_nome
AS ENUM (
  'azul'
);