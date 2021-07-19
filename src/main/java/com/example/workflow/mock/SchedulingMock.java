package com.example.workflow.mock;

import com.example.workflow.domain.StatusEnum;
import com.example.workflow.dto.SchedulingDTO;
import org.springframework.stereotype.Component;

@Component
public class SchedulingMock {

    public SchedulingDTO update(SchedulingDTO scheduling, String push, String finalOpinion) {
        scheduling.setPush(!push.isEmpty());
        scheduling.setFinalOpinion(finalOpinion);
        scheduling.setStatus(StatusEnum.A);
        //simulação de atualização de BD.
        return scheduling;
    }
}
