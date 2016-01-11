package com.example.administrador.teste.Gui.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;

import com.example.administrador.teste.R;

/**
 * Created by Jonlenes on 11/01/2016.
 */
public class DialogInsert extends AlertDialog {

    protected DialogInsert(Context context) {
        super(context);
        setContentView(R.layout.activity_add_item);

        setCancelable(false);
        setTitle("Teste");

        ((EditText) findViewById(R.id.descricaoItemEditText)).setText("Acho que deu mano....");

    }
}
