package com.myucel.account.balance.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import com.myucel.account.balance.domain.AccountBalanceProjection;
import com.myucel.account.balance.query.FindAllQuery;
import com.myucel.account.balance.query.FindByAccountIdQuery;

@Service
public class AccountBalanceService {

	private QueryGateway gateway;

	public AccountBalanceService(QueryGateway gateway) {
		super();
		this.gateway = gateway;
	}

	public CompletableFuture<List<AccountBalanceProjection>> getAccountBalances() {
		return gateway.query(new FindAllQuery(), ResponseTypes.multipleInstancesOf(AccountBalanceProjection.class));
	}

	public CompletableFuture<AccountBalanceProjection> getAccountBalance(String accountId) {
		return gateway.query(new FindByAccountIdQuery(accountId), AccountBalanceProjection.class);
	}
}
