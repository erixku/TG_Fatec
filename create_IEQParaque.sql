create database IEQParque;
use IEQParque;

create table tbTipoMembro(
    tpm_cod int primary key,
    tpm_tipo varchar(20) not null unique,
    tpm_descricao   varchar(50) not null unique
);

create table tbMembro(
    mem_id int primary key unique,
    mem_nome varchar(50) not null,
    mem_telefone varchar(15) not null,
    mem_email varchar(50) not null,
    mem_login varchar(40) not null unique,
    mem_senha varchar(40) not null,
    mem_dataNasc date not null,

    tpm_cod int not null REFERENCES tbTipoMembro(tpm_cod)
);

create table tbFuncoes(
    fun_cod int primary key unique,
    fun_nome varchar(50) not null unique,
    fun_descricao varchar(100) not null unique
);

create table tbFuncoesMembro(
    fmb_id int primary key,
    mem_id int not null REFERENCES tbMembro(mem_id),
    fun_cod int not null REFERENCES tbFuncoes(fun_cod)
);
