package com.myucel.account.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {

	AccountBalance findByAccountId(String accountId);
}
