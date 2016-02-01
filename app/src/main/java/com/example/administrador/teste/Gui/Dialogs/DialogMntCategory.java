package com.example.administrador.teste.Gui.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.teste.AsyncTasks.OperationCategoryTask;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumOperationBd;
import com.example.administrador.teste.R;

/**
 * Created by Jonlenes on 11/01/2016.
 */
public class DialogMntCategory extends AlertDialog {

    public EditText descricaoEditText;
    private Categoria categoria;

    public DialogMntCategory(Context context, Categoria categoria) {
        super(context);

        this.categoria = categoria;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mnt_category);
        setCancelable(false);

        descricaoEditText = ((EditText) findViewById(R.id.descricaoInsertCategoriaEditText));
        findViewById(R.id.cancelInsertCategoriaButton).setOnClickListener(clickListenerCancel);
        findViewById(R.id.okInsertCategoriaButton).setOnClickListener(clickListenerOk);

        if (categoria != null) {
            descricaoEditText.setText(categoria.getDescricao());
        }

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        /*descricaoEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DialogMntCategory.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                Toast.makeText(DialogMntCategory.this.getContext(), "Forcei mostrar o teclado...", Toast.LENGTH_LONG).show();
            }
        });*/
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

        }
    };
}
