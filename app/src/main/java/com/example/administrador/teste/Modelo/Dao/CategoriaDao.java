/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.administrador.teste.Modelo.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrador.teste.Modelo.Bo.DbHelper;
import com.example.administrador.teste.Modelo.Vo.Categoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrador
 */
public class CategoriaDao {
    private SQLiteDatabase db;

    public CategoriaDao() {
        this.db = DbHelper.getInstance().getWritableDatabase();
    }

    public void insere(Categoria categoria){
        ContentValues contentValues = new ContentValues();

        contentValues.put("descricao", categoria.getDescricao());
        contentValues.put("loginUser", categoria.getLoginUser());

        db.insert("Categoria", "Id", contentValues);
    }

    public void altera(Categoria categoria){
        ContentValues contentValues = new ContentValues();
        contentValues.put("descricao", categoria.getDescricao());
        db.update("Categoria", contentValues, "id = " + categoria.getId(), null);
    }
    public void exclui(Long id){
        db.delete("Categoria", "id = " + id, null);
    }

    public ArrayList<Categoria> getTodos(String loginUser) {
        String sql = "SELECT Categoria.*, SUM(Item.Saldo) AS Saldo FROM Categoria\n" +
                "LEFT JOIN Item\n" +
                "ON Categoria.id = Item.idCategoria\n" +
                "WHERE Categoria.loginUser = '" + loginUser + "'\n" +
                "GROUP BY(Categoria.id)\n";
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList list = new ArrayList();
        while (cursor.moveToNext()) {
            list.add(new Categoria(cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getDouble(3),
                    cursor.getString(2)
                    ));
        }

        return list;
    }

    public Boolean contemPorDescricao(String descricao, String loginUser) {
        String sql = "SELECT * FROM Categoria\n" +
                "WHERE descricao = '" + descricao + "'\n" +
                "   AND loginUser = '" + loginUser + "'";
        return  db.rawQuery(sql, null).moveToFirst();
    }

    public Categoria getCategoriaById(String loginUser, Long id) {
        String sql = "SELECT Categoria.*, SUM(Item.Saldo) AS Saldo FROM Categoria\n" +
                "LEFT JOIN Item\n" +
                "ON Categoria.id = Item.idCategoria\n" +
                "WHERE Categoria.loginUser = '" + loginUser + "'\n" +
                "AND Categoria.id = " + id + "\n" +
                "GROUP BY(Categoria.id)\n";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            return new Categoria(cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getDouble(3),
                    cursor.getString(2));
        }

        return null;
    }

    public Map<String, Double> getAllByBankAccout(Long idBankAccount) {
        String sql = "SELECT Categoria.id, Categoria.descricao, SUM(Item.saldo) AS valor  FROM Item\n" +
                "INNER JOIN Categoria\n" +
                "ON Categoria.id = Item.idCategoria\n" +
                "WHERE Item.idBankAccount = " + idBankAccount + "\n" +
                "GROUP BY(Item.idCategoria)";
        Cursor cursor = db.rawQuery(sql, null);

        Map<String, Double> mapPercent = new HashMap<String, Double>();
        while (cursor.moveToNext()) {
            mapPercent.put(cursor.getString(1),
                    cursor.getDouble(2));
        }
        return mapPercent;
    }
}
