package ru.vladigeras.springcamundabpm.delegate.delegate

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import ru.vladigeras.springcamundabpm.shared.BpmContextVariables
import ru.vladigeras.springcamundabpm.shared.LoggerDelegate
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Component
class CheckOrderDelegate : JavaDelegate {
    val log by LoggerDelegate()

    override fun execute(execution: DelegateExecution?) {
        val orderId = execution?.getVariable(BpmContextVariables.ORDER_ID)
        log.info("Order with ID $orderId will check")
        TimeUnit.SECONDS.sleep(1)
        val result = Random.nextBoolean()
        log.info("Order with ID $orderId was checked. Result is $result")
        execution?.setVariable(BpmContextVariables.IS_SUCCESSFUL_CHECK_RESULT, result)
    }
}