package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.JwtService;

import java.io.IOException;


// intercepts HTTP requests only once before passing it to the next filter in the chain
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    // filter that intercepts incoming HTTP requests once
    // pass to next filter in chain if valid JWT in Authorization header
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        System.out.println("Inside JwtAuthFilter");
        String jwtFromCookie = jwtService.getJwtFromCookie(request);
        if(jwtFromCookie != null) {
            System.out.println("jwtFromCookie: " + jwtFromCookie);
            String userEmailFromCookie = jwtService.extractUserEmail(jwtFromCookie);
            if (userEmailFromCookie != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmailFromCookie);
                // System.out.println(userDetails.toString());
                if (jwtService.isTokenValid(jwtFromCookie, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        System.out.println("The url hit: " + request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}
