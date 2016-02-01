package com.example.administrador.teste.AsyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumOperationBd;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class OperationCategoryTask extends AsyncTask<Categoria, Void, Void> {

    private Context context;
    private AlertDialog dialog;
    private ProgressDialog progressDialog;
    private String exceptionMessage;
    private EnumOperationBd operation;
    private String messageProgress[] = {"Inserindo categoria...",
            "Atualizando categoria...",
            "Deletando categoria"};

    public OperationCategoryTask(Context context, AlertDialog dialog, EnumOperationBd operation) {
        this.context = context;
        this.dialog = dialog;
        this.operation = operation;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(messageProgress[operation.ordinal()]);
        progressDialog.setCancelable(false);
    }

    @Override
    protected Void doInBackground(Categoria... params) {
        try {
            CategoriaBo categoriaBo = new CategoriaBo();
            switch (operation) {
                case insert:
                    categoriaBo.insert(params[0]);
                    break;

                case update:
                    categoriaBo.altera(params[0]);
                    break;

                case delete:
                    categoriaBo.excluir(params[0]);
                    break;
            }
        } catch (ModelException e) {
            exceptionMessage = e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        progressDialog.dismiss();
        if (exceptionMessage != null && !exceptionMessage.isEmpty()) {
            dialog.show();
            Toast.makeText(context, exceptionMessage, Toast.LENGTH_LONG).show();
        } else if (operation != EnumOperationBd.delete) {
            dialog.dismiss();
        }

    }
}
