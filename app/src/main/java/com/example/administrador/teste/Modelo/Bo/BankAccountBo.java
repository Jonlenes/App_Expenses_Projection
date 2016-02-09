package com.example.administrador.teste.Modelo.Bo;

import com.example.administrador.teste.Modelo.Dao.BankAccountDao;
import com.example.administrador.teste.Modelo.Vo.BankAccount;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumTypeBankAccount;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.List;

import static com.example.administrador.teste.Modelo.Vo.Enum.EnumTypeBankAccount.*;

/**
 * Created by Administrador on 21/01/2016.
 */
public class BankAccountBo {
    private BankAccountDao bankAccountDao;

    public BankAccountBo() {
        bankAccountDao = new BankAccountDao();
    }

    public void insert(BankAccount bankAccount) throws ModelException {
        if (bankAccountDao.containByName(-1l, bankAccount.getName(), DbHelper.getInstance().getUserActive().getLogin()))
            throw new ModelException("Já existe uma conta bancária com esse nome.");

        bankAccount.setLoginUser(DbHelper.getInstance().getUserActive().getLogin());
        bankAccount.setSaldoCorrente(0.0);
        bankAccount.setSaldoPoupanca(0.0);

        bankAccountDao.insert(bankAccount);
    }

    public void update(BankAccount bankAccount) throws ModelException {
        if (bankAccountDao.containByName(bankAccount.getId(), bankAccount.getName(), DbHelper.getInstance().getUserActive().getLogin()))
            throw new ModelException("Já existe uma conta bancária com esse nome.");

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

    public BankAccount getBankAccount(Long id) {
        return bankAccountDao.getBankAccount(id);
    }

    public void depositar(Long idBankAccount, Double value, EnumTypeBankAccount typeBankAccount) throws ModelException {
        if (value <= 0) throw new ModelException("O valor deve ser maior do que zero.");

        BankAccount bankAccount = bankAccountDao.getBankAccount(idBankAccount);
        switch (typeBankAccount) {
            case corrente:
                bankAccount.setSaldoCorrente(bankAccount.getSaldoCorrente() + value);
                break;

            case poupanca:
                bankAccount.setSaldoPoupanca(bankAccount.getSaldoPoupanca() + value);
                break;
        }
        bankAccountDao.update(bankAccount);

    }

    public void sacar(Long idBankAccount, Double value) throws ModelException {
        if (value <= 0) throw new ModelException("O valor deve ser maior do que zero.");

        BankAccount bankAccount = bankAccountDao.getBankAccount(idBankAccount);
        bankAccount.setSaldoCorrente(bankAccount.getSaldoCorrente() - value);
        bankAccountDao.update(bankAccount);

    }

    public void projetar(Long idBankAccout, Double percent) throws ModelException {
        ItemBo itemBo = new ItemBo();
        List<Item> items = itemBo.getAll(idBankAccout);
        BankAccount bankAccount = new BankAccountBo().getBankAccount(idBankAccout);
        Double valorNecessario = 0.0;

        for (Item item : items) {
            valorNecessario += item.getValor() * percent;
        }

        if (valorNecessario >= (bankAccount.getSaldoCorrente() - bankAccount.getSaldoProjetado()))
            throw new ModelException("Saldo disponivel insuficiente.");

        for (Item item : items) {
            item.setSaldo(item.getSaldo() + item.getValor() * percent);
            itemBo.altera(item);
        }
    }
}
