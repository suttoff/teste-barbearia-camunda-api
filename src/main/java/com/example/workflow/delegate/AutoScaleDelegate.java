package com.example.workflow.delegate;

import com.example.workflow.dto.BarberDTO;
import com.example.workflow.dto.BarbersResponseDTO;
import com.example.workflow.dto.SchedulingDTO;
import com.example.workflow.util.JsonUtil;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("AutoScaleDelegate")
public class AutoScaleDelegate implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(AutoScaleDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        LOG.info("Iniciando AutoScaleDelegate");

        BarbersResponseDTO dto = JsonUtil.jsonToObject((String) execution.getVariable("barbers"), BarbersResponseDTO.class);
        SchedulingDTO scheduling = JsonUtil.jsonToObject((String) execution.getVariable("scheduling"), SchedulingDTO.class);

        BarberDTO barber;
        if(dto.getBarbers().size() > 0) {
            barber = dto.getBarbers().get(0);
            scheduling.setBarber(barber);

            execution.setVariable("scheduling", JsonUtil.objectToJson(scheduling));
            execution.setVariable("mostRelated", barber.getId().toString());
        }
        LOG.info("Finalizando AutoScaleDelegate");
    }
}
