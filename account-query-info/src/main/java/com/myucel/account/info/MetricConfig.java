package com.myucel.account.info;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.Configurer;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.config.MessageMonitorFactory;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.micrometer.CapacityMonitor;
import org.axonframework.micrometer.MessageCountingMonitor;
import org.axonframework.micrometer.MessageTimerMonitor;
import org.axonframework.micrometer.PayloadTypeMessageMonitorWrapper;
import org.axonframework.monitoring.MultiMessageMonitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class MetricConfig {

	@Bean
	public ConfigurerModule metricConfigurer(MeterRegistry meterRegistry) {
		return configurer -> {
			instrumentEventProcessors(meterRegistry, configurer);
		};
	}

	private void instrumentEventProcessors(MeterRegistry meterRegistry, Configurer configurer) {
		MessageMonitorFactory messageMonitorFactory = (configuration, componentType, componentName) -> {
			// We want to count the messages per type of event being published.
			PayloadTypeMessageMonitorWrapper<MessageCountingMonitor> messageCounterPerType = new PayloadTypeMessageMonitorWrapper<>(
					monitorName -> MessageCountingMonitor.buildMonitor(monitorName, meterRegistry),
					clazz -> componentName + "_" + clazz.getSimpleName());
			// And we also want to set a message timer per payload type
			PayloadTypeMessageMonitorWrapper<MessageTimerMonitor> messageTimerPerType = new PayloadTypeMessageMonitorWrapper<>(
					monitorName -> MessageTimerMonitor.buildMonitor(monitorName, meterRegistry),
					clazz -> componentName + "_" + clazz.getSimpleName());
			// Which we group in a MultiMessageMonitor
			return new MultiMessageMonitor<>(messageCounterPerType, messageTimerPerType);
		};
		configurer.configureMessageMonitor(TrackingEventProcessor.class, messageMonitorFactory);
	}
}