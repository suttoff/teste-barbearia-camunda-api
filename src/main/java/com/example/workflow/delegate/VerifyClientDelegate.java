package com.example.workflow.delegate;

import com.example.workflow.dto.ClientDTO;
import com.example.workflow.dto.SchedulingDTO;
import com.example.workflow.mock.ClientMock;
import com.example.workflow.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("VerifyClientDelegate")
@AllArgsConstructor
public class VerifyClientDelegate implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(VerifyClientDelegate.class);
    private final ClientMock mock;

    @Override
    public void execute(DelegateExecution execution) {
        LOG.info("Iniciando VerifyClientDelegate");

        ClientDTO clientRequest = JsonUtil.jsonToObject((String) execution.getVariable("client"), ClientDTO.class);
        SchedulingDTO scheduling = JsonUtil.jsonToObject((String) execution.getVariable("scheduling"), SchedulingDTO.class);

        ClientDTO client = mock.findSpecificClient(clientRequest.getId());

        if(Objects.isNull(client)) {
            execution.setVariable("isClient", false);
        } else {
            scheduling.setClient(client);
            execution.setVariable("isClient", true);
            execution.setVariable("client", JsonUtil.objectToJson(client));
        }

        LOG.info("Finalizando VerifyClientDelegate");
    }
}
