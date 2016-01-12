package com.example.administrador.teste.Gui.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

import com.example.administrador.teste.R;

/**
 * Created by Administrador on 09/01/2016.
 */
public class TesteAAA extends AlertDialog {
    private EditText editText;

    public TesteAAA(Context context) {
        super(context);
        setContentView(R.layout.dialog_insert_category);

        editText = (EditText) findViewById(R.id.descricaoInsertCategoriaEditText);
        editText.setText("Testando....");

        show();
    }
}
