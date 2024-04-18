package com.kingslandinghotelandsuites.kingslandinghotelandsuites.controller;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.exceptions.UserAlreadyExistsException;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.User;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.requests.LoginRequest;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.response.JwtResponse;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.security.jwt.JwtUtils;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.security.user.HotelUserDetails;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private  AuthenticationManager authManager;
    private final JwtUtils jwtUtils;


    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registration Successful");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUsers(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authManager
                .authenticate(

                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        HotelUserDetails userDetails = (HotelUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(new JwtResponse(
                userDetails.getId(),
                userDetails.getEmail(),
                jwt,
                roles
        ));


    }

}
