package com.myucel.account.info.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import com.myucel.account.info.domain.AccountInfoProjection;
import com.myucel.account.info.query.FindAllQuery;

@Service
public class AccountInfoService {

	private final QueryGateway gateway;

	public AccountInfoService(QueryGateway gateway) {
		super();
		this.gateway = gateway;
	}

	public CompletableFuture<List<AccountInfoProjection>> getAccounts() {
		return gateway.query(new FindAllQuery(), ResponseTypes.multipleInstancesOf(AccountInfoProjection.class));
	}
}
