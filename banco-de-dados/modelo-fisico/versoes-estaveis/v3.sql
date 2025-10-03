DROP SCHEMA IF EXISTS utils CASCADE;
CREATE SCHEMA utils;

CREATE OR REPLACE FUNCTION
  utils.conversor_mb_para_byte(IN tamanho INTEGER)
RETURNS INTEGER
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF tamanho IS NULL THEN
    RAISE EXCEPTION 'O tamanho em megabytes não pode ser nulo';
  END IF;

  IF tamanho < 0 THEN
    RAISE EXCEPTION 'O tamanho em megabytes não pode ser negativo';
  END IF;

  RETURN tamanho * 1024 * 1024;
END;
$$;

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

CREATE DOMAIN utils.domain_uf AS CHAR(2);
ALTER DOMAIN utils.domain_uf
ADD CONSTRAINT ck_s_utils_d_domain_uf
CHECK (
  VALUE IN (
    'ac', 'al', 'am', 'ap', 'ba', 'ce', 'df', 'es', 'go',
    'ma', 'mg', 'ms', 'mt', 'pa', 'pb', 'pe', 'pi', 'pr',
    'rj', 'rn', 'ro', 'rr', 'rs', 'sc', 'se', 'sp', 'to'
  )
);

CREATE DOMAIN utils.domain_cep AS VARCHAR(8);
ALTER DOMAIN utils.domain_cep
ADD CONSTRAINT ck_s_utils_d_domain_cep
CHECK (
  VALUE ~ '^[0-9]{8}$'
);

CREATE DOMAIN utils.domain_cidade AS VARCHAR(40);
ALTER DOMAIN utils.domain_cidade
ADD CONSTRAINT ck_s_utils_d_domain_cidade
CHECK (
  VALUE ~* (
    '^'                  ||
    '['                  ||
      'a-z'              ||
      'A-Z'              ||
      'áéíóúâêôãõçàèìòù' ||
      'ÁÉÍÓÚÂÊÔÃÕÇÀÈÌÒÙ' ||
      '\-''\.'           ||
    ']'                  ||
    '{3,40}'             ||
    '$'
  )
);

CREATE DOMAIN utils.domain_bairro AS VARCHAR(70);
ALTER DOMAIN utils.domain_bairro
ADD CONSTRAINT ck_s_utils_d_domain_bairro
CHECK (
  VALUE ~ (
    '^'                  ||
    '['                  ||
      'a-z'              ||
      'A-Z'              ||
      'áéíóúâêôãõçàèìòù' ||
      'ÁÉÍÓÚÂÊÔÃÕÇÀÈÌÒÙ' ||
      '0-9'              ||
      '\-''\., '         ||
    ']'                  ||
    '{2,70}'             ||
    '$'
  )
);

CREATE DOMAIN utils.domain_logradouro AS VARCHAR(120);
ALTER DOMAIN utils.domain_logradouro
ADD CONSTRAINT ck_s_utils_d_domain_logradouro
CHECK (
  VALUE ~ (
    '^'                  ||
    '['                  ||
      'a-z'              ||
      'A-Z'              ||
      'áéíóúâêôãõçàèìòù' ||
      'ÁÉÍÓÚÂÊÔÃÕÇÀÈÌÒÙ' ||
      '0-9'              ||
      '\-''\., '         ||
    ']'                  ||
    '+'                  ||
    '$'
  )
);

CREATE DOMAIN utils.domain_numero AS VARCHAR(5);
ALTER DOMAIN utils.domain_numero
ADD CONSTRAINT ck_s_utils_d_domain_numero
CHECK (
  VALUE ~ '^[0-9]{1,5}$' OR
  VALUE = 'S/N'
);

CREATE DOMAIN utils.domain_complemento AS VARCHAR(50);
ALTER DOMAIN utils.domain_complemento
ADD CONSTRAINT ck_s_utils_d_domain_complemento
CHECK (
  VALUE ~ (
  '^'                  ||
  '['                  ||
    'a-z'              ||
    'A-Z'              ||
    'áéíóúâêôãõçàèìòù' ||
    'ÁÉÍÓÚÂÊÔÃÕÇÀÈÌÒÙ' ||
    '0-9'              ||
    ',\-\.\(\)'' '     ||
  ']'                  ||
  '*'                  ||
  '$'
  )
);

CREATE TYPE
  utils.enum_s_storage_t_tb_bucket_c_nome
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
  utils.enum_s_storage_t_tb_arquivo_c_mime_type
AS ENUM (
  'image/png', 'image/jpeg', 'image/svg+xml',
  'audio/mpeg', 'audio/wav', 'audio/ogg', 'audio/flac', 'audio/mp4', 'audio/x-alac',
  'application/pdf'
);

CREATE TYPE
  utils.enum_s_storage_t_tb_arquivo_c_extensao
AS ENUM (
  'png', 'jpg', 'jpeg', 'svg',
  'mp3', 'wav', 'ogg',  'flac', 'm4a', 'alac',
  'pdf'
);

CREATE TYPE
  utils.enum_s_accessibility_t_tb_visual_c_cor_tema
AS ENUM (
  'claro',
  'escuro'
);

CREATE TYPE
  utils.enum_s_accessibility_t_tb_visual_c_daltonismo
AS ENUM (
  'tricromata',
  'protanopia',
  'protanomalia',
  'deuteranopia',
  'deuteranomalia',
  'tritanopia',
  'tritanomalia',
  'acromatopsia'
);

CREATE TYPE
  utils.enum_s_notification_t_tb_tipo_c_nome
AS ENUM (
  'novo aviso',
  'nova escala',
  'atribuição de escala',
  'novo agendamento',
  'atribuição de agendamento',
  'novo compromisso',
  'lembrete de aniversário',
  'menção em mensagem'
);

CREATE TYPE
  utils.enum_s_notification_t_tb_cor_c_nome
AS ENUM (
  'azul'
);

CREATE TYPE
  utils.enum_s_song_c_tonalidade
AS ENUM (
  -- tonalidades maiores
  'C', 'C#',
  'D', 'D#',
  'E',
  'F', 'F#',
  'G', 'G#',
  'A', 'A#',
  'B',

  -- tonalidades menores
  'Am', 'A#m',
  'Bm',
  'Cm', 'C#m',
  'Dm', 'D#m',
  'Em',
  'Fm', 'F#m',
  'Gm', 'G#m'
);

CREATE TYPE
  utils.enum_s_church_t_tb_igreja_c_denominacao
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
  utils.enum_s_church_t_tb_categoria_c_tipo
