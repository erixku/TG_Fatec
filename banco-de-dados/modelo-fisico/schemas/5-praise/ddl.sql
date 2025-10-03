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

  bpm            VARCHAR(3)                                                NULL,
  tonalidade     utils.enum_s_song_t_tb_musica_tonalidade_c_tonalidade     NULL,
  link_musica    VARCHAR(500)                                          NOT NULL,
  link_letra     VARCHAR(500)                                              NULL,
  link_cifra     VARCHAR(500)                                              NULL,
  link_partitura VARCHAR(500)                                              NULL,

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
  bpm        VARCHAR(3)                                            NOT NULL,
  tonalidade utils.enum_s_song_t_tb_musica_tonalidade_c_tonalidade NOT NULL,
  parte      VARCHAR(50)                                           NOT NULL,

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