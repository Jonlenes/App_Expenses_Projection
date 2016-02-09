package com.example.administrador.teste.Gui.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.teste.AsyncTasks.SearchCategoryTask;
import com.example.administrador.teste.Modelo.Bo.BankAccountBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumOperationBankAccount;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumTypeBankAccount;
import com.example.administrador.teste.R;

/**
 * Created by Jonlenes on 11/01/2016.
 */
public class DialogOperationBankAccount extends AlertDialog {
    private Long idBankAccout;
    private Context context;
    private EditText valorDepositarEditText;
    private EnumOperationBankAccount operationBankAccount;
    private EnumTypeBankAccount typeBankAccount;

    String s;

    public DialogOperationBankAccount(Context context, Long idBankAccout, EnumOperationBankAccount operationBankAccount, String s) {
        super(context);

        this.idBankAccout = idBankAccout;
        this.context = context;
        this.operationBankAccount = operationBankAccount;
        this.s = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operation_bank_account);
        setCancelable(true);

        Spinner spinner = ((Spinner) findViewById(R.id.typeBankAccoutnSpinner));
        if (operationBankAccount == EnumOperationBankAccount.depositar) {
            spinner.setAdapter(ArrayAdapter.createFromResource(context,
                    R.array.itens_type_bank_account, android.R.layout.simple_spinner_item));
        } else {
            spinner.setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.titleTextView)).setText("Sacar");
            typeBankAccount = EnumTypeBankAccount.corrente;
        }
        valorDepositarEditText = (EditText) findViewById(R.id.valorDepositarEditText);
        valorDepositarEditText.setText(s);

        findViewById(R.id.cancelDepositarButton).setOnClickListener(clickListenerCancel);
        findViewById(R.id.okDepositarButton).setOnClickListener(clickListenerOk);

    }

    private View.OnClickListener clickListenerCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
    private View.OnClickListener clickListenerOk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Double valor = Double.parseDouble(((EditText) findViewById(R.id.valorDepositarEditText)).getText().toString());

            if (valor <= 0)  valorDepositarEditText.setError("Valor inválido.");
            else {
                if (typeBankAccount == null) typeBankAccount = EnumTypeBankAccount.values()[
                        ((Spinner) findViewById(R.id.typeBankAccoutnSpinner)).getSelectedItemPosition()];
                new BankAccountTask().execute(valor);
            }
        }
    };

    public class BankAccountTask extends AsyncTask<Double, Void, Void> {
        private ProgressDialog progressDialog;
        private String messageException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Executando operação...");
        }

        @Override
        protected Void doInBackground(Double... params) {
            try {
            if (operationBankAccount == EnumOperationBankAccount.depositar)
                new BankAccountBo().depositar(idBankAccout, params[0], typeBankAccount);
            else
                new BankAccountBo().sacar(idBankAccout, params[0]);
            } catch (ModelException e) {
                e.printStackTrace();
                messageException = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (messageException != null) {
                Toast.makeText(context, messageException, Toast.LENGTH_LONG).show();
            } else {
                dismiss();
            }
        }
    }
}
