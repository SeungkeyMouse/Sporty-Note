package com.sportynote.server.controller;

import com.sportynote.server.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class MachineController {

    private final MachineRepository machineRepository;

    @GetMapping("/machines")
    public ResponseEntity<?> getMachines() throws URISyntaxException {
        return ResponseEntity.ok(machineRepository.findAll());
    }
}