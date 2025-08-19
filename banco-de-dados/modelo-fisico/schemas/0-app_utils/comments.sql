COMMENT ON SCHEMA app_utils IS '
  Schema que contém funções utilitárias do sistema, como funções e domains.
';



COMMENT ON FUNCTION app_utils.conversor_mb_para_byte(INTEGER) IS '
  Descrição:
    Recebe um valor inteiro em Megabytes (MB) e retorna seu equivalente em bytes.
    O objetivo desta função é simplificar a escrita de valores em bytes, permitindo
    que grandes números sejam expressos em MB e convertidos automaticamente para bytes.

  Parâmetros:
    tamanho INTEGER: valor em Megabytes (MB).

  Retorno:
    INTEGER: valor convertido em bytes.
';



COMMENT ON FUNCTION app_utils.get_codigo_ministerio() IS '
  Descrição:
    Gera um código único e aleatório de 6 caracteres alfanuméricos para um ministério
    de louvor. Esse código é utilizado por usuários para enviar solicitações de entrada
    aos administradores ou líderes do ministério desejado.

  Parâmetros:
    Nenhum.

  Retorno:
    VARCHAR(6): código gerado, composto por caracteres alfanuméricos.
';