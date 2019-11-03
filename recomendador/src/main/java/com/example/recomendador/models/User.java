package com.example.recomendador.models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -3009157732242241606L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

    @Transient
    private String passwordConfirm;

    public User(){
    }
    public User(String name){
        this.name= name;
    }
    public Integer getId(){
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String name) {
        this.password = name;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    public void setPasswordConfirm(String name) {
        this.passwordConfirm = name;
    }
    public String getUsername(){
        return name;
    }
    public void setUsername(String username){
        this.name=username;
    }

    @Override
    public String toString(){
        return String.format("User[id=%d, name='%s']",id,name);
    }
}