use ieqparque;

-- View que retorna todos os membros que possuem uma função e um tipo determinado
create view vw_funcoesMembro as
select f.*, m.*, tm.tpm_tipo, tm.tpm_descricao 
from tb_funcoes f left join tb_funcoesmembro fm on f.fun_id = fm.fun_id
right join tb_membro m on m.mem_id = fm.mem_id
inner join tb_tipomembro tm on tm.tpm_id = m.tpm_id;

-- View que retorna a ausência de um membro à alguma atividade da igreja
create view vw_ausenciaMembro as
select au.aus_id, m.mem_nome, ta.tat_nome, a.atv_horarioInicio, a.atv_horarioFim, au.aus_justificativa
from tb_tipoatividade ta inner join tb_atividade a on ta.tat_id = a.tat_id
inner join tb_ausencia au on a.atv_id = au.atv_id
inner join tb_membro m on m.mem_id = au.mem_id
order by m.mem_nome;

create view vw_musicaTonalidade as 
select m.mus_id, m.mus_nome, m.mus_descricao, t.ton_maiorTom, t.ton_menorTom
from tb_musica m inner join tb_tonalidade t on m.ton_id = t.ton_id;

create view vw_musicasMedley as
select md.med_nome, m.mus_nome, t.ton_maiorTom, t.ton_menorTom
from tb_medley md join tb_trechosmedley tm on md.med_id = tm.med_id
join tb_musica m on m.mus_id = tm.mus_id
join tb_tonalidade t on m.ton_id = t.ton_id

create view vw_musicas_trechos as
select m.mus_nome, tt.ttm_tipo
from tb_musica m
join tb_trechosMusica tm on m.mus_id = tm.mus_id
join tb_tipoTrechoMusica tt on tm.ttm_id = tt.ttm_id;
