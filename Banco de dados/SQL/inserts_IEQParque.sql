use ieqparque;

insert into tb_tonalidade(ton_maiorTom, ton_menorTom) 
values('C', 'Am'),
      ('C#', 'A#m'),
      ('D', 'Bm'),
      ('D#', 'Cm'),
      ('E', 'C#m'),
      ('F', 'Dm'),
      ('F#', 'D#m'),
      ('G', 'Em'),
      ('G#', 'Fm'),
      ('A', 'F#m'),
      ('A#', 'Gm'),
      ('B', 'G#m');     

INSERT INTO tb_tipoMembro (tpm_tipo, tpm_descricao) VALUES
('Líder', 'Membro que lidera um grupo ou ministério'),
('Membro', 'Membro regular da igreja'),
('Visitante', 'Pessoa que visita a igreja');

INSERT INTO tb_membro (mem_nome, mem_telefone, mem_email, mem_login, mem_senha, mem_dataNasc, tpm_id) VALUES
('João Silva', '11987654321', 'joao.silva@email.com', 'joaosilva', 'senha123', '1985-05-15', 1),
('Maria Oliveira', '11912345678', 'maria.oliveira@email.com', 'mariaoliveira', 'senha456', '1990-08-20', 2),
('Carlos Pereira', '11998765432', 'carlos.pereira@email.com', 'carlospereira', 'senha789', '2000-12-30', 3);

INSERT INTO tb_funcoes (fun_nome, fun_descricao) VALUES
('Líder de Louvor', 'Responsável por coordenar o ministério de louvor e dirigir os ensaios'),
('Vocalista', 'Membro que canta em grupo ou solo durante os cultos e eventos'),
('Instrumentista', 'Músico que toca instrumentos durante os louvores'),
('Compositor', 'Membro que cria músicas originais para o ministério de louvor'),
('Técnico de Som', 'Responsável pela parte técnica de som e iluminação durante os cultos');

INSERT INTO tb_funcoesMembro (mem_id, fun_id) VALUES
(10, 200),  -- João Silva
(10, 201),  -- João Silva
(11, 202),  -- Maria Oliveira
(12, 204);  -- Carlos Pereira

INSERT INTO tb_tipoAtividade (tat_nome, tat_descricao) VALUES
('Culto de Louvor', 'Reunião semanal dedicada à adoração e louvor a Deus'),
('Ensaios de Louvor', 'Reuniões regulares para ensaiar músicas e preparar o ministério'),
('Apresentação Especial', 'Eventos especiais onde o ministério de louvor se apresenta'),
('Retiro Espiritual', 'Encontro para aprofundamento espiritual e musical do ministério'),
('Workshop de Música', 'Oficinas para aprimoramento de habilidades musicais e vocais');

INSERT INTO tb_atividade (atv_planejamento, atv_data, atv_horarioInicio, atv_horarioFim, tat_id) VALUES
(1, '2023-10-01', '18:00:00', '20:00:00', 350),
(2, '2023-10-03', '19:00:00', '21:00:00', 351),
(3, '2023-10-15', '19:00:00', '20:30:00', 352),
(4, '2023-11-05', '09:00:00', '17:00:00', 353),
(5, '2023-11-12', '14:00:00', '16:00:00', 354),
(6, '2023-10-08', '18:00:00', '20:00:00', 350),
(7, '2023-10-10', '19:00:00', '21:00:00', 351),
(8, '2023-10-22', '19:00:00', '20:30:00', 352);

INSERT INTO tb_ausencia (aus_justificativa, mem_id, atv_id) VALUES
('Doença', 10, 1),  -- João Silva, Culto de Louvor
('Viagem', 11, 2),  -- Maria Oliveira, Ensaios de Louvor
('Feriado', 12, 3), -- Carlos Pereira, Apresentação Especial
('Compromisso pessoal', 10, 4), -- João Silva, Retiro Espiritual
('Doença', 11, 5),  -- Maria Oliveira, Workshop de Música
('Viagem', 10, 6),  -- João Silva, Culto de Louvor
('Feriado', 12, 7);  -- Carlos Pereira, Ensaios de Louvor

