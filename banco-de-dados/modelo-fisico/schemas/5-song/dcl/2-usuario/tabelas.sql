GRANT USAGE ON SCHEMA song TO r_usuario;



-- tb_musica
GRANT
  INSERT (
    created_by,
    nome,
    artista,
    tem_artista_secundario,
    album,
    duracao,
    bpm,
    tonalidade,
    link_musica,
    link_letra,
    link_cifra,
    link_partitura,
    parte_de_medley,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE song.tb_musica
  TO r_usuario;

GRANT
  SELECT (
    created_by,
    is_deleted,
    nome,
    artista,
    tem_artista_secundario,
    album,
    duracao,
    duracao_em_segundos,
    bpm,
    tonalidade,
    link_musica,
    link_letra,
    link_cifra,
    link_partitura,
    parte_de_medley,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE song.tb_musica
  TO r_usuario;

GRANT
  UPDATE (
    updated_at,
    deleted_at,
    is_deleted,
    nome,
    artista,
    tem_artista_secundario,
    album,
    duracao,
    duracao_em_segundos,
    bpm,
    tonalidade,
    link_musica,
    link_letra,
    link_cifra,
    link_partitura,
    parte_de_medley,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE song.tb_musica
  TO r_usuario;



-- tb_parte
GRANT
  INSERT (
    posicao,
    parte,
    bpm,
    tonalidade,
    mus_id
  )
  ON TABLE song.tb_parte
  TO r_usuario;

GRANT
  SELECT (
    is_deleted,
    posicao,
    parte,
    bpm,
    tonalidade,
    mus_id
  )
  ON TABLE song.tb_parte
  TO r_usuario;

GRANT
  UPDATE (
    updated_at,
    deleted_at,
    is_deleted,
    posicao,
    parte,
    bpm,
    tonalidade
  )
  ON TABLE song.tb_parte
  TO r_usuario;



-- tb_artista_secundario
GRANT
  INSERT (
    nome,
    mus_id
  )
  ON TABLE song.tb_artista_secundario
  TO r_usuario;

GRANT
  SELECT (
    is_deleted,
    nome,
    mus_id
  )
  ON TABLE song.tb_artista_secundario
  TO r_usuario;

GRANT
  UPDATE (
    updated_at,
    deleted_at,
    is_deleted,
    nome
  )
  ON TABLE song.tb_artista_secundario
  TO r_usuario;



-- tb_medley_ass_musica
GRANT
  INSERT (
    posicao,
    med_id,
    mus_id
  )
  ON TABLE song.tb_medley_ass_musica
  TO r_usuario;

GRANT
  SELECT (
    is_deleted,
    posicao,
    med_id,
    mus_id
  )
  ON TABLE song.tb_medley_ass_musica
  TO r_usuario;

GRANT
  UPDATE (
    updated_at,
    deleted_at,
    is_deleted,
    posicao
  )
  ON TABLE song.tb_medley_ass_musica
  TO r_usuario;



-- tb_medley
GRANT
  INSERT (
    nome,
    quantidade_musicas,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE song.tb_medley
  TO r_usuario;

GRANT
  SELECT (
    is_deleted,
    nome,
    quantidade_musicas,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE song.tb_medley
  TO r_usuario;

GRANT
  UPDATE (
    updated_at,
    deleted_at,
    is_deleted,
    nome,
    quantidade_musicas,
    s_storage_t_tb_arquivo_c_foto
  )
  ON TABLE song.tb_medley
  TO r_usuario;