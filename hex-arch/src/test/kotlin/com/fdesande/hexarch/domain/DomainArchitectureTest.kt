package com.fdesande.hexarch.domain

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.GeneralCodingRules.ACCESS_STANDARD_STREAMS
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition
import com.tngtech.archunit.library.plantuml.rules.PlantUmlArchCondition.Configuration.consideringOnlyDependenciesInAnyPackage
import com.tngtech.archunit.library.plantuml.rules.PlantUmlArchCondition.adhereToPlantUmlDiagram
import org.junit.jupiter.api.Test
import java.util.*

class DomainArchitectureTest {
    companion object {
        const val DOMAIN_ROOT: String = "com.fdesande.hexarch.domain"
        val classes: JavaClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(DOMAIN_ROOT)
    }

    @Test
    fun plantUmlDiagramIsNotViolated() {
        val plantUmlDiagram = DomainArchitectureTest::class.java.getResource("/domain-architecture.puml")
        classes().should(
            adhereToPlantUmlDiagram(
                Objects.requireNonNull(plantUmlDiagram),
                consideringOnlyDependenciesInAnyPackage("$DOMAIN_ROOT..")
            )
        ).check(classes)
    }

    @Test
    fun noCycles() {
        SlicesRuleDefinition.slices()
            .matching("$DOMAIN_ROOT.(*)..")
            .should()
            .beFreeOfCycles().check(classes)
    }

    @Test
    fun noClassCanAccessWithStandardStreams() {
        noClasses().should(ACCESS_STANDARD_STREAMS).check(classes)
    }
}
