package com.example.workflow.delegate;

import com.example.workflow.dto.BarbersResponseDTO;
import com.example.workflow.dto.ClientDTO;
import com.example.workflow.service.MostTelatedAttendantService;
import com.example.workflow.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("MostTelatedAttendantDelegate")
@AllArgsConstructor
public class MostTelatedAttendantDelegate implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(MostTelatedAttendantDelegate.class);
    private final MostTelatedAttendantService service;

    @Override
    public void execute(DelegateExecution execution) {
        LOG.debug("Iniciando MostTelatedAttendantDelegate");

        BarbersResponseDTO barbers = JsonUtil.jsonToObject((String) execution.getVariable("barbers"), BarbersResponseDTO.class);
        ClientDTO clientRequest = JsonUtil.jsonToObject((String) execution.getVariable("client"), ClientDTO.class);

        String aux = this.service.findMostTelated(barbers, clientRequest);
        execution.setVariable("mostRelated", aux);

        LOG.debug("Finalizando MostTelatedAttendantDelegate");
    }
}
