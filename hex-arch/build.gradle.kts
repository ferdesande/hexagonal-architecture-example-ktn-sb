plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.detekt)
}

group = "com.fdesande"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.fasterxml.jackson.module.kotlin)
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(libs.kotlin.reflect)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.mockito.kotlin)
    testRuntimeOnly(libs.junit.platform.launcher)

    detektPlugins(libs.detekt.formatting)
}

detekt {
    toolVersion = "1.21.0"
    config = files("${project.rootDir}/detekt.yml")
    buildUponDefaultConfig = true
    autoCorrect = true
    parallel = true
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
}

tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektAll") {
    description = "Runs detekt on the whole project at once."
    parallel = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/build/**")
    exclude("**/resources/**")
    config.setFrom(files("${project.rootDir}/detekt.yml"))
    buildUponDefaultConfig = true
}

tasks.withType<Test> {
    useJUnitPlatform()
}
