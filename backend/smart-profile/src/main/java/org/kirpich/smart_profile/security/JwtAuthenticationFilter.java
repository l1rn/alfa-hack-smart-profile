package org.kirpich.smart_profile.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.kirpich.smart_profile.services.JwtService;
import org.kirpich.smart_profile.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ){
        String jwt = parseJwt(request);
        if (jwt == null){

        }
    }

    private String parseJwt(HttpServletRequest req){
        if(req.getCookies() != null){
            for(Cookie c : req.getCookies()){
                if("accessToken".equals(c.getName())){
                    return c.getValue();
                }
            }
        }
    }
}