INSERT INTO tb_musica (mus_nome, mus_interprete, mus_capa, mus_link, mus_bpm, mus_descricao, ton_id) VALUES
('Santo, Santo, Santo', 'Reginaldo Veloso', 'capa_santo_santo_santo.jpg', 'https://link-para-musica.com/santo_santo_santo', 75, 'Uma canção de adoração que exalta a santidade de Deus.', 375),  -- C
('Te Louvarei', 'Davi Sacer', 'capa_te_louvarei.jpg', 'https://link-para-musica.com/te_louvarei', 80, 'Uma música que expressa gratidão e louvor a Deus.', 376),                -- C#
('Apenas um Toque', 'Anderson Freire', 'capa_apenas_um_toque.jpg', 'https://link-para-musica.com/apenas_um_toque', 85, 'Uma canção que fala sobre o poder do toque de Deus.', 377),      -- D
('Eu Sou Teu', 'Leonardo Gonçalves', 'capa_eu_sou_teu.jpg', 'https://link-para-musica.com/eu_sou_teu', 90, 'Uma declaração de entrega total a Deus.', 378),         -- D#
('Ousado Amor', 'Hillsong', 'capa_ousado_amor.jpg', 'https://link-para-musica.com/ousado_amor', 70, 'Uma canção que fala sobre o amor incondicional de Deus.', 379),                  -- E
('Como é Bom Te Adorar', 'Ana Paula Valadão', 'capa_como_e_bom_te_adorar.jpg', 'https://link-para-musica.com/como_e_bom_te_adorar', 78, 'Uma música que celebra a alegria de adorar a Deus.', 380); -- F

INSERT INTO tb_tipoTrechoMusica (ttm_tipo, ttm_descricao) VALUES
('Verso', 'Parte da música que apresenta a história ou mensagem.'),
('Refrão', 'Parte repetitiva e cativante da música.'),
('Ponte', 'Parte que conecta diferentes seções da música.'),
('Intro', 'Parte inicial da música que prepara o ouvinte.'),
('Final', 'Parte que encerra a música.');

INSERT INTO tb_trechosMusica (trm_id, ttm_id, mus_id) VALUES
(500, 400, 4000),  -- Verso da música "Santo, Santo, Santo"
(501, 401, 4000),  -- Refrão da música "Santo, Santo, Santo"
(502, 400, 4001),  -- Verso da música "Te Louvarei"
(503, 401, 4001),  -- Refrão da música "Te Lou varei"
(504, 400, 4002),  -- Verso da música "Apenas um Toque"
(505, 401, 4002),  -- Refrão da música "Apenas um Toque"
(506, 400, 4003),  -- Verso da música "Eu Sou Teu"
(507, 401, 4003),  -- Refrão da música "Eu Sou Teu"
(508, 400, 4004),  -- Verso da música "Ousado Amor"
(509, 401, 4004),  -- Refrão da música "Ousado Amor"
(510, 400, 4005),  -- Verso da música "Como é Bom Te Adorar"
(511, 401, 4005);  -- Refrão da música "Como é Bom Te Adorar"

INSERT INTO tb_tomTrechoMusica (trm_id, ton_id) VALUES
(500, 375),  -- Tom do verso da música "Santo, Santo, Santo" (C)
(501, 375),  -- Tom do refrão da música "Santo, Santo, Santo" (C)
(502, 376),  -- Tom do verso da música "Te Louvarei" (C#)
(503, 376),  -- Tom do refrão da música "Te Louvarei" (C#)
(504, 377),  -- Tom do verso da música "Apenas um Toque" (D)
(505, 377),  -- Tom do refrão da música "Apenas um Toque" (D)
(506, 378),  -- Tom do verso da música "Eu Sou Teu" (D#)
(507, 378),  -- Tom do refrão da música "Eu Sou Teu" (D#)
(508, 379),  -- Tom do verso da música "Ousado Amor" (E)
(509, 379),  -- Tom do refrão da música "Ousado Amor" (E)
(510, 380),  -- Tom do verso da música "Como é Bom Te Adorar" (F)
(511, 380);  -- Tom do refrão da música "Como é Bom Te Adorar" (F)

INSERT INTO tb_medley (med_nome, med_capa, med_link, med_descricao) VALUES
('Medley de Adoração', 'capa_medley_adoracao.jpg', 'https://link-para-medley.com/medley_adoracao', 'Um medley que reúne canções de adoração.');

INSERT INTO tb_trechosMedley (med_id, mus_id, ton_id) VALUES
(7000, 4000, 375),  -- "Santo, Santo, Santo" no Medley de Adoração (C)
(7000, 4001, 376),  -- "Te Louvarei" no Medley de Adoração (C#)
(7000, 4002, 377);  -- "Apenas um Toque" no Medley de Adoração (D)      