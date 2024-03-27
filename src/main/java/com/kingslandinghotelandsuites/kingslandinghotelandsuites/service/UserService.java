package com.kingslandinghotelandsuites.kingslandinghotelandsuites.service;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.model.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getAUser(String email);
}
