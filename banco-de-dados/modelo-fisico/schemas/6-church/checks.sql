ALTER TABLE church.tb_igreja
ADD CONSTRAINT ck_s_church_t_tb_igreja_c_cnpj
CHECK (
  utils.s_church_f_validador_cnpj(cnpj)
);

ALTER TABLE church.tb_igreja
ADD CONSTRAINT ck_s_church_t_tb_igreja_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'igreja', 1::SMALLINT, 100::SMALLINT)
);

ALTER TABLE church.tb_igreja
ADD CONSTRAINT ck_s_church_t_tb_igreja_c_outra_denominacao
CHECK (
  utils.s_church_f_validador_nome(outra_denominacao, 'outra denominação de igreja', 1::SMALLINT, 100::SMALLINT)
);



ALTER TABLE church.tb_categoria
ADD CONSTRAINT ck_s_church_t_tb_atividade_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'atividade', 1::SMALLINT, 30::SMALLINT)
);

ALTER TABLE church.tb_categoria
ADD CONSTRAINT ck_s_church_t_tb_atividade_c_descricao
CHECK (
  utils.s_church_f_validador_descricao(descricao, 1::SMALLINT, 50::SMALLINT)
);



ALTER TABLE church.tb_ministerio_louvor
ADD CONSTRAINT ck_s_church_t_tb_ministerio_louvor_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'ministério de louvor', 1::SMALLINT, 100::SMALLINT)
);

ALTER TABLE church.tb_ministerio_louvor
ADD CONSTRAINT ck_s_church_t_tb_ministerio_louvor_c_descricao
CHECK (
  utils.s_church_f_validador_descricao(descricao, 1::SMALLINT, 50::SMALLINT)
);

ALTER TABLE church.tb_ministerio_louvor
ADD CONSTRAINT ck_s_church_t_tb_ministerio_louvor_c_codigo
CHECK (
  codigo ~ '^[a-zA-Z0-9]{6}$'
);



ALTER TABLE church.tb_instrumento_marca
ADD CONSTRAINT ck_s_church_t_tb_instrumento_marca_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'marca de instrumento', 1::SMALLINT, 30::SMALLINT)
);



ALTER TABLE church.tb_instrumento_modelo
ADD CONSTRAINT ck_s_church_t_tb_instrumento_modelo_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'modelo de instrumento', 1::SMALLINT, 30::SMALLINT)
);



ALTER TABLE church.tb_instrumento
ADD CONSTRAINT ck_s_church_t_tb_instrumento_c_outro_nome
CHECK (
  utils.s_church_f_validador_nome(outro_nome, 'outro instrumento', 1::SMALLINT, 30::SMALLINT)
);

ALTER TABLE church.tb_instrumento
ADD CONSTRAINT ck_s_church_t_tb_instrumento_c_outra_marca
CHECK (
  utils.s_church_f_validador_nome(outra_marca, 'outra marca de instrumento', 1::SMALLINT, 30::SMALLINT)
);

ALTER TABLE church.tb_instrumento
ADD CONSTRAINT ck_s_church_t_tb_instrumento_c_outro_modelo
CHECK (
  utils.s_church_f_validador_nome(outro_modelo, 'outro modelo de instrumento', 1::SMALLINT, 30::SMALLINT)
);