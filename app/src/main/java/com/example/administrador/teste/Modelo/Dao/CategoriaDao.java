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

    public ArrayList<Categoria> getTodos(){
        String sql = "SELECT Categoria.*, SUM(Item.Saldo) AS Saldo FROM Categoria\n" +
                "LEFT JOIN Item\n" +
                "ON Categoria.id = Item.idCategoria\n" +
                "GROUP BY(Categoria.id)\n" +
                "ORDER BY(Categoria.descricao)";
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList list = new ArrayList();
        while (cursor.moveToNext()) {
            list.add(new Categoria(cursor.getString(1),
                    cursor.getLong(0),
                    cursor.getDouble(2)));
        }

        return list;
    }

    public Boolean contemPorDescricao(String descricao) {
        String sql = "SELECT * FROM Categoria\n" +
                "WHERE descricao = '" + descricao + "'";
        return  db.rawQuery(sql, null).moveToFirst();
    }
}
