package com.example.administrador.teste.Gui.Activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrador.teste.AsyncTasks.InsertItemTask;
import com.example.administrador.teste.AsyncTasks.SearchItemTask;
import com.example.administrador.teste.Gui.AdapterListView.AdapterListItem;
import com.example.administrador.teste.Modelo.Dao.ItemDao;
import com.example.administrador.teste.R;

public class ItemActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterListItem adapterListItem;

    private EditText descricaoEditText;
    private EditText valorEditText;
    private Button addItemButton;
    private Button cancelButton;
    private CheckBox checkBoxPegarRestante;

    private Long idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        idCategoria = getIntent().getLongExtra("Id", -1);

        listView = ((ListView) findViewById(R.id.itensListView));
        descricaoEditText = (EditText) findViewById(R.id.descricaoItemEditText);
        valorEditText = (EditText) findViewById(R.id.valorItemEditText);
        addItemButton = (Button) findViewById(R.id.addItemButton);
        cancelButton = (Button) findViewById(R.id.buttonCancel);
        checkBoxPegarRestante = (CheckBox) findViewById(R.id.restanteValorCheckBox);

        addItemButton.setOnClickListener(onClickListenerInserir);
        cancelButton.setOnClickListener(onClickListenerCancel);
        checkBoxPegarRestante.setOnCheckedChangeListener(onCheckedChangeListenerPegarRestante);

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

    private View.OnClickListener onClickListenerInserir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (descricaoEditText.getText().length() <= 0) {
                descricaoEditText.setError("Preencha a descrição do item.");
            } else if (valorEditText.getText().length() <= 0 && !checkBoxPegarRestante.isChecked()) {
                valorEditText.setError("Preencha o valor.");
            } else {
                new InsertItemTask(ItemActivity.this).execute();
            }
        }
    };

    private View.OnClickListener onClickListenerCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListenerPegarRestante = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                valorEditText.setText("");
                valorEditText.setEnabled(isChecked);
        }
    };
}
