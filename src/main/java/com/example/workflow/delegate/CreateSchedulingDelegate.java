package com.example.workflow.delegate;

import com.example.workflow.dto.ClientDTO;
import com.example.workflow.dto.SchedulingDTO;
import com.example.workflow.service.SchedulingService;
import com.example.workflow.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@Service("CreateSchedulingDelegate")
@AllArgsConstructor
public class CreateSchedulingDelegate implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(CreateSchedulingDelegate.class);
    private final SchedulingService service;

    @Override
    public void execute(DelegateExecution execution) throws IOException, ParseException {
        LOG.info("Iniciando CreateSchedulingDelegate");

        String clientVar = String.valueOf(execution.getVariable("client"));
        Date schedule = (Date) execution.getVariable("schedule");
        String businessKey = execution.getBusinessKey();

        ClientDTO client = JsonUtil.jsonToObject(clientVar, ClientDTO.class);
        SchedulingDTO scheduling = this.service.createScheduling(schedule, client, businessKey);

        execution.setVariable("client", clientVar);
        execution.setVariable("startInstance", this.service.dateNow());
        execution.setVariable("scheduling", JsonUtil.objectToJson(scheduling));

        LOG.info("Finalizando CreateSchedulingDelegate");
    }
}
