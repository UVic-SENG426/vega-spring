package com.uvic.venus.controller;

import com.uvic.venus.model.CreateSecretRequest;
import com.uvic.venus.model.Secret;
import com.uvic.venus.repository.SecretDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/vault")
public class VaultController {

    @Autowired
    SecretDAO secretDAO;

    private static int newSecretID = 0;


    public Optional<String> getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return Optional.ofNullable(username);
    }

//    Example: localhost:8080/venus/vault/secrets?username=admin
    @RequestMapping(value ="/secrets", method = RequestMethod.GET)
    public ResponseEntity<?> getSecrets(@RequestParam String username){
        Optional<String> loggedInUsername = getLoggedInUsername();
        if (loggedInUsername.isEmpty() || ! loggedInUsername.get().equals(username)) {
            return ResponseEntity.badRequest().build();
        }
        List<Secret> secretList = secretDAO.findAllByUsername(username);
        return ResponseEntity.ok(secretList);
    }

//    Example: localhost:8080/venus/vault/secret-delete?secretID=0
    @RequestMapping(value ="/secret-delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSecret(@RequestParam String secretID){
        Secret s = secretDAO.getById(secretID);
        if (s == null || s.getSecret().equals("")){
            return ResponseEntity.notFound().build();
        }
        String username = s.getUsername();
        Optional<String> loggedInUsername = getLoggedInUsername();
        if (loggedInUsername.isEmpty() || ! loggedInUsername.get().equals(username)) {
            return ResponseEntity.badRequest().build();
        }
        secretDAO.delete(s);
        return ResponseEntity.ok("secret deleted successfully");
    }

/*
    example request
    Header: localhost:8080/venus/vault/secret-update?secretID=0
    Body:
    {
    "value": "VALUE_STR",
    }
 */
    @RequestMapping(value ="/secret-update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSecret(@RequestParam String secretID, @RequestBody CreateSecretRequest secretRequest){
        Secret s = secretDAO.getById(secretID);
        if (s == null || s.getSecret().equals("")){
            return ResponseEntity.notFound().build();
        }
        String username = s.getUsername();
        Optional<String> loggedInUsername = getLoggedInUsername();
        if (loggedInUsername.isEmpty() || ! loggedInUsername.get().equals(username)) {
            return ResponseEntity.badRequest().build();
        }
        s.setValue(secretRequest.getValue());
        secretDAO.save(s);
        return ResponseEntity.ok("OK: secret updated successfully");
    }

/*
    example request
    Header: localhost:8080/venus/vault/secret-upload
    Body:
    {
    "value": "VALUE_STR",
    "username": "ADMIN_STR"
    }
 */
    @RequestMapping(value="/secret-upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadSecret(@RequestBody CreateSecretRequest secretRequest){
        Optional<String> loggedInUsername = getLoggedInUsername();
        String username = secretRequest.getUsername();
        if (loggedInUsername.isEmpty() || ! loggedInUsername.get().equals(username)) {
            return ResponseEntity.badRequest().build();
        }
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

    /*
    used for unit testing
     */
    public void setSecretDAO(SecretDAO secretDAO){
        this.secretDAO = secretDAO;
    }
}

