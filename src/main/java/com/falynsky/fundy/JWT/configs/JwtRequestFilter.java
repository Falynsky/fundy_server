package com.falynsky.fundy.JWT.configs;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.falynsky.fundy.JWT.services.JwtUserDetailsService;
import com.falynsky.fundy.JWT.utils.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final static String REQUEST_HEADER_BEGIN = "Wave ";
    private final static int REQUEST_HEADER_BEGIN_LENGTH = REQUEST_HEADER_BEGIN.length();

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Auth");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(REQUEST_HEADER_BEGIN)) {
            jwtToken = requestTokenHeader.substring(REQUEST_HEADER_BEGIN_LENGTH);
            username = getUsername(jwtToken);
        } else {
            logger.warn("JWT Token does not begin with 'Wave ' String");
        }

        SecurityContext context = SecurityContextHolder.getContext();
        boolean contextHasAuthentication = context.getAuthentication() != null;
        boolean usernameExists = username != null && !username.isEmpty();

        if (usernameExists && !contextHasAuthentication) {

            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            Boolean isTokenValidate = jwtTokenUtil.validateToken(jwtToken, userDetails);

            if (isTokenValidate) {
                saveAuthenticationInContextFotUser(request, context, userDetails);
            }
        }

        chain.doFilter(request, response);
    }

    private String getUsername(String jwtToken) {
        String username = null;
        try {
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        }
        return username;
    }

    private void saveAuthenticationInContextFotUser(HttpServletRequest request, SecurityContext context,
            UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, authorities);

        WebAuthenticationDetails details = new WebAuthenticationDetailsSource().buildDetails(request);

        usernamePasswordAuthToken.setDetails(details);
        context.setAuthentication(usernamePasswordAuthToken);
    }

}