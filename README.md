# payment-gate

Projeto simples de exemplo: gateway de pagamento (Spring Boot + Kotlin).

Descrição

Este repositório contém um serviço de pagamento escrito em Kotlin com Spring Boot. O objetivo deste README é apenas garantir que o projeto tenha uma documentação mínima.

Requisitos

- JDK 17+ instalado (ou a versão compatível definida no projeto).
- Gradle (pode usar o wrapper incluído).

Como rodar

Usando o wrapper do Gradle (recomendado):

```
./gradlew build
./gradlew bootRun
```

Ou apenas para rodar os testes:

```
./gradlew test
```

Configuração

- Arquivo de configuração principal: `src/main/resources/application.yaml`.
- Migrações de banco: `src/main/resources/db/migration`.

Pontos importantes

- Endpoints e detalhes da API podem ser encontrados nos controllers do diretório `src/main/kotlin`.
- Tokens JWT e autenticação estão implementados nos serviços de segurança (veja `infrastructure/security`).

Contribuindo

Fork, crie uma branch com a feature/bugfix e abra um pull request.

Licença

Licença: MIT (ou substitua pela licença do seu projeto).

Contato

Para dúvidas ou problemas, abra uma issue neste repositório.

