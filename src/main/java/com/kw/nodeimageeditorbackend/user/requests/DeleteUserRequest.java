package com.kw.nodeimageeditorbackend.user.requests;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class DeleteUserRequest {
    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;
}
