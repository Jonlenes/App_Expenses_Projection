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
public class Item {
    private Long id;
    private Long idCategoria;
    private String descricao;
    private Double valor;
    private Double saldo;

    public Item() {
    }

    public Item(Long idCategoria, String descricao, Double valor, Double saldo) {
        this.idCategoria = idCategoria;
        this.descricao = descricao;
        this.valor = valor;
        this.saldo = saldo;
    }

    public Item(Long id, Long idCategoria, String descricao, Double valor, Double saldo) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.descricao = descricao;
        this.valor = valor;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    
    
}
