package com.myucel.account.balance.domain;

import java.util.List;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.SequenceNumber;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.myucel.account.api.AccountEvent;
import com.myucel.account.api.balance.DepositedEvent;
import com.myucel.account.api.balance.WithdrawnEvent;
import com.myucel.account.api.registration.AccountCreatedEvent;
import com.myucel.account.api.transfer.MoneyReceivedEvent;
import com.myucel.account.api.transfer.MoneyRecoveredEvent;
import com.myucel.account.api.transfer.MoneySentEvent;
import com.myucel.account.balance.query.FindAllQuery;
import com.myucel.account.balance.query.FindByAccountIdQuery;

@Component
@ProcessingGroup("balance")
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

	@EventHandler
	public void fallbackEventHandler(AccountEvent event) {
		// It must be the last event handler --> Are you sure? Check it!
		System.out.println("Noop event handler: " + event);
	}

	@QueryHandler
	public List<AccountBalanceProjection> handleFindAllQuery(FindAllQuery query) {
		return repository.findProjectionBy();
	}

	@QueryHandler
	public AccountBalanceProjection handleFindByAccountIdQuery(FindByAccountIdQuery query) {
		return repository.findProjectionByAccountId(query.getAccountId());
	}

	private AccountBalance getAccount(String accountId) {
		return repository.findByAccountId(accountId);
	}
}
