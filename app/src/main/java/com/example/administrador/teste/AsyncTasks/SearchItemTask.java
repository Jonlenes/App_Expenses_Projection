package com.example.administrador.teste.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.administrador.teste.Gui.AdapterListView.AdapterListCategoria;
import com.example.administrador.teste.Gui.AdapterListView.AdapterListItem;
import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.DbHelper;
import com.example.administrador.teste.Modelo.Bo.ItemBo;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Item;

import java.util.ArrayList;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class SearchItemTask extends AsyncTask<Long, Void, ArrayList<Item>> {
    private ProgressDialog progressDialog;

    private Activity activity;
    private ListView listView;
    private AdapterListItem adapterListItem;

    public SearchItemTask(Activity activity, ListView listView, AdapterListItem adapterListItem) {
        this.activity = activity;
        this.listView = listView;
        this.adapterListItem = adapterListItem;

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Buscando itens...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected ArrayList<Item> doInBackground(Long... params) {
        return new ItemBo().getTodosPorCategoria(params[0]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Item> arrayList) {
        super.onPostExecute(arrayList);

        if (adapterListItem == null) {
            adapterListItem = new AdapterListItem(activity, arrayList);
            listView.setAdapter(adapterListItem);
        } else {
            adapterListItem.setArrayList(arrayList);
            adapterListItem.notifyDataSetChanged();
        }

        progressDialog.dismiss();
    }

}
