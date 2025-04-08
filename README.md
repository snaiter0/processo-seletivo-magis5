
# Teste Técnico - Magis5

Olá! Este repositório contém a solução que desenvolvi para o teste técnico da **Magis5**.

---

## Como rodar na sua máquina

### Clonando o repositório
```bash
git clone <url-do-repositório>
cd logistica-estoque-service
```

### Rodando com Docker

1. No terminal, dentro da pasta do projeto:
   ```bash
   docker compose build
   docker compose up
   ```
2. Se tudo ocorrer bem, já pode fazer os testes na aplicação 
3. Caso receba erro de `FlywayInitializerError`, será necessário rodar o serviço **flyway-repair**:
   - Pela interface gráfica do Docker: execute o container `flyway-repair`
   - Ou via terminal:
     ```bash
     docker compose up flyway-repair
     ```
   > **Importante:** Evite usar `-d` (modo detached), pois é necessário acompanhar o log para garantir que o `repair` e `migrate` foram executados corretamente.

---

### Rodando via IDE

1. Crie um banco MySQL chamado `logistica`:
   ```sql
   CREATE DATABASE logistica;
   ```
2. No arquivo `application.yml`, substitua as credenciais e URL do banco:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/logistica
       username: seu_usuario
       password: sua_senha
   ```
3. No terminal:
   ```bash
   mvn clean install
   mvn flyway:repair -Dflyway.user=seu_usuario -Dflyway.password=sua_senha -Dflyway.url=<sua_url_do_mysql>
   mvn flyway:migrate -Dflyway.user=seu_usuario -Dflyway.password=sua_senha -Dflyway.url=<sua_url_do_mysql>
   ```
4. Pronto! A aplicação pode ser iniciada, todas as tabelas e colunas foram criadas via Flyway.

---

## Considerações Técnicas

- Tentei ao máximo criar uma solução para o uso do MySQL para recriar o ambiente de vocês na qual informaram que utilizam no dia a dia, por isso
criei o serviço adicional no docker chamado flyway-repair, na qual é capaz de executar seus comandos dentro do flyway usando as credenciais já setadas.

- Levando em conta a situação problema apresentada, tentei ao maximo desenvolver uma aplicação capaz de possuir desacoplamento em tecnologias e implementações de maneira a
garantir S(Single responsability) O(Open/Closed) I(Segregation Interfaces), D(Invertion of dependency), o L eu realmente não dei tanta importancia.

- A ideia de arquitetura era ter um orquestrador geral (LogisticaService) na qual recebia as requisições da controller de delegava conforme a necessidade para outros serviços
especializados através de seus contratos(ports), e adaptava suas tecnologias com o uso dos Adapters, dessa maneira é possivel trocar as tecnologias e adaptações destes
componentes caso necessário.

- Quase todo processamento de dados e verificação foi implementado via API Stream do Java, o que nos dá flexibilidade para escalar o desempenho usando parallelStream()
se a demanda de dados crescer. Essa abordagem favorece uma possível paralelização futura com baixo acoplamento e sem grandes mudanças na lógica já implementada.

- A aplicação possui Swagger documentando as rotas com suas funções e seus requisitos(parametros / corpo de requisicao). (http://localhost:8080/swagger-ui/index.html/)
![image](https://github.com/user-attachments/assets/0ebc5147-2c93-4ef4-ae67-d3d0dc46e46e)

---

## Organização e Validação de Dados

- Fiz a centralização das regras de negocio dentro dos modelos, referente a conferencia de limites de litragem, tipos de bebida, janela tempo para troca de
TipoBebida de armazenamento na secao, na consulta de histórico(chamei de extrato) antes de permitir o cadastro de novas bebidas preferi usar um id_externo na
qual é registrada junto com extrato_bebida, e é obrigatório usar para cadastrar uma nova lista de bebidas, sendo que durante o cadastro das bebidas, o extrato
é recuperado e alguns campos das bebidas são conferidos para ver se realmente o cadastro dos produtos estão sendo efetuados corretamente, nada impede de adicionar
a conferencia de mais campos ou a troca deles.

- Preferi também manter a aplicação o máximo possivel em **PT-BR**, já que o cliente alvo é brasileiro e a logica da aplicação é devolver valor através de consultas e cadastros
de Objetos e não o processamento deles, todos os atributos de response e request estão o mais descritivos possiveis.

- Criei Exception Handlers com exceptions personalizadas para a devolução de alguns erros para mensagens mais assertivas referente ao problema, seja em @RequestParams 
ou no corpo da requisição.

- Campos das requisições e respostas foram nomeados de forma **descritiva**.
- Utilização de **Exception Handlers** personalizados para mensagens mais claras de erro.
- As **regras de negócio** foram centralizadas nos modelos:
  - Verificação de litragem máxima
  - Tipos de bebidas por seção
  - Controle de trocas de tipos de bebida
  - Validação de histórico (extrato) antes de novas inserções
- É obrigatório usar `id_externo` para registros de bebida, garantindo integridade e consistência.

---

## Ordenação e Paginação

- Rota de extratos suporta ordenação e paginação com `Pageable`:
  ```http
  /extratos?page=0&size=10&sort=nome,asc
  ```
- Um arquivo `.yml` de exportação com todas as rotas estará disponível no projeto (compatível com **Insomnia**):
  - ![Insomnia screenshot](https://github.com/user-attachments/assets/90c3439d-ca32-43b8-b1fd-447dedd35fa7)

---

## Testes

- Utilizei nos testes unitários o Junity com Mockito, alguns testes de integração, mas nem todos me dei ao luxo de fazer com contexto do spring boot,
estava bem próximo da data de entrega, então alguns testes unitários não estão tão bem formulados, nessa parte usei o auxilio do chatGPT para multiplicar minha produção na janela de tempo
que me restava, os testes mais complexos e bem feitos ficaram na camada controller.


---

## Tecnologias / Bibliotecas**
- **Spring Data JPA/Hibernate**
- **MySql**
- **Spring boot 3**
- **Java 21**
- **JUnity**
- **Mockito**
- **Swagger**
- **Dockerização**
- **Lombok**
- **MapStruct**
- **jackson-datatype** (uso de `ObjectMapper` para conversão de objetos complexos)

---

## Curiosidade que impactou na entrega

Já havia previamente um projeto um pouco parecido com a ideia, na qual eu tinha um CRUD de produtos em um projeto para o lado de logistica na qual eu processava extratos de produtos
tanto de saída quanto entrada e gerava uma planilha(arquivo .csv) na qual consumia outro microserviço que rodava via docker e enviava estes emails para uma lista de destinatários
quanto ao resumo diario e do mês, isso facilitou um pouco a ideia e boa parte da minha solução também foi influenciada por essa experiencia, inclusive as soluções que talvez
pudessem ser melhores.

