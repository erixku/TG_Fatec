#!/bin/bash
set -euo pipefail
source "./lib-backup.sh"



function fnMain() {
  declare local opcaoAmbiente;

  clear;
  fnCarregarVariaveisAmbiente
  fnInstalarPsql

  while true; do
    clear;
    echo "Restaurador de Backup - Sistema Harppia";
    echo "";
    echo "Selecione o ambiente onde o backup completo será restaurado:";
    echo "1) DEV";
    echo "2) QA";
    echo "3) PROD";
    echo "4) Encerrar programa";
    echo "";

    read -p "R: " opcaoAmbiente;

    case $opcaoAmbiente in
      1) fnRestaurarBackup "DEV"  "$DB_DEV_HOST"  "$DB_DEV_PORT"  "$DB_DEV_NAME"  "$DB_DEV_USER"  "$DB_DEV_PASSWORD";;
      2) fnRestaurarBackup "QA"   "$DB_QA_HOST"   "$DB_QA_PORT"   "$DB_QA_NAME"   "$DB_QA_USER"   "$DB_QA_PASSWORD";;
      3) fnRestaurarBackup "PROD" "$DB_PROD_HOST" "$DB_PROD_PORT" "$DB_PROD_NAME" "$DB_PROD_USER" "$DB_PROD_PASSWORD";;
      4) echo "Encerrando..."; sleep 3; exit 0;;

      *)
        echo "Valor inválido!";
        read -p "Pressione ENTER para tentar novamente...";
      ;;
    esac;
  done;
};



function fnRestaurarBackup() {
  declare local DB_AMBIENTE="$1";
  declare local DB_HOST="$2";
  declare local DB_PORT="$3";
  declare local DB_NAME="$4";
  declare local DB_USER="$5";
  declare local DB_PASSWORD="$6";

  declare local NULLGLOB_ANTIGO;
  declare local BACKUP_ARQUIVOS;
  declare local opcaoArquivo;
  declare local i;
  declare local arquivoEscolhido;

  # usa IF/ELSE para verificar e armazenar o estado antigo de forma
  # a evitar más interpretações de erro pelo set -euo pipefail
  if shopt -q nullglob; then
    NULLGLOB_ANTIGO=0;
  else
    NULLGLOB_ANTIGO=1;
    shopt -s nullglob;
  fi;

  BACKUP_ARQUIVOS=(./gerados/*.{dump,bak,sql});
  if [ ${#BACKUP_ARQUIVOS[@]} -eq 0 ]; then
    echo "Não há arquivos válidos de backup na pasta ./gerados";
    sleep 3;
    return 0;
  fi;

  while true; do
    clear;
    echo "Selecione o arquivo que deseja utilizar para a restauração:";
    echo "0) Cancelar restauração";

    i=1;
    for arquivo in "${BACKUP_ARQUIVOS[@]}"; do
      echo "$i) $(basename "$arquivo")";
      ((i++));
    done;

    read -p "R: " opcaoArquivo;

    if ! [[ "$opcaoArquivo" =~ ^[0-9]+$ ]]; then
      echo "Valor inválido! Digite apenas números.";
      sleep 3;
      continue;
    fi;

    if
      [ "$opcaoArquivo" -lt 0 ] ||
      [ "$opcaoArquivo" -gt "${#BACKUP_ARQUIVOS[@]}" ];
    then
      echo "Opção fora do intervalo!";
      sleep 3;
      continue;
    fi;

    if [ "$opcaoArquivo" -eq 0 ]; then
      echo "Cancelando restauração...";
      sleep 3;
      return 0;
    fi;

    arquivoEscolhido="${BACKUP_ARQUIVOS[$((opcaoArquivo - 1))]}";
    break;
  done;

  echo "";
  read -p "Tem certeza que deseja restaurar o backup '$arquivoEscolhido' no ambiente de '$DB_AMBIENTE'? (s/N): " confirmacao;
  if [[ ! "$confirmacao" =~ ^[sS]$ ]]; then
    echo "Cancelando operação...";
    sleep 3;
    return 0;
  fi;

  echo "";
  echo "Restaurando backup com o arquivo $arquivoEscolhido no ambiente de $DB_AMBIENTE...";
  sleep 3;

  if [[ "$arquivoEscolhido" == *.sql ]]; then
    PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d postgres <<EOSQL
      DROP DATABASE IF EXISTS "$DB_NAME";
      CREATE DATABASE "$DB_NAME";
      \c "$DB_NAME"
      \i '$arquivoEscolhido'
EOSQL
  else
    PGPASSWORD="$DB_PASSWORD" pg_restore -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -F c --clean "$arquivoEscolhido";
  fi;

  if [ $? -ne 0 ]; then
    echo "Erro ao restaurar o backup!";
    sleep 3;
    return 1;
  fi;

  if [[ $NULLGLOB_ANTIGO -eq 1 ]]; then
    shopt -u nullglob;
  fi;

  echo "Restauração concluída com sucesso!";
  sleep 3;
};



fnMain