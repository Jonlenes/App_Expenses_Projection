/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.administrador.teste.Modelo.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrador
 */
public class CategoriaDao {
    private SQLiteDatabase db;

    public CategoriaDao(SQLiteDatabase db) {
        this.db = db;
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
                "INNER JOIN Item\n" +
                "ON Categoria.id = Item.idCategoria\n" +
                "GROUP BY(Item.idCategoria)\n" +
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

    public Map<Categoria, ArrayList<Item>> getCategoriasComItens() {
        ArrayList<Categoria> categoriaArrayList = getTodos();

        Map<Categoria, ArrayList<Item>> listMap = new HashMap<>();
        ItemDao itemDao = new ItemDao(db);
        for (Categoria categoria : categoriaArrayList) {
            ArrayList<Item> items = itemDao.getTodosPorCategoria(categoria.getId());
            listMap.put(categoria, items);
        }

        return listMap;
    }
}
