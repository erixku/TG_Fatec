COMMENT ON SCHEMA utils IS '
  Schema que contém recursos utilitários do sistema, como funções, domains,
  enumerações e semelhantes.
  
  Quando esses recursos utilitários são criados no diretório deste schema,
  é sinal de que podem ser utilizados em qualquer schema do sistema. Por
  outro lado, se criados em um schema específico, mesmo que estejam vinculados
  ao schema utils, serão utilizados apenas no schema referido.
';