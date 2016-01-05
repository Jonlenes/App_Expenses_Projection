package com.example.administrador.teste.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrador.teste.AdapterList.AdapterListCategoria;
import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.DbHelper;
import com.example.administrador.teste.Modelo.Dao.CategoriaDao;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.R;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private AdapterListCategoria adapterListCategoria;
    private ArrayList<Categoria> arrayList;
    private AdapterView.OnItemClickListener onClickLista = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intentItem = new Intent(getBaseContext(), ItemActivity.class);
            intentItem.putExtra("Id", ((Categoria) parent.getItemAtPosition(position)).getId());
            startActivity(intentItem);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper.newInstance(this);
        //helper.getWritableDatabase().execSQL("DELETE FROM Categoria");
        //helper.getWritableDatabase().execSQL("DELETE FROM Item");
        CategoriaDao categoriaDao = new CategoriaDao();
        /*categoriaDao.insere(new Categoria("Categoria 5"));
        categoriaDao.insere(new Categoria("Categoria 6"));
        categoriaDao.insere(new Categoria("Categoria 7"));

        ItemDao itemDao = new ItemDao();
        itemDao.insere(new Item(1l, "item1", 100.0, 100.0));
        itemDao.insere(new Item(1l, "item2", 100.0, 80.0));
        itemDao.insere(new Item(1l, "item3", 100.0, 1100.0));

        itemDao.insere(new Item(2l, "item4", 100.0, 100.0));
        itemDao.insere(new Item(2l, "item5", 100.0, 80.0));
        itemDao.insere(new Item(2l, "item6", 100.0, 400.0));*/

        ListView listViewCategoria = ((ListView) findViewById(R.id.categoriasListView));

        arrayList = categoriaDao.getTodos();
        adapterListCategoria = new AdapterListCategoria(this, arrayList);

        listViewCategoria.setAdapter(adapterListCategoria);
        listViewCategoria.setOnItemClickListener(onClickLista);


        /*Set<Map.Entry<Categoria, ArrayList<Item>>> set = categoriaDao.getCategoriasComItens().entrySet();

        helper.getWritableDatabase().close();

        String s = null;
        for (Map.Entry<Categoria, ArrayList<Item>> entry : set) {
            s += (entry.getKey().getDescricao()) + "\n";
            for (Item i : entry.getValue()) {
                s += ("\t" + i.getDescricao() + "\n");
            }
        }*/
        //((TextView) findViewById(R.id.resultEsforcoDoMeuAmor)).setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_category:
                showPopUpNewCategory();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopUpNewCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editTextDescricao = new EditText(this);

        DialogInterface.OnClickListener clickOk = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editTextDescricao.getText().length() > 0) {
                    //Log.i("Teste", "Entrou no onclick");
                    //Toast.makeText(getBaseContext(), "Entrou no onclick", Toast.LENGTH_LONG).show();
                    try {
                        CategoriaBo categoriaBo = new CategoriaBo();
                        categoriaBo.insert(new Categoria(editTextDescricao.getText().toString()));
                        arrayList = categoriaBo.getTodos();
                        //adapterListCategoria.notifyDataSetChanged();

                        ((ListView) findViewById(R.id.categoriasListView)).setAdapter(new AdapterListCategoria(MainActivity.this, arrayList));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                } else {
                    editTextDescricao.setError("Preencha a descrição da categoria");
                }
                Toast.makeText(getBaseContext(), "Descrição: " + editTextDescricao.getText().toString(), Toast.LENGTH_LONG).show();
            }
        };

        builder
                .setCancelable(false)
                .setTitle(R.string.titulo_nova_categoria)
                .setMessage(R.string.message_new_category)
                .setView(editTextDescricao)
                .setPositiveButton(R.string.add_categoria, clickOk)
                .setNegativeButton(R.string.cancel_operation, null);
        builder.create().show();
    }

}
