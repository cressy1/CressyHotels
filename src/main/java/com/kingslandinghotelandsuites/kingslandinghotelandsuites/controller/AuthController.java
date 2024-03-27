package com.kingslandinghotelandsuites.kingslandinghotelandsuites.controller;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.exceptions.UserAlreadyExistsException;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.User;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registration Successful");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
