-- DESCRIÇÃO DESSA VERSÃO:

-- AUTH: parece finalizado

-- STORAGE: parece finalizado

-- CHURCH: parece finalizado

-- Coisas já identificadas para se fazer em próximas versões:
-- * Remover o "tb_" do nome de todas as tabelas;
-- * Criar CONSTRAINTs para os SEQUENCES;
-- * Adicionar CHECK CONSTRAINTs para todos os atributos;
-- * Adicionar COMMENTs para todas as tabelas;
-- * Criar os Workflows para geração automática de backups e restores;
-- * Fazer o mesmo com todos os outros schemas;
-- * Criar ROLEs, variáveis de ambiente, policies e configurá-los para todos os schemas;
-- * Escrever README.md com todas as convenções do projeto;
-- * Implementar Migrations com FlyWay;
-- * Implementar schedules.












CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP SCHEMA IF EXISTS app_utils CASCADE;
CREATE SCHEMA app_utils;

CREATE DOMAIN app_utils.domain_uf AS CHAR(2);
ALTER DOMAIN app_utils.domain_uf
ADD CONSTRAINT check_s_app_utils_d_domain_uf
CHECK (
  VALUE IN (
    'AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF', 'ES', 'GO',
    'MA', 'MG', 'MS', 'MT', 'PA', 'PB', 'PE', 'PI', 'PR',
    'RJ', 'RN', 'RO', 'RR', 'RS', 'SC', 'SE', 'SP', 'TO'
  )
);

CREATE DOMAIN app_utils.domain_cep AS VARCHAR(8);
ALTER DOMAIN app_utils.domain_cep
ADD CONSTRAINT check_s_app_utils_d_domain_cpf
CHECK (
  char_length(VALUE) = 8 AND
  VALUE ~ '^[0-9]{8}$'
);

-- SCHEMA STORAGE
CREATE TYPE
app_utils.enum_s_storage_t_tb_bucket_c_nome
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
app_utils.enum_s_storage_t_tb_arquivo_c_mime_type
AS ENUM (
  'image/png', 'image/jpeg', 'image/svg+xml',
  'audio/mpeg', 'audio/wav', 'audio/ogg', 'audio/flac', 'audio/mp4', 'audio/x-alac',
  'application/pdf'
);

CREATE TYPE
app_utils.enum_s_storage_t_tb_arquivo_c_extensao
AS ENUM (
  'png', 'jpg', 'jpeg', 'svg',
  'mp3', 'wav', 'ogg',  'flac', 'm4a', 'alac',
  'pdf'
);



