package com.myucel.account.service;

import java.math.BigDecimal;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myucel.account.core.activation.ActivateAccountCommand;
import com.myucel.account.core.activation.ActivationRequestedEvent;
import com.myucel.account.core.registration.CreateAccountCommand;
import com.myucel.account.core.transfer.SendMoneyCommand;

@Service
@Transactional
public class AccountService {

	private final CommandGateway commandGateway;
	private final EventGateway eventGateway;

	public AccountService(CommandGateway commandGateway, EventGateway eventGateway) {
		super();
		this.commandGateway = commandGateway;
		this.eventGateway = eventGateway;
	}

	public String createAccount(String phoneNumber, BigDecimal initialBalance) {
		return commandGateway.sendAndWait(new CreateAccountCommand(phoneNumber, initialBalance));
	}

	public void activateAccount(String phoneNumber, String activationCode) {
		eventGateway.publish(new ActivationRequestedEvent(phoneNumber, activationCode));
	}

	public void transfer(String senderId, String recipientId, BigDecimal amount) {
		commandGateway.sendAndWait(new SendMoneyCommand(senderId, recipientId, amount));
	}

}
