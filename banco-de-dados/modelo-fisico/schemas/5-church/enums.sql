CREATE TYPE
  utils.enum_s_church_t_tb_igreja_c_denominacao
AS ENUM (
  'Outra',
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
  utils.enum_s_church_t_tb_usuario_funcao_c_funcao
AS ENUM (
  'Líder', 'Ministro', 'Levita'
);

CREATE TYPE
  utils.enum_s_church_t_tb_instrumento_c_nome
AS ENUM (
  'Outro',
  'Violão'
);

CREATE TYPE
  utils.enum_s_church_t_tb_instrumento_c_familia
AS ENUM (
  'Cordas'
);