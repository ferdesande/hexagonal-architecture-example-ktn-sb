package com.fdesande.hexarch.infrastructure.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    companion object {
        private const val POSTGRES_IMAGE = "postgres:17.4"
        private const val DB_NAME = "postgres"
        private const val USER_NAME = "test"
        private const val PASSWORD = "test"

        val postgresContainer: PostgreSQLContainer<*> = FixedPortPostgreSQLContainer(
            DockerImageName.parse(POSTGRES_IMAGE)
        )
            .withDatabaseName(DB_NAME)
            .withUsername(USER_NAME)
            .withPassword(PASSWORD)
            .withReuse(true)
            .apply {
                // Iniciar el contenedor si no está corriendo
                if (!isRunning) start()
            }
    }

    @Bean
    fun postgresContainer(): PostgreSQLContainer<*> = postgresContainer

    // Hint: In Spring 3, it's enough with adding the @ServiceConnection annotation to TestcontainersConfiguration
    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(context: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=${postgresContainer.jdbcUrl}",
                "spring.datasource.username=${postgresContainer.username}",
                "spring.datasource.password=${postgresContainer.password}",
                "spring.datasource.driver-class-name=${postgresContainer.driverClassName}"
            ).applyTo(context.environment)
        }
    }
}

class FixedPortPostgreSQLContainer(
    dockerImageName: DockerImageName
) : PostgreSQLContainer<FixedPortPostgreSQLContainer>(dockerImageName) {
    companion object {
        private const val POSTGRES_PORT = 5432
        private const val POSTGRES_MAPPED_PORT = 5434
    }

    init {
        addFixedExposedPort(POSTGRES_MAPPED_PORT, POSTGRES_PORT)
    }
}
