package com.uvic.venus.model;

public class CreateSecretRequest {
    private String value;
    private String username;

    public CreateSecretRequest(String value, String username) {
        this.value = value;
        this.username = username;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValue() {
        return value;
    }

    public String getUsername() {
        return username;
    }
}
