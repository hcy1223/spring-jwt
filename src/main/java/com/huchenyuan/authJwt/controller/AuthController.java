package com.huchenyuan.authJwt.controller;

import com.huchenyuan.authJwt.dto.JwtRequest;
import com.huchenyuan.authJwt.dto.JwtResponse;
import com.huchenyuan.authJwt.service.JwtTokenService;
import com.huchenyuan.authJwt.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }


}
