package com.example.administrador.teste.Modelo.Bo;

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
        if (itemDao.contemPorDescricao(item.getDescricao(), DbHelper.getInstance().getUserActive().getLogin()))
            throw new ModelException("Já possui um item com a mesma descrição.");

        List<BankAccount> accounts =  new BankAccountBo().getBankAccountUser();
        //Pegar apenas a primeira conta é temporário

        //verificar saldo da conta

        item.setIdBankAccount(accounts.get(0).getId());
        itemDao.insere(item);
    }

    public void altera(Item item) throws ModelException {
        if (itemDao.contemPorDescricao(item.getDescricao(), DbHelper.getInstance().getUserActive().getLogin()))
            throw new ModelException("Já possui um item com a mesma descrição.");

        itemDao.altera(item);

    }

    public void excluir(Item item) throws ModelException {
        itemDao.exclui(item.getId());
    }

    public ArrayList<Item> getTodos() {
        try {
            List<BankAccount> accounts =  new BankAccountBo().getBankAccountUser();
            return itemDao.getTodos(accounts.get(0).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Item> getTodosPorCategoria(Long idCategoria) {
        try {
            List<BankAccount> accounts =  new BankAccountBo().getBankAccountUser();
            return itemDao.getTodosPorCategoria(idCategoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
