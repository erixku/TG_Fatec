-- tb_igreja
CREATE UNIQUE INDEX uq_s_church_t_tb_igreja_c_cnpj
ON church.tb_igreja (cnpj)
WHERE is_deleted = FALSE;


-- tb_endereco
CREATE UNIQUE INDEX uq_s_church_t_tb_endereco_c_campos_de_endereco
ON church.tb_endereco (igr_id, uf, cidade, logradouro, numero)
WHERE is_deleted = FALSE;


-- tb_administrador
CREATE UNIQUE INDEX uq_s_church_t_tb_administrador_c_igr_id_c_adm
ON church.tb_administrador (igr_id, s_auth_t_tb_usuario_c_adm)
WHERE is_deleted = FALSE;


-- tb_categoria
CREATE UNIQUE INDEX uq_s_church_t_tb_categoria_c_nome_c_igr_id
ON church.tb_categoria (nome, igr_id)
WHERE is_deleted = FALSE;


-- tb_faixa_ass_categoria
CREATE UNIQUE INDEX uq_s_church_t_tb_faixa_ass_categoria_c_fai_id_c_cat_id
ON church.tb_faixa_ass_categoria (fai_id, cat_id)
WHERE is_deleted = FALSE;


-- tb_ministerio_louvor
CREATE UNIQUE INDEX uq_s_church_t_tb_ministerio_louvor_c_codigo
ON church.tb_ministerio_louvor (codigo)
WHERE is_deleted = FALSE;

CREATE UNIQUE INDEX uq_s_church_t_tb_ministerio_louvor_c_nome_c_igr_id
ON church.tb_ministerio_louvor (nome, igr_id)
WHERE is_deleted = FALSE;


-- tb_usuario_funcao
CREATE UNIQUE INDEX uq_s_church_t_tb_usuario_funcao_c_funcao_c_min_lou_id_c_lev
ON church.tb_usuario_funcao (funcao, min_lou_id, s_auth_t_tb_usuario_c_lev)
WHERE is_deleted = FALSE;


-- tb_instrumento_marca
CREATE UNIQUE INDEX uq_s_church_t_tb_instrumento_marca_c_marca
ON church.tb_instrumento_marca (nome)
WHERE is_deleted = FALSE;


-- tb_instrumento_modelo
CREATE UNIQUE INDEX uq_s_church_t_tb_instrumento_modelo_c_modelo_c_ins_mar_id
ON church.tb_instrumento_modelo (nome, ins_mar_id)
WHERE is_deleted = FALSE;


-- tb_instrumento
CREATE UNIQUE INDEX uq_s_church_t_tb_instrumento_c_nome_c_igr_id
ON church.tb_instrumento (nome, igr_id)
WHERE is_deleted = FALSE;


-- tb_instrumento_ass_usuario
CREATE UNIQUE INDEX uq_s_church_t_tb_instrumento_ass_usuario_c_ins_id_c_lev
ON church.tb_instrumento_ass_usuario (ins_id, s_auth_t_tb_usuario_c_lev)
WHERE is_deleted = FALSE;