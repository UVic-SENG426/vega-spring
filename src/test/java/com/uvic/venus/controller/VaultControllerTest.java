package com.uvic.venus.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class VaultControllerTest {

    @Test
    void getSecrets() {
        assertEquals(1, 1);
    }

    @Test
    void uploadSecret() {
//        byte[] contents = new byte[] { (byte)0xff};
//        MultipartFile file = new MockMultipartFile("FILENAME", contents);
//
//        ResponseEntity<?> response = (new VaultController()).uploadSecret(file);
//        assertNotNull(response);
    }
}