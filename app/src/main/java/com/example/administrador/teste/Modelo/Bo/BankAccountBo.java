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

    public void inserir(BankAccount bankAccount) throws ModelException {
        if (bankAccountDao.containByName(bankAccount.getName()))
            throw new ModelException("Já existe uma conta bancária com esse nome.");

        bankAccount.setLoginUser(DbHelper.getInstance().getUserActive().getLogin());
        bankAccount.setSaldo(0.0);
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

    public Boolean userHaveBankAccount() {
        return bankAccountDao.userHaveBankAccount(DbHelper.getInstance().getUserActive().getLogin());
    }
}
