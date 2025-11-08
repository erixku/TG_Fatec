ALTER TABLE song.tb_musica
ADD CONSTRAINT ck_s_song_t_tb_musica_c_duracao
CHECK (
  duracao >= INTERVAL '1 second' AND
  duracao <= INTERVAL '1 hour'
);

ALTER TABLE song.tb_musica
ADD CONSTRAINT ck_s_song_t_tb_musica_c_bpm
CHECK (
  bpm > 0 AND
  bpm < 1200
);

ALTER TABLE song.tb_musica
ADD CONSTRAINT ck_s_song_t_tb_musica_c_bpm_c_tonalidade
CHECK (
  (
    bpm IS NULL AND
    tonalidade IS NULL
  )
  OR
  (
    bpm IS NOT NULL AND
    tonalidade IS NOT NULL
  )
);



ALTER TABLE song.tb_parte
ADD CONSTRAINT ck_s_song_t_tb_parte_c_posicao
CHECK (
  posicao > 0 AND
  posicao < 20
);

ALTER TABLE song.tb_parte
ADD CONSTRAINT ck_s_song_t_tb_parte_c_bpm
CHECK (
  bpm > 0 AND
  bpm < 1200
);



ALTER TABLE song.tb_medley
ADD CONSTRAINT ck_s_song_t_tb_medley_c_quantidade_musicas
CHECK (
  quantidade_musicas > 0 AND
  quantidade_musicas <= 20
);



ALTER TABLE song.tb_medley_ass_musica
ADD CONSTRAINT ck_s_song_t_tb_medley_ass_musica_c_posicao
CHECK (
  posicao > 0 AND
  posicao <= 20
);