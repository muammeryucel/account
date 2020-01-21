package com.myucel.account.command;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.myucel.account.core.activation.AccountActivatedEvent;
import com.myucel.account.core.activation.ActivateAccountCommand;
import com.myucel.account.core.activation.ActivationExpiredEvent;
import com.myucel.account.core.activation.ExpireActivationCommand;
import com.myucel.account.core.exception.UnexpectedAccountStatusException;
import com.myucel.account.core.registration.AccountCreatedEvent;
import com.myucel.account.core.registration.CreateAccountCommand;
import com.myucel.account.core.transfer.MoneyReceivedEvent;
import com.myucel.account.core.transfer.MoneyRecoveredEvent;
import com.myucel.account.core.transfer.MoneySentEvent;
import com.myucel.account.core.transfer.ReceiveMoneyCommad;
import com.myucel.account.core.transfer.RecoverMoneyCommand;
import com.myucel.account.core.transfer.SendMoneyCommand;

@Aggregate(snapshotTriggerDefinition = "defaultSnapshotTriggerDefinition")
public class Account {

	@AggregateIdentifier
	private String accountId;
	private BigDecimal balance;

	private AccountStatus status;

	public Account() {
		// Required by Axon
	}

	@CommandHandler
	public Account(CreateAccountCommand command) {
		apply(new AccountCreatedEvent(command.getAccountId(), command.getPhoneNumber(), command.getInitialBalance()));
	}

	@EventSourcingHandler
	public void on(AccountCreatedEvent event) {
		this.accountId = event.getAccountId();
		this.balance = event.getInitialBalance();
		this.status = AccountStatus.ACTIVATION_REQUIRED;
	}

	@CommandHandler
	public void handle(ActivateAccountCommand command) {
		assertStatus(AccountStatus.ACTIVATION_REQUIRED);
		apply(new AccountActivatedEvent(accountId));
	}

	@EventSourcingHandler
	public void on(AccountActivatedEvent event) {
		status = AccountStatus.ACTIVE;
	}

	@CommandHandler
	public void handle(ExpireActivationCommand command) {
		assertStatus(AccountStatus.ACTIVATION_REQUIRED);
		apply(new ActivationExpiredEvent(accountId));
	}

	@EventSourcingHandler
	public void on(ActivationExpiredEvent event) {
		status = AccountStatus.ACTIVATION_EXPIRED;
	}

	@CommandHandler
	public void handle(SendMoneyCommand command) {
		assertStatus(AccountStatus.ACTIVE);
		apply(new MoneySentEvent(accountId, command.getRecipientId(), command.getAmount()));
	}

	@EventSourcingHandler
	public void on(MoneySentEvent event) {
		subtract(event.getAmount());
	}

	@CommandHandler
	public void handle(ReceiveMoneyCommad command) {
		assertStatus(AccountStatus.ACTIVE);
		apply(new MoneyReceivedEvent(accountId, command.getSenderId(), command.getAmount()));
	}

	@EventSourcingHandler
	public void on(MoneyReceivedEvent event) {
		add(event.getAmount());
	}

	@CommandHandler
	public void handle(RecoverMoneyCommand command) {
		apply(new MoneyRecoveredEvent(accountId, command.getAmount()));
	}

	@EventSourcingHandler
	public void on(MoneyRecoveredEvent event) {
		add(event.getAmount());
	}

	private void add(BigDecimal amount) {
		balance = balance.add(amount);
	}

	private void subtract(BigDecimal amount) {
		balance = balance.subtract(amount);
	}

	private void assertStatus(AccountStatus expectedStatus) {
		if (status != expectedStatus) {
			throw new UnexpectedAccountStatusException(accountId, status, expectedStatus);
		}
	}
}
