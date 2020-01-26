package com.myucel.account.query.balance;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.SequenceNumber;
import org.springframework.stereotype.Component;

import com.myucel.account.core.balance.DepositedEvent;
import com.myucel.account.core.balance.WithdrawnEvent;
import com.myucel.account.core.registration.AccountCreatedEvent;
import com.myucel.account.core.transfer.MoneyReceivedEvent;
import com.myucel.account.core.transfer.MoneyRecoveredEvent;
import com.myucel.account.core.transfer.MoneySentEvent;

@Component
public class AccountBalanceProjector {

	private final AccountBalanceRepository repository;

	public AccountBalanceProjector(AccountBalanceRepository repository) {
		super();
		this.repository = repository;
	}

	@EventHandler
	public void on(AccountCreatedEvent event, @SequenceNumber Long version) {
		AccountBalance account = new AccountBalance(event.getAccountId(), event.getInitialBalance());
		account.setVersion(version);
		repository.save(account);
	}

	@EventHandler
	public void on(DepositedEvent event, @SequenceNumber Long version) {
		AccountBalance account = getAccount(event.getAccountId());
		account.setVersion(version);
		account.addToBalance(event.getAmount());
		repository.save(account);
	}

	@EventHandler
	public void on(WithdrawnEvent event, @SequenceNumber Long version) {
		AccountBalance account = getAccount(event.getAccountId());
		account.setVersion(version);
		account.subtractFromBalance(event.getAmount());
		repository.save(account);
	}

	@EventHandler
	public void on(MoneySentEvent event, @SequenceNumber Long version) {
		AccountBalance account = getAccount(event.getSenderId());
		account.setVersion(version);
		account.subtractFromBalance(event.getAmount());
		repository.save(account);
	}

	@EventHandler
	public void on(MoneyReceivedEvent event, @SequenceNumber Long version) {
		AccountBalance account = getAccount(event.getRecipientId());
		account.setVersion(version);
		account.addToBalance(event.getAmount());
		repository.save(account);
	}

	@EventHandler
	public void on(MoneyRecoveredEvent event, @SequenceNumber Long version) {
		AccountBalance account = getAccount(event.getSenderId());
		account.setVersion(version);
		account.addToBalance(event.getAmount());
		repository.save(account);
	}

	private AccountBalance getAccount(String accountId) {
		return repository.findByAccountId(accountId);
	}
}
