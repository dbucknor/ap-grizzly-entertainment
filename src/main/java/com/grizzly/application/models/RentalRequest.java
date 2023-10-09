package com.grizzly.application.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "RentalRequest")
public class RentalRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String requestId;
	private Date date;
	private Timestamp time;
	private boolean approved;
	private Quote quote;
	private String approvedBy;

	// Primary constructor
	public RentalRequest(String requestId, Date date, Timestamp time, boolean approved, Quote quote,
			String approvedBy) {
		this.requestId = requestId;
		this.date = date;
		this.time = time;
		this.approved = approved;
		this.quote = quote;
		this.approvedBy = approvedBy;
	}

	// Secondary constructor
	public RentalRequest(Date date, Timestamp time, boolean approved, Quote quote, String approvedBy) {
		this(null, date, time, approved, quote, approvedBy);
	}

	// Copy constructor
	public RentalRequest(RentalRequest other) {
		this(other.requestId, other.date, other.time, other.approved, other.quote, other.approvedBy);
	}

}
