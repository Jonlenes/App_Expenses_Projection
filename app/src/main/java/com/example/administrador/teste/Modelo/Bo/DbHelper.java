package com.example.administrador.teste.Modelo.Bo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrador.teste.Modelo.Vo.User;

/**
 * Created by Administrador on 03/01/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private final static String NOME_BASE = "Financas";
    private final static int VERSAO_BASE = 8;
    private static DbHelper ourInstance = null;
    private final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";

    private User userActive;

    private DbHelper(Context context) {
        super(context, NOME_BASE, null, VERSAO_BASE);
    }

    public static DbHelper getInstance() {
        return ourInstance;
    }

    public static void newInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DbHelper(context);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableCategoria(db);
        createTableItens(db);
        createTableUsers(db);
        createTableBanlAccount(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Item");
        db.execSQL("DROP TABLE Categoria");
        db.execSQL("DROP TABLE BankAccount");
        db.execSQL("DROP TABLE User");
        onCreate(db);
    }

    public void createTableCategoria(SQLiteDatabase db){
        String sql = SQL_CREATE_TABLE + "Categoria";
        sql += "( id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "  descricao VARCHAR(100) NOT NULL, \n" +
                "  loginUser VARCHAR(30) NOT NULL, \n" +
                "  FOREIGN KEY(loginUser) REFERENCES User(login)\n" +
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
                "  idBankAccount INTEGER NOT NULL,\n" +
                "  FOREIGN KEY(idCategoria) REFERENCES Categoria(id), \n" +
                "  FOREIGN KEY(idBankAccount) REFERENCES BankAccount(id) \n" +
                ")";
        db.execSQL(sql);
    }

    public void createTableUsers(SQLiteDatabase db) {
        String sql = SQL_CREATE_TABLE + "User";
        sql += "( login VARCHAR(30) PRIMARY KEY, \n" +
                "  password VARCHAR(30) NOT NULL,\n" +
                "  email VARCHAR(50) NULL,\n" +
                "  isConnected INTEGER NULL\n" +
                ")";
        db.execSQL(sql);
    }

    public void createTableBanlAccount(SQLiteDatabase db) {
        String sql = SQL_CREATE_TABLE + "BankAccount";
        sql += "( id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "  name VARCHAR(30) NOT NULL,\n" +
                "  loginUser VARCHAR(30) NOT NULL,\n" +
                "  saldo REAL NULL, \n" +
                "  FOREIGN KEY(loginUser) REFERENCES User(login)\n" +
                ")";
        db.execSQL(sql);
    }

    public User getUserActive() {
        return userActive;
    }

    public void setUserActive(User userActive) {
        this.userActive = userActive;
    }
}
