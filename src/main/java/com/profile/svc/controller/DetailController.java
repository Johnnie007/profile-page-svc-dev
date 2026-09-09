package com.profile.svc.controller;

import com.profile.svc.model.Details;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DetailController {

    @GetMapping("/provide/details")
    public ResponseEntity<Details> getDetails(){
        return ResponseEntity.ok().build();
    }
}
