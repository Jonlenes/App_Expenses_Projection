package com.example.administrador.teste.Modelo.Bo;

import com.example.administrador.teste.Modelo.Dao.CategoriaDao;
import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 04/01/2016.
 */
public class CategoriaBo {
    private CategoriaDao categoriaDao;

    public CategoriaBo() {
        categoriaDao = new CategoriaDao();
    }

    public void insert(Categoria categoria) throws ModelException {
        if (categoriaDao.contemPorDescricao(categoria.getDescricao()))
            throw new ModelException("Já possui uma categoria com a mesma descrição.");
        categoriaDao.insere(categoria);
    }

    public void altera(Categoria categoria) {
        try {
            categoriaDao.altera(categoria);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(Categoria categoria) {
        try {
            categoriaDao.exclui(categoria.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Categoria> getTodos() {
        try {
            return categoriaDao.getTodos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<Categoria, ArrayList<Item>> getCategoriasComItens() {
        ArrayList<Categoria> categoriaArrayList = categoriaDao.getTodos();

        Map<Categoria, ArrayList<Item>> listMap = new HashMap<>();
        ItemDao itemDao = new ItemDao();
        for (Categoria categoria : categoriaArrayList) {
            ArrayList<Item> items = itemDao.getTodosPorCategoria(categoria.getId());
            listMap.put(categoria, items);
        }

        return listMap;
    }
}
