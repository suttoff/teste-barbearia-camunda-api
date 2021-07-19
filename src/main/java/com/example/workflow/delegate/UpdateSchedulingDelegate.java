package com.example.workflow.delegate;

import com.example.workflow.dto.SchedulingDTO;
import com.example.workflow.mock.SchedulingMock;
import com.example.workflow.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("UpdateSchedulingDelegate")
@AllArgsConstructor
public class UpdateSchedulingDelegate implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateSchedulingDelegate.class);
    private final SchedulingMock mock;

    @Override
    public void execute(DelegateExecution execution) {
        LOG.info("Iniciando UpdateSchedulingDelegate");

        SchedulingDTO scheduling = JsonUtil.jsonToObject((String) execution.getVariable("scheduling"), SchedulingDTO.class);
        String push = (String) execution.getVariable("push");
        String finalOpinion = (String) execution.getVariable("finalOpinion");

        SchedulingDTO schedulingFinal = this.mock.update(scheduling, push, finalOpinion);
        execution.setVariable("scheduling", schedulingFinal);

        LOG.info("Finalizando UpdateSchedulingDelegate");
    }
}
