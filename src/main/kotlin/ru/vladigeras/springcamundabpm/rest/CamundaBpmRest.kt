package ru.vladigeras.springcamundabpm.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CamundaBpmRest

fun main(args: Array<String>) {
    runApplication<CamundaBpmRest>(*args)
}
