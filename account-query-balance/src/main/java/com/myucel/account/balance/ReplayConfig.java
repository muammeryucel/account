package com.myucel.account.balance;

import java.time.Duration;
import java.time.Instant;

import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.jdbc.DataSourceConnectionProvider;
import org.axonframework.common.stream.BlockingStream;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.TrackedEventMessage;
import org.axonframework.eventhandling.TrackingToken;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.jdbc.EventSchema;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.messaging.StreamableMessageSource;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("replay")
public class ReplayConfig {

	@Value("${application.replay.jdbc-event-store.url}")
	private String jdbcEventStoreUrl;

	@Value("${application.replay.jdbc-event-store.username}")
	private String jdbcEventStoreUsername;

	@Value("${application.replay.jdbc-event-store.password}")
	private String jdbcEventStorePassword;

	@Bean
	public JdbcEventStoreDelegatingStreamableMessageSource replayMessageSource() {
		return new JdbcEventStoreDelegatingStreamableMessageSource(jdbcEventStoreUrl, jdbcEventStoreUsername,
				jdbcEventStorePassword);
	}

	private class JdbcEventStoreDelegatingStreamableMessageSource
			implements StreamableMessageSource<TrackedEventMessage<?>> {

		private EventStore eventStore;

		public JdbcEventStoreDelegatingStreamableMessageSource(String url, String username, String password) {

			HikariDataSource dataSource = new HikariDataSource();
			dataSource.setJdbcUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);

			DefaultTransactionDefinition txDef = new DefaultTransactionDefinition();
			txDef.setReadOnly(true);
			txDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

			TransactionManager txManager = new SpringTransactionManager(new DataSourceTransactionManager(dataSource),
					txDef);

			ConnectionProvider connectionProvider = new DataSourceConnectionProvider(dataSource);

			EventSchema schema = EventSchema.builder().eventTable("DOMAIN_EVENT_ENTRY")
					.globalIndexColumn("GLOBAL_INDEX").eventIdentifierColumn("EVENT_IDENTIFIER")
					.metaDataColumn("META_DATA").payloadColumn("PAYLOAD").payloadRevisionColumn("PAYLOAD_REVISION")
					.payloadTypeColumn("PAYLOAD_TYPE").timestampColumn("TIME_STAMP")
					.aggregateIdentifierColumn("AGGREGATE_IDENTIFIER").sequenceNumberColumn("SEQUENCE_NUMBER")
					.typeColumn("TYPE").build();

			JdbcEventStorageEngine storageEngine = JdbcEventStorageEngine.builder().schema(schema)
					.connectionProvider(connectionProvider).transactionManager(txManager).build();

			eventStore = EmbeddedEventStore.builder().storageEngine(storageEngine).build();
		}

		@Override
		public BlockingStream<TrackedEventMessage<?>> openStream(TrackingToken trackingToken) {
			return eventStore.openStream(trackingToken);
		}

		@Override
		public TrackingToken createHeadToken() {
			return eventStore.createHeadToken();
		}

		@Override
		public TrackingToken createTailToken() {
			return eventStore.createTailToken();
		}

		@Override
		public TrackingToken createTokenAt(Instant dateTime) {
			return eventStore.createTokenAt(dateTime);
		}

		@Override
		public TrackingToken createTokenSince(Duration duration) {
			return eventStore.createTokenSince(duration);
		}
	}
}
