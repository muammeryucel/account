package com.myucel.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myucel.account.query.balance.AccountBalanceRepository;
import com.myucel.account.query.info.AccountInfoRepository;

@Service
@Transactional(readOnly = true)
public class AccountQueryService {

	private final AccountInfoRepository infoRepository;
	private final AccountBalanceRepository balanceRepository;

	public AccountQueryService(AccountInfoRepository infoRepository, AccountBalanceRepository balanceRepository) {
		super();
		this.infoRepository = infoRepository;
		this.balanceRepository = balanceRepository;
	}

	public List<AccountBalanceDTO> getAccountBalances() {
		return balanceRepository.findAll().stream().map(AccountBalanceDTO::of).collect(Collectors.toList());
	}

	public AccountBalanceDTO getAccountBalance(String accountId) {
		return AccountBalanceDTO.of(balanceRepository.findByAccountId(accountId));
	}

	public List<AccountInfoDTO> getAccounts() {
		return infoRepository.findAll().stream().map(AccountInfoDTO::of).collect(Collectors.toList());
	}
}
