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

    public void altera(Categoria categoria) throws ModelException {
        if (categoriaDao.contemPorDescricao(categoria.getDescricao()))
            throw new ModelException("Já possui uma categoria com a mesma descrição.");

        categoriaDao.altera(categoria);
    }

    public void excluir(Categoria categoria) throws ModelException {
        ItemDao itemDao = new ItemDao();
		Item itemRestante = itemDao.getItemComRestanteAtivo();
		
		//verifico se a categoria a ser deletada não possui item restante
		if (itemRestante.getIdCategoria() == categoria.getId())
			throw new ModelException("Não é possivel excluir uma categoria que possua um item marcado como restante.");
		
		//soma o saldo dos itens exluidos e excluiu os mesmos
		ArrayList<Item> itens = itemDao.getTodosPorCategoria(categoria.getId());
		Double saldo = 0.0;
		for (Item item : itens) {
			saldo += item.getSaldo();
			itemDao.exclui(item.getId());
		}
		
		//atualiza o item restante somando o saldo dos itens excluidos
		itemRestante.setSaldo(itemRestante.getSaldo() + saldo);
		itemDao.altera(itemRestante);
		
		//finalmente, excluir a categoria
        categoriaDao.exclui(categoria.getId());
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
