package io.github.sunny4381

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.CrudRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

private val log = LoggerFactory.getLogger(Application::class.java)

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

interface CustomerRepository : CrudRepository<Customer, Long> {
    fun findByLastName(lastName: String): List<Customer>
}

@SpringBootApplication
open class Application {
    fun run(args: Array<String>) {
        println("こんにちわ世界！")
    }

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

fun main(args: Array<String>) {
    val ctx = SpringApplication.run(Application::class.java, *args)
    val app = ctx.getBean(Application::class.java)
    app.run(args)
}
