-- tb_parte
CREATE UNIQUE INDEX uq_s_song_t_tb_parte_c_posicao_c_mus_id
ON song.tb_parte (posicao, mus_id)
WHERE is_deleted = FALSE;


-- tb_artista
CREATE UNIQUE INDEX uq_s_song_t_tb_artista_c_nome_c_mus_id
ON song.tb_artista_secundario (nome, mus_id)
WHERE is_deleted = FALSE;


-- tb_medley_ass_musica
CREATE UNIQUE INDEX uq_s_song_t_tb_medley_ass_musica_c_posicao_c_med_id
ON song.tb_medley_ass_musica (posicao, med_id)
WHERE is_deleted = FALSE;