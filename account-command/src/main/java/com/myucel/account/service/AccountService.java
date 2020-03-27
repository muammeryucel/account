package com.myucel.account.service;

import java.math.BigDecimal;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.stereotype.Service;

import com.myucel.account.api.activation.ActivationRequestedEvent;
import com.myucel.account.api.balance.DepositCommand;
import com.myucel.account.api.balance.WithdrawCommand;
import com.myucel.account.api.registration.CreateAccountCommand;
import com.myucel.account.api.transfer.SendMoneyCommand;

@Service
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

	public void deposit(String accountId, Long version, BigDecimal amount) {
		commandGateway.sendAndWait(new DepositCommand(accountId, version, amount));
	}

	public void withdraw(String accountId, Long version, BigDecimal amount) {
		commandGateway.sendAndWait(new WithdrawCommand(accountId, version, amount));
	}

//	@EventHandler
//	public void handle(Object event) {
//		System.out.println("Event: " + event);
//		//throw new RuntimeException("Exception thrown whilst event handling!");
//	}

}
