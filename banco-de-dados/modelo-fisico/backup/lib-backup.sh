function fnCarregarVariaveisAmbiente() {
  echo "Carregando variáveis de ambiente...";
  sleep 3;

  if [ -f "../.env" ]; then
    echo "Variáveis de ambiente encontradas...";
    dos2unix ../.env;
    source "../.env";
    sleep 3;
  else
    echo "Variáveis de ambiente não encontradas!";
    sleep 3;
    return 1;
  fi;

  echo "Variáveis de ambiente carregadas com sucesso!";
  sleep 3;
  clear;
};

function fnInstalarPsql() {
  declare local PSQL17_PATH="/usr/lib/postgresql/17/bin/psql";
  declare local isPsql17Encontrado;

  echo "Verificando se o PSQL 17 está instalado...";
  sleep 3;

  isPsql17Encontrado=$(fnBuscarPsql17 "$PSQL17_PATH");

  if [ -n "$isPsql17Encontrado" ]; then
    echo "O PSQL 17 já está instalado na sua máquina em: $isPsql17Encontrado";
    sleep 3;
  else
    echo "O PSQL 17 não foi encontrado na sua máquina."
    echo "Instalando PSQL 17...";
    sleep 3;

    if ! apt-cache policy postgresql-client-17 | grep -q 'Candidate:'; then
      echo "O PSQL 17 não foi encontrado nos repositórios da sua máquina...";
      
      echo "Adicionando repositório oficial do PSQL 17...";
      sleep 3;
      sudo sh -c "echo 'deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main' > /etc/apt/sources.list.d/pgdg.list";
      wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | \
        gpg --dearmor | sudo tee /etc/apt/trusted.gpg.d/postgresql.gpg > /dev/null;
      
      echo "Atualizando repositórios...";
      sleep 3;
      sudo apt update -y;
    fi;

    echo "Instalando PSQL 17...";
    sleep 3;
    sudo apt install -y postgresql-client-17;

    if [ -f "$PSQL17_PATH" ]; then
      echo "Instalação do PSQL 17 concluída com sucesso!";
      isPsql17Encontrado="$PSQL17_PATH";
      sleep 3;
    else
      echo "Falha durante a instalação do PSQL 17: PSQL 17 não encontrado.";
      sleep 3;
      exit 1;
    fi;

    echo "Definindo PSQL 17 como padrão durante esta sessão...";
    export PATH="/usr/lib/postgresql/17/bin:$PATH";
    sleep 3;
  fi;
};

function fnBuscarPsql17() {
  if [ -f "$1" ]; then
    echo "$1"
  else
    which psql-17 2>/dev/null || which psql17 2>/dev/null || echo ""
  fi;
};