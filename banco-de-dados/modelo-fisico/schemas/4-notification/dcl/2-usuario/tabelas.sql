GRANT USAGE ON SCHEMA notification TO r_usuario;



-- tb_tipo
GRANT
  SELECT (
    is_deleted,
    nome,
    s_storage_t_tb_arquivo_c_icone
  )
  ON TABLE notification.tb_tipo
  TO r_usuario;



-- tb_notificacao
GRANT
  INSERT (
    descricao,
    link,
    tip_id,
    s_auth_t_tb_usuario_c_notificado,
    s_auth_t_tb_usuario_c_notificador
  )
  ON TABLE notification.tb_notificacao
  TO r_usuario;

GRANT
  SELECT (
    id,
    created_at,
    descricao,
    link,
    was_readed,
    tip_id,
    s_auth_t_tb_usuario_c_notificado,
    s_auth_t_tb_usuario_c_notificador
  )
  ON TABLE notification.tb_notificacao
  TO r_usuario;



-- tb_tipo_por_usuario
GRANT
  SELECT (
    id,
    is_disabled,
    tip_id,
    s_auth_t_tb_usuario_c_lev
  )
  ON TABLE notification.tb_tipo_por_usuario
  TO r_usuario;

GRANT
  UPDATE (
    updated_at,
    is_disabled
  )
  ON TABLE notification.tb_tipo_por_usuario
  TO r_usuario;



-- tb_cor
GRANT
  SELECT (
    is_deleted,
    nome,
    s_storage_t_tb_arquivo_c_icone
  )
  ON TABLE notification.tb_cor
  TO r_usuario;



-- tb_configuracao_por_usuario
GRANT
  SELECT (
    ativar_notificacoes,
    mostrar_em_tela_bloqueio,
    nao_perturbar_horario,
    nao_perturbar_horario_dias,
    horario_inicio,
    horario_fim,
    fuso_horario,
    nao_perturbar_dia,
    nao_perturbar_dia_dias,
    cor_pop_up,
    cor_led
  )
  ON TABLE notification.tb_configuracao_por_usuario
  TO r_usuario;

GRANT
  UPDATE (
    updated_at,
    ativar_notificacoes,
    mostrar_em_tela_bloqueio,
    nao_perturbar_horario,
    nao_perturbar_horario_dias,
    horario_inicio,
    horario_fim,
    fuso_horario,
    nao_perturbar_dia,
    nao_perturbar_dia_dias,
    cor_pop_up,
    cor_led
  )
  ON TABLE notification.tb_configuracao_por_usuario
  TO r_usuario;