AS ENUM (
  'agendamento', 'compromisso', 'classificação de música'
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

CREATE TYPE
  utils.enum_s_schedule_t_tb_registro_ausencia_c_motivo
AS ENUM (
  'trabalho',
  'licença médica',
  'exame laboratorial',
  'outros'
);

DROP SCHEMA IF EXISTS storage CASCADE;
CREATE SCHEMA storage;



CREATE TABLE storage.tb_bucket (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados do bucket
  is_deleted                         BOOLEAN                                 NOT NULL DEFAULT FALSE,
  nome                               utils.enum_s_storage_t_tb_bucket_c_nome NOT NULL,
  tempo_expiracao_upload_em_segundos SMALLINT                                NOT NULL DEFAULT 30,
  tamanho_minimo                     INTEGER                                 NOT NULL DEFAULT 1,
  tamanho_maximo                     INTEGER                                 NOT NULL DEFAULT utils.conversor_mb_para_byte(1000),

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_bucket PRIMARY KEY (id)
);



CREATE TABLE storage.tb_arquivo (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_usu UUID        NOT NULL,
  deleted_by_usu UUID            NULL DEFAULT NULL,

  -- dados do arquivo
  is_deleted       BOOLEAN                                       NOT NULL DEFAULT FALSE,
  nome             VARCHAR(300)                                  NOT NULL,
  mime_type        utils.enum_s_storage_t_tb_arquivo_c_mime_type NOT NULL,
  extensao         utils.enum_s_storage_t_tb_arquivo_c_extensao  NOT NULL,
  tamanho_em_bytes INTEGER                                       NOT NULL,

  -- chaves estrangeiras
  buc_id SMALLINT NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_arquivo PRIMARY KEY (uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_buc_id
    FOREIGN KEY (buc_id)
    REFERENCES storage.tb_bucket (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);

DROP SCHEMA IF EXISTS auth CASCADE;
CREATE SCHEMA auth;



CREATE TABLE auth.tb_endereco (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados de endereço
  cep         utils.domain_cep         NOT NULL,
  uf          utils.domain_uf          NOT NULL,
  cidade      utils.domain_cidade      NOT NULL,
  bairro      utils.domain_bairro      NOT NULL,
  logradouro  utils.domain_logradouro  NOT NULL,
  numero      utils.domain_numero      NOT NULL,
  complemento utils.domain_complemento     NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_endereco PRIMARY KEY (id)
);



CREATE TABLE auth.tb_usuario (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at  TIMESTAMPTZ     NULL DEFAULT NULL,
  last_access TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados do usuário
  is_deleted       BOOLEAN      NOT NULL DEFAULT FALSE,
  cpf              VARCHAR(11)  NOT NULL,
  nome             VARCHAR(20)  NOT NULL,
  sobrenome        VARCHAR(50)  NOT NULL,
  nome_social      VARCHAR(20)      NULL,
  sobrenome_social VARCHAR(50)      NULL,
  sexo             CHAR(1)      NOT NULL,
  data_nascimento  DATE         NOT NULL,
  email            VARCHAR(79)  NOT NULL,
  telefone         VARCHAR(11)  NOT NULL,
  senha            VARCHAR(128) NOT NULL,

  -- chaves estrangeiras
  end_id                        INTEGER NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID        NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_usuario PRIMARY KEY (uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_end_id
    FOREIGN KEY (end_id)
    REFERENCES auth.tb_endereco (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);

-- declaração de chaves estrangeiras de logs (STORAGE.TB_ARQUIVO)
ALTER TABLE storage.tb_arquivo
ADD CONSTRAINT fk_s_storage_t_tb_arquivo_c_created_by_usu
  FOREIGN KEY (created_by_usu)
  REFERENCES auth.tb_usuario (uuid)
  ON UPDATE RESTRICT
  ON DELETE RESTRICT
  NOT DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE storage.tb_arquivo
ADD CONSTRAINT fk_s_storage_t_tb_arquivo_c_deleted_by_usu
  FOREIGN KEY (deleted_by_usu)
  REFERENCES auth.tb_usuario (uuid)
  ON UPDATE RESTRICT
  ON DELETE RESTRICT
  NOT DEFERRABLE INITIALLY IMMEDIATE;

  -- tb_bucket
CREATE UNIQUE INDEX uq_s_storage_t_tb_bucket_c_nome
ON storage.tb_bucket (nome)
WHERE is_deleted = FALSE;


-- tb_arquivo
CREATE UNIQUE INDEX uq_s_storage_t_tb_arquivo_c_buc_id_c_nome_c_extensao
ON storage.tb_arquivo (buc_id, nome, extensao)
WHERE is_deleted = FALSE;

ALTER TABLE storage.tb_bucket
ADD CONSTRAINT ck_s_storage_t_tb_bucket_c_tempo_expiracao
CHECK (
  tempo_expiracao_upload_em_segundos > 0 AND
  tempo_expiracao_upload_em_segundos <= 300
);

-- garante que arquivos de buckets tenham de 1 byte a 1 gigabyte de tamanho
ALTER TABLE storage.tb_bucket
ADD CONSTRAINT ck_s_storage_t_tb_bucket_c_tamanho_minimo_e_maximo
CHECK (
  tamanho_minimo > 0 AND
  tamanho_minimo <= utils.conversor_mb_para_byte(1000) AND
  tamanho_maximo > 0 AND
  tamanho_maximo <= utils.conversor_mb_para_byte(1000) AND
  tamanho_maximo >= tamanho_minimo
);

ALTER TABLE storage.tb_arquivo
ADD CONSTRAINT ck_s_storage_t_tb_arquivo_c_nome
CHECK (
  nome ~ (
    '^'      ||
    '['      ||
      'a-z'  ||
      'A-Z'  ||
      '0-9'  ||
      '\-_ ' ||
    ']'      ||
    '+'      ||
    '$'
  )
);

INSERT INTO storage.tb_bucket (
  nome,
  tempo_expiracao_upload_em_segundos,
  tamanho_minimo,
  tamanho_maximo
) VALUES 
  ('foto-perfil-usuario',       30, 1, utils.conversor_mb_para_byte(5)),
  ('foto-perfil-igreja',        30, 1, utils.conversor_mb_para_byte(5)),
  ('foto-perfil-ministerio',    30, 1, utils.conversor_mb_para_byte(5)),
  ('icone-instrumento',         30, 1, utils.conversor_mb_para_byte(5)),
  ('audio',                     30, 1, utils.conversor_mb_para_byte(150)),
  ('pdf',                       30, 1, utils.conversor_mb_para_byte(5)),

  ('sistema-icone-instrumento', 30, 1, utils.conversor_mb_para_byte(5)),
  ('sistema-imagem',            30, 1, utils.conversor_mb_para_byte(5)),
  ('sistema-audio',             30, 1, utils.conversor_mb_para_byte(5));

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

-- tb_usuario
CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_cpf
ON auth.tb_usuario (cpf)
WHERE is_deleted = FALSE;

CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_email
ON auth.tb_usuario (email)
WHERE is_deleted = FALSE;

CREATE UNIQUE INDEX uq_s_auth_t_tb_usuario_c_telefone
ON auth.tb_usuario (telefone)
WHERE is_deleted = FALSE;

COMMENT ON SCHEMA auth IS '
  Schema que armazena dados pessoais, de acesso e de autenticação dos usuários
';

COMMENT ON TABLE auth.tb_endereco IS '
  Tabela que armazena os dados do endereço dos usuários
';

COMMENT ON TABLE auth.tb_usuario IS '
  Tabela que armazena os dados pessoais e de acesso dos usuários
';

CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_cpf(IN cpf TEXT)
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
DECLARE
  cpf_vetor TEXT[];
  soma INT;
  resto INT;
  i INT;
BEGIN
  IF cpf IS NULL THEN
    RAISE EXCEPTION 'CPF inválido: não pode ser nulo';
  END IF;

  cpf := trim(cpf);
  IF cpf = '' THEN
    RAISE EXCEPTION 'CPF inválido: não pode ser vazio';
  END IF;

  IF length(cpf) != 11 THEN
    RAISE EXCEPTION 'CPF inválido: deve conter exatamente 11 dígitos';
  END IF;

  IF cpf ~ '[ ]' THEN
    RAISE EXCEPTION 'CPF inválido: não deve conter espaços';
  END IF;

  IF cpf ~ '[\t]' THEN
    RAISE EXCEPTION 'CPF inválido: não deve conter tabulações';
  END IF;

  IF cpf ~ '[\n]' THEN
    RAISE EXCEPTION 'CPF inválido: não deve conter quebras de linha (ENTER)';
  END IF;

  IF cpf ~ '[\r]' THEN
    RAISE EXCEPTION 'CPF inválido: não deve conter Carriage Return';
  END IF;

  IF cpf ~ '[\f]' THEN
    RAISE EXCEPTION 'CPF inválido: não deve conter Form Feed';
  END IF;

  IF cpf ~ '[^0-9]' THEN
    RAISE EXCEPTION 'CPF inválido: deve conter apenas números (sem pontos ou traços)';
  END IF;

  IF cpf ~ '^(\d)\1{10}$' THEN
    RAISE EXCEPTION 'CPF inválido: não pode conter todos os dígitos iguais';
  END IF;

  -- converte o CPF em um vetor de caracteres
  cpf_vetor := regexp_split_to_array(cpf, '');

  -- Valida primeiro dígito verificador
  soma := 0;
  FOR i IN 1..9 LOOP
    soma := soma + (cast(cpf_vetor[i] AS INTEGER) * (11 - i));
  END LOOP;
  resto := (soma * 10) % 11;
  IF resto = 10 THEN
    resto := 0;
  END IF;
  IF resto != cast(cpf_vetor[10] AS INTEGER) THEN
    RAISE EXCEPTION 'CPF inválido: primeiro dígito verificador incorreto';
  END IF;

  -- Valida segundo dígito verificador
  soma := 0;
  FOR i IN 1..10 LOOP
    soma := soma + (cast(cpf_vetor[i] AS INTEGER) * (12 - i));
  END LOOP;
  resto := (soma * 10) % 11;
  IF resto = 10 THEN
    resto := 0;
  END IF;
  IF resto != cast(cpf_vetor[11] AS INTEGER) THEN
    RAISE EXCEPTION 'CPF inválido: segundo dígito verificador incorreto';
  END IF;

  RETURN TRUE;
END;
$$;

CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_email(IN email TEXT)
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF email IS NULL THEN
    RAISE EXCEPTION 'E-mail inválido: não pode ser nulo';
  END IF;

  email := trim(email);
  IF email = '' THEN
    RAISE EXCEPTION 'E-mail inválido: não pode ser vazio';
  END IF;

  IF length(email) < 11 THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter, pelo menos, 11 caracteres';
  END IF;

  IF length(email) > 79 THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter, no máximo, 79 caracteres';
  END IF;

  IF email ~ '[ ]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter espaços';
  END IF;

  IF email ~ '[\t]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter tabulações';
  END IF;

  IF email ~ '[\n]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter quebras de linha (ENTER)';
  END IF;

  IF email ~ '[\r]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter Carriage Return';
  END IF;

  IF email ~ '[\f]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter Form Feed';
  END IF;

  IF email ~ '[^a-z0-9!@#%_+-.]' THEN
    RAISE EXCEPTION 'E-mail inválido: só pode conter letras minúsculas, números e símbolos (!, @, #, %%, _, +, - e .)';
  END IF;

  IF email !~ '[@]' THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter arroba (@)';
  END IF;

  IF email !~ '^[^@]+@[^@]+$' THEN
    RAISE EXCEPTION 'E-mail inválido: deve ter apenas um único arroba (@)';
  END IF;

  IF split_part(email, '@', 1) ~ '[.]' THEN
    RAISE EXCEPTION 'E-mail inválido: não deve conter pontos (.) antes do arroba (@)';
  END IF;

  IF split_part(email, '@', 2) NOT IN (
    'gmail.com',
    'outlook.com',
    'outlook.com.br',
    'hotmail.com',
    'yahoo.com',
    'myyahoo.com'
  ) THEN
    RAISE EXCEPTION '
      E-mail inválido: só são permitidos domínios de e-mail válidos
      (gmail.com, outlook.com, outlook.com.br, hotmail.com, yahoo.com e myyahoo.com)
    ';
  END IF;

  IF
    length(split_part(email, '@', 1)) > 30 AND
    split_part(email, '@', 2) = 'gmail.com'
  THEN
    RAISE EXCEPTION 'E-mail inválido: e-mails com endereço de domínio "gmail.com" só podem ter, antes do arroba, até 30 caracteres';
  END IF;

  IF
    length(split_part(email, '@', 1)) > 64 AND
    split_part(email, '@', 2) IN ('outlook.com', 'outlook.com.br', 'hotmail.com')
  THEN
    RAISE EXCEPTION '
      E-mail inválido: e-mails com endereço de domínio "outlook.com", "outlook.com.br"
      ou "hotmail.com" só podem ter, antes do arroba, até 64 caracteres
    ';
  END IF;

  IF
    length(split_part(email, '@', 1)) > 32 AND
    split_part(email, '@', 2) IN ('yahoo.com', 'myyahoo.com')
  THEN
    RAISE EXCEPTION '
      E-mail inválido: e-mails com endereço de domínio "yahoo.com" ou "myyahoo.com"
      só podem ter, antes do arroba, até 32 caracteres
    ';
  END IF;

  RETURN TRUE;
END;
$$;

CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_nome (
    IN valor TEXT,
    IN tipo  TEXT
  )
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF tipo IS NULL THEN
    RAISE EXCEPTION 'Tipo inválido: não pode ser nulo';
  END IF;

  tipo := trim(tipo);
  IF tipo NOT IN ('Nome', 'Sobrenome', 'Nome social', 'Sobrenome social') THEN
    RAISE EXCEPTION 'Tipo inválido: só são aceitos os tipos "Nome", "Sobrenome", "Nome social" ou "Sobrenome social"';
  END IF;

  IF tipo IN ('Nome', 'Sobrenome') AND valor IS NULL THEN
    RAISE EXCEPTION 'Valor inválido: não pode ser nulo';
  END IF;

  IF tipo IN ('Nome social', 'Sobrenome social') AND valor IS NULL THEN
    RETURN TRUE;
  END IF;

  valor := trim(valor);
  IF valor = '' THEN
    RAISE EXCEPTION '% inválido: não pode ser vazio', tipo;
  END IF;

  IF length(valor) < 2 THEN
    RAISE EXCEPTION '% inválido: deve conter, pelo menos, dois caracteres', tipo;
  END IF;

  IF valor ~ '^['']+$' THEN
    RAISE EXCEPTION '% inválido: não pode conter apenas apóstrofos', tipo;
  END IF;

  IF valor ~ '[\t]' THEN
    RAISE EXCEPTION '% inválido: não deve conter tabulações', tipo;
  END IF;

  IF valor ~ '[\n]' THEN
    RAISE EXCEPTION '% inválido: não deve conter quebras de linha (ENTER)', tipo;
  END IF;

  IF valor ~ '[\r]' THEN
    RAISE EXCEPTION '% inválido: não deve conter Carriage Return', tipo;
  END IF;

  IF valor ~ '[\f]' THEN
    RAISE EXCEPTION '% inválido: não deve conter Form Feed', tipo;
  END IF;

  -- regras específicas de nome e nome social
  IF tipo = 'Nome' OR tipo = 'Nome social' THEN
    IF length(valor) > 20 THEN
      RAISE EXCEPTION '% inválido: não pode ter mais de 20 caracteres', tipo;
    END IF;

    IF valor ~* '[^a-záéíóúâêôãõçàèìòù'' ]' THEN
      RAISE EXCEPTION '% inválido: deve conter apenas letras e apóstrofos', tipo;
    END IF;

    IF valor ~ '[ ]' THEN
      RAISE EXCEPTION '% inválido: não pode conter espaços', tipo;
    END IF;

  -- regras específicas de sobrenome e sobrenome social
  ELSE
    IF length(valor) > 50 THEN
      RAISE EXCEPTION '% inválido: não pode ter mais de 50 caracteres', tipo;
    END IF;

    IF valor ~* '[^a-záéíóúâêôãõçàèìòù'' ]' THEN
      RAISE EXCEPTION '% inválido: deve conter apenas letras, espaços e apóstrofos', tipo;
    END IF;

    IF valor ~ ' {2,}' THEN
      RAISE EXCEPTION '% inválido: não pode conter espaços consecutivos', tipo;
    END IF;
  END IF;

  RETURN TRUE;
END;
$$;

CREATE OR REPLACE FUNCTION
  utils.s_auth_f_validador_telefone(IN telefone TEXT)
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
DECLARE
  ddds_validos CONSTANT CHAR(2)[] = ARRAY [
    '11','12','13','14','15','16','17','18','19',
    '21','22','24',
    '27','28',
    '31','32','33','34','35','37','38',
    '41','42','43','44','45','46',
    '47','48','49',
    '51','53','54','55',
    '61','62','64','65','66','67',
    '68','69',
    '71','73','74','75','77',
    '79','81','82','83','84','85','86','87','88','89',
    '91','92','93','94','95','96','97','98','99'
  ]::CHAR(2)[];
BEGIN
  IF telefone IS NULL THEN
    RAISE EXCEPTION 'Telefone inválido: não pode ser nulo';
  END IF;

  telefone := trim(telefone);
  IF telefone = '' THEN
    RAISE EXCEPTION 'Telefone inválido: não pode ser vazio';
  END IF;

  IF telefone ~ '[ ]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter espaços';
  END IF;

  IF telefone ~ '[\t]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter tabulações';
  END IF;

  IF telefone ~ '[\n]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter quebras de linha (ENTER)';
  END IF;

  IF telefone ~ '[\r]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter Carriage Return';
  END IF;

  IF telefone ~ '[\f]' THEN
    RAISE EXCEPTION 'Telefone inválido: não deve conter Form Feed';
  END IF;

  IF telefone ~ '[^0-9]' THEN
    RAISE EXCEPTION 'Telefone inválido: deve conter apenas números';
  END IF;

  IF length(telefone) != 11 THEN
    RAISE EXCEPTION 'Telefone inválido: deve ter exatamente 11 caracteres (DDD e número)';
  END IF;

  IF LEFT(telefone, 2) != ALL(ddds_validos) THEN
    RAISE EXCEPTION 'Telefone inválido: DDD inválido';
  END IF;

  IF substring(telefone FROM 3 FOR 1) != '9' THEN
    RAISE EXCEPTION 'Telefone inválido: o primeiro dígito do telefone deve ser "9"';
  END IF;

  RETURN TRUE;
END;
$$;

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_cpf
CHECK (
  utils.s_auth_f_validador_cpf(cpf)
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_nome
CHECK (
  utils.s_auth_f_validador_nome(nome, 'Nome')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sobrenome
CHECK (
  utils.s_auth_f_validador_nome(sobrenome, 'Sobrenome')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_nome_social
CHECK (
  utils.s_auth_f_validador_nome(nome_social, 'Nome social')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sobrenome_social
CHECK (
  utils.s_auth_f_validador_nome(sobrenome_social, 'Sobrenome social')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_sexo
CHECK (
  sexo IN (
    'm', 'f', 'o'
  )
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_data_nascimento
CHECK (
  data_nascimento BETWEEN
  (CURRENT_DATE - INTERVAL '120 years') AND
  (CURRENT_DATE - INTERVAL '18 years')
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_email
CHECK (
  utils.s_auth_f_validador_email(email)
);

ALTER TABLE auth.tb_usuario
ADD CONSTRAINT ck_s_auth_t_tb_usuario_c_telefone
CHECK (
  utils.s_auth_f_validador_telefone(telefone)
);

DROP SCHEMA IF EXISTS accessibility CASCADE;
CREATE SCHEMA accessibility;



CREATE TABLE accessibility.tb_intelectual (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  -- dados de acessibilidade intelectual
  tamanho_icone     CHAR(1) NOT NULL DEFAULT '3',
  modo_foco         BOOLEAN NOT NULL DEFAULT FALSE,
  feedback_imediato BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_usu UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_accessibility_t_tb_intelectual PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_accessibility_t_tb_intelectual_c_usu UNIQUE (s_auth_t_tb_usuario_c_usu),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_accessibility_t_tb_intelectual_c_usu
    FOREIGN KEY (s_auth_t_tb_usuario_c_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE CASCADE
    ON DELETE CASCADE
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE accessibility.tb_auditiva (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados de acessibilidade auditiva
  modo_flash          BOOLEAN NOT NULL DEFAULT FALSE,
  intensidade_flash   CHAR(1) NOT NULL DEFAULT '3',
  transcricao_audio   BOOLEAN NOT NULL DEFAULT FALSE,
  vibracao_aprimorada BOOLEAN NOT NULL DEFAULT FALSE,
  alertas_visuais     BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_usu UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_accessibility_t_tb_auditiva PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_accessibility_t_tb_auditiva_c_usu UNIQUE (s_auth_t_tb_usuario_c_usu),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_accessibility_t_tb_auditiva_c_usu
    FOREIGN KEY (s_auth_t_tb_usuario_c_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE CASCADE
    ON DELETE CASCADE
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE accessibility.tb_visual (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados de acessibilidade visual
  cor_tema               utils.enum_s_accessibility_t_tb_visual_c_cor_tema   NOT NULL DEFAULT 'escuro',
  tamanho_texto          CHAR(1)                                             NOT NULL DEFAULT '3',
  negrito                BOOLEAN                                             NOT NULL DEFAULT FALSE,
  alto_contraste         BOOLEAN                                             NOT NULL DEFAULT FALSE,
  modo_daltonismo        utils.enum_s_accessibility_t_tb_visual_c_daltonismo NOT NULL DEFAULT 'tricromata',
  intensidade_daltonismo CHAR(1)                                             NOT NULL DEFAULT '3',
  remover_animacoes      BOOLEAN                                             NOT NULL DEFAULT FALSE,
  vibrar_ao_tocar        BOOLEAN                                             NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_usu UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_accessibility_t_tb_visual PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_accessibility_t_tb_visual_c_usu UNIQUE (s_auth_t_tb_usuario_c_usu),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_accessibility_t_tb_visual_c_usu
    FOREIGN KEY (s_auth_t_tb_usuario_c_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE CASCADE
    ON DELETE CASCADE
    NOT DEFERRABLE INITIALLY IMMEDIATE
);

COMMENT ON SCHEMA accessibility IS '
  Schema que armazena as configurações de acessibilidade do usuário.
  Todas as tabelas recebem valores padrão no momento do cadastro, que podem ser,
  posteriormente, personalizados pelo próprio usuário.
';



COMMENT ON TABLE accessibility.tb_intelectual IS '
  Tabela que armazena as configurações de acessibilidade intelectual do usuário
';

COMMENT ON COLUMN accessibility.tb_intelectual.modo_foco IS '
  Quando ativada, remove parte da estilização de componentes visuais
  secundários na tela para destacar os componentes visuais principais
';

COMMENT ON COLUMN accessibility.tb_intelectual.feedback_imediato IS '
  Quando ativada, permite que o sistema exiba mensagens temporárias
  na parte inferior da tela que deem feedback imediato ao usuário sobre as
  ações que ele realizou
';



COMMENT ON TABLE accessibility.tb_auditiva IS '
  Tabela que armazena as configurações de acessibilidade auditiva do usuário
';

COMMENT ON COLUMN accessibility.tb_auditiva.modo_flash IS '
  Quando ativada, permite que o sistema acione flashes da cor tema da
  aplicação (azul) na tela, em sincronia com as batidas do metrônomo,
  na ferramenta de metrônomo
';

COMMENT ON COLUMN accessibility.tb_auditiva.intensidade_flash IS '
  Define a intensidade visual dos flashes emitidos pelo modo
  de flashes na tela
';

COMMENT ON COLUMN accessibility.tb_auditiva.vibracao_aprimorada IS '
  Quando ativada, permite que o sistema acione vibrações
  em sincronia com as batidas do metrônomo na ferramenta de metrônomo
';

COMMENT ON COLUMN accessibility.tb_auditiva.alertas_visuais IS '
  Quando ativada, permite que o sistema substitua sons de
  notificação por flashes de cor branca na tela
';



COMMENT ON TABLE accessibility.tb_visual IS '
  Tabela que armazena as configurações de acessibilidade visual do usuário
';

COMMENT ON COLUMN accessibility.tb_visual.remover_animacoes IS '
  Quando ativada, permite que o sistema desative todas as
  animações da aplicação
';

COMMENT ON COLUMN accessibility.tb_visual.vibrar_ao_tocar IS '
  Quando ativada, permite que o sistema acione vibração no dispositivo
  móvel do usuário sempre que ele pressionar um item interativo em sua tela
';

ALTER TABLE accessibility.tb_intelectual
ADD CONSTRAINT ck_s_accessibility_t_tb_intelectual_c_tamanho_icone
CHECK (
  tamanho_icone IN (
    '1', '2', '3', '4', '5', '6'
  )
);

ALTER TABLE accessibility.tb_auditiva
ADD CONSTRAINT ck_s_accessibility_t_tb_auditiva_c_intensidade_flash
CHECK (
  intensidade_flash IN (
    '1', '2', '3', '4', '5', '6'
  )
);

ALTER TABLE accessibility.tb_visual
ADD CONSTRAINT ck_s_accessibility_t_tb_visual_c_tamanho_texto
CHECK (
  tamanho_texto IN (
    '1', '2', '3', '4', '5', '6'
  )
);

ALTER TABLE accessibility.tb_visual
ADD CONSTRAINT ck_s_accessibility_t_tb_visual_c_intensidade_daltonismo
CHECK (
  intensidade_daltonismo IN (
    '1', '2', '3', '4', '5', '6'
  )
);

DROP SCHEMA IF EXISTS notification CASCADE;
CREATE SCHEMA notification;



CREATE TABLE notification.tb_tipo (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados do tipo de notificação
  is_deleted BOOLEAN                                    NOT NULL DEFAULT FALSE,
  nome       utils.enum_s_notification_t_tb_tipo_c_nome NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_icone UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_tipo PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_tipo_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_notificacao (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados da notificação
  descricao VARCHAR(50)  NOT NULL,
  link      VARCHAR(200) NOT NULL,
  is_lida   BOOLEAN      NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  tip_id                            SMALLINT NOT NULL,
  s_auth_t_tb_usuario_c_notificado  UUID     NOT NULL,
  s_auth_t_tb_usuario_c_notificador UUID     NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_notificacao PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_notificacao_c_tip_id
    FOREIGN KEY (tip_id)
    REFERENCES notification.tb_tipo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_notification_t_tb_notificacao_c_notificado
    FOREIGN KEY (s_auth_t_tb_usuario_c_notificado)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_notification_t_tb_notificacao_c_notificador
    FOREIGN KEY (s_auth_t_tb_usuario_c_notificador)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_tipo_por_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

   -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados do tipo de notificação por usuário
  is_disabled BOOLEAN NOT NULL DEFAULT TRUE,

  -- chaves estrangeiras
  tip_id                    SMALLINT NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID     NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_tipo_por_usuario PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_notification_t_tb_tipo_por_usuario_c_tip_id_c_lev
  UNIQUE (tip_id, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_tipo_por_usuario_c_tip_id
    FOREIGN KEY (tip_id)
    REFERENCES notification.tb_tipo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
    
  CONSTRAINT fk_s_notification_t_tb_tipo_por_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_cor (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados da cor da notificação
  is_deleted BOOLEAN                                   NOT NULL DEFAULT FALSE,    
  nome       utils.enum_s_notification_t_tb_cor_c_nome NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_icone UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_cor PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_cor_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE notification.tb_configuracao_por_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados da configuração de notificação por usuário
  ativar_notificacoes        BOOLEAN     NOT NULL DEFAULT TRUE,
  mostrar_em_tela_bloqueio   BOOLEAN     NOT NULL DEFAULT TRUE,
  nao_perturbar_horario      BOOLEAN     NOT NULL DEFAULT FALSE,
  nao_perturbar_horario_dias BIT(7)      NOT NULL DEFAULT B'0000000',
  horario_inicio             TIME(0)         NULL DEFAULT NULL,
  horario_fim                TIME(0)         NULL DEFAULT NULL,
  fuso_horario               VARCHAR(50)     NULL DEFAULT NULL,
  nao_perturbar_dia          BOOLEAN     NOT NULL DEFAULT FALSE,
  nao_perturbar_dia_dias     BIT(7)      NOT NULL DEFAULT B'0000000',

  -- chaves estrangeiras
  cor_pop_up                SMALLINT NOT NULL DEFAULT 1,
  cor_led                   SMALLINT NOT NULL DEFAULT 1,
  s_auth_t_tb_usuario_c_lev UUID     NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_notification_t_tb_configuracao_por_usuario PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_notification_t_tb_configuracao_por_usuario_c_lev
  UNIQUE (s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_notification_t_tb_configuracao_por_usuario_c_cor_pop_up
    FOREIGN KEY (cor_pop_up)
    REFERENCES notification.tb_cor (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
  
  CONSTRAINT fk_s_notification_t_tb_configuracao_por_usuario_c_cor_led
    FOREIGN KEY (cor_led)
    REFERENCES notification.tb_cor (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_notification_t_tb_configuracao_por_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);

COMMENT ON SCHEMA notification IS '
  Schema que armazena as dados relacionados a notificações.
  Ela armazena as notificações do usuário, assim como
  configurações que ele determinou para suas notificações.
  Ainda, há configurações gerais do sistema para notificações
  presentes nesse schema. A propósito, algumas configurações
  possuem valor padrão configurado, mas o usuário pode
  personalizar como quiser posteriormente
';



COMMENT ON TABLE notification.tb_tipo IS '
  Tabela que armazena os tipos de notificações
  existentes no sistema
';

COMMENT ON TABLE notification.tb_notificacao IS '
  Tabela que armazena as notificações dos usuários
';

COMMENT ON TABLE notification.tb_tipo_por_usuario IS '
  Tabela que armazena as configurações de tipo de
  notificação por usuário. O usuário pode selecionar
  quais tipos de notificação quer receber e quais tipos
  não quer receber. Por padrão, todas são habilitadas
  durante o cadastro do usuário
';

COMMENT ON TABLE notification.tb_cor IS '
  Tabela que armazena cores. Essas cores são utilizadas
  para definir a cor do pop-up da notificação do usuário,
  assim como a cor que o LED do celular dele deve emitir
  durante o recebimento da notificação
';



COMMENT ON TABLE notification.tb_configuracao_por_usuario IS '
  Tabela que armazena as configurações de notificações
  do usuário
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.nao_perturbar_horario IS '
  Habilita/desabilita a configuração para bloquear notificações em horários
  específicos de dias específicos
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.nao_perturbar_horario_dias IS '
  Define os dias em que as notificações serão bloqueadas por horário
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.horario_inicio IS '
  Horário de início do bloqueio de notificação por horário definido.
  É aplicado o mesmo horário de início para todos os dias selecionados
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.horario_fim IS '
  Horário final do bloqueio de notificação por horário definido.
  É aplicado o mesmo horário final para todos os dias selecionados
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.fuso_horario IS '
  Fuso horário no qual o usuário estava incluído ao fazer o INSERT
  da configuração de bloqueio de notificação por horário
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.nao_perturbar_dia IS '
  Habilita/desabilita a configuração para bloquear notificações
  em dias específicos, por inteiro
';

COMMENT ON COLUMN notification.tb_configuracao_por_usuario.nao_perturbar_dia_dias IS '
  Especifica os dias em que a configuração de bloquear notificações em
  dias específicos deve surtir efeito
';

CREATE OR REPLACE FUNCTION
  utils.s_notification_f_validador_fuso_horario(IN fuso_horario TEXT)
RETURNS BOOLEAN
LANGUAGE sql
STABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
  SELECT EXISTS (
    SELECT 1
    FROM pg_catalog.pg_timezone_names
    WHERE name = fuso_horario
  );
$$;

ALTER TABLE notification.tb_notificacao
ADD CONSTRAINT ck_s_notification_t_tb_notificacao_c_descricao
CHECK (
  descricao ~ (
    '^'                  ||
    '['                  ||
      'a-z'              ||
      'A-Z'              ||
      'áéíóúâêôãõçàèìòù' ||
      'ÁÉÍÓÚÂÊÔÃÕÇÀÈÌÒÙ' ||
      '0-9'              ||
      '''. '             ||
    ']'                  ||
    '+'                  ||
    '$' 
  )
);

ALTER TABLE notification.tb_notificacao
ADD CONSTRAINT ck_s_notification_t_tb_notificacao_c_links
CHECK (
  link ~ (
    '^'     ||
    '['     ||
      'a-z' ||
      'A-Z' ||
      '0-9' ||
      '/'   ||
    ']'     ||
    '+'     ||
    '$'
  )
);



ALTER TABLE notification.tb_configuracao_por_usuario
ADD CONSTRAINT ck_s_notification_t_tb_configuracao_por_usuario_c_horario
CHECK (
  (
    horario_inicio IS NULL AND
    horario_fim    IS NULL AND
    fuso_horario   IS NULL
  )
  OR
  (
    horario_inicio IS NOT NULL AND
    horario_fim    IS NOT NULL AND
    fuso_horario   IS NOT NULL AND

    horario_inicio < horario_fim AND
    EXTRACT(SECOND FROM horario_inicio)::INTEGER = 0 AND
    EXTRACT(SECOND FROM horario_fim)::INTEGER = 0 AND

    utils.s_notification_f_validador_fuso_horario(fuso_horario)
  )
);

DROP SCHEMA IF EXISTS song CASCADE;
CREATE SCHEMA song;



CREATE TABLE song.tb_musica (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados da música
  is_deleted BOOLEAN      NOT NULL DEFAULT FALSE,
  nome       VARCHAR(150) NOT NULL,
  artista    VARCHAR(70)      NULL,
  duracao    INTERVAL     NOT NULL,

  duracao_em_segundos SMALLINT GENERATED ALWAYS AS (
    EXTRACT(EPOCH FROM duracao)::SMALLINT
  ) STORED,

  bpm             VARCHAR(3)                         NULL,
  tonalidade      utils.enum_s_song_c_tonalidade     NULL,
  link_musica     VARCHAR(500)                   NOT NULL,
  link_letra      VARCHAR(500)                       NULL,
  link_cifra      VARCHAR(500)                       NULL,
  link_partitura  VARCHAR(500)                       NULL,
  parte_de_medley BOOLEAN                        NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_foto UUID     NULL,
  s_auth_t_tb_usuario_c_min     UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_musica PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_song_t_tb_musica_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_song_t_tb_musica_c_min
    FOREIGN KEY (s_auth_t_tb_usuario_c_min)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE song.tb_partes (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados das partes da música
  bpm        VARCHAR(3)                     NOT NULL,
  tonalidade utils.enum_s_song_c_tonalidade NOT NULL,
  parte      VARCHAR(50)                    NOT NULL,

  -- chaves estrangeiras
  mus_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_partes PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_song_t_tb_partes_c_mus_id
    FOREIGN KEY (mus_id)
    REFERENCES song.tb_musica (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE song.tb_artistas (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados dos artistas da música
  nome VARCHAR(70) NOT NULL,

  -- chaves estrangeiras
  mus_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_artistas PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_song_t_tb_artistas_c_nome_c_mus_id
  UNIQUE (nome, mus_id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_song_t_tb_artistas_c_mus_id
    FOREIGN KEY (mus_id)
    REFERENCES song.tb_musica (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE song.tb_medley (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados do medley
  nome               VARCHAR(150) NOT NULL,
  quantidade_musicas SMALLINT     NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_foto UUID     NULL,
  s_auth_t_tb_usuario_c_min     UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_medley PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_song_t_tb_medley_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_song_t_tb_medley_c_min
    FOREIGN KEY (s_auth_t_tb_usuario_c_min)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE song.tb_medley_ass_musica (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de associação entre músicas e medleys
  posicao SMALLINT NOT NULL,

  -- chaves estrangeiras
  med_id INTEGER NOT NULL,
  mus_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_medley_ass_musica PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_song_t_tb_medley_ass_musica_c_posicao_c_med_id
  UNIQUE (posicao, med_id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_song_t_tb_medley_ass_musica_c_med_id
    FOREIGN KEY (med_id)
    REFERENCES song.tb_medley (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_song_t_tb_medley_ass_musica_c_mus_id
    FOREIGN KEY (mus_id)
    REFERENCES song.tb_musica (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);

CREATE OR REPLACE FUNCTION
  utils.s_church_f_get_codigo_ministerio()
RETURNS VARCHAR(6)
LANGUAGE plpgsql
VOLATILE
SECURITY DEFINER
SET search_path = church, pg_catalog
AS $$
DECLARE
  caracteres CONSTANT VARCHAR(62) := 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  codigo_gerado VARCHAR(6);
  is_codigo BOOLEAN;
  tentativas SMALLINT := 0;
BEGIN
  LOOP
    codigo_gerado := '';
    tentativas := tentativas + 1;

    -- gera o código do ministério
    FOR i IN 1..6 LOOP
      codigo_gerado := codigo_gerado || substr(caracteres, ceil(random() * length(caracteres))::int, 1);
    END LOOP;

    -- verifica se o código gerado já está em uso por algum ministério
    SELECT EXISTS (
      SELECT 1
      FROM church.tb_ministerio_louvor
      WHERE codigo = codigo_gerado
    ) INTO is_codigo;

    -- caso não esteja em uso por outro ministério, retorna o código. Senão, tenta novamente
    IF NOT is_codigo THEN
      RETURN codigo_gerado;
    END IF;

    IF tentativas >= 10000 THEN
      RAISE EXCEPTION 'Falha ao gerar código único de ministério por excesso de tentativas';
    END IF;
  END LOOP;
END;
$$;

DROP SCHEMA IF EXISTS church CASCADE;
CREATE SCHEMA church;



CREATE TABLE church.tb_igreja (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_adm UUID        NOT NULL,
  updated_by_adm UUID        NOT NULL,
  deleted_by_adm UUID            NULL DEFAULT NULL,

  -- dados da igreja
  is_deleted        BOOLEAN                                       NOT NULL DEFAULT FALSE,
  cnpj              VARCHAR(14)                                   NOT NULL,
  nome              VARCHAR(100)                                  NOT NULL,
  denominacao       utils.enum_s_church_t_tb_igreja_c_denominacao NOT NULL,
  outra_denominacao VARCHAR(100)                                      NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_foto          UUID NOT NULL,
  s_auth_t_tb_usuario_c_adm_proprietario UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_igreja PRIMARY KEY (uuid),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_igreja_c_created_by_adm
    FOREIGN KEY (created_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_igreja_c_updated_by_adm
    FOREIGN KEY (updated_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_igreja_c_deleted_by_adm
    FOREIGN KEY (deleted_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_igreja_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_igreja_c_adm_proprietario
    FOREIGN KEY (s_auth_t_tb_usuario_c_adm_proprietario)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_endereco (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_adm UUID        NOT NULL,
  updated_by_adm UUID        NOT NULL,
  deleted_by_adm UUID            NULL DEFAULT NULL,

  -- dados do endereço
  is_deleted            BOOLEAN                  NOT NULL DEFAULT FALSE,
  cep                   utils.domain_cep         NOT NULL,
  uf                    utils.domain_uf          NOT NULL,
  cidade                utils.domain_cidade      NOT NULL,
  bairro                utils.domain_bairro      NOT NULL,
  logradouro            utils.domain_logradouro  NOT NULL,
  numero                utils.domain_numero      NOT NULL,
  complemento           utils.domain_complemento     NULL,
  is_endereco_principal BOOLEAN                  NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_endereco PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_endereco_c_created_by_adm
    FOREIGN KEY (created_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_endereco_c_updated_by_adm
    FOREIGN KEY (updated_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_endereco_c_deleted_by_adm
    FOREIGN KEY (deleted_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_endereco_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_administrador (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_adm UUID        NOT NULL,
  deleted_by_adm UUID            NULL DEFAULT NULL,

  -- dados do administrador
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  igr_uuid                  UUID NOT NULL,
  s_auth_t_tb_usuario_c_adm UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_administrador PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_administrador_c_created_by_adm
    FOREIGN KEY (created_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_administrador_c_deleted_by_adm
    FOREIGN KEY (deleted_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_administrador_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
  
  CONSTRAINT fk_s_church_t_tb_administrador_c_adm
    FOREIGN KEY (s_auth_t_tb_usuario_c_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_categoria (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  disabled_at    TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_adm UUID        NOT NULL,
  updated_by_adm UUID        NOT NULL,
  deleted_by_adm UUID            NULL DEFAULT NULL,

  -- dados do tipo de agendamento
  is_deleted  BOOLEAN                                   NOT NULL DEFAULT FALSE,
  is_disabled BOOLEAN                                   NOT NULL DEFAULT TRUE,
  tipo        utils.enum_s_church_t_tb_categoria_c_tipo NOT NULL,
  nome        VARCHAR(30)                               NOT NULL,
  descricao   VARCHAR(50)                               NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_categoria PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_categoria_c_created_by_adm
    FOREIGN KEY (created_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_categoria_c_updated_by_adm
    FOREIGN KEY (updated_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_categoria_c_deleted_by_adm
    FOREIGN KEY (deleted_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_categoria_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_faixa (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at  TIMESTAMPTZ     NULL DEFAULT NULL,
  disabled_at TIMESTAMPTZ     NULL DEFAULT NULL,
  
  -- dados da música da igreja
  is_deleted  BOOLEAN NOT NULL DEFAULT FALSE,
  is_disabled BOOLEAN NOT NULL DEFAULT FALSE,
  snapshot    JSONB       NULL DEFAULT NULL,     

  -- chaves estrangeiras
  igr_uuid                    UUID    NOT NULL,
  s_song_t_tb_musica_c_mus_id INTEGER     NULL,
  s_song_t_tb_medley_c_med_id INTEGER     NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_faixa PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_faixa_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_faixa_c_mus_id
    FOREIGN KEY (s_song_t_tb_musica_c_mus_id)
    REFERENCES song.tb_musica (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_faixa_c_med_id
    FOREIGN KEY (s_song_t_tb_medley_c_med_id)
    REFERENCES song.tb_medley (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_faixa_ass_categoria (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- chaves estrangeiras
  fai_id INTEGER NOT NULL,
  cat_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_faixa_ass_categoria PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_faixa_ass_categoria_c_fai_id_c_cat_id
  UNIQUE (fai_id, cat_id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_faixa_ass_categoria_c_fai_id
    FOREIGN KEY (fai_id)
    REFERENCES church.tb_faixa (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_faixa_ass_categoria_c_cat_id
    FOREIGN KEY (cat_id)
    REFERENCES church.tb_categoria (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_ministerio_louvor (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_adm UUID        NOT NULL,
  updated_by_adm UUID        NOT NULL,
  deleted_by_adm UUID            NULL DEFAULT NULL,
  
  -- dados do ministério de louvor
  is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE,
  nome        VARCHAR(100) NOT NULL,
  descricao   VARCHAR(50)  NOT NULL,
  codigo      VARCHAR(6)   NOT NULL DEFAULT utils.s_church_f_get_codigo_ministerio(),

  -- chaves estrangeiras
  igr_uuid                      UUID NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID     NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_ministerio_louvor PRIMARY KEY (uuid),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_created_by_adm
    FOREIGN KEY (created_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_updated_by_adm
    FOREIGN KEY (updated_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_deleted_by_adm
    FOREIGN KEY (deleted_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_usuario_funcao (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_lid UUID        NOT NULL,
  deleted_by_lid UUID            NULL DEFAULT NULL,

  -- dados de funções dos usuários
  is_deleted BOOLEAN                                          NOT NULL DEFAULT FALSE,
  funcao     utils.enum_s_church_t_tb_usuario_funcao_c_funcao NOT NULL DEFAULT 'levita',

  -- chaves estrangeiras
  min_lou_uuid              UUID NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_usuario_funcao PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_usuario_funcao_c_created_by_lid
    FOREIGN KEY (created_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_usuario_funcao_c_deleted_by_lid
    FOREIGN KEY (deleted_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_usuario_funcao_c_min_lou_uuid
    FOREIGN KEY (min_lou_uuid)
    REFERENCES church.tb_ministerio_louvor (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_usuario_funcao_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_instrumento_marca (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados da marca do instrumento
  is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
  nome       VARCHAR(30) NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_marca PRIMARY KEY (id)
);



CREATE TABLE church.tb_instrumento_modelo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados da marca do instrumento
  is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
  nome       VARCHAR(30) NOT NULL,

  -- chaves estrangeiras
  ins_mar_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_modelo PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_instrumento_modelo_c_ins_mar_id
    FOREIGN KEY (ins_mar_id)
    REFERENCES church.tb_instrumento_marca (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_instrumento (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  disabled_at    TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_adm UUID        NOT NULL,
  updated_by_adm UUID        NOT NULL,
  deleted_by_adm UUID            NULL DEFAULT NULL,

  -- dados de instrumentos
  is_deleted   BOOLEAN                                        NOT NULL DEFAULT FALSE,
  is_disabled  BOOLEAN                                        NOT NULL DEFAULT TRUE,
  nome         utils.enum_s_church_t_tb_instrumento_c_nome    NOT NULL,
  outro_nome   VARCHAR(30)                                        NULL,
  familia      utils.enum_s_church_t_tb_instrumento_c_familia NOT NULL,
  outra_marca  VARCHAR(30)                                        NULL,
  outro_modelo VARCHAR(30)                                        NULL,

  -- chaves estrangeiras
  ins_mod_id                     INTEGER     NULL,
  igr_uuid                       UUID    NOT NULL,
  s_storage_t_tb_arquivo_c_foto  UUID        NULL,
  s_storage_t_tb_arquivo_c_icone UUID    NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_instrumento_c_created_by_adm
    FOREIGN KEY (created_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_instrumento_c_updated_by_adm
    FOREIGN KEY (updated_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_instrumento_c_deleted_by_adm
    FOREIGN KEY (deleted_by_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_instrumento_c_ins_mod_id
    FOREIGN KEY (ins_mod_id)
    REFERENCES church.tb_instrumento_modelo (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_instrumento_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
    
  CONSTRAINT fk_s_church_t_tb_instrumento_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
  
  CONSTRAINT fk_s_church_t_tb_instrumento_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE church.tb_instrumento_ass_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_lid UUID        NOT NULL,
  deleted_by_lid UUID            NULL DEFAULT NULL,

  -- dados da associação de instrumento e usuário
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

  -- chaves estrangeiras
  ins_id                    INTEGER NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID    NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_ass_usuario PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_created_by_lid
    FOREIGN KEY (created_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_deleted_by_lid
    FOREIGN KEY (deleted_by_lid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_ins_id
    FOREIGN KEY (ins_id)
    REFERENCES church.tb_instrumento (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);

-- tb_igreja
CREATE UNIQUE INDEX uq_s_church_t_tb_igreja_c_cnpj
ON church.tb_igreja (cnpj)
WHERE is_deleted = FALSE;


-- tb_endereco
CREATE UNIQUE INDEX uq_s_church_t_tb_endereco_c_campos_de_endereco
ON church.tb_endereco (igr_uuid, uf, cidade, logradouro, numero)
WHERE is_deleted = FALSE;


-- tb_administrador
CREATE UNIQUE INDEX uq_s_church_t_tb_administrador_c_igr_uuid_c_adm
ON church.tb_administrador (igr_uuid, s_auth_t_tb_usuario_c_adm)
WHERE is_deleted = FALSE;


-- tb_categoria
CREATE UNIQUE INDEX uq_s_church_t_tb_categoria_c_nome_c_igr_uuid
ON church.tb_categoria (nome, igr_uuid)
WHERE is_deleted = FALSE;


-- tb_ministerio_louvor
CREATE UNIQUE INDEX uq_s_church_t_tb_ministerio_louvor_c_codigo
ON church.tb_ministerio_louvor (codigo)
WHERE is_deleted = FALSE;

CREATE UNIQUE INDEX uq_s_church_t_tb_ministerio_louvor_c_nome_c_igr_uuid
ON church.tb_ministerio_louvor (nome, igr_uuid)
WHERE is_deleted = FALSE;


-- tb_usuario_funcao
CREATE UNIQUE INDEX uq_s_church_t_tb_usuario_funcao_c_funcao_c_min_lou_uuid_c_lev
ON church.tb_usuario_funcao (funcao, min_lou_uuid, s_auth_t_tb_usuario_c_lev)
WHERE is_deleted = FALSE;


-- tb_instrumento_marca
CREATE UNIQUE INDEX uq_s_church_t_tb_instrumento_marca_c_marca
ON church.tb_instrumento_marca (nome)
WHERE is_deleted = FALSE;


-- tb_instrumento_modelo
CREATE UNIQUE INDEX uq_s_church_t_tb_instrumento_modelo_c_modelo_c_ins_mar_id
ON church.tb_instrumento_modelo (nome, ins_mar_id)
WHERE is_deleted = FALSE;


-- tb_instrumento
CREATE UNIQUE INDEX uq_s_church_t_tb_instrumento_c_nome_c_igr_uuid
ON church.tb_instrumento (nome, igr_uuid)
WHERE is_deleted = FALSE;


-- tb_instrumento_ass_usuario
CREATE UNIQUE INDEX uq_s_church_t_tb_instrumento_ass_usuario_c_ins_id_c_lev
ON church.tb_instrumento_ass_usuario (ins_id, s_auth_t_tb_usuario_c_lev)
WHERE is_deleted = FALSE;

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
  e classificação de música. Os dois primeiros tipos são inseridos
  em atividades da igreja para definir o tipo delas, enquanto o
  de classificação de música é inserido em músicas para que a
  igreja classifique músicas como quiser.
  Toda igreja pode inserir categorias personalizadas, de acordo
  com sua vontade.
  
  Exemplos de categorias de atividades:
  - Consagração, santa ceia de músicos, ensaio etc.

  Exemplos de categorias de classificação de músicas:
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
  sendo atualizadas pela FK da música do ministro, então
  é criado um "snapshot" da música do ministro, ou seja,
  uma cópia da música original no momento de saída do ministro
  da igreja, de forma que a igreja possa continuar com a música.
  Essa cópia deve conter todos os dados originais da música,
  salvos em formato JSONB

  Após a saída do ministro, essa música só pode ser acessada
  pelo JSONB, não sendo mais utilizado a referência de FK,
  apesar dela continuar registrada. Além disso, a igreja não
  deve ter permissão de usar essa faixa. Ela fica apenas visível,
  podendo ser copiada para uso com outra instância ou podendo ser
  excluída definitivamente
';



COMMENT ON TABLE church.tb_faixa_ass_categoria IS '
  Tabela que associa faixas da igreja com categorias de
  classificação de música
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

CREATE OR REPLACE FUNCTION
  utils.s_church_f_validador_cnpj(IN cnpj TEXT)
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
DECLARE
  cnpj_vetor TEXT[];
  soma INT;
  resto INT;
  i INT;
  pesos1 INTEGER[] := ARRAY[5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2];
  pesos2 INTEGER[] := ARRAY[6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2];
BEGIN
  IF cnpj IS NULL THEN
    RAISE EXCEPTION 'CNPJ inválido: não pode ser nulo';
  END IF;

  cnpj := trim(cnpj);
  IF cnpj = '' THEN
    RAISE EXCEPTION 'CNPJ inválido: não pode ser vazio';
  END IF;

  IF length(cnpj) != 14 THEN
    RAISE EXCEPTION 'CNPJ inválido: deve ter exatamente 14 caracteres';
  END IF;

  IF cnpj ~ '[ ]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter espaços';
  END IF;

  IF cnpj ~ '[\t]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter tabulações';
  END IF;

  IF cnpj ~ '[\n]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter quebras de linha (ENTER)';
  END IF;

  IF cnpj ~ '[\r]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter Carriage Return';
  END IF;

  IF cnpj ~ '[\f]' THEN
    RAISE EXCEPTION 'CNPJ inválido: não deve conter Form Feed';
  END IF;

  IF cnpj ~ '[^0-9]' THEN
    RAISE EXCEPTION 'CNPJ inválido: deve conter apenas números (sem pontos ou traços)';
  END IF;

  IF cnpj ~ '^(\d)\1{13}$' THEN
    RAISE EXCEPTION 'CNPJ inválido: não pode conter todos os dígitos iguais';
  END IF;

  -- converte o CNPJ em um vetor de caracteres
  cnpj_vetor := regexp_split_to_array(cnpj, '');
  
  -- Valida o primeiro dígito verificador
  soma := 0;
  FOR i IN 1..12 LOOP
    soma := soma + (cast(cnpj_vetor[i] AS INTEGER) * pesos1[i]);
  END LOOP;
  resto := soma % 11;
  IF resto < 2 THEN
    resto := 0;
  ELSE
    resto := 11 - resto;
  END IF;
  IF resto != cast(cnpj_vetor[13] AS INTEGER) THEN
    RAISE EXCEPTION 'CNPJ inválido: primeiro dígito verificador incorreto';
  END IF;

  -- Valida o segundo dígito verificador
  soma := 0;
  FOR i IN 1..13 LOOP
    soma := soma + (cast(cnpj_vetor[i] AS INTEGER) * pesos2[i]);
  END LOOP;
  resto := soma % 11;
  IF resto < 2 THEN
    resto := 0;
  ELSE
    resto := 11 - resto;
  END IF;
  IF resto != cast(cnpj_vetor[14] AS INTEGER) THEN
    RAISE EXCEPTION 'CNPJ inválido: segundo dígito verificador incorreto';
  END IF;

  RETURN TRUE;
END;
$$;

CREATE OR REPLACE FUNCTION
  utils.s_church_f_validador_descricao (
    IN descricao      TEXT,
    IN tamanho_minimo SMALLINT,
    IN tamanho_maximo SMALLINT
  )
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
BEGIN
  IF descricao IS NULL THEN
    RAISE EXCEPTION 'Descrição inválida: não pode ser nulo';
  END IF;

  IF tamanho_minimo IS NULL THEN
    RAISE EXCEPTION 'Tamanho mínimo inválido: não pode ser nulo';
  END IF;

  IF tamanho_maximo IS NULL THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser nulo';
  END IF;

  descricao := trim(descricao);
  IF descricao = '' THEN
    RAISE EXCEPTION 'Descrição inválida: não pode ser vazia';
  END IF;

  IF tamanho_minimo < 0 THEN
    RAISE EXCEPTION 'Tamanho mínimo inválido: não pode ser menor que 0';
  END IF;

  IF tamanho_maximo < 1 THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser menor que 1';
  END IF;

  IF length(descricao) < tamanho_minimo THEN
    RAISE EXCEPTION 'Descrição inválida: deve ter, ao menos, % caractere(s)', tamanho_minimo;
  END IF;

  IF length(descricao) > tamanho_maximo THEN
    RAISE EXCEPTION 'Descrição inválida: não pode exceder % caractere(s)', tamanho_maximo;
  END IF;

  IF descricao ~ '[\r]' THEN
    RAISE EXCEPTION 'Descrição inválida: não deve conter Carriage Return';
  END IF;

  IF descricao ~ '[\f]' THEN
    RAISE EXCEPTION 'Descrição inválida: não deve conter Form Feed';
  END IF;

  IF descricao ~* '[^a-záéíóúâêôãõçàèìòù0-9\-\n\(\):/+.&'' ]' THEN
    RAISE EXCEPTION 'Descrição inválida: deve conter apenas letras, números, espaços, quebras de linha e algumas pontuações (-, (, ), :, ., +, '')';
  END IF;

  RETURN TRUE;
END;
$$;

CREATE OR REPLACE FUNCTION
  utils.s_church_f_validador_nome (
    IN nome           TEXT,
    IN tipo           TEXT,
    IN tamanho_minimo SMALLINT,
    IN tamanho_maximo SMALLINT
  )
RETURNS BOOLEAN
LANGUAGE plpgsql
IMMUTABLE
SECURITY INVOKER
SET search_path = pg_catalog
AS $$
DECLARE
  tipos CONSTANT TEXT[] := ARRAY [
    'igreja',
    'outra denominação de igreja',
    'atividade',
    'ministério de louvor',
    'marca de instrumento',
    'modelo de instrumento',
    'outro instrumento',
    'outra marca de instrumento',
    'outro modelo de instrumento'
  ];
BEGIN
  IF tipo IS NULL THEN
    RAISE EXCEPTION 'Tipo inválido: não pode ser nulo';
  END IF;

  IF tipo != ALL(tipos) THEN
    RAISE EXCEPTION 'Tipo inválido: só são aceitos os tipos
      "igreja",
      "outra denominação de igreja",
      "atividade",
      "ministério de louvor",
      "marca de instrumento",
      "modelo de instrumento",
      "outro instrumento",
      "outra marca de instrumento" ou
      "outro modelo de instrumento"
    ';
  END IF;

  IF tipo IN (tipos[6], tipos[7], tipos[8]) AND nome IS NULL THEN
    RETURN TRUE;
  END IF;

  IF tipo NOT IN (tipos[6], tipos[7], tipos[8]) AND nome IS NULL THEN
    RAISE EXCEPTION 'Nome de % inválido: não pode ser nulo', tipo;
  END IF;

  IF tamanho_minimo IS NULL THEN
    RAISE EXCEPTION 'Tamanho mínimo inválido: não pode ser nulo';
  END IF;

  IF tamanho_maximo IS NULL THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser nulo';
  END IF;

  nome := trim(nome);
  IF nome = '' THEN
    RAISE EXCEPTION 'Nome de % inválido: não pode ser vazio', tipo;
  END IF;

  IF tamanho_minimo < 0 THEN
    RAISE EXCEPTION 'Tamanho mínimo inválido: não pode ser menor que 0';
  END IF;

  IF tamanho_maximo < 1 THEN
    RAISE EXCEPTION 'Tamanho máximo inválido: não pode ser menor que 1';
  END IF;

  IF length(nome) < tamanho_minimo THEN
    RAISE EXCEPTION 'Nome de % inválido: deve ter, ao menos, % caractere(s)', tipo, tamanho_minimo;
  END IF;

  IF length(nome) > tamanho_maximo THEN
    RAISE EXCEPTION 'Nome de % inválido: não pode exceder % caractere(s)', tipo, tamanho_maximo;
  END IF;

  IF nome ~ '[\r]' THEN
    RAISE EXCEPTION 'Nome de % inválido: não deve conter Carriage Return', tipo;
  END IF;

  IF nome ~ '[\f]' THEN
    RAISE EXCEPTION 'Nome de % inválido: não deve conter Form Feed', tipo;
  END IF;

  IF nome ~* '[^a-záéíóúâêôãõçàèìòù0-9\-\(\):/+.&'' ]' THEN
    RAISE EXCEPTION 'Nome de % inválido: deve conter apenas letras, números, espaços e algumas pontuações (-, (, ), :, ., +, '')', tipo;
  END IF;

  RETURN TRUE;
END;
$$;

ALTER TABLE church.tb_igreja
ADD CONSTRAINT ck_s_church_t_tb_igreja_c_cnpj
CHECK (
  utils.s_church_f_validador_cnpj(cnpj)
);

ALTER TABLE church.tb_igreja
ADD CONSTRAINT ck_s_church_t_tb_igreja_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'igreja', 1::SMALLINT, 100::SMALLINT)
);

ALTER TABLE church.tb_igreja
ADD CONSTRAINT ck_s_church_t_tb_igreja_c_outra_denominacao
CHECK (
  utils.s_church_f_validador_nome(outra_denominacao, 'outra denominação de igreja', 1::SMALLINT, 100::SMALLINT)
);



ALTER TABLE church.tb_categoria
ADD CONSTRAINT ck_s_church_t_tb_atividade_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'atividade', 1::SMALLINT, 30::SMALLINT)
);

ALTER TABLE church.tb_categoria
ADD CONSTRAINT ck_s_church_t_tb_atividade_c_descricao
CHECK (
  utils.s_church_f_validador_descricao(descricao, 1::SMALLINT, 50::SMALLINT)
);



ALTER TABLE church.tb_ministerio_louvor
ADD CONSTRAINT ck_s_church_t_tb_ministerio_louvor_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'ministério de louvor', 1::SMALLINT, 100::SMALLINT)
);

ALTER TABLE church.tb_ministerio_louvor
ADD CONSTRAINT ck_s_church_t_tb_ministerio_louvor_c_descricao
CHECK (
  utils.s_church_f_validador_descricao(descricao, 1::SMALLINT, 50::SMALLINT)
);

ALTER TABLE church.tb_ministerio_louvor
ADD CONSTRAINT ck_s_church_t_tb_ministerio_louvor_c_codigo
CHECK (
  codigo ~ '^[a-zA-Z0-9]{6}$'
);



ALTER TABLE church.tb_instrumento_marca
ADD CONSTRAINT ck_s_church_t_tb_instrumento_marca_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'marca de instrumento', 1::SMALLINT, 30::SMALLINT)
);



ALTER TABLE church.tb_instrumento_modelo
ADD CONSTRAINT ck_s_church_t_tb_instrumento_modelo_c_nome
CHECK (
  utils.s_church_f_validador_nome(nome, 'modelo de instrumento', 1::SMALLINT, 30::SMALLINT)
);



ALTER TABLE church.tb_instrumento
ADD CONSTRAINT ck_s_church_t_tb_instrumento_c_outro_nome
CHECK (
  utils.s_church_f_validador_nome(outro_nome, 'outro instrumento', 1::SMALLINT, 30::SMALLINT)
);

ALTER TABLE church.tb_instrumento
ADD CONSTRAINT ck_s_church_t_tb_instrumento_c_outra_marca
CHECK (
  utils.s_church_f_validador_nome(outra_marca, 'outra marca de instrumento', 1::SMALLINT, 30::SMALLINT)
);

ALTER TABLE church.tb_instrumento
ADD CONSTRAINT ck_s_church_t_tb_instrumento_c_outro_modelo
CHECK (
  utils.s_church_f_validador_nome(outro_modelo, 'outro modelo de instrumento', 1::SMALLINT, 30::SMALLINT)
);

DROP SCHEMA IF EXISTS schedule CASCADE;
CREATE SCHEMA schedule;



CREATE TABLE schedule.tb_registro_ausencia (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  
  -- dados do registro de ausência
  motivo        utils.enum_s_schedule_t_tb_registro_ausencia_c_motivo NOT NULL,
  outro_motivo  VARCHAR(20)                                               NULL DEFAULT NULL,
  justificativa VARCHAR(2000)                                         NOT NULL,
  periodo       TSTZRANGE                                             NOT NULL,

  -- chaves estrangeiras
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_registro_ausencia PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_schedule_t_tb_registro_ausencia_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);

ALTER TABLE schedule.tb_registro_ausencia
ADD CONSTRAINT ex_s_schedule_t_tb_registro_ausencia_c_periodo
EXCLUDE USING GIST (
  s_auth_t_tb_usuario_c_lev WITH =,
  periodo WITH &&
);