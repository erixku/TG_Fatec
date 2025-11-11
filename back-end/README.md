# PROJETO DE BACK-END - HARPPIA API


## Sobre Este Arquivo
Este documento contempla explanações acerca da arquitetura de diretórios e projeto, padrões de desenvolvimento, convenções utilizadas e outros tópicos relevantes para a utilização, desenvolvimento e testes deste projeto.  


## Sumário
Esse artigo abrange os seguintes tópicos:
- [Sobre Este Arquivo](#sobre-este-arquivo);
- [Ficha Técnica do Projeto](#ficha-técnica-do-projeto)
- [Introdução](#introdução);
- [Arquitetura do projeto](#arquitetura-do-projeto);
- [Padrão de nomenclatura de arquivos](#padrão-de-nomenclatura-de-arquivos);
- [Padrão de nomenclatura de métodos](#padrão-de-nomenclatura-de-métodos);
- [Padrão de nomenclatura de variáveis](#padrão-de-nomenclatura-de-variáveis);
- [Gestão de exceções](#gestão-de-exceções);


## Ficha Técnica do Projeto



## Introdução




## ARQUITETURA DO PROJETO
A estrutura de arquivos do back-end acompanha a segregação usada no banco de dados - por módulos. Afim de auxiliar nessa separação lógicaa, foi utilizado princípios como Arquitetura Hexagonal, Domain Driven Design (DDD), Clean Architecture e, principalmente, o conceito de um monolito modular.

A principal base para essa arquitetura é o de monolito modular e arquitetura hexagonal, que pode ser verificada através da implementação de módulos lógicos (auth, file, church, ...) e os adapters e ports, cada um pertencente a cada conceito, respectivamente.

## NOMENCLATURA DE ARQUIVOS
Baseado no conceito de segregação de responsabilidades, toda classe, enum, record ou outro tipo de arquivo é portador de um nome exclusivo e que deixe sua função nesse ecossistema explícita. 

## NOMENCLATURA DE MÉTODOS
Considerando que todo método, estático ou não, requer o nome da classe ou o nome da variável de referência antes, fora utilizado um conjunto de conceitos interligados (como "Redundancy-Free Naming Principle", "Contextual Method Naming" e "Command-Verb Naming Convention"), que contribui para evitar redundâncias óbvias, como acontece em "CadastrarUsuarioUseCase.cadastrarUsuario()" ou em "EmailSanitizer.sanitizarEmail()". Ambas classes já explicitam com o que elas lidam, portanto, basta dizer que ação ela fará naquele momento. Dito isso, os exemplos anteriores ficariam parecidos com "CadastrarUsuarioUseCase.cadastrar()" ou "CadastrarUsuarioUseCase.execute()" e "EmailSanitizer.sanitizar()" ou "EmailSanitizer.executar()".

## NOMENCLATURA DE VARIÁVEIS
Como toda declaração de variável em Java precisa conter seu tipo declarado junto, afim de melhorar a legibilidade, uso sempre um trigrama de cada palavra do tipo, concatenado pelo formato camelCase. Exemplo:

- `Arquivo arq;`
- `UsuarioRepository usrRep;`

Para o caso de arquivos que possuem prefixos ou sufixos constantes, definidos por tipo de arquivo, eles serão abreviados com suas iniciais em maiúsculo. Exemplo:

- `SalvarIgrejaUseCase slvIgrUC;`

Caso eles possuam palavras com apenas quatro (4) letras, ou se faça necessário, a custo de legibilidade, pode-se usar a palavra inteira, como nos exemplos a seguir:

- `MultipartFile mltPrtFile;`
- `ENomeBucket nomeBkt;`

### ATRIBUTOS
Seguem a lógica do trigrama.

### PARÂMETROS
Seguem a lógica do trigrama.
	
### VARIÁVEIS DE MÉTODOS
Seguem a lógica do trigrama.


## GESTÃO DE EXCEÇÕES
Afim de evitar uma desordem na gestão de exceções e sacrificando o nível de precisão de cada tipo de erro possível, cada módulo da API possui uma classe dedicada a cuidar das exceções lançadas (todas em tempo de runtime).

Nesse projeto são usadas apenas exceções no nível de runtime (non-checked), que não requerem o uso de try/catch, o que melhora a legibilidade e reduzem o excesso de códigos (boilerplate). Entretanto, isso causa ...

Sempre que uma exceção é lançada, por ser a nível de runtime, ela sempre chegará a classe "WarningController". Ela é responsável por interceptar todos os tipos de erros em toda a aplicação e retornar uma mensagem de erro mais amigável para o usuário ou utilizadores da API.