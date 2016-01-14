package com.example.administrador.teste.Gui.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.administrador.teste.AsyncTasks.OperationCategoryTask;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.EnumOperation;
import com.example.administrador.teste.R;

/**
 * Created by Jonlenes on 11/01/2016.
 */
public class DialogInsertCategory extends AlertDialog {

    private EditText descricaoEditText;
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
                new OperationCategoryTask(getContext(), DialogInsertCategory.this, EnumOperation.insert).execute(new Categoria(descricaoEditText.getText().toString()));
            } else {
                descricaoEditText.setError("Preencha a descrição.");
            }
        }
    };

    public DialogInsertCategory(Context context) {
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
}
