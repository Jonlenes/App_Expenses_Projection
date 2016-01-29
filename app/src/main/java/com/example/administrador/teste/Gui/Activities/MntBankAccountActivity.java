package com.example.administrador.teste.Gui.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.teste.Modelo.Bo.BankAccountBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.BankAccount;
import com.example.administrador.teste.R;

public class MntBankAccountActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private String nameBankAccout;
    private Boolean isOnlyAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnt_bank_account);

        isOnlyAdd = getIntent().getBooleanExtra("isOnlyAdd", false);
        Button nextButtonBankAccount = (Button) findViewById(R.id.nextButtonBankAccount);
        nextButtonBankAccount.setOnClickListener(onClickListenerNext);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Inserindo conta bancária...");

        if (isOnlyAdd) {
            nextButtonBankAccount.setText("OK");
        }

    }

    private View.OnClickListener onClickListenerNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nameBankAccout = ((EditText) findViewById(R.id.nameBankAccountEditText)).getText().toString();

            if (nameBankAccout.isEmpty()) {
                Toast.makeText(MntBankAccountActivity.this,
                        "Preencha uma descrição para a conta bancária.",
                        Toast.LENGTH_LONG).show();
            } else {
                new BankAccountTask().execute(nameBankAccout);
            }
        }
    };

    public class BankAccountTask extends AsyncTask<String, Void, Void> {

        private String exceptionMessage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                new BankAccountBo().inserir(new BankAccount(params[0]));
            } catch (ModelException e) {
                e.printStackTrace();
                exceptionMessage = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            if (exceptionMessage != null && !exceptionMessage.isEmpty()) {
                Toast.makeText(MntBankAccountActivity.this, exceptionMessage, Toast.LENGTH_LONG).show();
            } else {
                if (!isOnlyAdd) startActivity(new Intent(MntBankAccountActivity.this, MainActivity.class));
                finish();
            }

        }
    }
}
