package com.example.administrador.teste.Modelo.Bo;

import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;

/**
 * Created by Jonlenes on 05/01/2016.
 */
public class ContaBo {

/*
* PARÂMETROS:
*   value: valor a ser depositado
*   categoria: categoria a depositar
*   item: item a depositar
*
* FLUXOGRAMA
*   se o item != null:
*       depositar todo o valor para o item
*   se a categoria != null:
*       se "item marcado como restante" pertence a categoria (nesse teste é só comparar o idCategoria do item restante com o id da categoria)
*           somar o campo "valor" de todos os itens da categoria
*           se valor somado for <= ao valor a ser depositado
*               atualize o saldo de cada item (exceto o item restante) para saldo + valor (itemAtual.setSaldo(itemAtual.getSaldo() + itemAtual.getValor())
*               atualize o saldo do "item restante" com saldo do "item restante" + (valor a depositar - valor somado)
*           else
*               lançar ModelException para informar o usuário.
*       else
*           distribuir o valor a depositar proporcionalmente ao campo "valor" de cada item
*   se item != null e categoria != null:
*       somar o campo "valor" de todos os itens da categoria
*       se valor somado for <= ao valor a ser depositado
*           atualize o saldo de cada item (exceto o item restante) para saldo + valor (itemAtual.setSaldo(itemAtual.getSaldo() + itemAtual.getValor())
*           atualize o saldo do "item restante" com saldo do "item restante" + (valor a depositar - valor somado)
*       else
*           lançar ModelException para informar o usuário.
*
* PRÓPOSITO:
*   depositar um valor do usuário
*
* RETORNO: void
*/

    /*
    * PARÂMETROS:
    *   value: valor a ser depositado
    *   categoria: categoria a depositar
    *   item: item a depositar
    *
    * FLUXOGRAMA
    *   se o item != null
    *       depositar todo o valor para o item
    *   else se categoria != null:
    *           somar o campo "valor" de todos os itens da categoria
    *           se valor somado for <= ao valor a ser depositado
    *       		se "item marcado como restante" pertence a categoria (nesse teste é só comprar o idCategoria do item restante com o id da categoria)
    *             		atualize o saldo de cada item (exceto o item restante) para saldo + valor (itemAtual.setSaldo(itemAtual.getSaldo() + itemAtual.getValor())
    *             		atualize o saldo do "item restante" com saldo do "item restante" + (valor a depositar - valor somado)
    *             	else
    *                   distribuir o valor a depositar proposionalmente ao campo "valor" de cada item
    *		  	else
    *          	    lançar ModelException para informar o usuário.
    *
    *  else
    *       somar o campo "valor" de todos os itens
    *       se valor somado for <= ao valor a ser depositado
    *           atualize o saldo de cada item (exceto o item restante) para saldo + valor (itemAtual.setSaldo(itemAtual.getSaldo() + itemAtual.getValor())
    *           atualize o saldo do "item restante" com saldo do "item restante" + (valor a depositar - valor somado)
    *       else
    *           lanchar ModelException para informar o usuário.
    */
    public void depositar(Double value, Categoria categoria, Item item) throws ModelException {

        if (item != null) {

        } else if (categoria != null) {

        } else {

        }


        ItemDao itemDao = new ItemDao();

        if (item != null) {
            item.setSaldo(item.getSaldo() + value);
            itemDao.altera(item);
            return;
        }

        if (categoria != null) {

        }

        Double valorNecessario = 0.0;/*itemDao.somarValor(categoria)*/
        if (valorNecessario > value) throw new ModelException("Valor insuficiente para distribuição.");

        ArrayList<Item> items = null;/*itemDao.getTodosNaoRestante(categoria);*/
        for (Item i : items) {
            i.setSaldo(i.getSaldo() + i.getValor());
            itemDao.altera(item);
        }

        return;
    }

    public void retirar(Double value, Item item) throws ModelException {
        if (item.getSaldo() < value) throw new ModelException("Saldo insuficiente.");
		item.setSaldo(item.getSaldo() - value);
		new ItemDao().altera(item);
    }
	
}
