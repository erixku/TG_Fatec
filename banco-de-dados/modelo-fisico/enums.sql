-- SCHEMA STORAGE
CREATE TYPE
enum_s_storage_t_tb_bucket_c_nome
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
enum_s_storage_t_tb_arquivo_c_mime_type
AS ENUM (
  'image/png',  'image/jpeg', 'image/svg+xml',
  'audio/mpeg', 'audio/wav',  'audio/ogg', 'audio/flac', 'audio/mp4', 'audio/x-alac',
  'application/pdf'
);

CREATE TYPE
enum_s_storage_t_tb_arquivo_c_extensao
AS ENUM (
  'png', 'jpg', 'jpeg', 'svg',
  'mp3', 'wav', 'ogg',  'flac', 'm4a', 'alac',
  'pdf'
);



-- SCHEMA CHURCH
CREATE TYPE
enum_s_church_t_tb_igreja_c_denominacao
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
enum_s_church_t_tb_usuario_funcao_c_funcao
AS ENUM (
  'Líder', 'Ministro', 'Levita'
);