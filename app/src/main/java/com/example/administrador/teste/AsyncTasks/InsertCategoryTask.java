package com.example.administrador.teste.AsyncTasks;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.administrador.teste.Gui.AdapterListView.AdapterListCategoria;
import com.example.administrador.teste.Gui.Dialogs.DialogLoading;
import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.Categoria;

import java.util.ArrayList;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class InsertCategoryTask extends AsyncTask<String, Void, ArrayList<Categoria>> {
    private String message;

    private AdapterListCategoria adapterListCategoria;
    private AlertDialog dialogLoading;
    private AlertDialog dialogInsert;

    public InsertCategoryTask(AdapterListCategoria adapterListCategoria, AlertDialog dialogLoading, AlertDialog dialogInsert) {
        this.adapterListCategoria = adapterListCategoria;
        this.dialogLoading = dialogLoading;
        this.dialogInsert = dialogInsert;
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

        dialogLoading.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Categoria> arrayList) {
        super.onPostExecute(arrayList);

        dialogLoading.dismiss();
        if (message.isEmpty()) {
            adapterListCategoria.setArrayList(arrayList);
            adapterListCategoria.notifyDataSetChanged();
            dialogInsert.dismiss();
        } else {
            Toast.makeText(dialogInsert.getContext(), message, Toast.LENGTH_LONG).show();
        }

    }
}
