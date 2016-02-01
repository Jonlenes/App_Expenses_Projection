package com.example.administrador.teste.Modelo.Bo;

import com.example.administrador.teste.Modelo.Dao.CategoriaDao;
import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        String loginUser = DbHelper.getInstance().getUserActive().getLogin();

        if (categoriaDao.contemPorDescricao(categoria.getDescricao(), loginUser))
            throw new ModelException("Já possui uma categoria com a mesma descrição.");

        categoria.setLoginUser(loginUser);
        categoriaDao.insere(categoria);
    }

    public void altera(Categoria categoria) throws ModelException {
        if (categoriaDao.contemPorDescricao(categoria.getDescricao(), categoria.getLoginUser()))
            throw new ModelException("Já possui uma categoria com a mesma descrição.");

        categoriaDao.altera(categoria);
    }

    public Categoria getCategoriaById(Long id) {
        return categoriaDao.getCategoriaById(DbHelper.getInstance().getUserActive().getLogin(), id);
    }

    public void excluir(Categoria categoria) throws ModelException {
        ItemDao itemDao = new ItemDao();

        List<Item> items = itemDao.getTodosPorCategoria(categoria.getId());
        for (Item item : items) {
            itemDao.exclui(item.getId());
        }
        categoriaDao.exclui(categoria.getId());
    }

    public ArrayList<Categoria> getTodos() {
        return categoriaDao.getTodos(DbHelper.getInstance().getUserActive().getLogin());
    }

    public Map<String, Double> getAllByBankAccout(Long idBankAccout) {
        return categoriaDao.getAllByBankAccout(idBankAccout);
    }

}
