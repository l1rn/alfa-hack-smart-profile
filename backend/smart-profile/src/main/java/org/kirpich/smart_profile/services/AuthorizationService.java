package org.kirpich.smart_profile.services;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.kirpich.smart_profile.dto.authorization.AuthorizationRequest;
import org.kirpich.smart_profile.enums.UserRole;
import org.kirpich.smart_profile.models.User;
import org.kirpich.smart_profile.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.CredentialNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void signUp(AuthorizationRequest r) throws BadRequestException, CredentialNotFoundException {
        if(userRepository.findByEmail(r.getEmail()).isPresent()){
            throw new CredentialNotFoundException("email или пароль неверны");
        }
        if(r.getPassword().length() < 6){
            throw new BadRequestException("Пароль слишком маленький (нужен пароль > 6)");
        }

        User user = User.builder()
                .email(r.getEmail())
                .password(passwordEncoder.encode(r.getPassword()))
                .role(UserRole.CLIENT)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void signIn(AuthorizationRequest r) throws CredentialNotFoundException{
        User user = userRepository.findByEmail(r.getEmail())
                .orElseThrow(() -> new CredentialNotFoundException("email или пароль неверны"));
        if(!passwordEncoder.matches(user.getPassword(), r.getPassword())){
            throw new CredentialNotFoundException("email или пароль неверны");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        r.getEmail(),
                        r.getPassword()
                )
        );

        jwtService.generateAccessToken(user);
    }
}
