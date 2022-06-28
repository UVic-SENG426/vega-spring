package com.uvic.venus.controller;

import com.uvic.venus.model.CreateSecretRequest;
import com.uvic.venus.model.Secret;
import com.uvic.venus.repository.SecretDAO;
import com.uvic.venus.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/vault")
public class VaultController {

    @Autowired
    SecretDAO secretDAO;

    @Autowired
    StorageService storageService;

//    Example: localhost:8080/venus/vault/secrets?username=admin
    @RequestMapping(value ="/secrets", method = RequestMethod.GET)
    public ResponseEntity<?> getSecrets(@RequestParam String username){
        List<Secret> secretList = secretDAO.findAllByUsername(username);
        return ResponseEntity.ok(secretList);
    }

    @RequestMapping(value="/secret-upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadSecret(@RequestBody CreateSecretRequest secretRequest){
        // storageService.store(file);
        Secret secretInfo = new Secret(secretRequest.getValue(), secretRequest.getUsername());
        System.out.println(secretInfo.toString());
        secretDAO.save(secretInfo);
        return ResponseEntity.ok("OK: secrets stored successfully");
    }
}

