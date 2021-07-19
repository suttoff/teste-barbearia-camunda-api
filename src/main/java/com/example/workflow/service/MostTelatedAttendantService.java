package com.example.workflow.service;

import com.example.workflow.dto.BarberDTO;
import com.example.workflow.dto.BarbersResponseDTO;
import com.example.workflow.dto.ClientDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MostTelatedAttendantService {

    public String findMostTelated(BarbersResponseDTO barbers, ClientDTO clientRequest) {
        Optional<BarberDTO> barberGR = barbers.getBarbers()
                .stream()
                .filter(barberDTO -> barberDTO.getId().equals(clientRequest.getBarberGR())).findFirst();

        if (barberGR.isEmpty()) {
            return "N";
        } else {
            return barberGR.get().getId().toString();
        }
    }
}
