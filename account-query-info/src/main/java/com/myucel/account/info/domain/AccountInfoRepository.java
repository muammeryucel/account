package com.myucel.account.info.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {

	AccountInfo findByAccountId(String accountId);

	List<AccountInfoProjection> findProjectionBy();
}
