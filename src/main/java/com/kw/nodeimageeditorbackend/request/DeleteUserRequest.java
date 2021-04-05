package com.kw.nodeimageeditorbackend.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteUserRequest {

    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;
}
