package org.kirpich.smart_profile.dto.authorization;

import lombok.Data;

@Data
public class AuthorizationRequest {
    private String email;
    private String password;
}
