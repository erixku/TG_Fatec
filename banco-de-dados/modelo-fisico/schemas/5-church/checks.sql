ALTER TABLE church.tb_igreja
ADD CONSTRAINT ck_s_church_t_tb_igreja_c_cnpj
CHECK (
  utils.s_church_f_validador_cnpj(cnpj)
);