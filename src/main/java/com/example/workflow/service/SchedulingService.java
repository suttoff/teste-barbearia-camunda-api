package com.example.workflow.service;

import com.example.workflow.domain.StatusEnum;
import com.example.workflow.dto.*;
import com.example.workflow.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class SchedulingService {

    public static final String AGUARDAR_GESTOR = "AguardarGestor";
    public static final String AGUARDAR_BARBER = "AguardarBarbeiro";

    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final RepositoryService repositoryService;

    public SchedulingDTO createScheduling(Date schedule, ClientDTO client, String businessKey){
        var agendamento = new SchedulingDTO();
        agendamento.setId(businessKey);
        agendamento.setClient(client);
        agendamento.setDate(schedule);
        agendamento.setStatus(StatusEnum.P);
        agendamento.setPush(Boolean.FALSE);
        return agendamento;
    }

    public Date dateNow() {
        return new Date(System.currentTimeMillis());
    }

    public ResponseSchedulingDTO start(SchedulingDTO scheduling) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("client", JsonUtil.objectToJson(scheduling.getClient()));
        variables.put("managerCode", scheduling.getManagerCode());
        variables.put("schedule", scheduling.getDate());

        ProcessDefinitionQuery process = this.repositoryService.createProcessDefinitionQuery().active().latestVersion();
        var instance = this.runtimeService.startProcessInstanceById(process.singleResult().getId(), scheduling.getId(), variables);
        return new ResponseSchedulingDTO(instance.getBusinessKey(), scheduling.getDate(), "Agendamento enviado com sucesso.", null);
    }

    public ResponseSchedulingDTO accept(String id, AcceptedRequestDTO request) throws Exception {
        Optional<Task> task = getTask(id, AGUARDAR_GESTOR);
        if (task.isPresent()) {
            Map<String, Object> variables = new HashMap<>();
            variables.put("finalOpinion", request.getMessage());
            Map<String, Object> variableMap = taskService.completeWithVariablesInReturn(task.get().getId(), variables, true);
            return new ResponseSchedulingDTO(id, (Date) variableMap.getOrDefault("date", null), (String) variableMap.getOrDefault("push", null), null);
        } else {
            throw new Exception("Tarefa nao encontrada");
        }
    }

    public ResponseSchedulingDTO confirm(String id, BarberDTO request) throws Exception {
        Optional<Task> task = getTask(id, AGUARDAR_BARBER);

        if (task.isPresent()) {
            Map<String, Object> variables = this.taskService.getVariables(task.get().getId());
            SchedulingDTO scheduling = JsonUtil.jsonToObject((String) variables.get("scheduling"), SchedulingDTO.class);

            if (Objects.nonNull(scheduling.getBarber())) {
                throw new Exception("Agendamento já atribuido a outro barbeiro");
            }

            scheduling.setBarber(request);
            variables.replace("scheduling", JsonUtil.objectToJson(scheduling));
            variables.put("finalOpinion", "Agendamento aceito pelo Barbeiro de maior relacionamento.");

            this.taskService.complete(task.get().getId(), variables);
            return new ResponseSchedulingDTO(id, (Date) variables.getOrDefault("date", null), "Agendamento confirmado com o barbeiro", scheduling.getBarber());
        } else {
            throw new Exception("Tarefa nao encontrada");
        }
    }

    private Optional<Task> getTask(String businessKey, String taskId) {
        List<Task> tasks = this.taskService.createTaskQuery().processInstanceBusinessKey(businessKey).endOr().list();
        return tasks.stream().filter(t -> t.getTaskDefinitionKey().equals(taskId)).findFirst();
    }
}
