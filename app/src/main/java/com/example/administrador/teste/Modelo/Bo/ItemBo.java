package com.example.administrador.teste.Modelo.Bo;

import android.graphics.AvoidXfermode;

import com.example.administrador.teste.Modelo.Dao.BankAccountDao;
import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.Modelo.Vo.BankAccount;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 04/01/2016.
 */
public class ItemBo {
    private ItemDao itemDao;

    public ItemBo() {
        itemDao = new ItemDao();
    }

    public void insert(Item item) throws ModelException {
        if (itemDao.contemPorDescricao(-1l, item.getDescricao(), DbHelper.getInstance().getUserActive().getLogin()))
            throw new ModelException("Já possui um item com a mesma descrição.");

        List<BankAccount> accounts =  new BankAccountBo().getBankAccountUser();
        //Pegar apenas a primeira conta é temporário

        BankAccount bankAccount = new BankAccountBo().getBankAccount(accounts.get(0).getId());
        if ((bankAccount.getSaldoCorrente() - bankAccount.getSaldoProjetado()) < item.getSaldo())
            throw new ModelException("Saldo insuficiente.");

        item.setIdBankAccount(accounts.get(0).getId());
        itemDao.insere(item);
    }

    public void altera(Item item) throws ModelException {
        if (itemDao.contemPorDescricao(item.getId(), item.getDescricao(), DbHelper.getInstance().getUserActive().getLogin()))
            throw new ModelException("Já possui um item com a mesma descrição.");

        itemDao.altera(item);

    }

    public void excluir(Item item) throws ModelException {
        itemDao.exclui(item.getId());
    }

    public ArrayList<Item> getAll(Long idBankAccount) {
        try {
            return itemDao.getTodos(idBankAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Item> getTodosPorCategoria(Long idCategoria) {
        try {
            return itemDao.getTodosPorCategoria(idCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void lancarDespesa(Item item, Double valor) throws ModelException {
        if (item.getSaldo() < valor)
            throw new ModelException("Saldo insuficiente.");

        item.setSaldo(item.getSaldo() - valor);
        BankAccountBo bankAccountBo = new BankAccountBo();
        BankAccount bankAccount = bankAccountBo.getBankAccountUser().get(0);
        bankAccount.setSaldoCorrente(bankAccount.getSaldoCorrente() - valor);
        bankAccountBo.update(bankAccount);

        itemDao.altera(item);


    }

    public Item getItem(Long id) {
        return itemDao.getItem(id);
    }
	
	/*public void transferirSaldo(Item itemSaida, Item itemEntrada, Double valor) throws ModelException {
		if (itemSaida.getSaldo() < valor)  throw new ModelException("Saldo insuficiente.");
		
		itemEntrada.setSaldo(itemEntrada.getSaldo() + valor);
		itemSaida.setSaldo(itemSaida.getSaldo() - valor);
		
		//begin transações
		itemDao.altera(itemEntrada);
		itemDao.altera(itemSaida);
		//commit
		
	}*/
}
