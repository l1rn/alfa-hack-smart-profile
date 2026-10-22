package org.kirpich.smart_profile.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.kirpich.smart_profile.dto.authorization.AuthorizationRequest;
import org.kirpich.smart_profile.services.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.security.auth.login.CredentialNotFoundException;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody @Validated AuthorizationRequest request) {
        try{
            authorizationService.signUp(request);
            return ResponseEntity.ok("OK");
        }
        catch(BadRequestException | CredentialNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (RuntimeException e){
            // для отладки -> в проде убирается
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody @Validated AuthorizationRequest request){
        try{
            authorizationService.signIn(request);
            return ResponseEntity.ok("OK");
        }
        catch(CredentialNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (RuntimeException e){
            // для отладки -> в проде убирается
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
