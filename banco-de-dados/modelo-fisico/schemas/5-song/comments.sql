COMMENT ON SCHEMA song IS '
  Schema que armazena as músicas e medleys
  criados por usuários no sistema. Posteriormente,
  caso esses usuários tenham perfil de ministro em
  determinada igreja, eles podem vincular suas músicas
  e medleys criados em suas igrejas
';



COMMENT ON TABLE song.tb_musica IS '
  Tabela que armazena músicas criadas pelos usuários,
  que ficam salvas em seus perfis
';

COMMENT ON COLUMN song.tb_musica.bpm IS '
  Armazena o BPM que meça a música inteira.
  Caso esta coluna seja nula, quer dizer que
  durante a música, o BPM muda pelo menos uma vez.
  Sendo assim, o BPM é cadastrado na tabela
  "song.tb_partes", onde é possível cadastrar
  o BPM por parte da música
';

COMMENT ON COLUMN song.tb_musica.tonalidade IS '
  Armazena a tonalidade que harmoniza a música inteira.
  Caso esta coluna seja nula, quer dizer que
  durante a música, a tonalidade muda pelo menos uma vez.
  Sendo assim, a tonalidade é cadastrado na tabela
  "song.tb_partes", onde é possível cadastrar
  tonalidades por parte da música
';

COMMENT ON COLUMN song.tb_musica.parte_de_medley IS '
  Caso essa música tenha sido criada para fazer parte de
  um medley, esta coluna fica marcada como TRUE. Caso
  não, ela fica marcada como FALSE
';



COMMENT ON TABLE song.tb_artista_secundario IS '
  Tabela que armazena os artistas secundários de uma música
';



COMMENT ON TABLE song.tb_parte IS '
  Tabela que armazena o BPM e tonalidade de uma música
  por partes, permitindo que músicas possam ter
  mudanças de BPM e tonalidade em seu decorrer
';



COMMENT ON TABLE song.tb_medley IS '
  Tabela que armazena medleys criados pelos usuários,
  que ficam salvos em seus perfis
';



COMMENT ON TABLE song.tb_medley_ass_musica IS '
  Tabela que armazena a associação entre medleys
  e músicas, de forma a identificar quais músicas
  fazem parte de um medley e em qual posição elas
  estão no medley
';