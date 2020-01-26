package com.myucel.account;

import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

	@Bean
	public SnapshotTriggerDefinition defaultSnapshotTriggerDefinition(Snapshotter snapshotter,
			@Value("${application.axon.snapshot.threshold}") int threshold) {
		return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
	}

	@Bean
	public DeadlineManager deadlineManager(AxonConfiguration configuration, TransactionManager transactionManager) {
		return SimpleDeadlineManager.builder().scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration))
				.transactionManager(transactionManager).build();
	}
}