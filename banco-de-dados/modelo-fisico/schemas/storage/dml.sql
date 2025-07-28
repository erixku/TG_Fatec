INSERT INTO storage.tb_bucket (
  nome,
  tempo_expiracao_upload_em_segundos,
  tamanho_minimo,
  tamanho_maximo
) VALUES 
  ('foto-perfil-usuario',       30, 1, app_utils.get_tamanho_em_mb(5)),
  ('foto-perfil-igreja',        30, 1, app_utils.get_tamanho_em_mb(5)),
  ('foto-perfil-ministerio',    30, 1, app_utils.get_tamanho_em_mb(5)),
  ('icone-instrumento',         30, 1, app_utils.get_tamanho_em_mb(5)),
  ('audio',                     30, 1, app_utils.get_tamanho_em_mb(150)),
  ('pdf',                       30, 1, app_utils.get_tamanho_em_mb(5)),

  ('sistema-icone-instrumento', 30, 1, app_utils.get_tamanho_em_mb(5)),
  ('sistema-imagem',            30, 1, app_utils.get_tamanho_em_mb(5)),
  ('sistema-audio',             30, 1, app_utils.get_tamanho_em_mb(5));