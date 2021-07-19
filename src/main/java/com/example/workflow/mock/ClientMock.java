package com.example.workflow.mock;

import com.example.workflow.dto.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public class ClientMock {

    public ClientDTO findSpecificClient(Long id) {
        if (id.equals(56L)) {
            return makeClient(id, "Matheus", 1L);
        } else {
            return null;
        }
    }

    private ClientDTO makeClient(Long id, String nome, Long mr) {
        ClientDTO c = new ClientDTO();
        c.setId(id);
        c.setName(nome);
        c.setBarberGR(mr);
        return c;
    }
}
