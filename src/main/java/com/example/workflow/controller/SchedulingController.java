package com.example.workflow.controller;

import com.example.workflow.dto.AcceptedRequestDTO;
import com.example.workflow.dto.BarberDTO;
import com.example.workflow.dto.ResponseSchedulingDTO;
import com.example.workflow.dto.SchedulingDTO;
import com.example.workflow.service.SchedulingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduling")
@AllArgsConstructor
public class SchedulingController {

    private final SchedulingService service;

    /*https://docs.camunda.org/manual/7.15/user-guide/process-engine/process-engine-api/*/
    @PostMapping
    public ResponseSchedulingDTO create(@RequestBody SchedulingDTO scheduling) {
        return service.start(scheduling);
    }

    @PutMapping("/manager/{id}")
    public ResponseSchedulingDTO accepted(@PathVariable String id, @RequestBody AcceptedRequestDTO request) throws Exception {
        return service.accept(id, request);
    }

    @PutMapping("/barber/{id}")
    public ResponseSchedulingDTO confirmed(@PathVariable String id, @RequestBody BarberDTO request) throws Exception {
        return service.confirm(id, request);
    }


}
