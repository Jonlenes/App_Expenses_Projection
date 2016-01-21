package com.example.administrador.teste.Modelo.Vo;

/**
 * Created by Administrador on 21/01/2016.
 */
public class BankAccount {

    private  Long id;
    private String name;
    private String loginUser;
    private Double saldo;

    public BankAccount() {
    }

    public BankAccount(String name, String loginUser, Double saldo) {
        this.id = id;
        this.name = name;
        this.loginUser = loginUser;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
