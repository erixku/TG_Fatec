create database IEQParque;
use IEQParque;

create table ieqparque.tb_tipoMembro(
    tpm_id int auto_increment,
    tpm_tipo varchar(20) not null unique,
    tpm_descricao   varchar(50) not null unique,
    primary key(tpm_id)
);

create table ieqparque.tb_membro(
    mem_id int auto_increment unique,
    mem_nome varchar(50) not null,
    mem_telefone varchar(15) not null,
    mem_email varchar(50) not null,
    mem_login varchar(40) not null unique,
    mem_senha varchar(40) not null,
    mem_dataNasc date not null,
    primary key(mem_id),
    tpm_id int not null REFERENCES tb_tipoMembro(tpm_id)
);

alter table tb_membro AUTO_INCREMENT = 10;

create table ieqparque.tb_funcoes(
    fun_id int auto_increment unique,
    fun_nome varchar(50) not null unique,
    fun_descricao varchar(100) not null unique,
    primary key(fun_id)
);

alter table tb_funcoes AUTO_INCREMENT = 200;

create table tb_funcoesMembro(
    fmb_id int AUTO_INCREMENT,
    mem_id int not null REFERENCES tb_membro(mem_id),
    fun_id int not null REFERENCES tb_funcoes(fun_id),
    primary key(fmb_id)
);

alter table tb_funcoesmembro AUTO_INCREMENT = 320;

create table tb_tipoAtividade(
    tat_id int AUTO_INCREMENT PRIMARY KEY,
    tat_nome varchar(50) not null unique,
    tat_descricao varchar(100) not null unique
);

alter table tb_tipoatividade AUTO_INCREMENT = 350;

create table tb_atividade(
    atv_id int AUTO_INCREMENT PRIMARY KEY,
    atv_planejamento int not null,
    atv_data date not null,
    atv_horarioInicio time not null,
    atv_horarioFim time not null,
    tat_id int not null REFERENCES tb_tipoAtividade(tat_id)
);

alter table tb_atividade AUTO_INCREMENT = 2000;

create table tb_ausencia(
    aus_id int AUTO_INCREMENT PRIMARY KEY,
    aus_justificativa text not null,
    mem_id int not null REFERENCES tb_membro(mem_id),
    atv_id int not null REFERENCES tb_atividade(atv_id)
);

alter table tb_ausencia AUTO_INCREMENT = 3000;

create table tb_tonalidade(
    ton_id int AUTO_INCREMENT PRIMARY KEY,
    ton_maiorTom varchar(3) not null,
    ton_menorTom varchar(3) not null
);

alter table tb_tonalidade AUTO_INCREMENT = 375;

create table tb_musica(
    mus_id int AUTO_INCREMENT PRIMARY KEY,
    mus_nome varchar(50) not null,
    mus_interprete varchar(60) not null,
    mus_capa varchar(100) not null,
    mus_link varchar(100) not null,
    mus_bpm int not null,
    mus_descricao text not null,
    ton_id int not null REFERENCES tb_tonalidade(ton_id)
);

alter table tb_musica AUTO_INCREMENT = 4000;

create table tb_tipoTrechoMusica(
    ttm_id int primary key auto_increment,
    ttm_tipo varchar(20) not null,
    ttm_descricao text not null
);

alter table tb_tipotrechomusica AUTO_INCREMENT = 400;

create table tb_trechosMusica(
    trm_id int primary key auto_increment,
    ttm_id int REFERENCEs tb_tipoTrechoMusica(ttm_id),
    mus_id int REFERENCES tb_musica(mus_id)
);

alter table tb_trechosmusica AUTO_INCREMENT = 500;

create table tb_tomTrechoMusica(
    tdm_id int primary key auto_increment,
    trm_id int REFERENCES tb_trechosMusica(trm_int),
    ton_id int REFERENCES tb_tonalidade(ton_id)
);

alter table tb_tomtrechomusica AUTO_INCREMENT = 600;

create table tb_medley(
    med_id int primary key auto_increment,
    med_nome varchar(70) not null,
    med_capa varchar(100) not null,
    med_link varchar(100) not null,
    med_descricao text
);

alter table tb_medley AUTO_INCREMENT = 5000;

create table tb_trechosMedley(
    tmd_id int primary key auto_increment,
    med_id int REFERENCES tb_medley(med_id),
    mus_id int REFERENCES tb_musica(mus_id),
    ton_id int REFERENCES tb_tonalidade(ton_id)
);

alter table tb_trechosmedley AUTO_INCREMENT = 700;
