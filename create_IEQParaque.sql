create database IEQParque;
use IEQParque;

create table ieqparque.tbTipoMembro(
    tpm_cod int auto_increment,
    tpm_tipo varchar(20) not null unique,
    tpm_descricao   varchar(50) not null unique,
    primary key(tpm_cod)
);

create table ieqparque.tbMembro(
    mem_id int auto_increment unique,
    mem_nome varchar(50) not null,
    mem_telefone varchar(15) not null,
    mem_email varchar(50) not null,
    mem_login varchar(40) not null unique,
    mem_senha varchar(40) not null,
    mem_dataNasc date not null,
    primary key(mem_id),
    tpm_cod int not null REFERENCES tbTipoMembro(tpm_cod)
);

create table ieqparque.tbFuncoes(
    fun_cod int auto_increment unique,
    fun_nome varchar(50) not null unique,
    fun_descricao varchar(100) not null unique,
    primary key(fun_cod)
);

create table tbFuncoesMembro(
    fmb_id int AUTO_INCREMENT,
    mem_id int not null REFERENCES tbMembro(mem_id),
    fun_cod int not null REFERENCES tbFuncoes(fun_cod),
    primary key(fmb_id)
);

create table tbTipoAtividade(
    tat_id int AUTO_INCREMENT PRIMARY KEY,
    tat_nome varchar(50) not null unique,
    tat_descricao varchar(100) not null unique
);

create table tbAtividade(
    atv_id int AUTO_INCREMENT PRIMARY KEY,
    atv_planejamento int not null,
    atv_data date not null,
    atv_horarioInicio time not null,
    atv_horarioFim time not null,
    tat_id int not null REFERENCES tbTipoAtividade(tat_id)
);

create table tbAusencia(
    aus_id int AUTO_INCREMENT PRIMARY KEY,
    aus_justificativa text not null,
    mem_id int not null REFERENCES tbMembro(mem_id),
    atv_id int not null REFERENCES tbAtividade(atv_id)
);

create table tbTonalidade(
    ton_id int AUTO_INCREMENT PRIMARY KEY,
    ton_maiorTom varchar(3) not null,
    ton_menorTom varchar(3) not null
);

create table tbMusica(
    mus_id int AUTO_INCREMENT PRIMARY KEY,
    mus_nome varchar(50) not null,
    mus_interprete varchar(60) not null,
    mus_capa varchar(100) not null,
    mus_link varchar(100) not null,
    mus_bpm int not null,
    mus_descricao text not null,
    ton_id int not null REFERENCES tbTonalidade(ton_id)
);
