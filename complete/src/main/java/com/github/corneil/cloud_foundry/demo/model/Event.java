package com.github.corneil.cloud_foundry.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String eventSource;
	@Temporal(value = TemporalType.TIMESTAMP)
	@NotNull
	private Date eventDate;

	public Event(@NotNull String eventSource, @NotNull Date eventDate) {
		this.eventSource = eventSource;
		this.eventDate = eventDate;
	}

	public Event() {
	}
}
