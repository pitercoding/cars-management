package com.cars.backend.auth;

import com.cars.backend.config.JwtServiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtServiceGenerator jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public String signIn(Login login) {
        String token = this.generateToken(login);
        return token;
    }

    public String generateToken(Login login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );
        User user = repository.findByUsername(login.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
        String jwtToken = jwtService.generateToken(user);
        return jwtToken;
    }

}