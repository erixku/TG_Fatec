ALTER TABLE schedule.tb_registro_ausencia
ADD CONSTRAINT ex_s_schedule_t_tb_registro_ausencia_c_periodo
EXCLUDE USING GIST (
  created_by_lev WITH =,
  s_church_t_tb_igreja_c_igreja WITH =,
  periodo WITH &&
)
WHERE (
  is_deleted = FALSE
);