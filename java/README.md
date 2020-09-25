# Teste de Back-end Bcredi
## Java
Para resolver o desafio em Java, edite o arquivo `Solution.java`. Os testes rodam a partir do arquivo `Main.java`.

 - **com/bcredi/testebackend/domain/entities:** Pacote com as entidades utilizadas na solução.
  - **com/bcredi/testebackend/domain/services:** Pacote com as regras de negócio aplicadas à cada entidade.
  - **com/bcredi/testebackend/usecase:** Pacote com os tratamentos de cada evento utilizando o Strategy Pattern.
 - **com/bcredi/testebackend/gateway:** Pacote com os gateways de entradas da solução, onde o EventGateway é a classe de contexto do Strategy, tratado cada tipo de evento, e o ProposalGateway é a classe onde  retorna as propostas válidas.
 - **com/bcredi/testebackend/test:** Testes unitários.
 - **com/bcredi/testebackend/data:** Classes para armazenar as listas de propostas e eventos durante o processamento.

Para testar sua solução, rode:

```
javac Main.java Solution.java && java Main
```
