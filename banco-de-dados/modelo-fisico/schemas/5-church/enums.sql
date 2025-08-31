CREATE TYPE
  utils.enum_s_church_t_tb_igreja_c_denominacao
AS ENUM (
  'outra',
  'adventista',
  'assembleia de Deus',
  'batista',
  'bola de Neve',
  'casa da Bênção',
  'comunidade Cristã',
  'congregação Cristã no Brasil',
  'deus é Amor',
  'evangelho Quadrangular',
  'igreja Episcopal',
  'igreja Internacional da Graça de Deus',
  'igreja Luterana',
  'igreja Metodista',
  'igreja Pentecostal',
  'igreja Presbiteriana',
  'igreja Renascer em Cristo',
  'igreja Sara Nossa Terra',
  'igreja Universal do Reino de Deus',
  'ministério Apascentar',
  'ministério Fonte da Vida',
  'ministério Internacional da Restauração',
  'ministério Voz da Verdade',
  'nova Vida',
  'o Brasil Para Cristo',
  'paz e Vida',
  'projeto Vida',
  'verbo da Vida',
  'videira',
  'vitória em Cristo',
  'wesleyana',
  'zion Church'
);

CREATE TYPE
  utils.enum_s_church_t_tb_atividade_c_tipo
AS ENUM (
  'agendamento', 'compromisso'
);

CREATE TYPE
  utils.enum_s_church_t_tb_usuario_funcao_c_funcao
AS ENUM (
  'líder', 'ministro', 'levita'
);

CREATE TYPE
  utils.enum_s_church_t_tb_instrumento_c_nome
AS ENUM (
  'outro',
  'violão'
);

CREATE TYPE
  utils.enum_s_church_t_tb_instrumento_c_familia
AS ENUM (
  'cordas'
);