CREATE TYPE
utils.enum_s_storage_t_tb_bucket_c_nome
AS ENUM (
  'foto-perfil-usuario',
  'foto-perfil-igreja',
  'foto-perfil-ministerio',
  'icone-instrumento',
  'audio',
  'pdf',
  'sistema-icone-instrumento',
  'sistema-imagem',
  'sistema-audio'
);

CREATE TYPE
utils.enum_s_storage_t_tb_arquivo_c_mime_type
AS ENUM (
  'image/png', 'image/jpeg', 'image/svg+xml',
  'audio/mpeg', 'audio/wav', 'audio/ogg', 'audio/flac', 'audio/mp4', 'audio/x-alac',
  'application/pdf'
);

CREATE TYPE
utils.enum_s_storage_t_tb_arquivo_c_extensao
AS ENUM (
  'png', 'jpg', 'jpeg', 'svg',
  'mp3', 'wav', 'ogg',  'flac', 'm4a', 'alac',
  'pdf'
);