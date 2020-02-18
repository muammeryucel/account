package com.myucel.account.balance.ordercheck;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.axonframework.config.Configurer;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.stereotype.Component;

import com.myucel.account.balance.ordercheck.AggregateVersion.AggregateVersionId;

@Component
public class MessageDuplicationAndOrderCheckingInterceptor
		implements MessageHandlerInterceptor<EventMessage<?>>, ConfigurerModule {

	private static final Log LOGGER = LogFactory.getLog(MessageDuplicationAndOrderCheckingInterceptor.class);

	private final AggregateVersionRepository repository;

	public MessageDuplicationAndOrderCheckingInterceptor(AggregateVersionRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Object handle(UnitOfWork<? extends EventMessage<?>> unitOfWork, InterceptorChain interceptorChain)
			throws Exception {

		if (unitOfWork.getMessage() instanceof DomainEventMessage) {

			DomainEventMessage<?> message = (DomainEventMessage<?>) unitOfWork.getMessage();

			String aggregateType = message.getType();
			String aggregateId = message.getAggregateIdentifier();
			long newVersion = message.getSequenceNumber();

			AggregateVersionId id = new AggregateVersionId(aggregateType, aggregateId);

			if (newVersion == 0) {
				repository.save(new AggregateVersion(id));
			} else {
				long previousVersion = newVersion - 1;
				AggregateVersion aggregateVersion = repository.findById(id)
						.orElseThrow(() -> new IllegalStateException("Unrecognized aggregate: " + id));
				Long currentVersion = aggregateVersion.getAggregateVersion();
				if (currentVersion >= newVersion) {
					LOGGER.debug("Duplicate message detected and ignored: " + message);
					return null;
				} else if (!currentVersion.equals(previousVersion)) {
					throw new IllegalStateException("Message order corrupted! Aggregate: " + id + ", Current version: "
							+ currentVersion + ", New Version: " + newVersion);
				} else {
					aggregateVersion.setAggregateVersion(newVersion);
					repository.save(aggregateVersion);
				}
			}
		}
		return interceptorChain.proceed();
	}

	@Override
	public void configureModule(Configurer configurer) {
		configurer.eventProcessing().registerDefaultHandlerInterceptor((configuration, processorName) -> this);
	}
}
