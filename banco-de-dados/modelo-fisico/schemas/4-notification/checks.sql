ALTER TABLE notification.tb_notificacao
ADD CONSTRAINT ck_s_notification_t_tb_notificacao_c_descricao
CHECK (
  descricao ~* '^[a-záéíóúâêôãõçàèìòù0-9''. ]{1,50}$'
);

ALTER TABLE notification.tb_notificacao
ADD CONSTRAINT ck_s_notification_t_tb_notificacao_c_links
CHECK (
  link ~ '^[a-zA-Z0-9/]{1,200}$'
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
    EXTRACT(SECOND FROM horario_inicio)::int = 0 AND
    EXTRACT(SECOND FROM horario_fim)::int = 0 AND

    utils.s_notification_f_validador_fuso_horario(fuso_horario)
  )
);