use ieqparque;

-- Trigger para verificar a idade de um membro da igreja
CREATE TRIGGER trg_verificar_idade
BEFORE INSERT ON tb_membro
FOR EACH ROW
BEGIN
    IF fn_calcular_idade(NEW.mem_dataNasc) < 18 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Membro deve ter pelo menos 18 anos.';
    END IF;
END

-- Trigger para manter a integridade da tabela de funções
CREATE TRIGGER trg_verificar_funcao_unica
BEFORE INSERT ON tb_funcoesMembro
FOR EACH ROW
BEGIN
    DECLARE funcao_existente INT;
    SELECT COUNT(*) INTO funcao_existente 
    FROM tb_funcoesMembro 
    WHERE mem_id = NEW.mem_id AND fun_id = NEW.fun_id;

    IF funcao_existente > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Membro já possui esta função.';
    END IF;
END
