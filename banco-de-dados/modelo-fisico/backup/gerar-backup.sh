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
    echo "Gerador de Backup - Sistema Harppia";
    echo "";
    echo "Selecione o ambiente onde o backup completo será executado:";
    echo "1) DEV";
    echo "2) QA";
    echo "3) PROD";
    echo "4) Encerrar programa";
    echo "";

    read -p "R: " opcaoAmbiente;

    case $opcaoAmbiente in
      1) fnGerarBackup "DEV"  "$DB_DEV_HOST"  "$DB_DEV_PORT"  "$DB_DEV_NAME"  "$DB_DEV_USER"  "$DB_DEV_PASSWORD";;
      2) fnGerarBackup "QA"   "$DB_QA_HOST"   "$DB_QA_PORT"   "$DB_QA_NAME"   "$DB_QA_USER"   "$DB_QA_PASSWORD";;
      3) fnGerarBackup "PROD" "$DB_PROD_HOST" "$DB_PROD_PORT" "$DB_PROD_NAME" "$DB_PROD_USER" "$DB_PROD_PASSWORD";;
      4) echo "Encerrando..."; sleep 3; exit 0;;

      *)
        echo "Valor inválido!";
        read -p "Pressione ENTER para tentar novamente...";
      ;;
    esac;
  done;
};



function fnGerarBackup() {
  declare local DB_AMBIENTE="$1";
  declare local DB_HOST="$2";
  declare local DB_PORT="$3";
  declare local DB_NAME="$4";
  declare local DB_USER="$5";
  declare local DB_PASSWORD="$6";

  declare local TIMESTAMP=$(date +"%Y%m%d%H%M%S");
  declare local BACKUP_SQL="./gerados/${DB_AMBIENTE}_${DB_NAME}_${TIMESTAMP}.sql";
  declare local BACKUP_DUMP="./gerados/${DB_AMBIENTE}_${DB_NAME}_${TIMESTAMP}.dump";

  echo "";
  echo "Gerando backup em texto para $BACKUP_SQL...";
  sleep 3;
  PGPASSWORD="$DB_PASSWORD" pg_dump "postgresql://$DB_USER:$DB_PASSWORD@$DB_HOST:$DB_PORT/$DB_NAME?sslmode=prefer&connect_timeout=10" -F p -f "$BACKUP_SQL";

  echo "Gerando backup em binário para $BACKUP_DUMP...";
  sleep 3;
  PGPASSWORD="$DB_PASSWORD" pg_dump "postgresql://$DB_USER:$DB_PASSWORD@$DB_HOST:$DB_PORT/$DB_NAME?sslmode=prefer&connect_timeout=10" -F c -f "$BACKUP_DUMP";

  echo "Backups gerados com sucesso!";
  sleep 3;
};



fnMain
