package com.example.administrador.teste.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.administrador.teste.Modelo.Bo.ItemBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.EnumOperation;
import com.example.administrador.teste.Modelo.Vo.Item;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class OperationItemTask extends AsyncTask<Item, Void, Void> {

    private ProgressDialog progressDialog;
    private String message;
    private Activity activity;
    private EnumOperation operation;

    public OperationItemTask(Activity activity, EnumOperation operation) {
        this.activity = activity;
        this.operation = operation;

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Inserindo item...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected Void doInBackground(Item... params) {

        try {
            ItemBo itemBo = new ItemBo();
            switch (operation) {
                case insert:
                    itemBo.insert(params[0]);
                    break;

                case update:
                    itemBo.altera(params[0]);
                    break;

                case delete:
                    itemBo.excluir(params[0]);
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
        if (message.isEmpty()) {
            activity.finish();
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        }
    }
}
