-- SCHEMA STORAGE
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

-- SCHEMA CHURCH
CREATE TYPE
enum_schema_church_tb_igreja_denominacao
AS ENUM (
  'Adventista',
  'Assembleia de Deus',
  'Batista',
  'Bola de Neve',
  'Casa da Bênção',
  'Comunidade Cristã',
  'Congregação Cristã no Brasil',
  'Deus é Amor',
  'Evangelho Quadrangular',
  'Igreja Episcopal',
  'Igreja Internacional da Graça de Deus',
  'Igreja Luterana',
  'Igreja Metodista',
  'Igreja Pentecostal',
  'Igreja Presbiteriana',
  'Igreja Renascer em Cristo',
  'Igreja Sara Nossa Terra',
  'Igreja Universal do Reino de Deus',
  'Ministério Apascentar',
  'Ministério Fonte da Vida',
  'Ministério Internacional da Restauração',
  'Ministério Voz da Verdade',
  'Nova Vida',
  'O Brasil Para Cristo',
  'Paz e Vida',
  'Projeto Vida',
  'Verbo da Vida',
  'Videira',
  'Vitória em Cristo',
  'Wesleyana',
  'Zion Church'
);

CREATE TYPE
enum_schema_church_tb_usuario_funcao_funcao
AS ENUM (
  'Líder', 'Ministro', 'Levita'
);