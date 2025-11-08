COMMENT ON SCHEMA church IS '
  Schema que armazena dados referentes às igrejas. Isso constitui
  a igreja, seus endereços, suas atividades, seus ministérios de louvor,
  seus membros, seus administradores, seus instrumentos etc.
';



COMMENT ON TABLE church.tb_igreja IS '
  Tabela que armazena os dados da igreja
';

COMMENT ON COLUMN church.tb_igreja.s_auth_t_tb_usuario_c_adm_proprietario IS '
  Toda igreja pode ter N administradores (church.tb_administrador),
  contudo sempre há um administrador principal entre todos eles.
  Este administrador tem seu UUID cadastrado nesta coluna.
  Este administrador também deve estar cadastrado na tabela
  church.tb_administrador
';



COMMENT ON TABLE church.tb_endereco IS '
  Tabela que armazena os dados de endereço da igreja
';

COMMENT ON COLUMN church.tb_endereco.is_endereco_principal IS '
  Toda igreja possui, obrigatoriamente, um único
  endereço principal, e pode ter N endereços secundários.
  O endereço principal da igreja deve ser marcado com
  TRUE nesta coluna
';



COMMENT ON TABLE church.tb_administrador IS '
  Tabela que armazena os dados dos administradores de uma igreja
';



COMMENT ON TABLE church.tb_categoria IS '
  Tabela que armazena categorias gerais de uma igreja.
  Essas categorias possuem três tipos: agendamento, compromisso
  e classificação de faixa. Os dois primeiros tipos são inseridos
  em atividades da igreja para definir o tipo delas, enquanto o
  de classificação de faixa é inserido em faixas para que a
  igreja classifique faixas como quiser.
  Toda igreja pode inserir categorias personalizadas, de acordo
  com sua vontade.
  
  Exemplos de categorias de atividades:
  - Consagração, santa ceia de músicos, ensaio etc.

  Exemplos de categorias de classificação de faixas:
  - Adoração, comunhão, quebrantamento etc.
';



COMMENT ON TABLE church.tb_faixa IS '
  Tabela que armazena as faixas de músicas da igreja.
  As músicas e medleys ficam registradas no nome do ministro,
  em seu perfil, mas para que elas possam fazer parte de uma
  igreja, elas devem ser cadastradas nesta tabela. Sendo assim,
  essa tabela é uma associação entre as músicas e medleys
  que um ministro tem e a igreja a qual ele associou essas
  músicas
';

COMMENT ON COLUMN church.tb_faixa.is_disabled IS '
  É habilitado como TRUE caso o ministro, proprietário da faixa,
  saia da igreja. Está relacionado com o campo "snapshot"
';

COMMENT ON COLUMN church.tb_faixa.snapshot IS '
  Quando o ministro sai de uma igreja, suas músicas vinculadas
  a igreja continuam na igreja, mas elas não podem continuar
  sendo atualizadas pela FK da faixa do ministro, então
  é criado um "snapshot" da faixa do ministro, ou seja,
  uma cópia da faixa original no momento de saída do ministro
  da igreja, de forma que a igreja possa continuar com a faixa.
  Essa cópia deve conter todos os dados originais da faixa,
  salvos em formato JSONB

  Após a saída do ministro, essa faixa só pode ser acessada
  pelo JSONB, não sendo mais utilizado a referência de FK,
  apesar dela continuar registrada. Além disso, a igreja não
  deve ter permissão de usar essa faixa. Ela fica apenas visível,
  podendo ser copiada para uso com outra instância ou podendo ser
  excluída definitivamente
';



COMMENT ON TABLE church.tb_faixa_ass_categoria IS '
  Tabela que associa faixas da igreja com categorias de
  classificação de faixa
';



COMMENT ON TABLE church.tb_ministerio_louvor IS '
  Tabela que armazena os dados dos ministérios de louvor
  de uma igreja. Uma igreja pode ter zero, um ou N ministérios
  de louvor. Os membros que não são administradores nunca
  entram "na igreja", mas sim no ministério de louvor,
  e possuem acesso apenas a ele
';

COMMENT ON FUNCTION utils.s_church_f_get_codigo_ministerio() IS '
  Descrição:
    Gera um código único e aleatório de 6 caracteres alfanuméricos para um ministério
    de louvor. Esse código é utilizado por usuários para enviar solicitações de entrada
    aos administradores ou líderes do ministério desejado

  Parâmetros:
    Nenhum

  Retorno:
    VARCHAR(6): código gerado, composto por caracteres alfanuméricos
';



COMMENT ON TABLE church.tb_usuario_funcao IS '
  Tabela que armazena as funções que os membros exercem
  dentro de um ministério de louvor (líder, ministro e levita).
  Um ministério de louvor pode ter zero, um ou N líderes,
  ministros e levitas. Essas funções são atribuídas
  pelo líder ou administrador
';



COMMENT ON TABLE church.tb_instrumento_marca IS '
  Tabela que armazena as marcas de instrumentos pré-cadastradas
  no sistema
';



COMMENT ON TABLE church.tb_instrumento_modelo IS '
  Tabela que armazena os modelos de instrumentos pré-cadastrados
  no sistema
';



COMMENT ON TABLE church.tb_instrumento IS '
  Tabela que armazena os instrumentos que uma igreja possui
';

COMMENT ON COLUMN church.tb_instrumento.s_storage_t_tb_arquivo_c_icone IS '
  Por padrão, há vários ícones pré-cadastrados no sistema
  para que o usuário insira o ícone que desejar ao
  instrumento que cadastrar. Contudo, ele pode adicionar
  ícones personalizados, caso deseje
';



COMMENT ON TABLE church.tb_instrumento_ass_usuario IS '
  Tabela que armazena os vínculos de instrumentos e levitas,
  de forma a dizer quais instrumentos um levita pode tocar
';