use ieqparque;

CREATE FUNCTION fn_calcular_idade(data_nasc DATE) 
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE idade INT;
    SET idade = YEAR(CURDATE()) - YEAR(data_nasc);
    IF MONTH(CURDATE()) < MONTH(data_nasc) OR (MONTH(CURDATE()) = MONTH(data_nasc) AND DAY(CURDATE()) < DAY(data_nasc)) THEN
        SET idade = idade - 1;
    END IF;
    RETURN idade;
