package com.example.administrador.teste.Gui.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.teste.Modelo.Bo.BankAccountBo;
import com.example.administrador.teste.Modelo.Bo.ItemBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumOperationBankAccount;
import com.example.administrador.teste.Modelo.Vo.Item;
import com.example.administrador.teste.R;

import java.text.NumberFormat;

public class ResumeItemActivity extends AppCompatActivity {
    private Long idItem;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idItem = getIntent().getLongExtra("id", -1);

        new ResumeItemTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resume_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_lancar_gastos:
                final EditText editText = new EditText(ResumeItemActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                new AlertDialog.Builder(ResumeItemActivity.this).setTitle("Valor a lançar")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new LancarGastos().execute(Double.parseDouble(editText.getText().toString()));
                            }
                        })
                        .setView(editText)
                        .create().show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ResumeItemTask extends AsyncTask<Void, Void, Item> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ResumeItemActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Buscando dados...");
        }

        @Override
        protected Item doInBackground(Void... params) {
            return new ItemBo().getItem(idItem);
        }

        @Override
        protected void onPostExecute(Item item) {
            super.onPostExecute(item);

            ResumeItemActivity.this.item = item;
            ResumeItemActivity.this.getSupportActionBar().setTitle(item.getDescricao());
            ResumeItemActivity.this.getSupportActionBar().setSubtitle(NumberFormat.getCurrencyInstance().format(item.getSaldo()));
            ((TextView) findViewById(R.id.valorPorjetarTextView)).setText(NumberFormat.getCurrencyInstance().format(item.getValor()));

            progressDialog.dismiss();

        }
    }

    public class LancarGastos extends AsyncTask<Double, Void, Void> {
        private ProgressDialog progressDialog;
        private String messageException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ResumeItemActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Lancando gastos...");
        }

        @Override
        protected Void doInBackground(Double... params) {
            try {
                new ItemBo().lancarDespesa(item, params[0]);
            } catch (ModelException e) {
                e.printStackTrace();
                messageException = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            if (messageException != null) {
                Toast.makeText(ResumeItemActivity.this, messageException, Toast.LENGTH_LONG).show();
            } else {
                new ResumeItemTask().execute();
                Toast.makeText(ResumeItemActivity.this, "sucesso.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
