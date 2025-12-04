CREATE TYPE
  utils.s_auth_t_tb_usuario_e_status
AS ENUM (
  'em verificação',
  'ativo',
  'deletado'
);

CREATE TYPE
  utils.s_auth_t_tb_role_e_nome
AS ENUM (
  'r_anonimo',
  'r_usuario',
  'r_levita',
  'r_ministro',
  'r_lider',
  'r_administrador'
);