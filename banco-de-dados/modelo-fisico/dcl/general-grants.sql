SET ROLE neondb_owner;



-- neondb_owner
GRANT r_anonimo       TO neondb_owner;
GRANT r_usuario       TO neondb_owner;
GRANT r_levita        TO neondb_owner;
GRANT r_ministro      TO neondb_owner;
GRANT r_lider         TO neondb_owner;
GRANT r_administrador TO neondb_owner;
GRANT r_api           TO neondb_owner;
GRANT r_manutencao    TO neondb_owner;
GRANT r_sistema       TO neondb_owner;



-- r_api
GRANT CONNECT ON DATABASE neondb TO r_api;

GRANT r_anonimo       TO r_api;
GRANT r_usuario       TO r_api;
GRANT r_levita        TO r_api;
GRANT r_ministro      TO r_api;
GRANT r_lider         TO r_api;
GRANT r_administrador TO r_api;



-- r_manutencao
GRANT CONNECT ON DATABASE neondb TO r_manutencao;