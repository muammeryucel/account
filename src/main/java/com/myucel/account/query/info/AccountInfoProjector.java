package com.myucel.account.query.info;

import java.time.Instant;
import java.util.Date;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import com.myucel.account.command.AccountStatus;
import com.myucel.account.core.activation.AccountActivatedEvent;
import com.myucel.account.core.activation.ActivationExpiredEvent;
import com.myucel.account.core.registration.AccountCreatedEvent;

@Component
public class AccountInfoProjector {

	private final AccountInfoRepository repository;

	public AccountInfoProjector(AccountInfoRepository repository) {
		super();
		this.repository = repository;
	}

	@EventHandler
	public void on(AccountCreatedEvent event, @Timestamp Instant creationDate) {
		AccountInfo account = new AccountInfo(event.getAccountId(), event.getPhoneNumber(), event.getInitialBalance());
		account.setCreationDate(Date.from(creationDate));
		repository.save(account);
	}

	@EventHandler
	public void on(AccountActivatedEvent event, @Timestamp Instant activationDate) {
		AccountInfo account = getAccount(event.getAccountId());
		account.setActivationDate(Date.from(activationDate));
		account.setStatus(AccountStatus.ACTIVE);
		repository.save(account);
	}

	@EventHandler
	public void on(ActivationExpiredEvent event, @Timestamp Instant expirationDate) {
		AccountInfo account = getAccount(event.getAccountId());
		account.setExpirationDate(Date.from(expirationDate));
		account.setStatus(AccountStatus.ACTIVATION_EXPIRED);
		repository.save(account);
	}

	private AccountInfo getAccount(String accountId) {
		return repository.findByAccountId(accountId);
	}
}
