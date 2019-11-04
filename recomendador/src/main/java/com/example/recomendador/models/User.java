package com.example.recomendador.models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -3009157732242241606L;

    //He aqui mi entrada para r/shittyprogramming
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

    @Column(name="watched")
    private UserSeries[] watched;

    @Transient
    private String passwordConfirm;

    public User(){
        this.watched=new UserSeries[0];
    }

    public User(String name){
        this.name= name;
        this.watched=new UserSeries[0];
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

    public UserSeries[] getWatched() {
        return watched;
    }

    public void setWatched(UserSeries[] watched) {
        this.watched = watched;
    }

    @Override
    public String toString(){
        return String.format("User[id=%d, name='%s']",id,name);
    }
}
