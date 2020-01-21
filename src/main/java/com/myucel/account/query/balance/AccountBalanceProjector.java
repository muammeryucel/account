package com.myucel.account.query.balance;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

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
	public void on(AccountCreatedEvent event) {
		repository.save(new AccountBalance(event.getAccountId(), event.getInitialBalance()));
	}

	@EventHandler
	public void on(MoneySentEvent event) {
		getAccount(event.getSenderId()).subtract(event.getAmount());
	}

	@EventHandler
	public void on(MoneyReceivedEvent event) {
		getAccount(event.getRecipientId()).add(event.getAmount());
	}
	
	@EventHandler
	public void on(MoneyRecoveredEvent event) {
		getAccount(event.getSenderId()).add(event.getAmount());
	}

	private AccountBalance getAccount(String accountId) {
		return repository.findByAccountId(accountId);
	}
}
