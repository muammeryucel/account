package com.myucel.account.balance.ordercheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myucel.account.balance.ordercheck.AggregateVersion.AggregateVersionId;

@Repository
public interface AggregateVersionRepository extends JpaRepository<AggregateVersion, AggregateVersionId> {

}
