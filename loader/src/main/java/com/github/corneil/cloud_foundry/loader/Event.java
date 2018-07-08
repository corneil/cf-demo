package com.github.corneil.cloud_foundry.loader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String id;
	private String eventSource;
	private Date eventDate;
}
