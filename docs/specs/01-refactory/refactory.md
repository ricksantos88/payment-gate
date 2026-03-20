# 🔄 Refactoring Plan — Clean Architecture Migration (Kotlin + Spring Boot)

Este documento define **como refatorar o projeto atual para Clean Architecture**, com regras claras para execução por humanos e por IA (ex: Copilot Agent criando uma branch para PR).

Objetivo: transformar o projeto sem quebrar comportamento, garantindo **testabilidade, baixo acoplamento e organização escalável**.

---

# 🎯 Objetivos da Refatoração

* Separar regras de negócio do framework (Spring)
* Tornar o domínio independente
* Introduzir Use Cases claros
* Aumentar cobertura de testes
* Reduzir acoplamento entre camadas

---

# 🧱 Estrutura alvo

```text
src/main/kotlin/com/app

├── domain
│   ├── model
│   ├── usecase
│   └── repository

├── application
│   ├── dto
│   └── service (opcional)

├── infrastructure
│   ├── controller
│   ├── repository
│   ├── entity
│   └── config
```

---

# ⚠️ Regras obrigatórias

## Dependência

```text
infrastructure → application → domain
```

* `domain` não depende de ninguém
* `application` depende apenas de `domain`
* `infrastructure` pode depender de todos

---

## Isolamento de regras

* Regra de negócio → `domain/usecase`
* Framework (Spring, JPA, Feign, Kafka) → `infrastructure`
* DTOs → `application`

---

# 🔍 Estado atual (problemas comuns a corrigir)

* Controllers com regra de negócio
* Services acoplados ao Spring
* Uso direto de JPA fora da infraestrutura
* Falta de Use Cases explícitos
* Testes frágeis ou inexistentes

---

# 🛠️ Estratégia de Refatoração (passo a passo)

## 1. Criar estrutura de pacotes

Criar os pacotes alvo sem mover código ainda.

---

## 2. Identificar regras de negócio

Para cada fluxo:

* Criar um Use Case
* Nome baseado em ação:

```text
CreateUserUseCase
ProcessPaymentUseCase
CalculateFeeUseCase
```

---

## 3. Extrair Use Cases

### Antes (errado)

```kotlin
@Service
class UserService {

    fun createUser(request: CreateUserRequest): User {
        if (request.name.isBlank()) {
            throw IllegalArgumentException()
        }

        return repository.save(User(request.name))
    }
}
```

---

### Depois (correto)

```kotlin
class CreateUserUseCase(
    private val repository: UserRepository
) {

    fun execute(input: CreateUserInput): User {
        require(input.name.isNotBlank())

        val user = User(input.name)

        return repository.save(user)
    }
}
```

---

## 4. Criar interfaces no domínio

```kotlin
interface UserRepository {
    fun save(user: User): User
}
```

---

## 5. Implementar na infraestrutura

```kotlin
@Repository
class UserRepositoryImpl(
    private val jpaRepository: SpringDataUserRepository
) : UserRepository {

    override fun save(user: User): User {
        return jpaRepository.save(user.toEntity()).toDomain()
    }
}
```

---

## 6. Refatorar controllers

### Antes

```kotlin
@RestController
class UserController {

    @PostMapping
    fun create(@RequestBody request: CreateUserRequest): User {
        return service.createUser(request)
    }
}
```

---

### Depois

```kotlin
@RestController
class UserController(
    private val createUserUseCase: CreateUserUseCase
) {

    @PostMapping
    fun create(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val result = createUserUseCase.execute(request.toInput())
        return ResponseEntity.ok(result.toResponse())
    }
}
```

---

## 7. Introduzir DTOs (application)

```kotlin
data class CreateUserRequest(val name: String)

data class CreateUserInput(val name: String)
```

---

## 8. Criar mappers

```kotlin
fun CreateUserRequest.toInput() = CreateUserInput(name)
```

---

## 9. Adicionar testes (TDD obrigatório)

### Use Case

```kotlin
class CreateUserUseCaseTest {

    private val repository = mockk<UserRepository>()
    private val useCase = CreateUserUseCase(repository)

    @Test
    fun `should create user`() {
        val input = CreateUserInput("Wendel")

        every { repository.save(any()) } returns User("Wendel")

        val result = useCase.execute(input)

        assertEquals("Wendel", result.name)
    }
}
```

---

# 🧪 Estratégia de testes

| Camada         | Tipo de teste       |
| -------------- | ------------------- |
| domain         | unitário puro       |
| application    | unitário            |
| infrastructure | integração (Spring) |

---

# 🚫 Anti-patterns proibidos

* Controller com regra de negócio
* UseCase com anotação Spring (`@Service`)
* Repositório do Spring no domínio
* DTO com lógica
* Entidade JPA fora da infraestrutura

---

# 🔀 Estratégia de branch (para Copilot Agent)

## Nome da branch

```text
refactor/clean-architecture-migration
```

---

## Commits esperados

```text
feat: create clean architecture structure

refactor: extract use cases from services

refactor: move domain models to domain layer

refactor: isolate repository interfaces

refactor: move jpa implementations to infrastructure

refactor: simplify controllers

test: add use case unit tests
```

---

# 🤖 Instruções para IA (Copilot Agent)

## Objetivo

Criar uma branch com a refatoração completa seguindo este documento.

---

## Regras obrigatórias

* NÃO alterar comportamento funcional
* NÃO quebrar endpoints existentes
* Criar Use Cases para cada regra de negócio
* Garantir separação de camadas
* Criar testes para Use Cases
* Remover lógica de controllers/services

---

## Ordem de execução

```text
1. Criar estrutura de pacotes
2. Criar interfaces no domínio
3. Extrair Use Cases
4. Refatorar controllers
5. Implementar adapters (infra)
6. Criar testes
7. Validar build
```

---

## Critérios de aceite (PR)

* [ ] Código compila
* [ ] Testes passando
* [ ] Sem lógica de negócio fora do domínio
* [ ] Controllers apenas orquestram
* [ ] Dependências respeitam Clean Architecture

---

# 🧠 Checklist final

* [ ] Use Cases criados
* [ ] Domain isolado
* [ ] Infra separada
* [ ] DTOs organizados
* [ ] Testes cobrindo regras

---

# ⚡ TL;DR

* Extrai regra → UseCase
* Domínio puro (sem Spring)
* Infra adapta o mundo externo
* Controller só chama UseCase
* Testa tudo

---

Se fizer isso direito, teu projeto sai de “funciona” pra “escala sem dor”.

E no code review?
Vai parecer que foi feito por um time sênior… mesmo sendo só tu + uma IA bem treinada 😏
