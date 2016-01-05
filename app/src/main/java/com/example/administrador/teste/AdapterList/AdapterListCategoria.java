package com.example.administrador.teste.AdapterList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.R;

import java.util.ArrayList;

/**
 * Created by Administrador on 03/01/2016.
 */
public class AdapterListCategoria extends BaseAdapter {
    private ArrayList<Categoria> arrayList;
    private Activity activity;

    public AdapterListCategoria(Activity activity, ArrayList<Categoria> arrayList) {
        super();
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.listviewcategoria_row, null);

            Categoria categoria = arrayList.get(position);

            ((TextView) convertView.findViewById(R.id.descricaoCategoriaTextView)).setText(categoria.getDescricao());
            ((TextView) convertView.findViewById(R.id.saldoCategoriaTextView)).setText(String.valueOf(categoria.getSaldo()));
        }
        return convertView;
    }
}
