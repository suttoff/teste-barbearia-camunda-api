package com.example.workflow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BarbersResponseDTO {
    private List<BarberDTO> barbers;
}
