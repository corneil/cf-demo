package com.github.corneil.cloudfoundry.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;
    @NotNull
    private String eventSource;
    @Temporal(value = TemporalType.TIMESTAMP)
    @NotNull
    private Date eventDate;

}
