package com.tutorial.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequestModel {
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String userName;
    @Email
    private String email;
    @NotBlank
    private String role;

    public UpdateUserRequestModel(){}

    public UpdateUserRequestModel(String name, String lastName, String userName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
    }

    public UpdateUserRequestModel(String name, String lastName, String userName, String email, String role) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    public @NotBlank String getName() {
        return name;
    }

    public @NotBlank String getLastName() {
        return lastName;
    }

    public @NotBlank String getUserName() {
        return userName;
    }

    public @Email String getEmail() {
        return email;
    }

    public @NotBlank String getRole() {
        return role;
    }


}
