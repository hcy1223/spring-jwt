package com.huchenyuan.authJwt.configruation.Filter;

import com.huchenyuan.authJwt.service.JwtTokenService;
import com.huchenyuan.authJwt.service.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_PATTREN = "Bearer";

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(HEADER_AUTHORIZATION);
        String userName = null;
        String jwtToken;
//        JWT token is in the form "Bearer token", remove and get only the Token
        if (requestTokenHeader == null || !requestTokenHeader.startsWith(BEARER_PATTREN)) {
            log.warn("can not get token");
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = requestTokenHeader.substring(7);
        try {
            userName = jwtTokenService.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userName);
            if (jwtTokenService.validateToken(userDetails, jwtToken)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
