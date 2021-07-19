package com.example.workflow.mock;

import com.example.workflow.dto.SchedulingDTO;
import org.springframework.stereotype.Component;

@Component
public class PushMock {

    public String sendPush(SchedulingDTO scheduling) {
        // aqui estou simulando um metodo onde vai postar em uma fila para que seja enviada a notificação push ao usuario

        return "Push enviado com sucesso.";
    }
}
