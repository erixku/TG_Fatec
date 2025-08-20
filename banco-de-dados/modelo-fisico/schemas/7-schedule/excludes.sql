ALTER TABLE schedule.tb_registro_ausencia
ADD CONSTRAINT ex_s_schedule_t_tb_registro_ausencia_c_periodo
EXCLUDE USING GIST (
  usu_uuid WITH =,
  periodo WITH &&
);