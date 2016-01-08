package com.example.administrador.teste.Modelo.Bo;

import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;

/**
 * Created by Administrador on 04/01/2016.
 */
public class ItemBo {
    private ItemDao itemDao;

    public ItemBo() {
        itemDao = new ItemDao();
    }

    public void insert(Item item) throws ModelException {
        if (itemDao.contemPorDescricao(item.getDescricao()))
            throw new ModelException("Já possui um item com a mesma descrição.");

        itemDao.insere(item);
    }

    public void altera(Item item) {
        try {
            itemDao.altera(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(Item item) {
        try {
            itemDao.exclui(item.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getTodos() {
        try {
            return itemDao.getTodos();
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
	
	public void transferirSaldo(Item itemSaida, Item itemEntrada, Double valor) throws ModelException {
		if (itemSaida.getSaldo() < valor)  throw new ModelException("Saldo insuficiente.");
		
		itemEntrada.setSaldo(itemEntrada.getSaldo() + valor);
		itemSaida.setSaldo(itemSaida.getSaldo() - valor);
		
		//begin transações
		itemDao.altera(itemEntrada);
		itemDao.altera(itemSaida);
		//commit
		
	}
}
