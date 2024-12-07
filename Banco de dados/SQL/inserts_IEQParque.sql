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

INSERT INTO tb_funcoesmembro(mem_id, fun_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 5);

INSERT INTO tb_tipoAtividade (tat_nome, tat_descricao) VALUES
('Culto de Louvor', 'Reunião semanal dedicada à adoração e louvor a Deus'),
('Ensaios de Louvor', 'Reuniões regulares para ensaiar músicas e preparar o ministério'),
('Apresentação Especial', 'Eventos especiais onde o ministério de louvor se apresenta'),
('Retiro Espiritual', 'Encontro para aprofundamento espiritual e musical do ministério'),
('Workshop de Música', 'Oficinas para aprimoramento de habilidades musicais e vocais');

INSERT INTO tb_atividade (atv_planejamento, atv_data, atv_horarioInicio, atv_horarioFim, tat_id) VALUES
(1, '2023-10-01', '18:00:00', '20:00:00', 1),
(2, '2023-10-03', '19:00:00', '21:00:00', 2),
(3, '2023-10-15', '19:00:00', '20:30:00', 3),
(4, '2023-11-05', '09:00:00', '17:00:00', 4),
(5, '2023-11-12', '14:00:00', '16:00:00', 5),
(6, '2023-10-08', '18:00:00', '20:00:00', 1),
(7, '2023-10-10', '19:00:00', '21:00:00', 2),
(8, '2023-10-22', '19:00:00', '20:30:00', 3);

INSERT INTO tb_ausencia (aus_justificativa, mem_id, atv_id) VALUES
('Doença', 1, 1),
('Viagem', 2, 2),
('Feriado', 3, 3),
('Compromisso pessoal', 1, 4),
('Doença', 2, 5),
('Viagem', 3, 6),
('Feriado', 1, 7);

INSERT INTO tb_musica (mus_nome, mus_interprete, mus_capa, mus_link, mus_bpm, mus_descricao, ton_id) VALUES
('Santo, Santo, Santo', 'Reginaldo Veloso', 'capa_santo_santo_santo.jpg', 'https://link-para-musica.com/santo_santo_santo', 75, 'Uma canção de adoração que exalta a santidade de Deus.', 1),  -- C
('Te Louvarei', 'Davi Sacer', 'capa_te_louvarei.jpg', 'https://link-para-musica.com/te_louvarei', 80, 'Uma música que expressa gratidão e louvor a Deus.', 2),                -- C#
('Apenas um Toque', 'Anderson Freire', 'capa_apenas_um_toque.jpg', 'https://link-para-musica.com/apenas_um_toque', 85, 'Uma canção que fala sobre o poder do toque de Deus.', 3),      -- D
('Eu Sou Teu', 'Leonardo Gonçalves', 'capa_eu_sou_teu.jpg', 'https://link-para-musica.com/eu_sou_teu', 90, 'Uma declaração de entrega total a Deus.', 4),         -- D#
('Ousado Amor', 'Hillsong', 'capa_ousado_amor.jpg', 'https://link-para-musica.com/ousado_amor', 70, 'Uma canção que fala sobre o amor incondicional de Deus.', 5),                  -- E
('Como é Bom Te Adorar', 'Ana Paula Valadão', 'capa_como_e_bom_te_adorar.jpg', 'https://link-para-musica.com/como_e_bom_te_adorar', 78, 'Uma música que celebra a alegria de adorar a Deus.', 6); -- F

INSERT INTO tb_tipoTrechoMusica (ttm_tipo, ttm_descricao) VALUES
('Verso', 'Parte da música que apresenta a história ou mensagem.'),
('Refrão', 'Parte repetitiva e cativante da música.'),
('Ponte', 'Parte que conecta diferentes seções da música.'),
('Intro', 'Parte inicial da música que prepara o ouvinte.'),
('Final', 'Parte que encerra a música.');

INSERT INTO tb_trechosMusica (ttm_id, mus_id) VALUES
(1, 1),
(2, 1),
(1, 2),
(2, 2),
(1, 3),
(2, 3),
(1, 4),
(2, 4),
(1, 5),
(2, 5),
(1, 6),
(2, 6);

INSERT INTO tb_tomTrechoMusica (trm_id, ton_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 3),
(6, 3),
(7, 4),
(8, 4),
(9, 5),
(10, 5),
(11, 6),
(12, 6);

INSERT INTO tb_medley (med_nome, med_capa, med_link, med_descricao) VALUES
('Medley de Adoração', 'capa_medley_adoracao.jpg', 'https://link-para-medley.com/medley_adoracao', 'Um medley que reúne canções de adoração.');

INSERT INTO tb_trechosMedley (med_id, mus_id, ton_id) VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 3);