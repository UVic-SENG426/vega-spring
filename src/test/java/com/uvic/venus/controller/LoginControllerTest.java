package com.uvic.venus.controller;

import com.uvic.venus.model.AuthenticationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController loginController;
    private AuthenticationManager mockAuthManager;

    @BeforeEach
    void setUp() {
        this.loginController = new LoginController();
        this.mockAuthManager = Mockito.mock(AuthenticationManager.class);
        this.loginController.setAuthenticationManager(this.mockAuthManager);
    }

    @Test
    void reteievePrincipal() {
        Principal p = Mockito.mock(Principal.class);
        assertEquals(p, this.loginController.reteievePrincipal(p));
    }

    @Test
    void createAuthenticationToken_notFound() {
        AuthenticationRequest request = new AuthenticationRequest("uniqueUsername", "securePassword");
        Mockito.when(mockAuthManager.authenticate(Mockito.any())).thenThrow(BadCredentialsException.class);
        ResponseEntity<?> response = this.loginController.createAuthenticationToken(request);
        assertEquals(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not Found"), response);
    }
}
