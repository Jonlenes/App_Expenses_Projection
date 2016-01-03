package com.example.administrador.teste;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrador.teste.Modelo.Bo.DbHelper;
import com.example.administrador.teste.Modelo.Dao.CategoriaDao;
import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper helper = new DbHelper(this);

        CategoriaDao categoriaDao = new CategoriaDao(helper.getWritableDatabase());
        categoriaDao.insere(new Categoria("Categoria 1"));
        categoriaDao.insere(new Categoria("Categoria 2"));

        ItemDao itemDao = new ItemDao(helper.getWritableDatabase());
        itemDao.insere(new Item(1l, "item1", 100.0, 100.0));
        itemDao.insere(new Item(1l, "item2", 100.0, 80.0));
        itemDao.insere(new Item(1l, "item3", 100.0, 1100.0));

        itemDao.insere(new Item(2l, "item4", 100.0, 100.0));
        itemDao.insere(new Item(2l, "item5", 100.0, 80.0));
        itemDao.insere(new Item(2l, "item6", 100.0, 400.0));

        Set<Map.Entry<Categoria, ArrayList<Item>>> set = categoriaDao.getCategoriasComItens().entrySet();

        String s = null;
        for (Map.Entry<Categoria, ArrayList<Item>> entry : set) {
            s += (entry.getKey().getDescricao()) + "\n";
            for (Item i : entry.getValue()) {
                s += ("\t" + i.getDescricao() + "\n");
            }
        }
        ((TextView) findViewById(R.id.resultEsforcoDoMeuAmor)).setText(s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
