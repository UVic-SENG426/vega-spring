package com.uvic.venus.controller;

import com.uvic.venus.model.Secret;
import com.uvic.venus.repository.SecretDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class VaultController {

    @Autowired
    SecretDAO secretDAO;

    @RequestMapping(value ="/secrets", method = RequestMethod.GET)
    public ResponseEntity<?> getSecrets(@RequestParam String param1, @RequestParam String param2){

        List<Secret> secretList = secretDAO.findAll();
        return ResponseEntity.ok("OK: secrets returned successfully");
    }

//    TODO: other endpoints
}
