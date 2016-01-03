/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.administrador.teste.Modelo.Bo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @author Administrador
 */
public class DbHelper extends SQLiteOpenHelper {
    private final static String NOME_BASE = "Financas";
    private final static int VERSAO_BASE = 2;

    private final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";

    public DbHelper(Context context) {
        super(context, NOME_BASE, null, VERSAO_BASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableCategoria(db);
        createTableItens(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void createTableCategoria(SQLiteDatabase db){
        String sql = SQL_CREATE_TABLE + "Categoria";
        sql += "( id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "  descricao VARCHAR(100) NOT NULL\n" +
                ")";
        db.execSQL(sql);
    }
    public void createTableItens(SQLiteDatabase db){
        String sql = SQL_CREATE_TABLE + "Item";
        sql += "( id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "  idCategoria INTEGER NOT NULL,\n" +
                "  descricao VARCHAR(100) NOT NULL,\n" +
                "  valor REAL NOT NULL,\n" +
                "  saldo REAL NOT NULL,\n" +
                "  FOREIGN KEY(idCategoria) REFERENCES Categoria(id)\n" +
                ")";
        db.execSQL(sql);
    }
}
