package com.myucel.account;

import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.extensions.amqp.eventhandling.PackageRoutingKeyResolver;
import org.axonframework.extensions.amqp.eventhandling.RoutingKeyResolver;
import org.axonframework.spring.config.AxonConfiguration;
import org.axonframework.springboot.autoconfig.AxonServerAutoConfiguration;
import org.axonframework.springboot.autoconfig.JpaEventStoreAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myucel.account.api.registration.AccountCreatedEvent;

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

	@Bean
	public ListenerInvocationErrorHandler listenerInvocationErrorHandler() {
		return PropagatingErrorHandler.INSTANCE;
	}	
	
    @Bean
    public RoutingKeyResolver routingKeyResolver() {
    	return new PackageRoutingKeyResolver();
    	// For rabbitmq consistent hashing exchange, we need homogeneous distribution of route keys
//        return message->((AccountCreatedEvent)message.getPayload()).getAccountId();
    }
	
	@Configuration
	@Profile("jpa")
	@EnableAutoConfiguration(exclude = AxonServerAutoConfiguration.class)
	public static class JpaEventStoreConfig {

	}

	@Configuration
	@Profile("jdbc")
	@EnableAutoConfiguration(exclude = { AxonServerAutoConfiguration.class, JpaEventStoreAutoConfiguration.class })
	public static class JdbcEventStoreConfig {

	}
}