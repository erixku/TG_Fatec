CREATE TYPE
  utils.s_church_t_tb_igreja_e_denominacao
AS ENUM (
  'outra',
  'adventista',
  'assembleia de deus',
  'batista',
  'bola de neve',
  'casa da bênção',
  'comunidade cristã',
  'congregação cristã no brasil',
  'deus é amor',
  'evangelho quadrangular',
  'igreja episcopal',
  'igreja internacional da graça de deus',
  'igreja luterana',
  'igreja metodista',
  'igreja pentecostal',
  'igreja presbiteriana',
  'igreja renascer em cristo',
  'igreja sara nossa terra',
  'igreja universal do reino de deus',
  'ministério apascentar',
  'ministério fonte da vida',
  'ministério internacional da restauração',
  'ministério voz da verdade',
  'nova vida',
  'o brasil para cristo',
  'paz e vida',
  'projeto vida',
  'verbo da vida',
  'videira',
  'vitória em cristo',
  'wesleyana',
  'zion church'
);

CREATE TYPE
  utils.s_church_t_tb_categoria_e_tipo
AS ENUM (
  'agendamento', 'compromisso', 'classificação de faixas'
);

CREATE TYPE
  utils.s_church_t_tb_usuario_funcao_e_funcao
AS ENUM (
  'líder', 'ministro', 'levita'
);

CREATE TYPE
  utils.s_church_t_tb_instrumento_e_nome
AS ENUM (
  'outro',
  'violão'
);

CREATE TYPE
  utils.s_church_t_tb_instrumento_e_familia
AS ENUM (
  'cordas',
  'madeiras',
  'metais',
  'percussao'
);