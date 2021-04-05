package com.kw.nodeimageeditorbackend.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DeleteUserRequest {
    @NotNull
    @NotBlank
    private String usernameOrEmail;
    @NotNull
    @NotBlank
    private String password;
}
