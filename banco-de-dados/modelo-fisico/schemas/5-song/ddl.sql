DROP SCHEMA IF EXISTS song CASCADE;
CREATE SCHEMA song;



CREATE TABLE song.tb_musica (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_usu UUID        NOT NULL,

  -- dados da música
  is_deleted             BOOLEAN      NOT NULL DEFAULT FALSE,
  nome                   VARCHAR(150) NOT NULL,
  artista                VARCHAR(70)  NOT NULL,
  tem_artista_secundario BOOLEAN      NOT NULL,
  album                  VARCHAR(50)  NULL,
  duracao                INTERVAL     NOT NULL,

  duracao_em_segundos SMALLINT GENERATED ALWAYS AS (
    EXTRACT(EPOCH FROM duracao)::SMALLINT
  ) STORED,

  bpm             SMALLINT                           NULL,
  tonalidade      utils.enum_s_song_c_tonalidade     NULL,
  link_musica     utils.domain_link              NOT NULL,
  link_letra      utils.domain_link                  NULL,
  link_cifra      utils.domain_link                  NULL,
  link_partitura  utils.domain_link                  NULL,
  parte_de_medley BOOLEAN                        NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_foto UUID NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_musica PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_song_t_tb_musica_c_created_by_usu
    FOREIGN KEY (created_by_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,
    
  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_song_t_tb_musica_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE song.tb_parte (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados das parte da música
  is_deleted BOOLEAN                        NOT NULL DEFAULT FALSE,
  posicao    SMALLINT                       NOT NULL,
  parte      VARCHAR(50)                    NOT NULL,
  bpm        SMALLINT                       NOT NULL,
  tonalidade utils.enum_s_song_c_tonalidade NOT NULL,

  -- chaves estrangeiras
  mus_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_parte PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_song_t_tb_parte_c_mus_id
    FOREIGN KEY (mus_id)
    REFERENCES song.tb_musica (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE song.tb_artista_secundario (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados dos artistas da música
  is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
  nome       VARCHAR(70) NOT NULL,

  -- chaves estrangeiras
  mus_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_artista_secundario PRIMARY KEY (id),

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_song_t_tb_artista_secundario_c_mus_id
    FOREIGN KEY (mus_id)
    REFERENCES song.tb_musica (id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE song.tb_medley (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at     TIMESTAMPTZ     NULL DEFAULT NULL,
  created_by_usu UUID        NOT NULL,

  -- dados do medley
  is_deleted         BOOLEAN      NOT NULL DEFAULT FALSE,
  nome               VARCHAR(150) NOT NULL,
  quantidade_musicas SMALLINT     NOT NULL,

  -- chaves estrangeiras
  s_storage_t_tb_arquivo_c_foto UUID NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_medley PRIMARY KEY (id),

  -- declaração de chaves estrangeiras de logs
  CONSTRAINT fk_s_song_t_tb_medley_c_created_by_usu
    FOREIGN KEY (created_by_usu)
    REFERENCES auth.tb_usuario (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE,

  -- declaração de chaves estrangeiras
  CONSTRAINT fk_s_song_t_tb_medley_c_foto
    FOREIGN KEY (s_storage_t_tb_arquivo_c_foto)
    REFERENCES storage.tb_arquivo (uuid)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT
    NOT DEFERRABLE INITIALLY IMMEDIATE
);



CREATE TABLE song.tb_medley_ass_musica (
  -- chaves primárias
  id INTEGER GENERATED ALWAYS AS IDENTITY,

  -- dados de logs
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMPTZ     NULL DEFAULT NULL,

  -- dados de associação entre músicas e medleys
  is_deleted BOOLEAN  NOT NULL DEFAULT FALSE,
  posicao    SMALLINT NOT NULL,

  -- chaves estrangeiras
  med_id INTEGER NOT NULL,
  mus_id INTEGER NOT NULL,

  -- declaração de chaves primárias
  CONSTRAINT pk_s_song_t_tb_medley_ass_musica PRIMARY KEY (id),

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