-- tb_faixa_elencada
CREATE UNIQUE INDEX uq_s_schedule_t_tb_faixa_elencada_c_posicao_c_ati_id
ON schedule.tb_faixa_elencada (posicao, ati_id)
WHERE is_deleted = FALSE;


-- tb_escala_ass_atividade
CREATE UNIQUE INDEX uq_s_schedule_t_tb_escala_ass_atividade_c_posicao_c_esc_id
ON schedule.tb_escala_ass_atividade (posicao, esc_id)
WHERE is_deleted = FALSE;

CREATE UNIQUE INDEX uq_s_schedule_t_tb_escala_ass_atividade_c_esc_id_c_ati_id
ON schedule.tb_escala_ass_atividade (esc_id, ati_id)
WHERE is_deleted = FALSE;