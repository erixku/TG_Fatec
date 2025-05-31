CREATE TYPE
enum_schema_storage_tb_arquivo_mime_type
AS ENUM (
  'image/png',  'image/jpeg', 'image/svg+xml',
  'audio/mpeg', 'audio/wav',  'audio/ogg', 'audio/flac', 'audio/mp4', 'audio/x-alac',
  'application/pdf'
);

CREATE TYPE
enum_schema_storage_tb_arquivo_extensao
AS ENUM (
  'png', 'jpg', 'jpeg', 'svg',
  'mp3', 'wav', 'ogg',  'flac', 'm4a', 'alac',
  'pdf'
);