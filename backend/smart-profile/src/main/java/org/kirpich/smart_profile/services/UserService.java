package org.kirpich.smart_profile.services;

import lombok.RequiredArgsConstructor;
import org.kirpich.smart_profile.dto.user.MeInfoResponse;
import org.kirpich.smart_profile.models.User;
import org.kirpich.smart_profile.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public MeInfoResponse getMeInfo(UserDetails ud) throws CredentialNotFoundException {
        User user = userRepository.findByEmail(ud.getUsername())
                .orElseThrow(() -> new CredentialNotFoundException("пользователь не найден"));
        return new MeInfoResponse(
                user.getEmail(),
                user.getPhone()
        );
    }
}
