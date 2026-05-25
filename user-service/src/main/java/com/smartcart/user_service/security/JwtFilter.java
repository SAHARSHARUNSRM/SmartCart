package com.smartcart.user_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // CURRENT REQUEST PATH
        String path = request.getServletPath();

        // SKIP JWT CHECK FOR AUTH APIs
        if (path.startsWith("/auth")) {

            filterChain.doFilter(request, response);

            return;
        }

        // GET AUTHORIZATION HEADER
        final String authorizationHeader =
                request.getHeader("Authorization");

        String username = null;

        String jwt = null;

        // CHECK HEADER
        if (authorizationHeader != null
                &&
                authorizationHeader.startsWith("Bearer ")) {

            jwt = authorizationHeader.substring(7);

            username = jwtUtil.extractUsername(jwt);
        }

        // AUTHENTICATE USER
        if (username != null
                &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            new ArrayList<>()
                    );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder.getContext()
                    .setAuthentication(authToken);
        }

        // CONTINUE FILTER CHAIN
        filterChain.doFilter(request, response);
    }
}