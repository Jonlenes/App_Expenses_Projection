package com.example.administrador.teste.Gui.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrador.teste.AsyncTasks.OperationItemTask;
import com.example.administrador.teste.AsyncTasks.SearchItemTask;
import com.example.administrador.teste.Gui.AdapterListView.AdapterListItem;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumOperationBd;
import com.example.administrador.teste.Modelo.Vo.Item;
import com.example.administrador.teste.R;

public class ItemActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterListItem adapterListItem;
    private Long idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        idCategoria = getIntent().getLongExtra("Id", -1);
        listView = ((ListView) findViewById(R.id.itensListView));

        listView.setOnItemLongClickListener(onItemLongClickListenerItem);
        findViewById(R.id.fabInserirItem).setOnClickListener(onClickListenerAddItem);

        if (idCategoria != -1) {
            new SearchItemTask(this, listView, adapterListItem).execute(idCategoria);
        } else {
            Toast.makeText(this, "Erro na categoria.", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        new SearchItemTask(this, listView, adapterListItem).execute(idCategoria);
    }

    private View.OnClickListener onClickListenerAddItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentAddItem = new Intent(ItemActivity.this, MntItemActivity.class);
            intentAddItem.putExtra("idCategoria", ItemActivity.this.idCategoria);
            ItemActivity.this.startActivity(intentAddItem);
        }
    };

    private AdapterView.OnItemLongClickListener onItemLongClickListenerItem = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);

            builder
                    .setTitle("Opções")
                    .setItems(R.array.dialog_options_itens,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Item item = (Item) parent.getItemAtPosition(position);
                                            Intent intent = new Intent(ItemActivity.this, MntItemActivity.class);

                                            intent.putExtra("id", item.getId());
                                            intent.putExtra("idCategoria", item.getIdCategoria());
                                            intent.putExtra("saldo", item.getSaldo());
                                            intent.putExtra("descrisao", item.getDescricao());
                                            intent.putExtra("valor", item.getValor());

                                            startActivity(intent);

                                            break;

                                        case 1:
                                            AlertDialog dialogDelete = new AlertDialog.Builder(ItemActivity.this)
                                                    .setMessage("Tem certeza que deseja excluir?")
                                                    .setNegativeButton("Não", null)
                                                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            new OperationItemTask(ItemActivity.this, EnumOperationBd.delete).execute((Item) parent.getItemAtPosition(position));
                                                        }
                                                    })
                                                    .create();
                                            dialogDelete
                                                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                        @Override
                                                        public void onDismiss(DialogInterface dialog) {
                                                            new SearchItemTask(ItemActivity.this, listView, adapterListItem).execute(idCategoria);
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

}
