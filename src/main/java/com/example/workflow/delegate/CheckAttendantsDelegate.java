package com.example.workflow.delegate;

import com.example.workflow.dto.BarberDTO;
import com.example.workflow.dto.BarbersResponseDTO;
import com.example.workflow.mock.BarberMock;
import com.example.workflow.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("CheckAttendantsDelegate")
@AllArgsConstructor
public class CheckAttendantsDelegate implements JavaDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(CheckAttendantsDelegate.class);
    //private final BarberShopClient client;
    private final BarberMock mock;

    @Override
    public void execute(DelegateExecution execution) {
        LOG.info("Iniciando CheckAttendantsDelegate");

        //List<Barbeiro> list = client.verificaOnline();
        List<BarberDTO> list = mock.listBarber();
        BarbersResponseDTO dto = new BarbersResponseDTO();
        dto.setBarbers(list);

        execution.setVariable("barbers", JsonUtil.objectToJson(dto));
        LOG.info("Finalizando CheckAttendantsDelegate");
    }
}
