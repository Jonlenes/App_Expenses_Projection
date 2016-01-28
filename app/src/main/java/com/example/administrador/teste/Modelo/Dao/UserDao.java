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
import com.example.administrador.teste.Modelo.Vo.Enum.EnumUser;
import com.example.administrador.teste.Modelo.Vo.User;

/**
 * @author Administrador
 */
public class UserDao {
    private SQLiteDatabase db;

    public UserDao() {
        this.db = DbHelper.getInstance().getWritableDatabase();
    }

    public void insert(User user) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("login", user.getLogin());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("isConnected", user.getIsConnected()? 1 : 0);

        db.insert("User", null, contentValues);
    }

    public void update(User user) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("isConnected", user.getIsConnected()? 1 : 0);

        db.update("User", contentValues, "login = '" + user.getLogin() + "'", null);
    }

    public User getUser(String login) {
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE login = '" + login + "'", null);

        if (cursor.moveToNext()) {
            return new User(cursor.getString(EnumUser.login.ordinal()),
                    cursor.getString(EnumUser.password.ordinal()),
                    cursor.getString(EnumUser.email.ordinal()),
                    cursor.getInt(EnumUser.isConnected.ordinal()) == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        return null;
    }

    public User getUserConnected() {
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE isConnected = 1", null);

        if (cursor.moveToNext()) {
            return new User(cursor.getString(EnumUser.login.ordinal()),
                    cursor.getString(EnumUser.password.ordinal()),
                    cursor.getString(EnumUser.email.ordinal()),
                    cursor.getInt(EnumUser.isConnected.ordinal()) == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        return null;
    }

    public Boolean login(String login, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM User " +
                        " WHERE login = '" + login + "'" +
                        " AND password = '" + password + "'",
                null);

        return cursor.moveToNext();
    }

    public Boolean containByLogin(String login) {
        Cursor cursor = db.rawQuery("SELECT * FROM User " +
                        " WHERE login = '" + login + "'",
                null);

        return cursor.moveToNext();
    }
}
