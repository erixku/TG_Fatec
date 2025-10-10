SET ROLE neondb_owner;

-- revogação de permissões a nível de banco de dados
REVOKE CONNECT   ON DATABASE neondb FROM public;
REVOKE CREATE    ON DATABASE neondb FROM public;
REVOKE TEMPORARY ON DATABASE neondb FROM public;

-- concessão de permissões a nível de banco de dados
GRANT CONNECT ON DATABASE neondb TO role_api;