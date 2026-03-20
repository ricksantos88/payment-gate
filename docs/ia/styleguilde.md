# 🧠 AI Development Guide — Kotlin + Spring Boot

Este documento define padrões e diretrizes para desenvolvimento orientado a qualidade, utilizando **TDD, Clean Code e Clean Architecture**. Ele foi projetado para ser consumido tanto por humanos quanto por IA, garantindo consistência, testabilidade e evolução sustentável do sistema.

---

# 🧪 1. TDD (Test-Driven Development)

## 🔁 Ciclo obrigatório

Sempre seguir o ciclo:

```
1. RED    → Escrever teste que falha
2. GREEN  → Implementar o mínimo para passar
3. REFACTOR → Melhorar código mantendo testes verdes
```

## 📌 Regras

* Nunca escrever código de produção sem um teste antes
* Testes devem ser **rápidos, isolados e determinísticos**
* Evitar dependência de banco, rede ou filesystem real

## 🧱 Estrutura de testes

```kotlin
class CreateUserUseCaseTest {

    private val repository = mockk<UserRepository>()
    private val useCase = CreateUserUseCase(repository)

    @Test
    fun `should create user successfully`() {
        val input = CreateUserInput("Wendel")

        every { repository.save(any()) } returns User("Wendel")

        val result = useCase.execute(input)

        assertEquals("Wendel", result.name)
        verify(exactly = 1) { repository.save(any()) }
    }
}
```

## 🧠 Boas práticas

* Nome do teste descreve comportamento
* Um único motivo para falhar
* Evitar lógica complexa dentro do teste
* Usar builders/factories para criar objetos

---

# 🧼 2. Clean Code

## 📌 Princípios fundamentais

### 🔹 Nomes claros

```kotlin
// ❌ ruim
fun p(d: String): Boolean

// ✅ bom
fun isValidEmail(email: String): Boolean
```

### 🔹 Funções pequenas

* Máximo: ~20 linhas
* Fazem **uma coisa só**

### 🔹 Evitar comentários desnecessários

* Código deve ser autoexplicativo

### 🔹 Imutabilidade sempre que possível

```kotlin
val name = "Wendel" // preferir val ao invés de var
```

### 🔹 Evitar efeitos colaterais

* Funções devem ser previsíveis

---

# 🏗️ 3. Clean Architecture

## 📂 Estrutura de pastas

```
src/main/kotlin/com/app

├── domain
│   ├── model
│   ├── usecase
│   └── repository

├── application
│   ├── service
│   └── dto

├── infrastructure
│   ├── controller
│   ├── repository
│   └── config
```

---

## 🧠 Regras de dependência

* `domain` NÃO depende de ninguém
* `application` depende de `domain`
* `infrastructure` depende de todos

```
infrastructure → application → domain
```

---

## 🧱 Exemplo de Use Case

```kotlin
class CreateUserUseCase(
    private val repository: UserRepository
) {

    fun execute(input: CreateUserInput): User {
        validate(input)

        val user = User(input.name)

        return repository.save(user)
    }

    private fun validate(input: CreateUserInput) {
        require(input.name.isNotBlank()) {
            "Name must not be blank"
        }
    }
}
```

---

## 🧩 Interface de repositório (Domain)

```kotlin
interface UserRepository {
    fun save(user: User): User
}
```

---

## 🔌 Implementação (Infrastructure)

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

# ⚙️ 4. Padrões obrigatórios

## ✅ Use Cases

* Representam regras de negócio
* Não conhecem framework

## ✅ DTOs

* Apenas transporte de dados
* Nunca conter lógica

## ✅ Controllers

* Apenas orquestração
* Nunca conter regra de negócio

```kotlin
@RestController
@RequestMapping("/users")
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

# 🧪 5. Testes por camada

## 🧠 Domain

* Testes puros (sem Spring)
* 100% cobertura esperada

## ⚙️ Application

* Testar use cases
* Mockar dependências

## 🌐 Infrastructure

* Testes de integração (SpringBootTest)

---

# 🚫 Anti-patterns proibidos

* ❌ Lógica de negócio em controller
* ❌ Uso direto de JPA no domínio
* ❌ Classes gigantes (God Objects)
* ❌ Métodos com múltiplas responsabilidades
* ❌ Testes frágeis ou dependentes de ambiente

---

# 🚀 6. Guidelines para IA

## 📌 Sempre:

* Criar testes antes da implementação
* Seguir separação de camadas
* Priorizar legibilidade ao invés de “código esperto”
* Usar injeção de dependência
* Evitar acoplamento

## 📌 Nunca:

* Misturar regra de negócio com framework
* Criar lógica dentro de DTOs
* Ignorar testes

---

# 🧠 Filosofia final

> Código bom não é o que funciona.
> É o que continua funcionando quando tudo muda.

---

# ⚡ TL;DR (modo turbo)

* TDD sempre (RED → GREEN → REFACTOR)
* Use Cases centralizam regras
* Controllers são burros
* Domain é soberano
* Código simples > código inteligente

---

Se seguir isso aqui, teu projeto não vira um monstro… vira uma máquina. 😏
