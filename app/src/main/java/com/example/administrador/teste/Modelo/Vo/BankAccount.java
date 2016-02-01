package com.example.administrador.teste.Modelo.Vo;

/**
 * Created by Administrador on 21/01/2016.
 */
public class BankAccount {

    private  Long id;
    private String name;
    private String loginUser;
    private Double saldoCorrente;
    private Double saldoPoupanca;
    private Double saldoProjetado;

    public BankAccount() {
    }

    public BankAccount(String name) {
        this.name = name;
    }

    public BankAccount(String name, String loginUser, Double saldoCorrente, Double saldoPoupanca) {
        this.name = name;
        this.loginUser = loginUser;
        this.saldoCorrente = saldoCorrente;
        this.saldoPoupanca = saldoPoupanca;
    }

    public BankAccount(Long id, String name, String loginUser, Double saldoCorrente, Double saldoPoupanca) {
        this.id = id;
        this.name = name;
        this.loginUser = loginUser;
        this.saldoCorrente = saldoCorrente;
        this.saldoPoupanca = saldoPoupanca;
    }

    public BankAccount(Long id, String name, String loginUser, Double saldoCorrente, Double saldoPoupanca, Double saldoProjetado) {
        this.id = id;
        this.name = name;
        this.loginUser = loginUser;
        this.saldoCorrente = saldoCorrente;
        this.saldoPoupanca = saldoPoupanca;
        this.saldoProjetado = saldoProjetado;
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

    public Double getSaldoCorrente() {
        return saldoCorrente;
    }

    public void setSaldoCorrente(Double saldoCorrente) {
        this.saldoCorrente = saldoCorrente;
    }

    public Double getSaldoPoupanca() {
        return saldoPoupanca;
    }

    public void setSaldoPoupanca(Double saldoPoupanca) {
        this.saldoPoupanca = saldoPoupanca;
    }

    public Double getSaldoProjetado() {
        return saldoProjetado;
    }

    public void setSaldoProjetado(Double saldoProjetado) {
        this.saldoProjetado = saldoProjetado;
    }
}
