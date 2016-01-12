package com.example.administrador.teste.Gui.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrador.teste.AsyncTasks.InsertCategoryTask;
import com.example.administrador.teste.R;

/**
 * Created by Jonlenes on 11/01/2016.
 */
public class DialogInsert extends AlertDialog {
    private EditText descricaoEditText;


    public DialogInsert(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_insert_category);
        setCancelable(false);

        descricaoEditText = ((EditText) findViewById(R.id.descricaoInsertCategoriaEditText));
        findViewById(R.id.cancelInsertCategoriaButton).setOnClickListener(clickListenerCancel);
        findViewById(R.id.okInsertCategoriaButton).setOnClickListener(clickListenerOk);
    }

    private View.OnClickListener clickListenerCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
    private View.OnClickListener clickListenerOk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (descricaoEditText.getText().length() > 0) {
                new InsertCategoryTask(getContext()).execute(descricaoEditText.getText().toString());
            } else {
                descricaoEditText.setError("Preencha a descrição.");
            }
        }
    };
}
