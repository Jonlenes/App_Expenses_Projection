package com.example.administrador.teste.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.administrador.teste.Gui.AdapterListView.AdapterListCategoria;
import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.DbHelper;
import com.example.administrador.teste.Modelo.Vo.Categoria;

import java.util.ArrayList;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class InitialOperationDbTask extends AsyncTask<Void, Void, ArrayList<Categoria>> {
    private ProgressDialog progressDialog;

    private Activity activity;
    private ListView listView;
    private AdapterListCategoria adapterListCategoria;

    public InitialOperationDbTask(Activity activity, ListView listView, AdapterListCategoria adapterListCategoria) {
        this.activity = activity;
        this.listView = listView;
        this.adapterListCategoria = adapterListCategoria;

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Buscando...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected ArrayList<Categoria> doInBackground(Void... params) {
        DbHelper.newInstance(activity);
        return new CategoriaBo().getTodos();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Categoria> arrayList) {
        super.onPostExecute(arrayList);

        adapterListCategoria = new AdapterListCategoria(activity, arrayList);
        listView.setAdapter(adapterListCategoria);

        progressDialog.dismiss();
    }

}
