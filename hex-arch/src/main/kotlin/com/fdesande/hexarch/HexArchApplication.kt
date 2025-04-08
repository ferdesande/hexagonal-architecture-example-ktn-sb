package com.fdesande.hexarch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HexArchApplication

fun main(args: Array<String>) {
    runApplication<HexArchApplication>(*args)
}
