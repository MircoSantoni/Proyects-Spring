package com.mirco.curso.springboot.app.springboot_crud.Security.filter;

import static com.mirco.curso.springboot.app.springboot_crud.Security.TokenJtwConfig.HEADER_AUTHORIZATION;
import static com.mirco.curso.springboot.app.springboot_crud.Security.TokenJtwConfig.PREFIX_TOKEN;
import static com.mirco.curso.springboot.app.springboot_crud.Security.TokenJtwConfig.SECRET_KEY;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

                String header = request.getHeader(HEADER_AUTHORIZATION);

                if (header == null || !header.startsWith( PREFIX_TOKEN)) {
                    return;
                }
                String token = header.replace(PREFIX_TOKEN, "");

                try{
                    Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
                    String username = claims.getSubject();
                    Object authoritiesClaims = claims.get("authorities");

                    Collection<
                
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request, response);
                } catch (JwtException e){

                }   


    }

}
