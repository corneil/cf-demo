package com.github.corneil.cloudfoundry.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;
    @Indexed
    @NotNull
    private String eventSource;
    @Indexed
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date eventDate;

}
