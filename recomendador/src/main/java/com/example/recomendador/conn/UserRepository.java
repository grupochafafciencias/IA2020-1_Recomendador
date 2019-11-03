package com.example.recomendador.conn;

import com.example.recomendador.models.User;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository  extends CrudRepository <User, Integer>{
    User findByName(String name);
}
