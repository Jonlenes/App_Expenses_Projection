package com.example.administrador.teste.Gui.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.administrador.teste.AsyncTasks.OperationItemTask;
import com.example.administrador.teste.Modelo.Bo.ItemBo;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumOperationBd;
import com.example.administrador.teste.Modelo.Vo.Item;
import com.example.administrador.teste.R;

public class MntItemActivity extends Activity {

    private EditText descricaoEditText;
    private EditText valorEditText;
    private EditText saldoInicialEditText;
    private Long idItem;
    private Long idCategoria;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnt_item);

        idItem = getIntent().getLongExtra("id", -1);
        idCategoria = getIntent().getLongExtra("idCategoria", -1);

        descricaoEditText = (EditText) findViewById(R.id.descricaoItemEditText);
        valorEditText = (EditText) findViewById(R.id.valorItemEditText);
        saldoInicialEditText = (EditText) findViewById(R.id.saldoInicialEditText);
        Button addItemButton = (Button) findViewById(R.id.addItemButton);
        Button cancelButton = (Button) findViewById(R.id.buttonCancel);

        addItemButton.setOnClickListener(onClickListenerInserir);
        cancelButton.setOnClickListener(onClickListenerCancel);

        if (idItem != -1) {
            new BuscaItem().execute();
        }
    }

    private View.OnClickListener onClickListenerInserir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (descricaoEditText.getText().length() <= 0) {
                descricaoEditText.setError("Preencha a descrição do item.");
            } else if (valorEditText.getText().length() <= 0) {
                valorEditText.setError("Preencha o valor.");
            } else {
                EnumOperationBd operation;

                if (item != null) {
                    //atualizo os atributos que podem ser alterados na tela
                    item.setDescricao(descricaoEditText.getText().toString());
                    item.setValor(Double.parseDouble(valorEditText.getText().toString()));
                    operation = EnumOperationBd.update;
                } else {
                    //crio um novo item
                    item = new Item(MntItemActivity.this.idCategoria,
                                    descricaoEditText.getText().toString(),
                                    Double.parseDouble(valorEditText.getText().toString()),
                                    saldoInicialEditText.getText().length() <= 0? 0 : Double.parseDouble(saldoInicialEditText.getText().toString()));
                    operation = EnumOperationBd.insert;
                }

                new OperationItemTask(MntItemActivity.this, operation).execute(item);
            }
        }
    };

    private View.OnClickListener onClickListenerCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    public class BuscaItem extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MntItemActivity.this);
            progressDialog.setMessage("Buscando item...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            item = new ItemBo().getItem(idItem);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            descricaoEditText.setText(item.getDescricao());
            valorEditText.setText(String.valueOf(item.getValor()));
            saldoInicialEditText.setText(String.valueOf(item.getSaldo()));
            saldoInicialEditText.setEnabled(false);
            progressDialog.dismiss();
        }
    }

}
