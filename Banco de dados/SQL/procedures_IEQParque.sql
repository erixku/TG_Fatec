use ieqparque;

-- Procedure para adicionar um novo membro
CREATE PROCEDURE sp_adicionar_membro(
    IN p_nome VARCHAR(50),
    IN p_telefone VARCHAR(15),
    IN p_email VARCHAR(50),
    IN p_login VARCHAR(40),
    IN p_senha VARCHAR(40),
    IN p_dataNasc DATE,
    IN p_tpm_id INT
)
BEGIN
    DECLARE membro_existente INT;

    -- Verifica se o membro já existe
    SELECT COUNT(*) INTO membro_existente 
    FROM tb_membro 
    WHERE mem_email = p_email OR mem_login = p_login;

    IF membro_existente > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Email ou login já estão em uso.';
    ELSE
        INSERT INTO tb_membro (mem_nome, mem_telefone, mem_email, mem_login, mem_senha, mem_dataNasc, tpm_id)
        VALUES (p_nome, p_telefone, p_email, p_login, p_senha, p_dataNasc, p_tpm_id);
    END IF;
END