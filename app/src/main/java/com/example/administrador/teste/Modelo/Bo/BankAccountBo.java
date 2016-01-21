package com.example.administrador.teste.Modelo.Bo;

import com.example.administrador.teste.Modelo.Dao.BankAccountDao;
import com.example.administrador.teste.Modelo.Vo.BankAccount;

import java.util.List;

/**
 * Created by Administrador on 21/01/2016.
 */
public class BankAccountBo {
    private BankAccountDao bankAccountDao;

    public BankAccountBo() {
        bankAccountDao = new BankAccountDao();
    }

    public void inserir(BankAccount bankAccount) {
        bankAccountDao.insert(bankAccount);
    }

    public void update(BankAccount bankAccount) {
        bankAccountDao.update(bankAccount);
    }

    public void delete(BankAccount bankAccount) {
        bankAccountDao.delete(bankAccount.getId());
    }

    public List<BankAccount> getBankAccountUser() {
        return  bankAccountDao.getBankAccount(DbHelper.getInstance().getUserActive().getLogin());
    }
}
