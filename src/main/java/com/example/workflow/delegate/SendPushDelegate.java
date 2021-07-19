package com.example.workflow.delegate;

import com.example.workflow.dto.SchedulingDTO;
import com.example.workflow.mock.PushMock;
import com.example.workflow.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("SendPushDelegate")
@AllArgsConstructor
public class SendPushDelegate implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(SendPushDelegate.class);
    private final PushMock mock;

    @Override
    public void execute(DelegateExecution execution) {
        LOG.info("Iniciando SendPushDelegate");

        SchedulingDTO scheduling = JsonUtil.jsonToObject((String) execution.getVariable("scheduling"), SchedulingDTO.class);
        String push = this.mock.sendPush(scheduling);

        execution.setVariable("push", push);
        LOG.info("Finalizando SendPushDelegate");
    }
}
