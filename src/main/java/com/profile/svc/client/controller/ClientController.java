package com.profile.svc.client.controller;

import com.profile.svc.client.service.ClientService;
import com.profile.svc.shared.model.ClientModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/client")
@AllArgsConstructor
public class ClientController {

    ClientService service;
    @GetMapping("/details")
    public ResponseEntity<ClientModel> getProjects(){
        return ResponseEntity.ok(service.gatherClientData());
    }
}
