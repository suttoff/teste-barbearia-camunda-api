package com.example.workflow.dto;

import com.example.workflow.domain.StatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchedulingDTO {
    private String id;
    private BarberDTO barber;
    private ClientDTO client;

    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:SS", iso = DateTimeFormat.ISO.DATE)
    private Date date;

    private Boolean push;
    private String managerCode;
    private String finalOpinion;
    private StatusEnum status;
}
