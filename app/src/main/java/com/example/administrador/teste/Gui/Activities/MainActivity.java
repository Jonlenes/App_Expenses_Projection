package com.example.administrador.teste.Gui.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrador.teste.AsyncTasks.SearchCategoryTask;
import com.example.administrador.teste.Gui.AdapterListView.AdapterListCategoria;
import com.example.administrador.teste.Gui.Dialogs.TesteAAA;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.R;

public class MainActivity extends Activity {
    View.OnClickListener onClickListenerInserir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //new DialogInsertCategory(MainActivity.this, "");
            new TesteAAA(MainActivity.this);
        }
    };
    private ListView listViewCategoria;
    private FloatingActionButton fabInserir;
    private AdapterListCategoria adapterListCategoria;
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

        listViewCategoria = ((ListView) findViewById(R.id.categoriasListView));
        fabInserir = (FloatingActionButton) findViewById(R.id.fabInserir);

        listViewCategoria.setOnItemClickListener(onClickLista);
        fabInserir.setOnClickListener(onClickListenerInserir);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new SearchCategoryTask(this, listViewCategoria, adapterListCategoria).execute();
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
