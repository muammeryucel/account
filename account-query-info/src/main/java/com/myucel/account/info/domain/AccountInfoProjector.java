package com.myucel.account.info.domain;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.myucel.account.api.activation.AccountActivatedEvent;
import com.myucel.account.api.activation.ActivationExpiredEvent;
import com.myucel.account.api.registration.AccountCreatedEvent;
import com.myucel.account.info.query.FindAllQuery;

@Component
@ProcessingGroup("info")
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
		
//		throw new RuntimeException("Test Exception!");
	}

	@EventHandler
	public void on(AccountActivatedEvent event, @Timestamp Instant activationDate) {
		AccountInfo account = getAccount(event.getAccountId());
		account.setActivationDate(Date.from(activationDate));
		account.setStatus("Activated");
		repository.save(account);	
		
//		throw new RuntimeException("Test Exception!");
	}

	@EventHandler
	public void on(ActivationExpiredEvent event, @Timestamp Instant expirationDate) {
		AccountInfo account = getAccount(event.getAccountId());
		account.setExpirationDate(Date.from(expirationDate));
		account.setStatus("Activation expired");
		repository.save(account);
	}
	
	@QueryHandler
	public List<AccountInfoProjection> handleFindAllQuery(FindAllQuery query) {
		return repository.findProjectionBy();
	}

	private AccountInfo getAccount(String accountId) {
		return repository.findByAccountId(accountId);
	}
}
