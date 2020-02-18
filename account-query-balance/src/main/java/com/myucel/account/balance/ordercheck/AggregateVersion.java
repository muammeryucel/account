package com.myucel.account.balance.ordercheck;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "AGGREGATE_VERSION")
public class AggregateVersion implements Serializable {

	@EmbeddedId
	private AggregateVersionId id;

	@Version
	private Long version;
	
	@Column(name = "AGGREGATE_VERSION", nullable = false)
	private Long aggregateVersion = 0L;

	public AggregateVersion() {
		super();
	}

	public AggregateVersion(AggregateVersionId id) {
		this();
		this.id = id;
	}

	public AggregateVersion(String type, String id) {
		this(new AggregateVersionId(type, id));
	}

	public Long getAggregateVersion() {
		return aggregateVersion;
	}

	public void setAggregateVersion(Long aggregateVersion) {
		this.aggregateVersion = aggregateVersion;
	}
		
	@Embeddable
	public static class AggregateVersionId implements Serializable {

		@Column(name = "AGGR_TYPE")
		private String aggregateType;

		@Column(name = "AGGR_ID")
		private String aggregateId;

		public AggregateVersionId(String aggregateType, String aggregateId) {
			super();
			this.aggregateType = aggregateType;
			this.aggregateId = aggregateId;
		}

		public String getAggregateId() {
			return aggregateId;
		}

		public String getAggregateType() {
			return aggregateType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((aggregateId == null) ? 0 : aggregateId.hashCode());
			result = prime * result + ((aggregateType == null) ? 0 : aggregateType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AggregateVersionId other = (AggregateVersionId) obj;
			if (aggregateId == null) {
				if (other.aggregateId != null)
					return false;
			} else if (!aggregateId.equals(other.aggregateId))
				return false;
			if (aggregateType == null) {
				if (other.aggregateType != null)
					return false;
			} else if (!aggregateType.equals(other.aggregateType))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Type: " + aggregateType + ", ID: " + aggregateId;
		}
	}
}
