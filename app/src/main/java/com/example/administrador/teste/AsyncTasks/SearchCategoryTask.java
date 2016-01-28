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
public class SearchCategoryTask extends AsyncTask<Void, Void, ArrayList<Categoria>> {
    private ProgressDialog progressDialog;

    private Activity activity;
    private ListView listView;
    private AdapterListCategoria adapterListCategoria;

    public SearchCategoryTask(Activity activity, ListView listView, AdapterListCategoria adapterListCategoria) {
        this.activity = activity;
        this.listView = listView;
        this.adapterListCategoria = adapterListCategoria;

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Buscando categorias...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected ArrayList<Categoria> doInBackground(Void... params) {
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

        if (adapterListCategoria == null) {
            adapterListCategoria = new AdapterListCategoria(activity, arrayList);
            listView.setAdapter(adapterListCategoria);
        } else {
            adapterListCategoria.setArrayList(arrayList);
            adapterListCategoria.notifyDataSetChanged();
        }

        progressDialog.dismiss();
    }

}
