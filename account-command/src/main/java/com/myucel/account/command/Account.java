package com.myucel.account.command;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.conflictresolution.ConflictResolver;
import org.axonframework.eventsourcing.conflictresolution.Conflicts;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.myucel.account.api.activation.AccountActivatedEvent;
import com.myucel.account.api.activation.ActivateAccountCommand;
import com.myucel.account.api.activation.ActivationExpiredEvent;
import com.myucel.account.api.activation.ExpireActivationCommand;
import com.myucel.account.api.balance.DepositCommand;
import com.myucel.account.api.balance.DepositedEvent;
import com.myucel.account.api.balance.WithdrawCommand;
import com.myucel.account.api.balance.WithdrawnEvent;
import com.myucel.account.api.registration.AccountCreatedEvent;
import com.myucel.account.api.registration.CreateAccountCommand;
import com.myucel.account.api.transfer.MoneyReceivedEvent;
import com.myucel.account.api.transfer.MoneyRecoveredEvent;
import com.myucel.account.api.transfer.MoneySentEvent;
import com.myucel.account.api.transfer.ReceiveMoneyCommad;
import com.myucel.account.api.transfer.RecoverMoneyCommand;
import com.myucel.account.api.transfer.SendMoneyCommand;
import com.myucel.account.command.exception.UnexpectedAccountStatusException;

@Aggregate(snapshotTriggerDefinition = "defaultSnapshotTriggerDefinition")
public class Account {

	private static final List<Class<? extends Object>> BALANCE_CHANGING_EVENT_TYPES = Arrays
			.asList(DepositedEvent.class, WithdrawnEvent.class, MoneySentEvent.class, MoneyReceivedEvent.class);;

	private static Predicate<List<DomainEventMessage<?>>> balanceChangingEventMatching() {
		return Conflicts.payloadMatching(event -> BALANCE_CHANGING_EVENT_TYPES.stream()
				.anyMatch(type -> type.isAssignableFrom(event.getClass())));
	}

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
	public void handle(DepositCommand command, ConflictResolver conflictResolver) {
		conflictResolver.detectConflicts(balanceChangingEventMatching());
		assertStatus(AccountStatus.ACTIVE);
		apply(new DepositedEvent(accountId, command.getAmount()));
	}

	@EventSourcingHandler
	public void on(DepositedEvent event) {
		addToBalance(event.getAmount());
	}

	@CommandHandler
	public void handle(WithdrawCommand command, ConflictResolver conflictResolver) {
		conflictResolver.detectConflicts(balanceChangingEventMatching());
		assertStatus(AccountStatus.ACTIVE);
		apply(new WithdrawnEvent(accountId, command.getAmount()));
	}

	@EventSourcingHandler
	public void on(WithdrawnEvent event) {
		subtractFromBalance(event.getAmount());
	}

	@CommandHandler
	public void handle(SendMoneyCommand command) {
		assertStatus(AccountStatus.ACTIVE);
		apply(new MoneySentEvent(accountId, command.getRecipientId(), command.getAmount()));
	}

	@EventSourcingHandler
	public void on(MoneySentEvent event) {
		subtractFromBalance(event.getAmount());
	}

	@CommandHandler
	public void handle(ReceiveMoneyCommad command) {
		assertStatus(AccountStatus.ACTIVE);
		apply(new MoneyReceivedEvent(accountId, command.getSenderId(), command.getAmount()));
	}

	@EventSourcingHandler
	public void on(MoneyReceivedEvent event) {
		addToBalance(event.getAmount());
	}

	@CommandHandler
	public void handle(RecoverMoneyCommand command) {
		apply(new MoneyRecoveredEvent(accountId, command.getAmount()));
	}

	@EventSourcingHandler
	public void on(MoneyRecoveredEvent event) {
		addToBalance(event.getAmount());
	}

	private void addToBalance(BigDecimal amount) {
		balance = balance.add(amount);
	}

	private void subtractFromBalance(BigDecimal amount) {
		balance = balance.subtract(amount);
	}

	private void assertStatus(AccountStatus expectedStatus) {
		if (status != expectedStatus) {
			throw new UnexpectedAccountStatusException(accountId, status, expectedStatus);
		}
	}
}
