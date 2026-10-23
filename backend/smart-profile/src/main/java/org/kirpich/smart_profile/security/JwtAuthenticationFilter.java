package org.kirpich.smart_profile.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.kirpich.smart_profile.services.JwtService;
import org.kirpich.smart_profile.services.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest r){
        return r.getServletPath().startsWith("/api/auth");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try{
            String jwt = parseJwt(request);
            if (jwt == null){
                filterChain.doFilter(request, response);
                return;
            }
            if(!jwtService.validateToken(jwt)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String email = jwtService.getUsernameFromToken(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            logger.error("Ошибка аутентификации: ", e);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest req){
        if(req.getCookies() != null){
            for(Cookie c : req.getCookies()){
                if("accessToken".equals(c.getName())){
                    return c.getValue();
                }
            }
        }
        return null;
    }
}
