package com.example.administrador.teste.AsyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.administrador.teste.Gui.AdapterListView.AdapterListCategoria;
import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.Categoria;

import java.util.ArrayList;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class InsertCategoryTask extends AsyncTask<String, Void, ArrayList<Categoria>> {
    private ProgressDialog progressDialog;

    private String message;

    private AdapterListCategoria adapterListCategoria;
    private AlertDialog dialogInsert;

    public InsertCategoryTask(AdapterListCategoria adapterListCategoria, AlertDialog dialogInsert) {
        this.adapterListCategoria = adapterListCategoria;
        this.dialogInsert = dialogInsert;

        progressDialog = new ProgressDialog(dialogInsert.getContext());
        progressDialog.setMessage("Inserindo categoria...");
    }

    @Override
    protected ArrayList<Categoria> doInBackground(String... params) {
        try {
            CategoriaBo categoriaBo = new CategoriaBo();
            categoriaBo.insert(new Categoria(params[0]));
            return categoriaBo.getTodos();
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
    protected void onPostExecute(ArrayList<Categoria> arrayList) {
        super.onPostExecute(arrayList);

        progressDialog.dismiss();
        if (message.isEmpty()) {
            adapterListCategoria.setArrayList(arrayList);
            adapterListCategoria.notifyDataSetChanged();
            dialogInsert.dismiss();
        } else {
            dialogInsert.show();
            Toast.makeText(dialogInsert.getContext(), message, Toast.LENGTH_LONG).show();
        }

    }
}
