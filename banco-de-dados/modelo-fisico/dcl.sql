SET ROLE postgres;

DROP ROLE IF EXISTS administrador;
DROP ROLE IF EXISTS lider;
DROP ROLE IF EXISTS ministro;
DROP ROLE IF EXISTS levita;

CREATE ROLE administrador;
CREATE ROLE lider;
CREATE ROLE ministro;
CREATE ROLE levita;

-- administrador (nível de cluster)
ALTER ROLE administrador PASSWORD '12345678';
ALTER ROLE administrador LOGIN;
ALTER ROLE administrador NOINHERIT;

-- lider (nível de cluster)
ALTER ROLE lider PASSWORD '123456';
ALTER ROLE lider LOGIN;
ALTER ROLE lider NOINHERIT;

-- ministro (nível de cluster)
ALTER ROLE ministro PASSWORD '1234';
ALTER ROLE ministro LOGIN;
ALTER ROLE ministro NOINHERIT;

-- levita (nível de cluster)
ALTER ROLE levita PASSWORD '12';
ALTER ROLE levita LOGIN;
ALTER ROLE levita NOINHERIT;