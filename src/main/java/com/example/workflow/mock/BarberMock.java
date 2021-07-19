package com.example.workflow.mock;

import com.example.workflow.dto.BarberDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BarberMock {

    public List<BarberDTO> listBarber() {
        List<BarberDTO> list = new ArrayList<>();
        list.add(makeBarber(2L, "Oliveira"));
        list.add(makeBarber(1L, "Matheus"));
        return list;
    }

    public BarberDTO consultBarber() {
        return makeBarber(1L, "Matheus");
    }

    private BarberDTO makeBarber(Long id, String nome) {
        BarberDTO b1 = new BarberDTO();
        b1.setId(id);
        b1.setName(nome);
        return b1;
    }
}
