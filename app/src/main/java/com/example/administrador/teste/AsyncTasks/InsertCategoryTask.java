package com.example.administrador.teste.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.administrador.teste.Gui.Dialogs.DialogInsertCategory;
import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.Categoria;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class InsertCategoryTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ProgressDialog progressDialog;
    private String message;


    public InsertCategoryTask(Context context) {
        this.context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Inserindo categoria...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            new CategoriaBo().insert(new Categoria(params[0]));
        } catch (ModelException e) {
            message = e.getMessage();
        }
        return params[0];
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        progressDialog.dismiss();
        if (message != null && !message.isEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            //new DialogInsertCategory(context, s);
        }

    }
}
