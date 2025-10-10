SET ROLE neondb_owner;



-- revogação de permissões a nível de banco de dados
REVOKE CONNECT   ON DATABASE neondb FROM role_anonimo;
REVOKE CREATE    ON DATABASE neondb FROM role_anonimo;
REVOKE TEMPORARY ON DATABASE neondb FROM role_anonimo;

REVOKE CONNECT   ON DATABASE neondb FROM role_usuario;
REVOKE CREATE    ON DATABASE neondb FROM role_usuario;
REVOKE TEMPORARY ON DATABASE neondb FROM role_usuario;

REVOKE CONNECT   ON DATABASE neondb FROM role_levita;
REVOKE CREATE    ON DATABASE neondb FROM role_levita;
REVOKE TEMPORARY ON DATABASE neondb FROM role_levita;

REVOKE CONNECT   ON DATABASE neondb FROM role_ministro;
REVOKE CREATE    ON DATABASE neondb FROM role_ministro;
REVOKE TEMPORARY ON DATABASE neondb FROM role_ministro;

REVOKE CONNECT   ON DATABASE neondb FROM role_lider;
REVOKE CREATE    ON DATABASE neondb FROM role_lider;
REVOKE TEMPORARY ON DATABASE neondb FROM role_lider;

REVOKE CONNECT   ON DATABASE neondb FROM role_administrador;
REVOKE CREATE    ON DATABASE neondb FROM role_administrador;
REVOKE TEMPORARY ON DATABASE neondb FROM role_administrador;

REVOKE CONNECT   ON DATABASE neondb FROM role_api;
REVOKE CREATE    ON DATABASE neondb FROM role_api;
REVOKE TEMPORARY ON DATABASE neondb FROM role_api;

REVOKE CONNECT   ON DATABASE neondb FROM role_sistema;
REVOKE CREATE    ON DATABASE neondb FROM role_sistema;
REVOKE TEMPORARY ON DATABASE neondb FROM role_sistema;