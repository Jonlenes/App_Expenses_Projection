package com.example.administrador.teste.Gui.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrador.teste.AsyncTasks.InsertCategoryTask;
import com.example.administrador.teste.Gui.AdapterListView.AdapterListCategoria;
import com.example.administrador.teste.AsyncTasks.InitialOperationDbTask;
import com.example.administrador.teste.Gui.Dialogs.DialogLoading;
import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.DbHelper;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.R;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ListView listViewCategoria;

    private AdapterListCategoria adapterListCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewCategoria = ((ListView) findViewById(R.id.categoriasListView));
        listViewCategoria.setOnItemClickListener(onClickLista);

        new InitialOperationDbTask(this, new DialogLoading(this).create(), listViewCategoria, adapterListCategoria).execute();
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
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_insert_category, null));

        final EditText editTextDescricao = (EditText) findViewById(R.id.descricaoInsertCategoriaEditText);
        Button buttonOk = (Button) findViewById(R.id.inserirCategoryButton);
        final AlertDialog dialog = builder.create();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextDescricao.getText().length() > 0) {
                    new InsertCategoryTask(adapterListCategoria,
                            new DialogLoading(MainActivity.this).create(),
                            dialog);
                } else {
                    editTextDescricao.setError("Preencha a descrição da categoria");
                }
            }
        });

        dialog.show();


        /*final EditText editTextDescricao = new EditText(this);
        final Button buttonOk = new Button(this);
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);

        builder
                .setTitle(R.string.titulo_nova_categoria)
                .setMessage(R.string.message_new_category)
                .setView(editTextDescricao)
                .setView(buttonOk)
                .setNegativeButton(R.string.cancel_operation, null);

        final AlertDialog dialog = builder.create();

        buttonOk.setText(R.string.add_categoria);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextDescricao.getText().length() > 0) {
                    new InsertCategoryTask(adapterListCategoria,
                            new DialogLoading(MainActivity.this).create(),
                            dialog);
                } else {
                    editTextDescricao.setError("Preencha a descrição da categoria");
                }
            }
        });

        dialog.show();*/
    }

    /*private Runnable rInsertCategoria = new Runnable() {
        public String descricao;

        @Override
        public void run() {
            try {
                CategoriaBo categoriaBo = new CategoriaBo();
                categoriaBo.insert(new Categoria(descricao));
                arrayList = categoriaBo.getTodos();
                adapterListCategoria.setArrayList(arrayList);
                adapterListCategoria.notifyDataSetChanged();

                //((ListView) findViewById(R.id.categoriasListView)).setAdapter(new AdapterListCategoria(MainActivity.this, arrayList));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };*/

    private AdapterView.OnItemClickListener onClickLista = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intentItem = new Intent(getBaseContext(), ItemActivity.class);
            intentItem.putExtra("Id", ((Categoria) parent.getItemAtPosition(position)).getId());
            startActivity(intentItem);
        }
    };

}
