package com.mj.mjchuan.infrastructure.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization");
        UserJwt userJwt = null;

        if (token != null && token.startsWith("Bearer ")) {
            try {
                String jwt = token.substring(7);
                userJwt = JwtTokenUtil.parseJwtUser(jwt);
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        }

        if (userJwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 你可以根据实际情况设置 Authentication 对象
            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userJwt));
        }

        filterChain.doFilter(request, response);
    }
}

