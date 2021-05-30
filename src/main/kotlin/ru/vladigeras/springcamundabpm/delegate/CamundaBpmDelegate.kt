package ru.vladigeras.springcamundabpm.delegate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CamundaBpmDelegate

fun main(args: Array<String>) {
    runApplication<CamundaBpmDelegate>(*args)
}
