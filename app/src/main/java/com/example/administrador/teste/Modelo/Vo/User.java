package com.example.administrador.teste.Modelo.Vo;

/**
 * Created by Administrador on 20/01/2016.
 */
public class User {
    private String login;
    private String password;
    private String email;
    private Boolean isConnected;


    public User() {
    }

    public User(String user, String password, String email, Boolean isConnected) {
        this.login = user;
        this.password = password;
        this.email = email;
        this.isConnected = isConnected;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }
}
