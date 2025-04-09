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
            version("detekt", "1.21.0")

            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-spring", "org.jetbrains.kotlin.plugin.spring").versionRef("kotlin")
            plugin("kotlin-jpa", "org.jetbrains.kotlin.plugin.jpa").versionRef("kotlin")
            plugin("spring-boot", "org.springframework.boot").versionRef("spring-boot")
            plugin("spring-dependency-management", "io.spring.dependency-management").version("1.1.7")
            plugin("detekt", "io.gitlab.arturbosch.detekt").versionRef("detekt")

            library("spring-boot-starter-web", "org.springframework.boot", "spring-boot-starter-web").withoutVersion()
            library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").withoutVersion()
            library("fasterxml-jackson-module-kotlin", "com.fasterxml.jackson.module", "jackson-module-kotlin")
                .withoutVersion()
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").withoutVersion()
            library("kotlin-test-junit5", "org.jetbrains.kotlin", "kotlin-test-junit5").withoutVersion()
            library("junit-platform-launcher", "org.junit.platform", "junit-platform-launcher").withoutVersion()
            library("detekt-formatting", "io.gitlab.arturbosch.detekt", "detekt-formatting").versionRef("detekt")
            library("mockito-kotlin", "org.mockito.kotlin", "mockito-kotlin").version("4.1.0")
        }
    }
}
