# API Votação

API de serviços de votação orientado pela abertura de uma pauta para ser votada durante um determinado período, onde somente pode haver um voto por associado.

 Objetivos
  - Cadastrar uma nova pauta;
  - Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo
determinado na chamada de abertura ou 1 minuto por default);
  - Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é
identificado por um id único e pode votar apenas uma vez por pauta);
  - Contabilizar os votos e dar o resultado da votação na pauta.
### Tech

O motivo das tecnologias utilizadas a baixo foram pelo meu conhecimento
e a facilidade que as mesmas proporcionam diante ao desenvolvimento Java.

Por exemplo o spring boot que facilita e agiliza a configuração, O hibernate
como já cria a estrutura logica do banco de dados, assim não precisamos criar
manualmente os scripts.

O Swagger que serve para termos uma melhor documentação da api.

O Mysql foi utilizado pois é agil para pequenas aplicações e podemos utilizar no Heroku

* Java 8
* Spring Boot Web; Hibernate; Data;
* Swagger 2
* MySQL
* H2 Database

### Instalação

```sh
$ git clone https://github.com/jeanvisoski/votacao-pauta.git
$ cd votacao-pauta
$ mvn package
$ cd target
$ java -jar votacao-pauta.jar
```
#### Swagger
Heroku
```
https://app-votavao-pauta.herokuapp.com/swagger-ui.html
```

Desenvolvimento:
```
http://localhost:8080/swagger-ui.html#/
```

### Heroku Cloud Plataform

```
https://app-votavao-pauta.herokuapp.com/api/v1/
```
### Versionamento
    - Os endpoints são versionados pelo número major da versão (v1) diretamente na URL de acesso.