-- SCHEMA CHURCH
CREATE TYPE
app_utils.enum_s_church_t_tb_igreja_c_denominacao
AS ENUM (
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
app_utils.enum_s_church_t_tb_usuario_funcao_c_funcao
AS ENUM (
  'Líder', 'Ministro', 'Levita'
);

-- retorna um número passado com valor convertido para MB
CREATE OR REPLACE FUNCTION
app_utils.get_tamanho_em_mb(tamanho INT)
RETURNS INTEGER
LANGUAGE sql
SECURITY DEFINER
AS $$
  SELECT tamanho * 1024 * 1024
$$;

DROP SCHEMA IF EXISTS storage CASCADE;
CREATE SCHEMA storage;



CREATE TABLE storage.tb_bucket (
  -- chaves primárias
  id SMALLINT GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,

  -- dados do bucket
  deletado                           BOOLEAN                                      NOT NULL DEFAULT false,
  nome                               app_utils.enum_s_storage_t_tb_bucket_c_nome  NOT NULL,
  tempo_expiracao_upload_em_segundos SMALLINT                                     NOT NULL DEFAULT 30,
  tamanho_minimo                     INTEGER                                      NOT NULL DEFAULT 1,
  tamanho_maximo                     INTEGER                                      NOT NULL DEFAULT app_utils.get_tamanho_em_mb(1000),

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_bucket PRIMARY KEY (id),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_storage_t_tb_bucket_c_nome UNIQUE (nome)
);



CREATE TABLE storage.tb_arquivo (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),
  id   INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,

  -- dados do arquivo
  deletado         BOOLEAN                                            NOT NULL DEFAULT false,
  caminho          TEXT                                               NOT NULL,
  mime_type        app_utils.enum_s_storage_t_tb_arquivo_c_mime_type  NOT NULL,
  extensao         app_utils.enum_s_storage_t_tb_arquivo_c_extensao   NOT NULL,
  tamanho_em_bytes INTEGER                                            NOT NULL,

  -- chaves estrangeiras
  buc_id SMALLINT NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_arquivo PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_storage_t_tb_arquivo_c_id UNIQUE (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_bucket_c_id
    FOREIGN KEY (buc_id)
    REFERENCES storage.tb_bucket (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);

DROP SCHEMA IF EXISTS auth CASCADE;
CREATE SCHEMA auth;



CREATE TABLE auth.tb_endereco (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de endereço
  cep    app_utils.domain_cep   NOT NULL,
  uf     app_utils.domain_uf    NOT NULL,
  cidade VARCHAR(100)           NOT NULL,
  bairro VARCHAR(100)           NOT NULL,
  rua    VARCHAR(100)           NOT NULL,
  numero VARCHAR(5)             NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_endereco PRIMARY KEY (id)
);



CREATE TABLE auth.tb_usuario (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at    TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,
  ultimo_acesso TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

  -- dados do usuário
  deletado         BOOLEAN      NOT NULL DEFAULT false,
  cpf              VARCHAR(11)  NOT NULL,
  nome             VARCHAR(20)  NOT NULL,
  sobrenome        VARCHAR(50)  NOT NULL,
  nome_social      VARCHAR(20)      NULL,
  sobrenome_social VARCHAR(50)      NULL,
  sexo             CHAR(1)      NOT NULL,
  data_nascimento  DATE         NOT NULL,
  email            VARCHAR(50)  NOT NULL,
  telefone         VARCHAR(25)  NOT NULL,
  senha            VARCHAR(128) NOT NULL,

  -- chaves estrangeiras
  end_id                        INTEGER NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID        NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_usuario PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_auth_t_tb_usuario_c_id       UNIQUE (id),
  CONSTRAINT uq_s_auth_t_tb_usuario_c_cpf      UNIQUE (cpf),
  CONSTRAINT uq_s_auth_t_tb_usuario_c_email    UNIQUE (email),
  CONSTRAINT uq_s_auth_t_tb_usuario_c_telefone UNIQUE (telefone),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_end_id
    FOREIGN KEY (end_id)
    REFERENCES auth.tb_endereco (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE auth.tb_registro_ausencia (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,
  
  -- dados do registro de ausência
  motivo         VARCHAR(50)                     NOT NULL,
  justificativa  VARCHAR(2000)                   NOT NULL,
  data           DATE                            NOT NULL,
  horario_inicio TIME          WITHOUT TIME ZONE NOT NULL,
  horario_fim    TIME          WITHOUT TIME ZONE NOT NULL,

  -- chaves estrangeiras
  usu_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_auth_t_tb_registro_ausencia PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_auth_t_tb_usuario_c_usu_uuid
    FOREIGN KEY (usu_uuid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);

CREATE TABLE storage.tb_arquivo_ass_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- chaves estrangeiras
  arq_uuid                  UUID NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_storage_t_tb_arquivo_ass_usuario PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_storage_t_tb_arquivo_ass_usuario_c_uuid_c_lev
  UNIQUE (arq_uuid, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_arq_uuid
    FOREIGN KEY (arq_uuid)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);

INSERT INTO storage.tb_bucket (
  nome,
  tempo_expiracao_upload_em_segundos,
  tamanho_minimo,
  tamanho_maximo
) VALUES 
  ('foto-perfil-usuario',       30, 1, app_utils.get_tamanho_em_mb(5)),
  ('foto-perfil-igreja',        30, 1, app_utils.get_tamanho_em_mb(5)),
  ('foto-perfil-ministerio',    30, 1, app_utils.get_tamanho_em_mb(5)),
  ('icone-instrumento',         30, 1, app_utils.get_tamanho_em_mb(5)),
  ('audio',                     30, 1, app_utils.get_tamanho_em_mb(150)),
  ('pdf',                       30, 1, app_utils.get_tamanho_em_mb(5)),

  ('sistema-icone-instrumento', 30, 1, app_utils.get_tamanho_em_mb(5)),
  ('sistema-imagem',            30, 1, app_utils.get_tamanho_em_mb(5)),
  ('sistema-audio',             30, 1, app_utils.get_tamanho_em_mb(5));

DROP SCHEMA IF EXISTS church CASCADE;
CREATE SCHEMA church;



CREATE TABLE church.tb_endereco (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados do endereço
  cep    app_utils.domain_cep   NOT NULL,
  uf     app_utils.domain_uf    NOT NULL,
  cidade VARCHAR(100)           NOT NULL,
  bairro VARCHAR(100)           NOT NULL,
  rua    VARCHAR(100)           NOT NULL,
  numero VARCHAR(5)             NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_endereco PRIMARY KEY (id)
);



CREATE TABLE church.tb_igreja (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,

  -- dados da igreja
  deletado          BOOLEAN                                           NOT NULL DEFAULT false,
  cnpj              VARCHAR(14)                                       NOT NULL,
  nome              VARCHAR(100)                                      NOT NULL,
  denominacao       app_utils.enum_s_church_t_tb_igreja_c_denominacao     NULL,
  outra_denominacao VARCHAR(100)                                          NULL,

  -- chaves estrangeiras
  end_endereco_principal_id     INTEGER NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID    NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_igreja PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_church_t_tb_igreja_c_id   UNIQUE (id),
  CONSTRAINT uq_s_church_t_tb_igreja_c_cnpj UNIQUE (cnpj),

  -- declaração das chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



-- declaração de relacionamento cíclico entre
-- church.tb_endereco e church.tb_igreja
ALTER TABLE church.tb_endereco
  ADD CONSTRAINT fk_s_church_t_tb_endereco_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE church.tb_igreja
  ADD CONSTRAINT fk_s_church_t_tb_igreja_c_end_endereco_principal_id
    FOREIGN KEY (end_endereco_principal_id)
    REFERENCES church.tb_endereco (id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    DEFERRABLE INITIALLY DEFERRED;



CREATE TABLE church.tb_administrador (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- chaves estrangeiras
  igr_uuid                  UUID NOT NULL,
  s_auth_t_tb_usuario_c_adm UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_administrador PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_administrador_c_igr_uuid_c_adm
  UNIQUE (igr_uuid, s_auth_t_tb_usuario_c_adm),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_igreja_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,
  
  CONSTRAINT fk_s_auth_t_tb_usuario_c_adm
    FOREIGN KEY (s_auth_t_tb_usuario_c_adm)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_compromisso_tipo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados do tipo de compromisso
  ativo BOOLEAN     NOT NULL DEFAULT true,
  nome  VARCHAR(30) NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_compromisso_tipo PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_compromisso_tipo_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_compromisso_tipo_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_agendamento_tipo (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados do tipo de agendamento
  ativo BOOLEAN     NOT NULL DEFAULT true,
  nome  VARCHAR(30) NOT NULL,

  -- chaves estrangeiras
  igr_uuid UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_agendamento_tipo PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_agendamento_tipo_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_agendamento_tipo_c_igr_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_ministerio_louvor (
  -- chaves primárias
  uuid UUID NOT NULL DEFAULT gen_random_uuid(),
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP WITH TIME ZONE     NULL DEFAULT NULL,
  
  -- dados do ministério de louvor
  deletado BOOLEAN      NOT NULL DEFAULT false,
  nome     VARCHAR(100) NOT NULL,

  -- chaves estrangeiras
  igr_uuid                      UUID NOT NULL,
  s_storage_t_tb_arquivo_c_foto UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_ministerio_louvor PRIMARY KEY (uuid),

  -- declaração de chaves únicas
  CONSTRAINT uq_s_church_t_tb_ministerio_louvor_c_id UNIQUE (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_ministerio_louvor_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_igreja_c_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES church.tb_igreja (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_storage_t_tb_arquivo_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_usuario_funcao (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de funções dos usuários
  funcao app_utils.enum_s_church_t_tb_usuario_funcao_c_funcao NOT NULL,

  -- chaves estrangeiras
  min_lou_uuid              UUID NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_usuario_funcao PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_usuario_funcao_c_funcao_c_min_lou_uuid_c_lev
  UNIQUE (funcao, min_lou_uuid, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_ministerio_louvor_c_min_lou_uuid
    FOREIGN KEY (min_lou_uuid)
    REFERENCES church.tb_ministerio_louvor (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_instrumento (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de instrumentos
  ativo BOOLEAN     NOT NULL DEFAULT true,
  nome  VARCHAR(25) NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_icone UUID NOT NULL,
  igr_uuid                       UUID NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_instrumento_c_nome_c_igr_uuid
  UNIQUE (nome, igr_uuid),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_storage_t_tb_arquivo_c_icone
    FOREIGN KEY (s_storage_t_tb_arquivo_c_icone)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_church_t_tb_igreja_c_uuid
    FOREIGN KEY (igr_uuid)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);



CREATE TABLE church.tb_instrumento_ass_usuario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- chaves estrangeiras
  ins_id                    INTEGER NOT NULL,
  s_auth_t_tb_usuario_c_lev UUID    NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_church_t_tb_instrumento_ass_usuario PRIMARY KEY (id),

  -- declaração de chaves únicas compostas
  CONSTRAINT uq_s_church_t_tb_instrumento_ass_usuario_c_ins_id_c_lev
  UNIQUE (ins_id, s_auth_t_tb_usuario_c_lev),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_church_t_tb_instrumento_ass_usuario_c_ins_id
    FOREIGN KEY (ins_id)
    REFERENCES church.tb_instrumento (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT fk_s_auth_t_tb_usuario_c_lev
    FOREIGN KEY (s_auth_t_tb_usuario_c_lev)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
);

-- valida o "sexo"
ALTER TABLE auth.tb_usuario
ADD CONSTRAINT
check_s_auth_t_tb_endereco_c_sexo
CHECK (sexo IN (
  'M', 'F'
));

-- garante que o upload de arquivos
-- dure de mais que zero a no máximo 5 minutos
ALTER TABLE storage.tb_bucket
ADD CONSTRAINT
check_s_storage_t_tb_bucket_c_tempo_expiracao
CHECK (
  tempo_expiracao_upload_em_segundos > 0 AND
  tempo_expiracao_upload_em_segundos <= 300
);

-- garante que o tamanho mínimo e máximo
-- dos arquivos esteja no intervalo de
-- 1 byte a 1 gigabyte
ALTER TABLE storage.tb_bucket
ADD CONSTRAINT
check_s_storage_t_tb_bucket_c_tamanho_minimo_e_maximo
CHECK (
  tamanho_minimo > 0 AND
  tamanho_minimo <= app_utils.get_tamanho_em_mb(1000) AND
  tamanho_maximo > 0 AND
  tamanho_maximo <= app_utils.get_tamanho_em_mb(1000) AND
  tamanho_maximo >= tamanho_minimo
);