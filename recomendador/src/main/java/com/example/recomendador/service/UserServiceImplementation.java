package com.example.recomendador.service;

import com.example.recomendador.conn.UserRepository;
import com.example.recomendador.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void save(User usr) {
        usr.setPassword(bCryptPasswordEncoder.encode(usr.getPassword()));
        userRepository.save(usr);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByName(username);
    }
}
