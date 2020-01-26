package com.myucel.account.service;

import java.math.BigDecimal;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myucel.account.core.activation.ActivationRequestedEvent;
import com.myucel.account.core.balance.DepositCommand;
import com.myucel.account.core.balance.WithdrawCommand;
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

	public void deposit(String accountId, Long version, BigDecimal amount) {
		commandGateway.sendAndWait(new DepositCommand(accountId, version, amount));
	}

	public void withdraw(String accountId, Long version, BigDecimal amount) {
		commandGateway.sendAndWait(new WithdrawCommand(accountId, version, amount));
	}

}
