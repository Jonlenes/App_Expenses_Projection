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
import com.example.administrador.teste.Modelo.Vo.BankAccount;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumBackAccount;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class BankAccountDao {
    private SQLiteDatabase db;

    public BankAccountDao() {
        this.db = DbHelper.getInstance().getWritableDatabase();
    }

    public void insert(BankAccount bankAccount) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", bankAccount.getName());
        contentValues.put("loginUser", DbHelper.getInstance().getUserActive().getLogin());

        db.insert("BankAccount", "Id", contentValues);
    }

    public void update(BankAccount bankAccount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", bankAccount.getName());
        db.update("Item", contentValues, "id = " + bankAccount.getId(), null);
    }

    public void delete(Long id){
        db.delete("BankAccount", "id = " + id, null);
    }

    public List<BankAccount> getBankAccount(String login){
        String sql = "SELECT * FROM BankAccount\n" +
                "WHERE loginUser = '" + login + "'";
        Cursor cursor = db.rawQuery(sql, null);

        List<BankAccount> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(new BankAccount(cursor.getLong(EnumBackAccount.id.ordinal()),
                    cursor.getString(EnumBackAccount.name.ordinal()),
                    cursor.getString(EnumBackAccount.loginUser.ordinal()),
                    cursor.getDouble(EnumBackAccount.name.ordinal())));
        }

        return list;
    }
}