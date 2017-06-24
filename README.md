Kotlin Example of Accessing Data with JPA
=====

Kotlin を用いて [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/) を実装したサンプルです。

## Customer

JPA Entity クラスです。

```kotlin
@Entity
data class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,
        val firstName: String? = null,
        val lastName: String? = null
) {
    constructor(firstName: String, lastName: String) : this(null, firstName, lastName)
}
```

kotlin の data class を使っています。

Spring Data で JPA Entity を利用する際、デフォルトコンストラクターが必要ですが、
コンストラクターのパラメータに規定値を設定してやると、
コンパイ時に自動でデフォルトコンストラクターが生成されるので、
Spring Data で利用できるようになります。


## CrudRepository

```kotlin
interface CustomerRepository : CrudRepository<Customer, Long> {
    fun findByLastName(lastName: String): List<Customer>
}
```

Java コードを Kotlin へ自動変換すると生成されるコードそのままです。

## Application

Spring Boot の Application クラスです。

```kotlin
@SpringBootApplication
open class Application {
    @Bean
    open fun demo(repository: CustomerRepository): CommandLineRunner {
        return CommandLineRunner {
            // save a couple of customers
            repository.save(Customer("Jack", "Bauer"))
            repository.save(Customer("Chloe", "O'Brian"))
            repository.save(Customer("Kim", "Bauer"))
            repository.save(Customer("David", "Palmer"))
            repository.save(Customer("Michelle", "Dessler"))

            // fetch all customers
            log.info("Customers found with findAll():")
            log.info("-------------------------------")
            for (customer in repository.findAll()) {
                log.info(customer.toString())
            }
            log.info("")

            // fetch an individual customer by ID
            val customer = repository.findOne(1L)
            log.info("Customer found with findOne(1L):")
            log.info("--------------------------------")
            log.info(customer.toString())
            log.info("")

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):")
            log.info("--------------------------------------------")
            for (bauer in repository.findByLastName("Bauer")) {
                log.info(bauer.toString())
            }
            log.info("")
        }
    }
}
```

上記コードで登場する `log` はパッケージレベルの変数として定義しています。

```kotlin
private val log = LoggerFactory.getLogger(Application::class.java)
```


## main 関数

kotlin らしく、こちらもパッケージレベルのメソッドとして、次のように定義しています。

```kotlin
fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
```
