# Projeto de Banco de Dados - Sistema Harppia



## Sobre Este Arquivo

Este documento descreve a arquitetura, estrutura lógica e física, convenções de desenvolvimento e práticas operacionais do banco de dados do sistema Harppia.



## Sumário
- [Sobre Este Arquivo](#sobre-este-arquivo)
- [Ficha Técnica do Banco de Dados](#ficha-técnica-do-banco-de-dados)
- [Domínios de Negócio](#domínios-de-negócio)
- [Estrutura de Diretórios do Projeto](#estrutura-de-diretórios-do-projeto)
- [Ambientes](#ambientes)
- [Integrações Externas](#integrações-externas)
- [Acesso e Uso](#acesso-e-uso)
- [Controle de Acesso](#controle-de-acesso)
- [ROLEs](#roles)
  - [ROLE SUPERUSER](#role-superuser)
  - [ROLEs sem LOGIN habilitado](#roles-sem-login-habilitado)
  - [ROLEs com LOGIN habilitado](#roles-com-login-habilitado)
- [Versionamento](#versionamento)
- [Observabilidade](#observabilidade)
- [Política de Backup](#política-de-backup)
  - [Scripts de Backup](#scripts-de-backup)
  - [Arquivamento e Versionamento](#arquivamento-e-versionamento)
  - [Retenção de Backups](#retenção-de-backups)
  - [Considerações de Evolução](#considerações-de-evolução)
- [Convenções do Projeto](#convenções-do-projeto)
  - [Criação de Objetos em Geral](#criação-de-objetos-em-geral)
  - [Organização de Arquivos](#organização-de-arquivos)
  - [Ordem de Colunas na Criação de Tabelas](#ordem-de-colunas-na-criação-de-tabelas)
  - [Nomenclaturas de Objetos](#nomenclaturas-de-objetos)
    - [Princípios Gerais](#princípios-gerais)
    - [Nomenclaturas de Colunas de FKs](#nomenclaturas-de-colunas-de-fks)
    - [Nomenclaturas de CONSTRAINTs Nomeáveis](#nomenclaturas-de-constraints-nomeáveis)
    - [Nomenclaturas de Domínios, Enumerações, Funções e Procedimentos](#nomenclaturas-de-domínios-enumerações-funções-e-procedimentos)
    - [Nomenclaturas de Triggers](#nomenclaturas-de-triggers)
- [Equipe Responsável](#equipe-responsável)



## Ficha Técnica do Banco de Dados

- **SGBD**: PostgreSQL;

- **Versão**: 17;

- **Nome**: neondb.



## Domínios de Negócio
 
O projeto foi organizado em sete domínios, sendo que cada domínio corresponde a um schema específico no banco de dados. A seguir, são apresentadas as descrições de cada um deles:

- **Storage**: responsável pela gestão de arquivos do sistema;

- **Auth**: responsável pela gestão de usuários do sistema;

- **Accessibility**: responsável pela gestão das configurações de acessibilidade dos usuários;

- **Notification**: responsável pela gestão das configurações de notificações dos usuários;

- **Song**: responsável pela gestão das músicas e medleys criadas pelos usuários, que podem ser vinculados por ministros a igrejas;

- **Church**: responsável pela gestão de igrejas, ministérios de louvor, membros e instrumentos do sistema;

- **Schedule**: responsável pela gestão e publicação de avisos, agendamentos e consagrações do sistema.

Mais informações sobre esses domínios de negócio e schemas podem ser encontradas nos scripts `comments.sql`, presentes em cada diretório de schema.

 
 
## Estrutura de Diretórios do Projeto
 
Os diretórios do projeto foram divididos visando separação de responsabilidades. Abaixo, seguem as pastas do projeto e suas atribuições:

- 📁 **docs/**
  - 📁 **1-modelo-conceitual/** — documentação do DER  
  - 📁 **2-modelo-logico/** — documentações de MER  
  - 📁 **3-tabelas-instancias/** — exemplos de inserções de dados  
  - 📁 **4-tabelas-permissoes/** — permissões e ROLEs do sistema  
  - 📁 **5-dicionario-dados/** — dicionário de dados  

- 📁 **modelo-fisico/**
  - 📁 **backup/** — scripts de geração e restauração de backup  
    - 📁 **gerados/** — arquivos .bak. sql e .dump de backups para restore  
    - 📄 **gerar-backup.sh** - ferramenta CLI de backup manual  
    - 📄 **lib-backup.sh** - pequena lib para as ferramentas de backup  
    - 📄 **restaurar-backup.sh** - ferramenta CLI de backup restore  
  - 📁 **dcl/** — scripts de ROLEs, GRANTs globais e REVOKE all  
  - 📁 **schemas/** — código-fonte de cada schema  
    - 📁 **algum-schema/** - diretório de algum schema  
      - 📁 **dcl/** - configurações de permissionamento  
        - 📁 **algum-role/** - diretório de algum ROLE  
          - 📄 **policies.sql** - policies associadas ao ROLE  
          - 📄 **rotinas.sql** - grants do ROLE em rotinas  
          - 📄 **tabelas.sql** - grants do ROLE em tabelas e schemas  
      - 📁 **funcoes-default/** - funções usadas em DEFAULT de colunas  
      - 📁 **funcoes-validacoes/** - funções usadas em CKs  
      - 📄 **checks.sql** - validações de dados  
      - 📄 **comments.sql** - documentação do schema  
      - 📄 **ddl.sql** - criação do schema e de suas tabelas  
      - 📄 **dml.sql** - inserção de dados padrões do schema  
      - 📄 **enums.sql** - criação de enumerações usadas no schema  
      - 📄 **excludes.sql** - criação de índices excludes do schema  
      - 📄 **indexes.sql** - criação de índices do schema  
  - 📁 **versoes-estaveis/** — snapshots estáveis do banco  
  - 📄 **.env** — configuração de variáveis de ambiente  
  - 📄 **.env.example** — exemplo de configuração de variáveis de ambiente  
  - 📄 **ddl.sql** — criação do banco  
  - 📄 **extensoes.sql** — extensões PostgreSQL utilizadas  
  - 📄 **variaveis-sessao.sql** — rotinas de SET e GET de variáveis de sessão  

- 📄 **.gitignore** - arquivo .gitignore do projeto  
- 📄 **README.md** - é o arquivo que você está lendo

*Observação: alguns schemas podem omitir determinados arquivos ou diretórios, conforme suas necessidades funcionais. Entretanto, os arquivos de definição principal (`ddl.sql`, `checks.sql`, `comments.sql` e diretórios `dcl/`) são obrigatórios*.



## Ambientes
 
Todos os ambientes estão hospedados na plataforma Serverless [Neon](https://neon.com/), dentro de um único projeto, e são organizados em três branches: **DEV**, **QA** e **PROD**. O projeto não possui ambiente local. A seguir, são apresentadas as descrições de cada ambiente:
 
- **DEV**: ambiente destinado ao desenvolvimento contínuo do banco de dados, podendo apresentar instabilidades frequentes;
 
- **QA**: ambiente destinado a testes de qualidade de software e homologações, com expectativa de estabilidade;
 
- **PROD**: ambiente destinado ao uso do sistema pelos clientes, devendo ser estável.
 
O fluxo de deploy segue a sequência: DEV -> QA -> PROD.



## Integrações Externas
 
O banco de dados comunica-se exclusivamente com a API utilizada pelo sistema Harppia. A interação entre banco e API é mediada pelo **Hibernate**.



## Acesso e Uso
 
Para acessar o banco de dados, deve-se seguir o seguinte procedimento:

- Realizar o credenciamento na plataforma Neon utilizando a conta associada ao projeto;

- Selecionar a branch correspondente ao ambiente desejado: DEV, QA ou PROD;

- Utilizar o banco de dados diretamente na plataforma Neon ou copiar as configurações de conexão para comunicação via APIs ou clientes PostgreSQL.


 
## Controle de Acesso
 
O sistema implementa, predominantemente, Role-Based Access Control (RBAC), aplicando regras restritas de acesso de acordo com o perfil do usuário.

Adicionalmente, são utilizadas outras estratégias de controle de acesso:

- Discretionary Access Control (DAC): empregado para propriedades específicas, como músicas e medleys;

- Attribute-Based Access Control (ABAC): aplicado em determinados contextos, como validações de horários de eventos, com o apoio de **Row-Level Security** (RLS) e **policies**.


 
## ROLEs

As ROLEs do sistema Harppia foram definidas de forma hierárquica, com base em princípios de **least privilege** e segregação de funções. A seguir, estão descritas suas categorias e responsabilidades.

### ROLE SUPERUSER

A `ROLE SUPERUSER` do banco de dados é a **neondb_owner**.

### ROLEs sem LOGIN habilitado

Abaixo, constam ROLEs que não possuem permissão de `LOGIN` configurada, mas que servem como base de permissões para ROLEs autenticáveis. São elas:
 
- **r_anonimo**: utilizada por usuários não autenticados para que possam realizar cadastro e login;

- **r_usuario**: utilizada por usuários autenticados para realizar tarefas que não envolvam igrejas (configurações da aplicação, edição de perfil etc.);

- **r_levita**: utilizada por usuários autenticados que possuem perfil de levita em suas igrejas;

- **r_ministro**: utilizada por usuários autenticados que possuem perfil de ministro em suas igrejas;

- **r_lider**: utilizada por usuários autenticados que possuem perfil de líder em suas igrejas;

- **r_administrador**: utilizada por usuários autenticados que possuem perfil de administrador em suas igrejas;

- **r_sistema**: utilizada pelo sistema em rotinas `SECURITY DEFINER`, em backups e em manutenções automáticas agendadas.

### ROLEs com LOGIN habilitado
 
Abaixo, constam ROLEs com permissão de `LOGIN` configurada, que não possuem permissões próprias, mas que assumem papéis de ROLEs não autenticáveis. São elas:

- **r_api**: utilizada pela API do sistema Harppia para interação com o banco de dados. Possui permissão, com `NOINHERIT`, para assumir as ROLEs do sistema (`r_anonimo`, `r_usuario`, `r_levita`, `r_ministro`, `r_lider` e `r_administrador`), conforme o perfil do usuário autenticado.


 
## Versionamento
 
O código-fonte do banco de dados deve ser versionado no GitHub, de modo que o repositório reflita sempre o estado atual do banco de dados implantado.

Atualmente, o projeto não utiliza ferramentas de seed ou migration, considerando que ainda está em fase de desenvolvimento ativo e sofre alterações estruturais frequentes. Entretanto, quando o modelo estiver mais consolidado, será adotada a ferramenta **Flyway** para gerenciamento automatizado de versões e migrações.


 
## Observabilidade
 
Todos os logs e métricas para a realização de auditorias e garantia de observabilidade estão disponíveis nos painéis da plataforma Neon.



## Política de Backup

Considerando que o banco de dados ainda possui baixo volume de dados, os backups completos são realizados semanalmente, às quintas-feiras, às 00:00, de forma manual, utilizando scripts previamente preparados.

### Scripts de Backup

Os scripts estão localizados no diretório modelo-fisico/backup/ do repositório:

- **Geração de backup**: `gerar-backup.sh`

- **Restauração de backup**: `restaurar-backup.sh`

Os scripts devem ser executados conforme as instruções documentadas.

Sempre confirme que está no ambiente correto (DEV, QA ou PROD), antes de restaurar backups, para evitar perda de dados críticos!

### Arquivamento e Versionamento

Todos os arquivos de backup são armazenados e versionados no Google Drive corporativo, garantindo rastreabilidade e segurança.

O padrão de nomenclatura dos arquivos deve ser `<id>-<data>-<hora>`. Exemplo: `1-29102025-00:00:00`.

Os arquivos devem ser adicionados no repositório `harppia/backups/`.

### Retenção de Backups

Enquanto o volume de dados do banco for pequeno, os backups devem ser mantidos indefinidamente. Com o crescimento do banco, políticas de retenção e rotação de backups devem ser avaliadas para garantir eficiência e gestão do espaço.

### Considerações de Evolução

Caso o banco de dados cresça significativamente, recomenda-se:

- Implementar processos **automatizados de backup**;

- Avaliar a criação de **backups incrementais**;

- Revisar e formalizar políticas de retenção e versionamento.


 
## Convenções do Projeto

Nas seções a seguir, constam convenções gerais do projeto, com o intuito de garantir qualidade, segurança, padronização, manutenibilidade e rastreabilidade durante o ciclo de vida do banco de dados.

Todas as convenções descritas nesta seção devem ser seguidas integralmente em novos desenvolvimentos. Alterações nos padrões devem ser previamente aprovadas pela equipe de DBA responsável.

### Criação de Objetos em Geral
 
- Nada deve ser criado no schema `public`;
 
- Todo objeto deve ser criado no schema reservado para ele;
 
- Domains, enumerações e rotinas devem ser criadas no schema `utils`.
 
### Organização de Arquivos
 
- Índices únicos parciais, índices normais, índices de `EXCLUDE`, `CHECK CONSTRAINTs`, rotinas, enumerações e comentários devem ser criados em arquivos individuais, apartados do código-fonte das tabelas de cada schema;
 
- Todo schema pode ter arquivos para DDL, DML, `CHECK CONSTRAINTs`, comentários, rotinas, enumerações, índices e índices de `EXCLUDE`, de acordo com sua necessidade. Cada arquivo deve conter scripts que exerçam funções que estão vinculadas apenas à sua responsabilidade;
 
- Todo schema que exige configuração de permissionamento deve conter um diretório chamado `dcl`, que contém grants e `policies` para os ROLEs associados ao schema;
 
- Objetos utilizados no sistema todo, como functions, domains, types etc. devem ser arquivados no diretório do schema `utils`.
 
### Ordem de Colunas na Criação de Tabelas
 
1. Chaves primárias, sem a declaração de sua `CONSTRAINT`;
 
2. Colunas de log, nesta ordem: `created_at`, `updated_at`, `deleted_at`, `is_disabled`, `created_by`, `updated_by`, `deleted_by` e `disabled_by`. Caso alguma dessas colunas de log não fizer sentido para a tabela, basta não a adicionar. Caso seja necessário adicionar outro log para uma tabela em específico, adicione nesta seção, numa posição que faça sentido;
 
3. Colunas com conteúdo relacionado à entidade que a tabela cuida. Caso seja necessário adicionar colunas `is_deleted` ou `is_disabled` a tabela, elas devem estar nesta etapa, antes dos dados da entidade, vindo primeiro o `is_deleted` e depois o `is_disabled`;
 
4. Chaves estrangeiras, sem a declaração de sua `CONSTRAINT`;
 
5. Declarações de `CONSTRAINTs` de chaves primárias;
 
6. Declarações de `CONSTRAINTs` de chaves primárias compostas;
 
7. Declarações de `CONSTRAINTs` de chaves únicas;
 
8. Declarações de `CONSTRAINTs` de chaves únicas compostas;
 
9. Declarações de `CONSTRAINTs` de chaves estrangeiras de logs;
 
10. Declarações de `CONSTRAINTs` de chaves estrangeiras;
 
11. Declarações de `CONSTRAINTs` de chaves estrangeiras compostas.
 
Etapas que não se aplicarem à tabela podem ser ignoradas.
 
### Nomenclaturas de Objetos
 
#### Princípios Gerais
 
- Uso de underscore para separar palavras;
 
- Uso de lowercase;
 
- Nomenclaturas de tabelas devem estar no singular;
 
- Idioma português para uso geral e inglês para termos técnicos;
 
- Uso de prefixos para objetos:
  - Composite Type -> ct
  - Domain -> d
  - Enum -> e
  - Function -> f
  - Índice -> i
  - Procedure -> p
  - Role -> r
  - Trigger -> tr
  - Tabela -> tb
  - View -> v
  - View materializada -> vm
   
- Uso de prefixos para `CONSTRAINTs`:
  - Primary Key -> pk
  - Foreign Key -> fk
  - Unique Key -> uq
  - Check -> ck
  - Exclude -> ex
 
#### Nomenclaturas de Colunas de FKs
 
- A nomenclatura de colunas FK, que têm tabela origem no seu próprio schema, é o trigrama de cada palavra do nome da sua tabela origem, separados por underscore e finalizados pelo prefixo `_id`. A propósito, deve-se desconsiderar o `tb_`.

  Exemplos:
  - tb_teste_automatizado -> tes_aut_id

  - tb_venda -> ven_id

  - tb_solicitacacao_usuario -> sol_usu_id
 
- A nomenclatura de colunas FK, que têm tabela origem em um schema que não seja o seu, segue o padrão `s_<schema-origem>_t_<tabela-origem>_c_<nome-coluna-nesta-tabela>`, onde:

  - `s_`, `t_` e `c_`: delimitam onde o nome do schema (s_), tabela (t_) e coluna (c_) iniciam e terminam;
  
  - `<schema-origem>`: nome do schema origem da FK;

  - `<tabela-origem>`: nome da tabela origem da FK;

  - `<nome-coluna-nesta-tabela>`: nome da coluna na tabela que recebe a FK. Observação: esse nome não deve ser apenas **id** para impedir conflitos de nomenclatura em `CONSTRAINTs`.

  Exemplos:
  - s_devops_t_tb_teste_automatizado_c_teste

  - s_financeiro_t_tb_venda_c_venda

  - s_atendimento_usuario_t_tb_solicitacao_usuario_c_solicitacao
 
#### Nomenclaturas de CONSTRAINTs Nomeáveis
 
Toda nomenclatura de `CONSTRAINTs` nomeáveis segue o padrão `<sigla-constraint>_s_<schema>_t_<tabela>_c_<coluna>`. Caso seja uma `CONSTRAINT` composta, basta repetir o `c_<coluna>` até que as colunas terminem.

  Exemplos:
    - pk_s_nomeschema_t_tb_nometabela_c_id

    - pk_s_nomeschema_t_tb_nometabela_c_id1_c_id2 (composta)

    - fk_s_nomeschema_t_tb_nometabela_c_nomecoluna

    - fk_s_nomeschema_t_tb_nometabela_c_nomecoluna1_c_nomecoluna2 (composta)
    
    - uq_s_nomeschema_t_tb_nometabela_c_nomecoluna

    - uq_s_nomeschema_t_tb_nometabela_c_nomecoluna1_c_nomecoluna2 (composta)

    - ck_s_nomeschema_t_tb_nometabela_c_nomecoluna

    - ck_s_nomeschema_t_tb_nometabela_c_nomecoluna1_c_nomecoluna2 (composta)

    - ex_s_nomeschema_t_tb_nometabela_c_nomecoluna

    - ex_s_nomeschema_t_tb_nometabela_c_nomecoluna1_c_nomecoluna2 (composta)

#### Nomenclaturas de Domínios, Enumerações, Funções e Procedimentos

Domínios, enumerações, funções e procedimentos devem seguir o padrão de nomenclatura abaixo:

- **Domínio**: s_nomeschema_d_nomedominio

- **Enumeração por coluna**: s_nomeschema_t_tb_nometabela_e_nomeenumeracao (usada em apenas uma coluna)

- **Enumeração por schema**: s_nomeschema_e_nomeenumeracao (pode ser usada em todo o schema)

- **Função**: s_nomeschema_f_nomefuncao

- **Procedimento**: s_nomeschema_p_nomeprocedimento

#### Nomenclaturas de Triggers

Triggers devem seguir o padrão de nomenclatura `s_<nomeschema>_t_<nometabela>_tr_<momento-disparo>_<operacao-disparo>_<condicao-linhas>`, onde:

- `tr`: indica que é uma trigger;

- `<momento-disparo>`: pode receber valores "a" (AFTER) ou "b" (BEFORE);

- `<operacao-disparo>`: pode receber valores "i" (INSERT), "u" (UPDATE) ou "d" (DELETE);

- `<condicao-linhas>`: pode receber valores "er" (EACH ROW) ou "es" (EACH STATEMENT).

  Exemplos:
    - s_nomeschema_t_nometabela_tr_a_i_er

    - s_nomeschema_t_nometabela_tr_b_d_es

    - s_nomeschema_t_nometabela_tr_a_u_er

 

## Equipe Responsável
 
Este projeto de banco de dados foi desenvolvido pela equipe de desenvolvimento de software do sistema **Harppia**, em especial pelo DBA **Gustavo**.

Para dúvidas técnicas, contribuições ou solicitações de manutenção, entre em contato com o DBA responsável pelo e-mail gustavosouza.pro417@gmail.com.