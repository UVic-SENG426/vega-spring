package com.uvic.venus.controller;

import com.uvic.venus.model.CreateSecretRequest;
import com.uvic.venus.model.Secret;
import com.uvic.venus.repository.SecretDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VaultControllerTest {

    private VaultController vaultController;
    private SecretDAO mockSecretDao;


    @BeforeEach
    void setUp() {
        mockSecretDao = Mockito.mock(SecretDAO.class);
        this.vaultController = new VaultController();
        this.vaultController.setSecretDAO(mockSecretDao);
    }


    @Test
    void getSecrets() {
        Mockito.when(mockSecretDao.findAllByUsername("test")).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = vaultController.getSecrets("test");
        assertEquals(response, ResponseEntity.ok(new ArrayList<>()));
        Mockito.verify(mockSecretDao).findAllByUsername("test");
    }

    @Test
    void uploadSecret() {
        CreateSecretRequest request = new CreateSecretRequest("value", "username");
        ResponseEntity<?> response = vaultController.uploadSecret(request);
        assertEquals(ResponseEntity.ok("OK: secret stored successfully"), response);
        Mockito.verify(mockSecretDao).save(Mockito.any());
    }

    @Test
    void deleteSecret() {
        Secret s = getDefaultSecret();
        Mockito.when(mockSecretDao.getById("secretID")).thenReturn(s);
        ResponseEntity<?> response = vaultController.deleteSecret("secretID");
        assertEquals(ResponseEntity.ok("secret deleted successfully"), response);
        Mockito.verify(mockSecretDao).delete(s);
    }

    @Test
    void updateSecret() {
        Secret s = getDefaultSecret();
        Mockito.when(mockSecretDao.getById(s.getSecret())).thenReturn(s);
        CreateSecretRequest secretRequest = new CreateSecretRequest("200", s.getUsername());
        ResponseEntity<?> response = vaultController.updateSecret(s.getSecret(), secretRequest);
        assertEquals(ResponseEntity.ok("OK: secret updated successfully"), response);
        s.setValue("200");
        Mockito.verify(mockSecretDao).save(s);
    }

    private Secret getDefaultSecret() {
        Secret s = new Secret();
        s.setUsername("username");
        s.setDate("2020, January 1st");
        s.setValue("100");
        s.setSecret("secret String");
        return s;
    }
}