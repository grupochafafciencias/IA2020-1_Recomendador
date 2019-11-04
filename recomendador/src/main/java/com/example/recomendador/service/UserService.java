package com.example.recomendador.service;

import com.example.recomendador.models.User;

import java.util.List;

public interface UserService {
    void save(User usr);
    User findByUsername(String username);
    List<User> findAll();
}
