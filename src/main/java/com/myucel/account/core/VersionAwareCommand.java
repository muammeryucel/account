package com.myucel.account.core;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateVersion;

public class VersionAwareCommand extends BaseCommand {

	@TargetAggregateVersion
	private final Long version;

	public VersionAwareCommand(String accountId, Long version) {
		super(accountId);
		this.version = version;
	}
	
	public Long getVersion() {
		return version;
	}
}
