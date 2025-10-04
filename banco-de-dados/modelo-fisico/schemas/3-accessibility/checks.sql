ALTER TABLE accessibility.tb_intelectual
ADD CONSTRAINT ck_s_accessibility_t_tb_intelectual_c_tamanho_icone
CHECK (
  tamanho_icone IN (
    '1', '2', '3', '4', '5', '6'
  )
);



ALTER TABLE accessibility.tb_auditiva
ADD CONSTRAINT ck_s_accessibility_t_tb_auditiva_c_intensidade_flash
CHECK (
  intensidade_flash IN (
    '1', '2', '3', '4', '5', '6'
  )
);



ALTER TABLE accessibility.tb_visual
ADD CONSTRAINT ck_s_accessibility_t_tb_visual_c_tamanho_texto
CHECK (
  tamanho_texto IN (
    '1', '2', '3', '4', '5', '6'
  )
);

ALTER TABLE accessibility.tb_visual
ADD CONSTRAINT ck_s_accessibility_t_tb_visual_c_intensidade_daltonismo
CHECK (
  intensidade_daltonismo IN (
    '1', '2', '3', '4', '5', '6'
  )
);