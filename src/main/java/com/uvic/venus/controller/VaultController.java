package com.uvic.venus.controller;

import com.uvic.venus.model.CreateSecretRequest;
import com.uvic.venus.model.Secret;
import com.uvic.venus.repository.SecretDAO;
import com.uvic.venus.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;



@RestController
@RequestMapping("/vault")
public class VaultController {

    @Autowired
    SecretDAO secretDAO;

    @Autowired
    StorageService storageService;

    private static int newSecretID = 0;

//    Example: localhost:8080/venus/vault/secrets?username=admin
    @RequestMapping(value ="/secrets", method = RequestMethod.GET)
    public ResponseEntity<?> getSecrets(@RequestParam String username){
//        List<Secret> secretList = secretDAO.findAllByUsername(username); //TODO
        List<Secret> secretList = secretDAO.findAll();
        return ResponseEntity.ok(secretList);
    }

    @RequestMapping(value ="/secret-delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSecret(@RequestParam String secretID){
        return ResponseEntity.ok("secret successfully deleted");
    }

    @RequestMapping(value="/secret-upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadSecret(@RequestBody CreateSecretRequest secretRequest){
        Secret secret = new Secret();
        secret.setSecret(Integer.toString(newSecretID++));
        secret.setValue(secretRequest.getValue());
        secret.setUsername(secretRequest.getUsername());
        Instant now = Instant.now();
        Date dt = Date.from(now);
        secret.setDate(dt.toString());
        Secret s = secretDAO.save(secret);
        return ResponseEntity.ok("OK: secret stored successfully");
    }
}

