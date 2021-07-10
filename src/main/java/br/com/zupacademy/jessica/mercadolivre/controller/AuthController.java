package br.com.zupacademy.jessica.mercadolivre.controller;

import br.com.zupacademy.jessica.mercadolivre.request.AutenticarRequest;
import br.com.zupacademy.jessica.mercadolivre.response.AutenticarResponse;
import br.com.zupacademy.jessica.mercadolivre.security.TokenManager;
import br.com.zupacademy.jessica.mercadolivre.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private AuthenticationService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, TokenManager tokenManager, AuthenticationService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody AutenticarRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getSenha())
            );
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Login ou senha incorretos", HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
        final String jwt = tokenManager.generateToken(userDetails);

        return ResponseEntity.ok(new AutenticarResponse(jwt));
    }
}
