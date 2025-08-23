COMMENT ON SCHEMA acessibility IS '
  Schema que armazena as configurações de acessibilidade do usuário.
  Todas as tabelas recebem valores padrão no momento do cadastro, que podem ser,
  posteriormente, personalizados pelo próprio usuário.
';



COMMENT ON TABLE acessibility.tb_intelectual IS '
  Armazena as configurações de acessibilidade intelectual do usuário
';

COMMENT ON COLUMN acessibility.tb_intelectual.modo_foco IS '
  Quando ativada, remove parte da estilização de componentes visuais
  secundários na tela para destacar os componentes visuais principais
';

COMMENT ON COLUMN acessibility.tb_intelectual.feedback_imediato IS '
  Quando ativada, permite que o sistema exiba mensagens temporárias
  na parte inferior da tela que deem feedback imediato ao usuário sobre as
  ações que ele realizou
';



COMMENT ON TABLE acessibility.tb_auditiva IS '
  Armazena as configurações de acessibilidade auditiva do usuário
';

COMMENT ON COLUMN acessibility.tb_auditiva.flash IS '
  Quando ativada, permite que o sistema acione flashes da cor tema da
  aplicação (azul) na tela, em sincronia com as batidas do metrônomo,
  na ferramenta de metrônomo
';

COMMENT ON COLUMN acessibility.tb_auditiva.intensidade_flash IS '
  Define a intensidade visual dos flashes emitidos pelo modo
  de flashes na tela
';

COMMENT ON COLUMN acessibility.tb_auditiva.vibracao_aprimorada IS '
  Quando ativada, permite que o sistema acione vibrações
  em sincronia com as batidas do metrônomo na ferramenta de metrônomo
';

COMMENT ON COLUMN acessibility.tb_auditiva.alertas_visuais IS '
  Quando ativada, permite que o sistema substitua sons de
  notificação por flashes de cor branca na tela
';



COMMENT ON TABLE acessibility.tb_visual IS '
  Armazena as configurações de acessibilidade visual do usuário
';

COMMENT ON COLUMN acessibility.tb_visual.remover_animacoes IS '
  Quando ativada, permite que o sistema desative todas as
  animações da aplicação
';

COMMENT ON COLUMN acessibility.tb_visual.vibrar_ao_tocar IS '
  Quando ativada, permite que o sistema acione vibração no dispositivo
  móvel do usuário sempre que ele pressionar um item interativo em sua tela
';