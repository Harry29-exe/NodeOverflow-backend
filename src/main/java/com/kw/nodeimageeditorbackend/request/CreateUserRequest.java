package com.kw.nodeimageeditorbackend.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank
    @Pattern(regexp = "^[^@]+$")
    private String username;
    @Email
    private String email;
    @NotBlank
    private String password;
}
