package com.myucel.account.saga;

import static org.axonframework.modelling.saga.SagaLifecycle.associateWith;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.myucel.account.api.transfer.MoneyReceivedEvent;
import com.myucel.account.api.transfer.MoneyRecoveredEvent;
import com.myucel.account.api.transfer.MoneySentEvent;
import com.myucel.account.api.transfer.ReceiveMoneyCommad;
import com.myucel.account.api.transfer.RecoverMoneyCommand;

@Saga
public class TransferSaga {

	@Autowired
	private transient CommandGateway commandGateway;

	@StartSaga
	@SagaEventHandler(associationProperty = "senderId")
	public void on(MoneySentEvent event) {
		associateWith("recipientId", event.getRecipientId());
		commandGateway.send(new ReceiveMoneyCommad(event.getRecipientId(), event.getSenderId(), event.getAmount()),
				(message, resultMessage) -> {
					if (resultMessage.isExceptional()) {
						commandGateway.sendAndWait(new RecoverMoneyCommand(event.getSenderId(), event.getAmount()));
					}
				});
	}

	@SagaEventHandler(associationProperty = "recipientId")
	public void on(MoneyReceivedEvent event) {
		SagaLifecycle.end();
	}

	@SagaEventHandler(associationProperty = "senderId")
	public void on(MoneyRecoveredEvent event) {
		SagaLifecycle.end();
	}
}
