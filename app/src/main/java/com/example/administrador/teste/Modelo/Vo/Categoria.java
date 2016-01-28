/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.administrador.teste.Modelo.Vo;

/**
 *
 * @author Administrador
 */
public class Categoria {
    private Long id;
    private String descricao;
    private Double saldo;
    private String loginUser;

    public Categoria() {
    }

    public Categoria(String descricao) {
        this.descricao = descricao;
    }

    public Categoria(Long id, String descricao, Double saldo, String loginUser) {
        this.id = id;
        this.descricao = descricao;
        this.saldo = saldo;
        this.loginUser = loginUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }
}
