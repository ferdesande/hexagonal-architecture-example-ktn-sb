rootProject.name = "hex-arch"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.6.21")
            version("spring-boot", "2.7.18")
            version("database-rider", "1.44.0")
            version("detekt", "1.21.0")
            version("rest-assured", "5.1.1")
            version("testcontainers", "1.18.3")

            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-spring", "org.jetbrains.kotlin.plugin.spring").versionRef("kotlin")
            plugin("kotlin-jpa", "org.jetbrains.kotlin.plugin.jpa").versionRef("kotlin")
            plugin("spring-boot", "org.springframework.boot").versionRef("spring-boot")
            plugin("spring-dependency-management", "io.spring.dependency-management").version("1.1.7")
            plugin("detekt", "io.gitlab.arturbosch.detekt").versionRef("detekt")

            library("spring-boot-starter-web", "org.springframework.boot", "spring-boot-starter-web").withoutVersion()
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").withoutVersion()
            library("spring-boot-starter-data-jpa", "org.springframework.boot", "spring-boot-starter-data-jpa")
                .withoutVersion()
            library("archunit-junit5", "com.tngtech.archunit", "archunit-junit5").version("1.4.0")
            library("database-rider-core", "com.github.database-rider", "rider-core").versionRef("database-rider")
            library("database-rider-spring", "com.github.database-rider", "rider-spring").versionRef("database-rider")
            library("database-rider-junit5", "com.github.database-rider", "rider-junit5").versionRef("database-rider")
            library("detekt-formatting", "io.gitlab.arturbosch.detekt", "detekt-formatting").versionRef("detekt")
            library("fasterxml-jackson-module-kotlin", "com.fasterxml.jackson.module", "jackson-module-kotlin")
                .withoutVersion()
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").withoutVersion()
            library("kotlin-test-junit5", "org.jetbrains.kotlin", "kotlin-test-junit5").withoutVersion()
            library("junit-platform-launcher", "org.junit.platform", "junit-platform-launcher").withoutVersion()
            library("mockito-kotlin", "org.mockito.kotlin", "mockito-kotlin").version("4.1.0")
            library("postgresql", "org.postgresql", "postgresql").version("42.7.5")
            library("rest-assured-kotlin-extensions", "io.rest-assured", "kotlin-extensions").withoutVersion()
            library("testcontainers-postgresql", "org.testcontainers", "postgresql").versionRef("testcontainers")
            library("testcontainers-junit-jupiter", "org.testcontainers", "junit-jupiter").versionRef("testcontainers")
        }
    }
}
