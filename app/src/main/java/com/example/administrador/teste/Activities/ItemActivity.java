package com.example.administrador.teste.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrador.teste.AdapterList.AdapterListItem;
import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.R;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        long id = getIntent().getLongExtra("id", -1);

        if (id != -1) {
            ((ListView) findViewById(R.id.itensListView)).setAdapter(new AdapterListItem(this,
                    new ItemDao().getTodosPorCategoria(id)));
        } else {
            Toast.makeText(this, "Erro na categoria.", Toast.LENGTH_LONG);
        }
    }
}
