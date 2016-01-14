package com.example.administrador.teste.Gui.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.administrador.teste.AsyncTasks.OperationItemTask;
import com.example.administrador.teste.Modelo.Vo.EnumOperation;
import com.example.administrador.teste.Modelo.Vo.Item;
import com.example.administrador.teste.R;

public class MntItemActivity extends Activity {

    private EditText descricaoEditText;
    private EditText valorEditText;
    private EditText saldoInicialEditText;
    private Button addItemButton;
    private Button cancelButton;
    private CheckBox checkBoxPegarRestante;
    private Long idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        idCategoria = getIntent().getLongExtra("idCategoria", -1);

        descricaoEditText = (EditText) findViewById(R.id.descricaoItemEditText);
        valorEditText = (EditText) findViewById(R.id.valorItemEditText);
        saldoInicialEditText = (EditText) findViewById(R.id.saldoInicialEditText);
        addItemButton = (Button) findViewById(R.id.addItemButton);
        cancelButton = (Button) findViewById(R.id.buttonCancel);
        checkBoxPegarRestante = (CheckBox) findViewById(R.id.restanteValorCheckBox);

        addItemButton.setOnClickListener(onClickListenerInserir);
        cancelButton.setOnClickListener(onClickListenerCancel);
        checkBoxPegarRestante.setOnCheckedChangeListener(onCheckedChangeListenerPegarRestante);
    }

    private View.OnClickListener onClickListenerInserir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (descricaoEditText.getText().length() <= 0) {
                descricaoEditText.setError("Preencha a descrição do item.");
            } else if (valorEditText.getText().length() <= 0 && !checkBoxPegarRestante.isChecked()) {
                valorEditText.setError("Preencha o valor.");
            } else {
                new OperationItemTask(MntItemActivity.this, EnumOperation.insert).execute(new Item(MntItemActivity.this.idCategoria,
                        descricaoEditText.getText().toString(),
                        checkBoxPegarRestante.isChecked() ? 0 : Double.parseDouble(valorEditText.getText().toString()),
                        saldoInicialEditText.getText().length() <= 0 ? 0 : Double.parseDouble(saldoInicialEditText.getText().toString())));
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
