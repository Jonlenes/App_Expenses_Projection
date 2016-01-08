package com.example.administrador.teste.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by Jonlenes on 06/01/2016.
 */
public class Teste extends AsyncTask<Void, Void, Void> {
    Activity activity;
    private ProgressDialog progressDialog;

    public Teste(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Inserindo...");
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    }

}
