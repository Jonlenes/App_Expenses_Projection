package com.example.administrador.teste.Gui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrador.teste.AsyncTasks.SearchItemTask;
import com.example.administrador.teste.Gui.AdapterListView.AdapterListItem;
import com.example.administrador.teste.R;

public class ItemActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fabInserirItem;
    private AdapterListItem adapterListItem;
    private Long idCategoria;
    private View.OnClickListener onClickListenerAddItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentAddItem = new Intent(ItemActivity.this, MntItemActivity.class);
            intentAddItem.putExtra("idCategoria", ItemActivity.this.idCategoria);
            ItemActivity.this.startActivity(intentAddItem);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        idCategoria = getIntent().getLongExtra("Id", -1);
        listView = ((ListView) findViewById(R.id.itensListView));
        fabInserirItem = (FloatingActionButton) findViewById(R.id.fabInserirItem);

        fabInserirItem.setOnClickListener(onClickListenerAddItem);

        if (idCategoria != -1) {
            new SearchItemTask(this, listView, adapterListItem).execute(idCategoria);
        } else {
            Toast.makeText(this, "Erro na categoria.", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        new SearchItemTask(this, listView, adapterListItem).execute(idCategoria);
    }
}
