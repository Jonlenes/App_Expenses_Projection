package com.example.administrador.teste.AsyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.administrador.teste.Gui.AdapterListView.AdapterListCategoria;
import com.example.administrador.teste.Gui.AdapterListView.AdapterListItem;
import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.ItemBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class InsertItemTask extends AsyncTask<Item, Void, Void> {

    private ProgressDialog progressDialog;
    private String message;
    private Activity activity;

    public InsertItemTask(Activity activity) {
        this.activity = activity;

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Inserindo item...");
    }

    @Override
    protected Void doInBackground(Item... params) {

        try {
            ItemBo itemBo = new ItemBo();
            itemBo.insert(params[0]);
            return null;
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
        if (message.isEmpty()) {
            activity.finish();
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        }
    }
}
