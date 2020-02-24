package com.myucel.account.balance.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {

	AccountBalance findByAccountId(String accountId);
	
	List<AccountBalanceProjection> findProjectionBy();

	AccountBalanceProjection findProjectionByAccountId(String accountId); 
}
