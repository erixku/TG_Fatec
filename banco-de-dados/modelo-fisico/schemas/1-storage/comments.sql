COMMENT ON SCHEMA storage IS '
  Schema que armazena informações gerais de arquivos
';



COMMENT ON TABLE storage.tb_bucket IS '
  Tabela que armazena informações sobre os buckets dos arquivos.
  Eles definem categorias de arquivos e suas propriedades, de modo
  a permitir restrições de permissões baseadas em atributos para
  arquivos, de acordo com o bucket que recebem como chave estrangeira

  Os campos dessa tabela recebem valores padrões para facilitar a inserção
  de categorias novas com dados mais comuns, contudo eles são 100% personalizáveis,
  dentro dos limites das Check Constraints
';



COMMENT ON TABLE storage.tb_arquivo IS '
  Tabela que armazena informações sobre arquivos
';

COMMENT ON COLUMN storage.tb_arquivo.nome IS '
  Coluna que define o nome do arquivo. Nela, não são adicionados o
  nome do bucket e nem a extensão, já que é possível selecionar isso
  por meio das outras colunas
';

COMMENT ON COLUMN storage.tb_arquivo.tamanho_em_bytes IS '
  Coluna que define o tamanho máximo, em bytes, do arquivo.
  Apesar de não conter restrição de Check Constraint, é validado
  via policies, a partir das configurações de sua categoria de bucket
';