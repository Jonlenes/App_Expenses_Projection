package com.example.administrador.teste.Modelo.Bo;

import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.Modelo.Vo.Item;

/**
 * Created by Administrador on 04/01/2016.
 */
public class ItemBo {
    private ItemDao itemDao;

    public ItemBo() {
        itemDao = new ItemDao();
    }

    public void insert(Item item) {
        try {
            itemDao.insere(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void getTodos() {
        try {
            itemDao.getTodos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
