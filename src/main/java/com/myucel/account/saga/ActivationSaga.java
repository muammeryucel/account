package com.myucel.account.saga;

import static org.axonframework.modelling.saga.SagaLifecycle.associateWith;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.messaging.annotation.SourceId;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.myucel.account.core.activation.AccountActivatedEvent;
import com.myucel.account.core.activation.ActivateAccountCommand;
import com.myucel.account.core.activation.ActivationExpiredEvent;
import com.myucel.account.core.activation.ActivationRequestedEvent;
import com.myucel.account.core.activation.ExpireActivationCommand;
import com.myucel.account.core.exception.UnmatchedActivationCodeException;
import com.myucel.account.core.registration.AccountCreatedEvent;
import com.myucel.account.service.SmsService;

@Saga
public class ActivationSaga {

	private static final String ASSOCIATION_PROPERTY_ACCOUNT_ID = "accountId";
	private static final String ASSOCIATION_PROPERTY_PHONE_NUMBER = "phoneNumber";

	private static final String ACTIVATION_DEADLINE = "activation-deadline";
	private static final Duration ACTIVATION_TIMEOUT = Duration.ofSeconds(15);

	private static final Log LOGGER = LogFactory.getLog(ActivationSaga.class);

	@Autowired
	private transient CommandGateway commandGateway;

	@Autowired
	private transient DeadlineManager deadlineManager;

	@Autowired
	private transient SmsService smsService;

	private String activationCode;
	private String activationScheduleId;

	@StartSaga
	@SagaEventHandler(associationProperty = ASSOCIATION_PROPERTY_ACCOUNT_ID)
	public void on(AccountCreatedEvent event) {

		String accountId = event.getAccountId();
		String phoneNumber = event.getPhoneNumber();

		associateWith(ASSOCIATION_PROPERTY_PHONE_NUMBER, phoneNumber);

		activationScheduleId = deadlineManager.schedule(ACTIVATION_TIMEOUT, ACTIVATION_DEADLINE, accountId);
		activationCode = generateActivationCode();
		smsService.sendActivationCode(phoneNumber, activationCode);
		LOGGER.info("Waiting for activation: " + accountId);
	}

	@SagaEventHandler(associationProperty = ASSOCIATION_PROPERTY_PHONE_NUMBER)
	public void on(ActivationRequestedEvent event, @SourceId String accountId) {

		if (activationCode.equals(event.getActivationCode())) {
			deadlineManager.cancelSchedule(ACTIVATION_DEADLINE, activationScheduleId);
			commandGateway.sendAndWait(new ActivateAccountCommand(accountId));
		} else {
			throw new UnmatchedActivationCodeException(accountId);
		}
	}

	@EndSaga
	@SagaEventHandler(associationProperty = ASSOCIATION_PROPERTY_ACCOUNT_ID)
	public void on(AccountActivatedEvent event) {
		LOGGER.info("Account activated: " + event.getAccountId());
	}

	@DeadlineHandler(deadlineName = ACTIVATION_DEADLINE)
	public void handleActivationExpiration(String accountId) {
		commandGateway.sendAndWait(new ExpireActivationCommand(accountId));
	}

	@EndSaga
	@SagaEventHandler(associationProperty = ASSOCIATION_PROPERTY_ACCOUNT_ID)
	public void on(ActivationExpiredEvent event) {
		LOGGER.info("Account activation expired: " + event.getAccountId());
	}

	private String generateActivationCode() {
		return String.valueOf((int) Math.round(Math.random() * 1000));
	}
}
