GRANT USAGE ON SCHEMA notification TO r_anonimo;



-- tb_tipo_por_usuario
GRANT
  INSERT (
    tip_id,
    s_auth_t_tb_usuario_c_lev
  )
  ON TABLE notification.tb_tipo_por_usuario
  TO r_anonimo;



-- tb_configuracao_por_usuario
GRANT
  INSERT (id)
  ON TABLE notification.tb_configuracao_por_usuario
  TO r_anonimo;