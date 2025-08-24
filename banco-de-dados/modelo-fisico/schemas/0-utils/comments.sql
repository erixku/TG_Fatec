COMMENT ON SCHEMA utils IS '
  Schema que contém funções utilitárias do sistema, como funções e domains.
';

COMMENT ON FUNCTION utils.conversor_mb_para_byte(INTEGER) IS '
  Descrição:
    Recebe um valor inteiro em Megabytes (MB) e retorna seu equivalente em bytes.
    O objetivo desta função é simplificar a escrita de valores em bytes, permitindo
    que grandes números sejam expressos em MB e convertidos automaticamente para bytes.

  Parâmetros:
    tamanho INTEGER: valor em Megabytes (MB).

  Retorno:
    INTEGER: valor convertido em bytes.
';