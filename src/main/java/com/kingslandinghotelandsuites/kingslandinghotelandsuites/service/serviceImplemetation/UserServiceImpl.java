package com.kingslandinghotelandsuites.kingslandinghotelandsuites.service.serviceImplemetation;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.exceptions.UserAlreadyExistsException;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.Role;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.User;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.repository.RoleRepository;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.repository.UserRepository;
import com.kingslandinghotelandsuites.kingslandinghotelandsuites.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User registerUser(User user) {
        //check if user exists already in db
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER").get();// By default, a user is assigned ROLE_USER
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getAUser(email);
        if (theUser != null) {
            userRepository.deleteByEmail(email);
        }

        userRepository.deleteByEmail(email);
    }

    @Override
    public User getAUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
