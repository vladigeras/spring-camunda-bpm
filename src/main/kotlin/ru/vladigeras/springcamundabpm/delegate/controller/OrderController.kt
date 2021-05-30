package ru.vladigeras.springcamundabpm.delegate.controller

import org.camunda.bpm.engine.RuntimeService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.vladigeras.springcamundabpm.shared.BpmContextVariables
import ru.vladigeras.springcamundabpm.shared.LoggerDelegate
import java.util.*
import java.util.concurrent.TimeUnit

@RequestMapping("/api/v1/orders")
@RestController
class OrderController(val bpmEngine: RuntimeService) {

    val log by LoggerDelegate()

    @PostMapping("")
    fun save() {
        val orderId = UUID.randomUUID().toString()
        val processInstanceId =
            bpmEngine.startProcessInstanceByMessage(
                "car_order_was_created_delegate",
                orderId,
                mapOf(Pair(BpmContextVariables.ORDER_ID, orderId))
            ).processInstanceId
        log.info("ProcessID $processInstanceId started for orderID $orderId")
    }

    @PostMapping("/{orderId}/confirm")
    fun confirm(@PathVariable("orderId") orderId: String) {
        log.info("Order with ID $orderId will confirm")
        TimeUnit.SECONDS.sleep(1)
        log.info("Order with ID $orderId was confirmed")
        bpmEngine.correlateMessage("deal_confirmed_delegate", orderId)
    }
}