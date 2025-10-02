ALTER TABLE notification.tb_notificacao
ADD CONSTRAINT ck_s_notification_t_tb_notificacao_c_descricao
CHECK (
  descricao ~ (
    '^'                  ||
    '['                  ||
      'a-z'              ||
      'A-Z'              ||
      'áéíóúâêôãõçàèìòù' ||
      'ÁÉÍÓÚÂÊÔÃÕÇÀÈÌÒÙ' ||
      '0-9'              ||
      '''. '             ||
    ']'                  ||
    '+'                  ||
    '$' 
  )
);

ALTER TABLE notification.tb_notificacao
ADD CONSTRAINT ck_s_notification_t_tb_notificacao_c_links
CHECK (
  link ~ (
    '^'     ||
    '['     ||
      'a-z' ||
      'A-Z' ||
      '0-9' ||
      '/'   ||
    ']'     ||
    '+'     ||
    '$'
  )
);



ALTER TABLE notification.tb_configuracao_por_usuario
ADD CONSTRAINT ck_s_notification_t_tb_configuracao_por_usuario_c_horario
CHECK (
  (
    horario_inicio IS NULL AND
    horario_fim    IS NULL AND
    fuso_horario   IS NULL
  )
  OR
  (
    horario_inicio IS NOT NULL AND
    horario_fim    IS NOT NULL AND
    fuso_horario   IS NOT NULL AND

    horario_inicio < horario_fim AND
    EXTRACT(SECOND FROM horario_inicio)::INTEGER = 0 AND
    EXTRACT(SECOND FROM horario_fim)::INTEGER = 0 AND

    utils.s_notification_f_validador_fuso_horario(fuso_horario)
  )
);