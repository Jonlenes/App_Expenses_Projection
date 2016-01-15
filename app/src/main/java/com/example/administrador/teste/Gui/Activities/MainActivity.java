package com.example.administrador.teste.Gui.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrador.teste.AsyncTasks.OperationCategoryTask;
import com.example.administrador.teste.AsyncTasks.SearchCategoryTask;
import com.example.administrador.teste.Gui.AdapterListView.AdapterListCategoria;
import com.example.administrador.teste.Gui.Dialogs.DialogMntCategory;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.EnumOperation;
import com.example.administrador.teste.R;

public class MainActivity extends Activity {

    private ListView listViewCategoria;
    private FloatingActionButton fabInserir;
    private AdapterListCategoria adapterListCategoria;
    View.OnClickListener onClickListenerInserir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogMntCategory dialogInsertCategory = new DialogMntCategory(MainActivity.this, null);
            dialogInsertCategory.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    new SearchCategoryTask(MainActivity.this, listViewCategoria, adapterListCategoria).execute();
                }
            });
            dialogInsertCategory.show();
        }
    };
    private AdapterView.OnItemClickListener onClickLista = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intentItem = new Intent(getBaseContext(), ItemActivity.class);
            intentItem.putExtra("Id", ((Categoria) parent.getItemAtPosition(position)).getId());
            startActivity(intentItem);
        }
    };
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_category:
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
    private AdapterView.OnItemLongClickListener onItemLongClickListenerCategoria = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder
                    .setTitle("Opções")
                    .setItems(R.array.dialog_options_itens,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            DialogMntCategory dialogInsertCategory = new DialogMntCategory(MainActivity.this, (Categoria) parent.getItemAtPosition(position));
                                            dialogInsertCategory.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                @Override
                                                public void onDismiss(DialogInterface dialog) {
                                                    new SearchCategoryTask(MainActivity.this, listViewCategoria, adapterListCategoria).execute();
                                                }
                                            });
                                            dialogInsertCategory.show();
                                            break;

                                        case 1:
                                            AlertDialog dialogDelete = new AlertDialog.Builder(MainActivity.this)
                                                    .setMessage("Tem certeza que deseja excluir?")
                                                    .setNegativeButton("Não", null)
                                                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            new OperationCategoryTask(MainActivity.this, null, EnumOperation.delete).execute((Categoria) parent.getItemAtPosition(position));
                                                        }
                                                    })
                                                    .create();
                                            dialogDelete
                                                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                        @Override
                                                        public void onDismiss(DialogInterface dialog) {
                                                            new SearchCategoryTask(MainActivity.this, listViewCategoria, adapterListCategoria).execute();
                                                        }
                                                    });
                                            dialogDelete
                                                    .show();
                                            break;

                                    }
                                }
                            })
                    .setNegativeButton("Cancelar", null)
                    .create().show();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewCategoria = ((ListView) findViewById(R.id.categoriasListView));
        fabInserir = (FloatingActionButton) findViewById(R.id.fabInserir);

        listViewCategoria.setOnItemClickListener(onClickLista);
        listViewCategoria.setOnItemLongClickListener(onItemLongClickListenerCategoria);
        fabInserir.setOnClickListener(onClickListenerInserir);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new SearchCategoryTask(this, listViewCategoria, adapterListCategoria).execute();
    }


}
