ALTER TABLE church.tb_igreja
ADD CONSTRAINT ck_s_church_t_tb_igreja_c_cnpj
CHECK (
  utils.s_church_f_validador_cnpj(cnpj)
);

ALTER TABLE church.tb_atividade
ADD CONSTRAINT ck_s_church_t_tb_atividade_c_descricao
CHECK (
  utils.s_church_f_validador_descricao(descricao, 50)
);

ALTER TABLE church.tb_igreja
ADD CONSTRAINT ck_s_church_t_tb_ministerio_louvor_c_descricao
CHECK (
  utils.s_church_f_validador_descricao(descricao, 50)
);