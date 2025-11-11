GRANT
  EXECUTE
  ON FUNCTION utils.s_auth_f_validador_cpf(IN TEXT)
  TO r_anonimo;

GRANT
  EXECUTE
  ON FUNCTION utils.s_auth_f_validador_email(IN TEXT)
  TO r_anonimo;

GRANT
  EXECUTE
  ON FUNCTION utils.s_auth_f_validador_nome(IN TEXT, IN TEXT)
  TO r_anonimo;

GRANT
  EXECUTE
  ON FUNCTION utils.s_auth_f_validador_telefone(IN TEXT)
  TO r_anonimo;