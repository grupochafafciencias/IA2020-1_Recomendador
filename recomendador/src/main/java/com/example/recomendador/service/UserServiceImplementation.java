package com.example.recomendador.service;

import com.example.recomendador.conn.UserRepository;
import com.example.recomendador.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<User> findAll(){
        return toList(userRepository.findAll());
    }

    public static <E> List<E> toList(Iterable<E> iterable) {
        if(iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<E>();
        if(iterable != null) {
            for(E e: iterable) {
                list.add(e);
            }
        }
        return list;
    }
}
