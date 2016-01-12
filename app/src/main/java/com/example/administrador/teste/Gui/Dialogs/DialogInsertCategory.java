package com.example.administrador.teste.Gui.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.teste.AsyncTasks.InsertCategoryTask;
import com.example.administrador.teste.R;

/**
 * Created by Administrador on 08/01/2016.
 */
public class DialogInsertCategory extends AlertDialog.Builder {
    private Context context;
    private String descricao;
    private EditText editText;


    public DialogInsertCategory(final Context context, final String descricao) {
        super(context);

        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, DialogInsertCategory.this.descricao, Toast.LENGTH_LONG).show();
                new InsertCategoryTask(context).execute("Teste");
                dialog.dismiss();
            }
        };

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DialogInsertCategory.this.descricao = String.valueOf(s);
                Toast.makeText(context, DialogInsertCategory.this.descricao, Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        this
                .setPositiveButton(R.string.add_categoria, clickListener)
                .setNegativeButton(R.string.cancel_operation, null);
        AlertDialog alertDialog = create();


        setView(alertDialog.getLayoutInflater().inflate(R.layout.dialog_insert_category, null));

        editText = (EditText) alertDialog.findViewById(R.id.descricaoInsertCategoriaEditText);
        editText.addTextChangedListener(textWatcher);

        show();

    }
        /*final EditText editTextDescricao = (EditText) findViewById(R.id.descricaoInsertCategoriaEditText);
        Button buttonOk = (Button) findViewById(R.id.inserirCategoryButton);
        final AlertDialog dialog = builder.create();*/

        /*buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextDescricao.getText().length() > 0) {
                    new InsertCategoryTask(adapterListCategoria,
                            dialog);
                } else {
                    editTextDescricao.setError("Preencha a descrição da categoria");
                }
            }
        });*/

    //dialog.show();


        /*final EditText editTextDescricao = new EditText(this);
        final Button buttonOk = new Button(this);
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);

        builder
                .setTitle(R.string.titulo_nova_categoria)
                .setMessage(R.string.message_new_category)
                .setView(editTextDescricao)
                .setView(buttonOk)
                .setNegativeButton(R.string.cancel_operation, null);

        final AlertDialog dialog = builder.create();

        buttonOk.setText(R.string.add_categoria);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextDescricao.getText().length() > 0) {
                    new InsertCategoryTask(adapterListCategoria,
                            new DialogLoading(MainActivity.this).create(),
                            dialog);
                } else {
                    editTextDescricao.setError("Preencha a descrição da categoria");
                }
            }
        });

        dialog.show();*/
}
