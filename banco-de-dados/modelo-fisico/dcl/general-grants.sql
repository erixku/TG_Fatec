SET ROLE neondb_owner;

-- neondb_owner
GRANT role_anonimo       TO neondb_owner;
GRANT role_usuario       TO neondb_owner;
GRANT role_levita        TO neondb_owner;
GRANT role_ministro      TO neondb_owner;
GRANT role_lider         TO neondb_owner;
GRANT role_administrador TO neondb_owner;
GRANT role_api           TO neondb_owner;
GRANT role_sistema       TO neondb_owner;

-- role_api
GRANT CONNECT ON DATABASE neondb TO role_api;
GRANT role_anonimo       TO role_api;
GRANT role_usuario       TO role_api;
GRANT role_levita        TO role_api;
GRANT role_ministro      TO role_api;
GRANT role_lider         TO role_api;
GRANT role_administrador TO role_api;