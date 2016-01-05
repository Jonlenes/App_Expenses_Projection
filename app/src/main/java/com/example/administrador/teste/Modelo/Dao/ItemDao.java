/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.administrador.teste.Modelo.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.teste.Modelo.Bo.DbHelper;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
public class ItemDao {
    private SQLiteDatabase db;

    public ItemDao() {
        this.db = DbHelper.getInstance().getWritableDatabase();
    }

    public void insere(Item item) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("idCategoria", item.getIdCategoria());
        contentValues.put("descricao", item.getDescricao());
        contentValues.put("valor", item.getValor());
        contentValues.put("saldo", item.getSaldo());

        db.insert("Item", "Id", contentValues);
    }

    public void altera(Item item){
        ContentValues contentValues = new ContentValues();

        contentValues.put("idCategoria", item.getIdCategoria());
        contentValues.put("descricao", item.getDescricao());
        contentValues.put("valor", item.getValor());
        contentValues.put("saldo", item.getSaldo());

        db.update("Item", contentValues, "id = " + item.getId(), null);
    }
    public void exclui(Long id){
        db.delete("Item", "id = " + id, null);
    }

    public ArrayList<Item> getTodos(){
        String sql = "SELECT * FROM Item";
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<Item> list = new ArrayList<Item>();
        while (cursor.moveToNext()) {
            list.add(new Item(cursor.getLong(0),
                    cursor.getLong(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4)));
        }

        return list;
    }

    public ArrayList<Item> getTodosPorCategoria(Long idCategoria) {
        String sql = "SELECT * FROM Item\n" +
                "WHERE idCategoria = " + idCategoria;
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<Item> list = new ArrayList<Item>();
        while (cursor.moveToNext()) {
            list.add(new Item(cursor.getLong(0),
                    cursor.getLong(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4)));
        }
        return list;
    }
}
