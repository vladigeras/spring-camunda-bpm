package ru.vladigeras.springcamundabpm.rest.controller

import org.camunda.bpm.engine.RuntimeService
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import ru.vladigeras.springcamundabpm.rest.model.CheckResponse
import ru.vladigeras.springcamundabpm.rest.model.ConfirmationRequest
import ru.vladigeras.springcamundabpm.shared.BpmContextVariables
import ru.vladigeras.springcamundabpm.shared.LoggerDelegate
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@RequestMapping("/api/v1/orders")
@RestController
class OrderController(val bpmEngine: RuntimeService) {
    val log by LoggerDelegate()

    @Value("\${host.url}")
    private lateinit var hostUrl: String

    @PostMapping("")
    fun save() {
        val orderId = UUID.randomUUID().toString()
        val processInstanceId =
            bpmEngine.startProcessInstanceByMessage(
                "car_order_was_created_rest",
                orderId,
                mapOf(
                    Pair(BpmContextVariables.URL, hostUrl),
                    Pair(BpmContextVariables.ORDER_ID, orderId)
                )
            ).processInstanceId
        log.info("ProcessID $processInstanceId started for orderID $orderId")
    }

    @PostMapping("/{orderId}/check")
    fun check(@PathVariable("orderId") orderId: String): CheckResponse {
        log.info("Order with ID $orderId will check")
        TimeUnit.SECONDS.sleep(1)
        val result = Random.nextBoolean()
        log.info("Order with ID $orderId was checked. Result is $result")
        return CheckResponse(result)
    }

    @PostMapping("/{orderId}/deal")
    fun deal(@PathVariable("orderId") orderId: String, @RequestBody request: ConfirmationRequest) {
        log.info("Field1 is ${request.testField1}. Field2 is ${request.testField2}")
        log.info("Deal with order ID $orderId is coming soon...")
        TimeUnit.SECONDS.sleep(1)
        log.info("Deal with order ID $orderId was completed. Waiting confirmation....")
    }

    @PostMapping("/{orderId}/confirm")
    fun confirm(
        @PathVariable("orderId") orderId: String
    ) {
        log.info("Order with ID $orderId will confirm")
        TimeUnit.SECONDS.sleep(1)
        log.info("Order with ID $orderId was confirmed")
        bpmEngine.correlateMessage("deal_confirmed_rest", orderId)
    }
}