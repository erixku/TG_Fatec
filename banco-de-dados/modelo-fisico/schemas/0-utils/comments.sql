COMMENT ON SCHEMA utils IS '
  Schema que contém recursos utilitários do sistema, como funções, domains,
  enumerações e semelhantes.
  
  Quando esses recursos utilitários são criados no diretório deste schema,
  é sinal de que podem ser utilizados em qualquer schema do sistema. Por
  outro lado, se criados em um schema específico, mesmo que estejam vinculados
  ao schema utils, serão utilizados apenas no schema referido.
';

COMMENT ON FUNCTION utils.s_utils_f_conversor_mb_para_byte(IN INTEGER) IS '
  Descrição:
    Recebe um valor inteiro em Megabytes (MB) e retorna seu equivalente em bytes.
    O objetivo desta função é simplificar a escrita de valores em bytes, permitindo
    que grandes números sejam expressos em MB e convertidos automaticamente para bytes.

  Parâmetros:
    tamanho INTEGER: valor em Megabytes (MB).

  Retorno:
    INTEGER: valor convertido em bytes.
';