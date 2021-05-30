package ru.vladigeras.springcamundabpm.delegate.delegate

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import ru.vladigeras.springcamundabpm.shared.BpmContextVariables
import ru.vladigeras.springcamundabpm.shared.LoggerDelegate
import java.util.concurrent.TimeUnit

@Component
class DealOrderDelegate : JavaDelegate {
    val log by LoggerDelegate()

    override fun execute(execution: DelegateExecution?) {
        val orderId: String = execution?.getVariable(BpmContextVariables.ORDER_ID) as String
        log.info("Deal with order ID $orderId is coming soon...")
        TimeUnit.SECONDS.sleep(1)
        log.info("Deal with order ID $orderId was completed. Waiting confirmation....")
    }
}