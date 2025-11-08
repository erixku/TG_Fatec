CREATE TYPE
  utils.s_accessibility_t_tb_visual_e_cor_tema
AS ENUM (
  'claro',
  'escuro'
);

CREATE TYPE
  utils.s_accessibility_t_tb_visual_e_daltonismo
AS ENUM (
  'tricromata',
  'protanopia',
  'protanomalia',
  'deuteranopia',
  'deuteranomalia',
  'tritanopia',
  'tritanomalia',
  'acromatopsia'
);