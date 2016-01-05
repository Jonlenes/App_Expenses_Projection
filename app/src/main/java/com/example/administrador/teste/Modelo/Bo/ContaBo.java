package com.example.administrador.teste.Modelo.Bo;

import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;

/**
 * Created by Jonlenes on 05/01/2016.
 */
public class ContaBo {
    public Boolean depositar(Double value, Categoria categoria, Item item) throws ModelException {
        ItemDao itemDao = new ItemDao();

        if (item != null) {
            item.setSaldo(item.getSaldo() + value);
            itemDao.altera(item);
            return true;
        }

        Double valorNecessario = itemDao.somarValor(categoria);
        if (valorNecessario > value) throw new ModelException("Valor insuficiente para distribuição.");

        ArrayList<Item> items = itemDao.getTodosNaoRestante(categoria);
        for (Item i : items) {
            i.setSaldo(i.getSaldo() + i.getValor());
            itemDao.altera(item);
        }
        Item itemComRestante = itemDao.getItemComRestanteAtivo();
        itemComRestante.setSaldo(itemComRestante.getSaldo() + (value - valorNecessario));
        itemDao.altera(itemComRestante);

        return true;
    }

    public Boolean retirar(Double value, Item item) {
        return true;
    }
}
