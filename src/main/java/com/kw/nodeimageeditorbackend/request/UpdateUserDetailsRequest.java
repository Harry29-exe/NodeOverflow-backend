package com.kw.nodeimageeditorbackend.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDetailsRequest {
    @NonNull
    private Long id;
    @Nullable
    private String name;
    @Nullable
    private String email;
    @Nullable
    private String newPassword;
    @Nullable
    private String password;


}
