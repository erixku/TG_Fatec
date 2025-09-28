COMMENT ON SCHEMA notification IS '
  Schema que armazena as dados relacionados a notificações.
  Ela armazena as notificações do usuário, assim como
  configurações que ele determinou para suas notificações.
  Ainda, há configurações gerais do sistema para notificações
  presentes nesse schema. A propósito, algumas configurações
  possuem valor padrão configurado, mas o usuário pode
  personalizar como quiser posteriormente
';



COMMENT ON TABLE notification.tb_tipo IS '
  Tabela que armazena os tipos de notificações
  existentes no sistema
';

COMMENT ON TABLE notification.tb_notificacao IS '
  Tabela que armazena as notificações dos usuários
';

COMMENT ON TABLE notification.tb_tipo_por_usuario IS '
  Tabela que armazena as configurações de tipo de
  notificação por usuário. O usuário pode selecionar
  quais tipos de notificação quer receber e quais tipos
  não quer receber. Por padrão, todas são habilitadas
  durante o cadastro do usuário
';

COMMENT ON TABLE notification.tb_cor IS '
  Tabela que armazena cores. Essas cores são utilizadas
  para definir a cor do pop-up da notificação do usuário,
  assim como a cor que o LED do celular dele deve emitir
  durante o recebimento da notificação
';



COMMENT ON TABLE notification.tb_configuracao_por_usuario IS '
  Tabela que armazena as configurações de notificações
  do usuário
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.nao_perturbar_horario IS '
  Habilita/desabilita a configuração para bloquear notificações em horários
  específicos de dias específicos
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.nao_perturbar_horario_dias IS '
  Define os dias em que as notificações serão bloqueadas por horário
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.horario_inicio IS '
  Horário de início do bloqueio de notificação por horário definido.
  É aplicado o mesmo horário de início para todos os dias selecionados
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.horario_fim IS '
  Horário final do bloqueio de notificação por horário definido.
  É aplicado o mesmo horário final para todos os dias selecionados
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.fuso_horario IS '
  Fuso horário no qual o usuário estava incluído ao fazer o INSERT
  da configuração de bloqueio de notificação por horário
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.nao_perturbar_dia IS '
  Habilita/desabilita a configuração para bloquear notificações
  em dias específicos, por inteiro
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.nao_perturbar_dia_dias IS '
  Especifica os dias em que a configuração de bloquear notificações em
  dias específicos deve surtir efeito
';