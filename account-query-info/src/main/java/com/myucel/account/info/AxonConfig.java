package com.myucel.account.info;

import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

	@Bean
	public ListenerInvocationErrorHandler listenerInvocationErrorHandler() {
		return PropagatingErrorHandler.INSTANCE;
	}
}
