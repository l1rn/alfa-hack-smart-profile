package org.kirpich.smart_profile.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.kirpich.smart_profile.dto.authorization.AuthorizationRequest;
import org.kirpich.smart_profile.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Arrays;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    @Value("${jwt.access-exp}")
    private Long accessExp;
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
    public ResponseEntity<String> signIn(
            @RequestBody @Validated AuthorizationRequest request,
            HttpServletRequest req,
            HttpServletResponse resp){
        try{
            String accessToken = authorizationService.signIn(request);
            resp.setHeader("X-Token-Expires", String.valueOf(accessExp));
            setAuthCookies(resp, accessToken);
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest req,
            HttpServletResponse resp
    ){
        Cookie[] cookies = req.getCookies();
        String accessToken = Arrays.stream(cookies)
                .filter(c -> "accessToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .toString();

        clearAuthCookies(resp, accessToken);
        return ResponseEntity.ok("OK");
    }

    private void setAuthCookies(HttpServletResponse resp, String accessToken){
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(accessExp / 1000)
                .sameSite("Lax")
                .build();
        resp.addHeader("Set-Cookie", accessCookie.toString());
    }

    private void clearAuthCookies(HttpServletResponse resp, String accessToken){
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .maxAge(0)
                .path("/")
                .build();

        resp.addHeader("Set-Cookie", accessCookie.toString());
    }
}
