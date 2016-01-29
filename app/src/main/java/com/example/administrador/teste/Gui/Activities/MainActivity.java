package com.example.administrador.teste.Gui.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrador.teste.AsyncTasks.OperationCategoryTask;
import com.example.administrador.teste.AsyncTasks.SearchCategoryTask;
import com.example.administrador.teste.Gui.AdapterListView.AdapterListCategoria;
import com.example.administrador.teste.Gui.Dialogs.DialogMntCategory;
import com.example.administrador.teste.Modelo.Bo.BankAccountBo;
import com.example.administrador.teste.Modelo.Bo.UserBo;
import com.example.administrador.teste.Modelo.Vo.BankAccount;
import com.example.administrador.teste.Modelo.Vo.Categoria;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumOperation;
import com.example.administrador.teste.R;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listViewCategoria;
    private FloatingActionButton fabInserir;
    private AdapterListCategoria adapterListCategoria;
    private NavigationView navigationView;
    private List<BankAccount> listBankAccount;
    private boolean updateItensMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewCategoria = ((ListView) findViewById(R.id.categoriasListView));
        fabInserir = (FloatingActionButton) findViewById(R.id.fabInserir);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        listViewCategoria.setOnItemClickListener(onClickLista);
        listViewCategoria.setOnItemLongClickListener(onItemLongClickListenerCategoria);
        fabInserir.setOnClickListener(onClickListenerInserir);
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        new SearchBankAccountTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (updateItensMenu) {
            new SearchBankAccountTask().execute();
        } else {
            new SearchCategoryTask(this, listViewCategoria, adapterListCategoria).execute();
        }
        updateItensMenu = false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_category:
                new SearchCategoryTask(MainActivity.this, listViewCategoria, adapterListCategoria).execute();
                break;
            case R.id.action_exit:
                new LogOutTask().execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private  View.OnClickListener onClickListenerInserir = new View.OnClickListener() {
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
                                                    .setMessage("Todos os itens da categoria serão excluidos. Tem certeza que deseja excluir?")
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

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            if (item.getItemId() == 0) {
                Toast.makeText(MainActivity.this, getBankAccount(item.getTitle().toString()).getName(), Toast.LENGTH_LONG).show();
            } else {
                if (item.getItemId() == R.id.nav_add_bank_account) {
                    Intent intent = new Intent(MainActivity.this, MntBankAccountActivity.class);
                    intent.putExtra("isOnlyAdd", true);
                    startActivity(intent);
                    updateItensMenu = true;
                }
            }
            return true;
        }
    };

    public class LogOutTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        public LogOutTask() {
            progressDialog = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Saindo...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            new UserBo().logout();
            return null;
        }

        @Override
        protected void onPostExecute(Void isConnected) {
            super.onPostExecute(isConnected);
            progressDialog.dismiss();

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    public class SearchBankAccountTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listBankAccount = new BankAccountBo().getBankAccountUser();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            navigationView.getMenu().clear();
            SubMenu subMenu = navigationView.getMenu().addSubMenu("Contas bancárias");

            for (BankAccount bankAccount : listBankAccount) {
                MenuItem menuItem = subMenu.add(bankAccount.getName());
                menuItem.setIcon(R.drawable.ic_menu_send);
            }
        }
    }

    public BankAccount getBankAccount(String name) {
        for (BankAccount bankAccount : listBankAccount) {
            if (bankAccount.getName().equals(name))
                return bankAccount;
        }
        return null;
    }

}
