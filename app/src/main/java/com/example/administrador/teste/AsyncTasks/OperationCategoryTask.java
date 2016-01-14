package com.example.administrador.teste.AsyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.EnumOperation;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class OperationCategoryTask extends AsyncTask<Categoria, Void, Void> {

    private Context context;
    private AlertDialog dialog;
    private ProgressDialog progressDialog;
    private String message;
    private EnumOperation operation;


    public OperationCategoryTask(Context context, AlertDialog dialog, EnumOperation operation) {
        this.context = context;
        this.dialog = dialog;
        this.operation = operation;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Inserindo categoria...");
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
            message = e.getMessage();
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
        if (message != null && !message.isEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            dialog.dismiss();
        }

    }
}
