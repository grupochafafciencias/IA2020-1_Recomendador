package com.example.recomendador.service;

import com.example.recomendador.models.User;

public interface UserService {
    void save(User usr);
    User findByUsername(String username);
}
